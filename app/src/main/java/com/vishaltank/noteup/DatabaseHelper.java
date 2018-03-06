package com.vishaltank.noteup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishal on 03-03-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE = "database_one.db";
    private static String TABLE = "NoteTable";
    private static String TITLE = "title";
    private static String NAME = "name";
    private static String TIME = "time";
    private static String REMINDER_ID = "reminder_id";
    private static String REMINDER_TIME = "reminder_time";


    DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /* Create a new table in database. */
        db.execSQL("CREATE TABLE "
                + TABLE + "( "
                + TITLE + " TEXT, "
                + NAME + " TEXT, "
                + TIME + " LONG PRIMARY KEY, "
                + REMINDER_TIME + " LONG, "
                + REMINDER_ID + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + " ;");
    }


    /* Insert note into database. */
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


    /* Delete note from database. */
    void deleteNote(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = Long.toString(dataModel.getTime());

        db.delete(TABLE, "time = ?", new String[]{date});
    }


    /* Update note which is already in database. */
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


    /* Clear everything in database. */
    void truncate() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, null, null);
    }


    /* Get all info about a row from database. */
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
            Long reminder_time = cursor.getLong(cursor.getColumnIndexOrThrow(REMINDER_TIME));
            Integer reminder_id = cursor.getInt(cursor.getColumnIndexOrThrow(REMINDER_ID));

            dataModel.setTitle(title);
            dataModel.setName(name);
            dataModel.setDate(time);
            dataModel.setReminderTime(reminder_time);
            dataModel.setReminderID(reminder_id);

            stringBuffer.append(dataModel);

            data.add(dataModel);
        }
        cursor.close();
        return data;
    }


    /* Get ALL reminder ids(IF SET) of every note from database. */
    List<Integer> getAllReminderIDs() {

        ArrayList<Integer> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE, null);

        c.moveToFirst();

        while(!c.isAfterLast()){
            arrayList.add(c.getInt(c.getColumnIndex(REMINDER_ID)));
            c.moveToNext();
        }
        c.close();
        return arrayList;
    }
}