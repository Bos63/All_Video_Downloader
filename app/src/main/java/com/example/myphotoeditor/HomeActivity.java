package com.example.myphotoeditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myphotoeditor.databinding.ActivityHomeBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Uri selectedImageUri;

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ImageMemoryStore.downloadedBitmap = null;
                    binding.ivPreview.setImageURI(uri);
                    setStatus("Galeriden foto seçildi.");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPickGallery.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        binding.btnDownloadUrl.setOnClickListener(v -> showUrlDialog());
        binding.btnOpenEditor.setOnClickListener(v -> openEditor("home"));
        binding.btnTemplates.setOnClickListener(v -> openEditor("templates"));
        binding.btnLogos.setOnClickListener(v -> openEditor("stickers"));
    }

    private void showUrlDialog() {
        EditText input = new EditText(this);
        input.setHint("https://.../image.jpg");
        new AlertDialog.Builder(this)
                .setTitle("URL'den Foto İndir")
                .setView(input)
                .setPositiveButton("İndir", (dialog, which) -> {
                    String url = input.getText().toString().trim();
                    if (TextUtils.isEmpty(url)) {
                        setStatus("URL boş olamaz.");
                        return;
                    }
                    if (!isInternetAvailable()) {
                        setStatus("İnternet bağlantısı yok.");
                        return;
                    }
                    downloadImage(url);
                })
                .setNegativeButton("Vazgeç", null)
                .show();
    }

    private void downloadImage(String imageUrl) {
        setLoading(true);
        setStatus("URL'den indiriliyor...");

        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(imageUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.connect();

                int code = connection.getResponseCode();
                if (code != HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        setLoading(false);
                        setStatus("İndirme başarısız. HTTP " + code);
                    });
                    return;
                }

                String contentType = connection.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    runOnUiThread(() -> {
                        setLoading(false);
                        setStatus("Geçersiz görsel URL'si.");
                    });
                    return;
                }

                InputStream is = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap == null) {
                    runOnUiThread(() -> {
                        setLoading(false);
                        setStatus("Görsel çözümlenemedi.");
                    });
                    return;
                }

                ImageMemoryStore.downloadedBitmap = bitmap;
                selectedImageUri = null;

                runOnUiThread(() -> {
                    setLoading(false);
                    binding.ivPreview.setImageBitmap(bitmap);
                    setStatus("URL görseli hazır. Editöre geçebilirsiniz.");
                });

            } catch (IOException e) {
                runOnUiThread(() -> {
                    setLoading(false);
                    setStatus("İndirme hatası: " + e.getMessage());
                });
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    private void openEditor(String focus) {
        if (selectedImageUri == null && ImageMemoryStore.downloadedBitmap == null) {
            setStatus("Önce galeriden seçin veya URL'den foto indirin.");
            return;
        }
        Intent i = new Intent(this, EditorActivity.class);
        if (selectedImageUri != null) {
            i.putExtra("image_uri", selectedImageUri.toString());
        }
        i.putExtra("focus_tab", focus);
        startActivity(i);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        Network network = cm.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
    }

    private void setLoading(boolean loading) {
        binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.btnDownloadUrl.setEnabled(!loading);
        binding.btnPickGallery.setEnabled(!loading);
    }

    private void setStatus(String msg) {
        binding.tvStatus.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
