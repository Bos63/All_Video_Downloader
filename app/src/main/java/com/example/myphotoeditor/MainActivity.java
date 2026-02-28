package com.example.myphotoeditor;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myphotoeditor.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1001;

    private ActivityMainBinding binding;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private String pendingUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDownload.setOnClickListener(v -> {
            String url = binding.etImageUrl.getText().toString().trim();
            if (TextUtils.isEmpty(url)) {
                showStatus("Lütfen bir görsel URL girin.");
                return;
            }
            if (!isNetworkAvailable()) {
                showStatus("İnternet bağlantısı yok. Lütfen bağlantınızı kontrol edin.");
                return;
            }
            startDownloadFlow(url);
        });
    }

    private void startDownloadFlow(String url) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            pendingUrl = url;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE
            );
            return;
        }
        downloadAndSaveImage(url);
    }

    private void downloadAndSaveImage(String imageUrl) {
        setLoading(true);
        showStatus("İndiriliyor...");

        executorService.execute(() -> {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(imageUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    String message = "Sunucu hatası: HTTP " + responseCode;
                    runOnUiThreadSafe(() -> {
                        setLoading(false);
                        showStatus(message);
                    });
                    return;
                }

                String contentType = connection.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    runOnUiThreadSafe(() -> {
                        setLoading(false);
                        showStatus("URL bir görsel döndürmüyor. Content-Type: " + contentType);
                    });
                    return;
                }

                inputStream = connection.getInputStream();
                byte[] imageBytes = readAllBytes(inputStream);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                if (bitmap == null) {
                    runOnUiThreadSafe(() -> {
                        setLoading(false);
                        showStatus("Görsel çözümlenemedi. Dosya bozuk olabilir.");
                    });
                    return;
                }

                String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
                Uri savedUri = saveImageToStorage(imageBytes, fileName);

                runOnUiThreadSafe(() -> {
                    setLoading(false);
                    if (savedUri != null) {
                        binding.ivPreview.setImageBitmap(bitmap);
                        showStatus("Kaydedildi: " + savedUri);
                    } else {
                        showStatus("Kayıt başarısız: Medya deposuna yazılamadı.");
                    }
                });

            } catch (IOException e) {
                runOnUiThreadSafe(() -> {
                    setLoading(false);
                    showStatus("İndirme/Kayıt hatası: " + e.getMessage());
                });
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private Uri saveImageToStorage(byte[] imageBytes, String fileName) {
        ContentResolver resolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + "/MyPhotoEditor");
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
            collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Uri itemUri = resolver.insert(collection, contentValues);
        if (itemUri == null) {
            return null;
        }

        try (OutputStream outputStream = resolver.openOutputStream(itemUri)) {
            if (outputStream == null) {
                resolver.delete(itemUri, null, null);
                return null;
            }
            outputStream.write(imageBytes);
            outputStream.flush();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues finalizeValues = new ContentValues();
                finalizeValues.put(MediaStore.MediaColumns.IS_PENDING, 0);
                resolver.update(itemUri, finalizeValues, null, null);
            }
            return itemUri;

        } catch (IOException e) {
            resolver.delete(itemUri, null, null);
            return null;
        }
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities caps = cm.getNetworkCapabilities(network);
            return caps != null && (
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            );
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
    }

    private void setLoading(boolean isLoading) {
        runOnUiThreadSafe(() -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.btnDownload.setEnabled(!isLoading);
        });
    }

    private void showStatus(String message) {
        binding.tvStatus.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void runOnUiThreadSafe(Runnable action) {
        mainHandler.post(action);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (granted) {
                if (!TextUtils.isEmpty(pendingUrl)) {
                    downloadAndSaveImage(pendingUrl);
                    pendingUrl = null;
                }
            } else {
                showStatus("İzin reddedildi. Android 9 ve altı için depolama izni gerekli.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}
