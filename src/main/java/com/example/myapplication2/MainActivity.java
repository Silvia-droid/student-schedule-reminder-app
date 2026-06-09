package com.example.myapplication2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button btnMahasiswa, btnMatkul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMahasiswa = findViewById(R.id.button);
        btnMatkul = findViewById(R.id.button2);

        // Request Notification Permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        // Ke halaman Jadwal Mahasiswa
        btnMahasiswa.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JadwalMahasiswaActivity.class);
            startActivity(intent);
        });

        // Ke halaman Jadwal Mata Kuliah
        btnMatkul.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JadwalMatkulActivity.class);
            startActivity(intent);
        });
    }
}