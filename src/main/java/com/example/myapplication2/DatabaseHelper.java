package com.example.myapplication2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nama & Versi Database
    private static final String DATABASE_NAME = "JadwalMahasiswa.db";
    private static final int DATABASE_VERSION = 1;

    // Struktur Tabel
    public static final String TABLE_NAME = "tb_jadwal";
    public static final String COL_ID = "id";
    public static final String COL_MATKUL = "nama_matkul";
    public static final String COL_WAKTU = "waktu_kuliah";

    // Query untuk membuat tabel
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_MATKUL + " TEXT, " +
            COL_WAKTU + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Eksekusi pembuatan tabel saat database pertama kali dibuat
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Jika ada update versi database, hapus yang lama dan buat baru
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
