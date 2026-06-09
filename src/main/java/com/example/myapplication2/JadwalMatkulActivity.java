package com.example.myapplication2;

import android.app.*;
import android.content.*;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class JadwalMatkulActivity extends AppCompatActivity {

    EditText etInput;
    Button btnTime, btnAdd, btnBack;

    Calendar selectedCalendar = Calendar.getInstance();
    boolean isDateTimeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwalmatkul);

        etInput = findViewById(R.id.editTextText);
        btnTime = findViewById(R.id.btnTime);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.button4);

        // PILIH TANGGAL & WAKTU
        btnTime.setOnClickListener(v -> {
            Calendar currentCal = Calendar.getInstance();

            // Show Date Picker first
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedCalendar.set(Calendar.YEAR, year);
                selectedCalendar.set(Calendar.MONTH, month);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Then show Time Picker
                new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedCalendar.set(Calendar.MINUTE, minute);
                    selectedCalendar.set(Calendar.SECOND, 0);
                    selectedCalendar.set(Calendar.MILLISECOND, 0);

                    isDateTimeSelected = true;
                    String dateTimeStr = String.format("%02d/%02d/%d %02d:%02d",
                            dayOfMonth, month + 1, year, hourOfDay, minute);
                    btnTime.setText(dateTimeStr);
                }, currentCal.get(Calendar.HOUR_OF_DAY), currentCal.get(Calendar.MINUTE), true).show();

            }, currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), currentCal.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnAdd.setOnClickListener(v -> {
            String text = etInput.getText().toString();

            if (text.isEmpty() || !isDateTimeSelected) {
                Toast.makeText(this, "Isi data dan pilih waktu!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedCalendar.before(Calendar.getInstance())) {
                Toast.makeText(this, "Waktu sudah lewat!", Toast.LENGTH_SHORT).show();
                return;
            }

            setAlarm(text, selectedCalendar.getTimeInMillis());
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void setAlarm(String text, long timeInMillis) {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Check for SCHEDULE_EXACT_ALARM permission on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!am.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                Toast.makeText(this, "Please allow exact alarms for this app", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("msg", text);

        PendingIntent pi = PendingIntent.getBroadcast(
                this,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        am.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
        Toast.makeText(this, "Alarm diset!", Toast.LENGTH_SHORT).show();
    }
}