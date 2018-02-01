package com.example.manghi.notes;

import android.text.Html;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vishal on 12-12-2017.
 */

public class DataModel {

    private String name,title;
    private java.sql.Date date;
    private long notidate;
    private int reminder_id,bookm;

    //note's title
    String getTitle() { return title; }

    void setTitle(String title) { this.title = title; }


    //note
    String getName() { return name; }

    void setName(String name) {
        this.name = name;
    }


    //mark important
    int getBookmark() {
        return  bookm;
    }

    void setBookmark(int bookm) {
        this.bookm = bookm;
    }


    //created or updated date & time
    String getDate() { return new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm").format(date); }

    void setDate(Long date) { this.date = new java.sql.Date(date); }

    long getTime() { return date.getTime(); }


    //reminder's date & time
    long getNotiTime() { return notidate; }

    void setNotiTime(Long notidate) { this.notidate = notidate; }


    //reminder's ID
    void setReminderID(int reminder_id) { this.reminder_id = reminder_id; }

    int getReminderID() { return reminder_id; }
}