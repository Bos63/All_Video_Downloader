package com.example.myphotoeditor;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myphotoeditor.databinding.ActivityEditorBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    private ActivityEditorBinding binding;

    private final ActivityResultLauncher<String> storagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) saveOutput();
                else show("İzin verilmedi. Android 9 ve altı için depolama izni gerekli.");
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadInputImage();
        setupTemplates();
        setupStickers();
        setupTextTools();

        binding.btnAddText.setOnClickListener(v -> showTextInputDialog());
        binding.btnSave.setOnClickListener(v -> checkPermissionAndSave());
    }

    private void loadInputImage() {
        try {
            String uriText = getIntent().getStringExtra("image_uri");
            if (uriText != null) {
                Uri uri = Uri.parse(uriText);
                InputStream is = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (is != null) is.close();
                binding.editorView.setBaseBitmap(bitmap);
                return;
            }
            if (ImageMemoryStore.downloadedBitmap != null) {
                binding.editorView.setBaseBitmap(ImageMemoryStore.downloadedBitmap);
            }
        } catch (IOException e) {
            show("Görsel yükleme hatası: " + e.getMessage());
        }
    }

    private void setupTemplates() {
        List<TemplateModel> templates = TemplateRepository.getTemplates();
        TemplateAdapter adapter = new TemplateAdapter(templates, model -> {
            binding.editorView.setTemplate(model);
            show("Şablon: " + model.name);
        });
        binding.rvTemplates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvTemplates.setAdapter(adapter);
    }

    private void setupStickers() {
        List<StickerModel> stickers = StickerRepository.getAll();
        StickerAdapter adapter = new StickerAdapter(stickers, model -> {
            binding.editorView.addSticker(model);
            show("Sticker eklendi: " + model.name);
        });

        binding.rvStickers.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvStickers.setAdapter(adapter);

        List<String> categories = Arrays.asList("Hepsi", "Sosyal", "Geometrik", "Rozet", "Sembol");
        binding.spinnerCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));
        binding.spinnerCategory.setSelection(0);
        binding.spinnerCategory.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                adapter.setCategory(categories.get(position));
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    private void setupTextTools() {
        binding.seekTextSize.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                binding.editorView.setSelectedTextSize(Math.max(22, progress));
            }

            @Override public void onStartTrackingTouch(android.widget.SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(android.widget.SeekBar seekBar) {}
        });

        setColorClick(binding.color1, 0xFFFFFFFF);
        setColorClick(binding.color2, 0xFFFFEB3B);
        setColorClick(binding.color3, 0xFFF44336);
        setColorClick(binding.color4, 0xFF4CAF50);
        setColorClick(binding.color5, 0xFF03A9F4);
        setColorClick(binding.color6, 0xFF9C27B0);
        setColorClick(binding.color7, 0xFFFF9800);
        setColorClick(binding.color8, 0xFF000000);
    }

    private void setColorClick(View v, @ColorInt int color) {
        v.setOnClickListener(view -> binding.editorView.setSelectedTextColor(color));
    }

    private void showTextInputDialog() {
        EditText input = new EditText(this);
        input.setHint("Metin girin");
        new AlertDialog.Builder(this)
                .setTitle("Metin Ekle")
                .setView(input)
                .setPositiveButton("Ekle", (dialog, which) -> {
                    String txt = input.getText().toString().trim();
                    if (txt.isEmpty()) {
                        show("Metin boş olamaz.");
                    } else {
                        binding.editorView.addText(txt);
                    }
                })
                .setNegativeButton("İptal", null)
                .show();
    }

    private void checkPermissionAndSave() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        saveOutput();
    }

    private void saveOutput() {
        Bitmap output = binding.editorView.exportBitmap();
        String name = "EDIT_" + System.currentTimeMillis() + ".png";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyPhotoEditor");
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uri = getContentResolver().insert(collection, values);
        if (uri == null) {
            show("Kaydetme başarısız: Uri oluşturulamadı.");
            return;
        }

        try (OutputStream os = getContentResolver().openOutputStream(uri)) {
            if (os == null) {
                show("Kaydetme başarısız: Çıktı akışı açılamadı.");
                return;
            }
            output.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues done = new ContentValues();
                done.put(MediaStore.Images.Media.IS_PENDING, 0);
                getContentResolver().update(uri, done, null, null);
            }
            binding.tvEditorStatus.setText("Kaydedildi: " + uri);
            show("Kaydedildi: " + uri);
        } catch (IOException e) {
            getContentResolver().delete(uri, null, null);
            show("Kaydetme hatası: " + e.getMessage());
        }
    }

    private void show(String msg) {
        binding.tvEditorStatus.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
