package com.vishaltank.noteup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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


        Toolbar toolbar = findViewById(R.id.toolbar_ShowNote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setBackgroundDrawable((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? new ColorDrawable(Color.parseColor("#323232")) : new ColorDrawable(Color.parseColor("#fefefe")));

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#f2f2f2") : Color.parseColor("#303030"));


        TextView date = findViewById(R.id.date_ShowNote);
        TextView title = findViewById(R.id.title_ShowNote);
        TextView name = findViewById(R.id.note_ShowNote);
        TextView reminder_time = findViewById(R.id.reminder_ShowNote);


        if(getIntent().getStringExtra("title").trim().length() > 0) {
            title.setVisibility(View.VISIBLE);
            title.setText(getIntent().getStringExtra("title"));
        }

        name.setText(getIntent().getStringExtra("name"));
        date.setText(new SimpleDateFormat("MMM dd',' yyyy  hh:mm a").format(new Date(getIntent().getLongExtra("time",0))));

        if(getIntent().getLongExtra("reminder_time",0) > System.currentTimeMillis())
            reminder_time.setText(new SimpleDateFormat("MMM dd',' yyyy  hh:mm a").format(new Date(getIntent().getLongExtra("reminder_time",0))));
        else
            reminder_time.setText(R.string.reminder_Text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.show_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            default:
                this.finish();
                super.onOptionsItemSelected(item);
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

