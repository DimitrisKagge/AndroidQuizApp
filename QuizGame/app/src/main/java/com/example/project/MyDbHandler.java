package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Names.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mynames";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_CATEGORY = "category";

    public MyDbHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_NAME + " TEXT PRIMARY KEY," +
                COLUMN_SCORE + " INTEGER," +
                COLUMN_CATEGORY + " TEXT" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //method to add player in the database
    public boolean addPlayer(String name, int score, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SCORE, score);
        cv.put(COLUMN_CATEGORY, category);

        // Check if the name already exists in the database
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME}, COLUMN_NAME + "=?", new String[]{name}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false; // Name already exists
        }

        // If the name doesn't exist, proceed with insertion
        db.insert(TABLE_NAME, null, cv);
        cursor.close();
        db.close();
        return true; // Insertion successful
    }


    // Method to get top 10 high scores for a specific category.
    public List<String> getTop10HighscoresByCategory(String category) {
        List<String> highscores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ? ORDER BY " + COLUMN_SCORE + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(query, new String[]{category});

        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
        int scoreIndex = cursor.getColumnIndex(COLUMN_SCORE);

        if (nameIndex >= 0 && scoreIndex >= 0) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(nameIndex);
                    int score = cursor.getInt(scoreIndex);
                    highscores.add(name + ": " + score);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return highscores;
    }
}
