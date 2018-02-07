package com.tank.vishal.noteup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_show_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.WHITE : Color.parseColor("#404040"));

        TextView date = (TextView) findViewById(R.id.date1);
        TextView title = (TextView) findViewById(R.id.note_title1);
        TextView name = (TextView) findViewById(R.id.note_text1);

        date.setText(getIntent().getStringExtra("date"));
        title.setText(getIntent().getStringExtra("title"));
        name.setText(Html.fromHtml(getIntent().getStringExtra("name")));

    }

}
