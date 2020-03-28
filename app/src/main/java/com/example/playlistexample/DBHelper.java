package com.example.playlistexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "music.db";
    final static String TABLE_NAME = "playlist";
    final static String CREATE = "CREATE TABLE "+TABLE_NAME+ "( `_id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `duration` INTEGER NOT NULL DEFAULT 0, `year` INTEGER NOT NULL )";
    // при изменении структуры БД меняется и версия
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // выполняется, если базы данных нет
        db.execSQL(CREATE);
        db.execSQL("INSERT INTO playlist VALUES (1, 'Storm', 'U2', 300, 1998 )");
        db.execSQL("INSERT INTO playlist VALUES (2, 'Hi', 'U3', 305, 2009 )");
        db.execSQL("INSERT INTO playlist VALUES (3, 'Yeah', 'A-ha', 100, 2000 )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // выполняется, если версия базы данных отличается
        db.execSQL("DROP DATABASE "+DB_NAME);
        this.onCreate(db);
    }
}
