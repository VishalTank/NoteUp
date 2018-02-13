package com.tank.vishal.noteup;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ShowNote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Show Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.WHITE : Color.parseColor("#404040"));


        TextView date = (TextView) findViewById(R.id.date1);
        TextView title = (TextView) findViewById(R.id.note_title1);
        TextView name = (TextView) findViewById(R.id.note_text1);
        TextView reminder_time = (TextView) findViewById(R.id.noti_time1);


        title.setText(getIntent().getStringExtra("title"));
        name.setText(Html.fromHtml(getIntent().getStringExtra("name")));
        date.setText(getIntent().getStringExtra("date"));

        if(getIntent().getLongExtra("reminder_time",0) > System.currentTimeMillis())
            reminder_time.setText("Reminder time : " + new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm").format(new Date(getIntent().getLongExtra("reminder_time",0))));
        else
            reminder_time.setText(R.string.ex);

    }
}
