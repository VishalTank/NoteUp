package com.tank.vishal.noteup;

/**
 * Created by Vishal on 02-02-2018.
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE = "database2.db";
    private static String TABLE = "NoteTable";
    private static String TITLE = "title";
    private static String NAME = "name";
    private static String TIME = "time";
    private static String REMINDER_ID = "reminder_id";
    private static String BOOKMARK = "bookm";
    private static String REMINDER_TIME = "reminder_time";


    DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + TABLE + "( "
                + TITLE + " TEXT, "
                + NAME + " TEXT, "
                + TIME + " LONG PRIMARY KEY, "
                + REMINDER_TIME + " LONG, "
                + REMINDER_ID + " INTEGER, "
                + BOOKMARK + " INTEGER DEFAULT 1 )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + " ;");
    }

    void insertNote(String title, String name, Long noti_time,int reminder_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, title);
        contentValues.put(NAME, name);
        contentValues.put(TIME, System.currentTimeMillis());
        contentValues.put(REMINDER_TIME, noti_time);
        contentValues.put(REMINDER_ID,reminder_id);
        db.insert(TABLE, null, contentValues);
    }

    void deleteNote(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = Long.toString(dataModel.getTime());

        db.delete(TABLE, "time = ?", new String[]{date});
    }

    void updateNote(String title, String name, long time, long noti_time,long reminder_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String d = Long.toString(time);

        contentValues.put(TITLE, title);
        contentValues.put(NAME, name);
        contentValues.put(TIME, System.currentTimeMillis());
        contentValues.put(REMINDER_TIME, noti_time);
        contentValues.put(REMINDER_ID,reminder_id);
        db.update(TABLE, contentValues, "time = ?", new String[]{d});
    }

    void truncate() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, null, null);
    }

    void bookM(Long time, int b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String d = Long.toString(time);
        contentValues.put(BOOKMARK, b);
        db.update(TABLE, contentValues, "time = ?", new String[]{d});
    }

    List<DataModel> getData() {

        Cursor cursor;
        List<DataModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuilder stringBuffer = new StringBuilder();
        DataModel dataModel;

        cursor = db.rawQuery("select * from " + TABLE + " ORDER BY " + TIME + " DESC;", null);

        while (cursor.moveToNext()) {
            dataModel = new DataModel();

            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
            Long time = cursor.getLong(cursor.getColumnIndexOrThrow(TIME));
            Integer book = cursor.getInt(cursor.getColumnIndexOrThrow(BOOKMARK));
            Long reminder_time = cursor.getLong(cursor.getColumnIndexOrThrow(REMINDER_TIME));
            Integer reminder_id = cursor.getInt(cursor.getColumnIndexOrThrow(REMINDER_ID));

            dataModel.setTitle(title);
            dataModel.setName(name);
            dataModel.setDate(time);
            dataModel.setBookmark(book);
            dataModel.setReminderTime(reminder_time);
            dataModel.setReminderID(reminder_id);

            stringBuffer.append(dataModel);

            data.add(dataModel);
        }
        return data;
    }
}