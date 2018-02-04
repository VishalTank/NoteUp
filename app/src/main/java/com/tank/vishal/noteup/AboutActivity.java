package com.tank.vishal.noteup;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AboutActivity extends AppCompatActivity {

    ImageButton dev;
    ImageButton github;
    AnimationDrawable anim;
    ImageView img;
    private ImageButton twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        RelativeLayout container = (RelativeLayout) findViewById(R.id.main_layout);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(2400);
        anim.setExitFadeDuration(4000);
        anim.start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Developers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        getSupportActionBar().setBackgroundDrawable((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? new ColorDrawable(Color.parseColor("#f0cd00")) : new ColorDrawable(Color.parseColor("#fadb24")));


        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.WHITE : Color.parseColor("#404040"));

        dev = (ImageButton) findViewById(R.id.dev_button);
        github = (ImageButton) findViewById(R.id.git_button);
        twitter = (ImageButton) findViewById(R.id.twitter);

        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/vishal.tank.739";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/VishalTank";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/Hawk19101997";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            default:
                this.finish();
                return super.onOptionsItemSelected(item);
        }
    }
}
