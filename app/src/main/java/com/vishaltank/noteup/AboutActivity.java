package com.vishaltank.noteup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AboutActivity extends AppCompatActivity {

    private AnimationDrawable anim;
    private ImageButton facebook,github,twitter,google_plus,facebook1,github1,twitter1,website,rating,share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        CardView mainCardView = findViewById(R.id.mainCardView);
        mainCardView.setCardBackgroundColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#323232") : Color.parseColor("#ffffff"));

        CardView mainCardView1 = findViewById(R.id.mainCardView1);
        mainCardView1.setCardBackgroundColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#323232") : Color.parseColor("#ffffff"));

        CardView ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setCardBackgroundColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#323232") : Color.parseColor("#ffffff"));

        CardView feedbackBar = findViewById(R.id.feedbackBar);
        feedbackBar.setCardBackgroundColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#323232") : Color.parseColor("#ffffff"));

        RelativeLayout relativeLayout = findViewById(R.id.relative_layout);

        anim = (AnimationDrawable) relativeLayout.getBackground();
        anim.setEnterFadeDuration(3000);
        anim.setExitFadeDuration(4000);
        anim.start();


        Toolbar toolbar = findViewById(R.id.toolbar_About);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#f2f2f2") : Color.parseColor("#303030"));


        feedbackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(getString(R.string.mail_to))); // only email apps should be able to handle this action.
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.dev_mail) });
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
                intent.putExtra(Intent.EXTRA_TEXT   , getString(R.string.feedback_message));

                startActivity(Intent.createChooser(intent,"Send feedback"));
            }
        });

        rating = findViewById(R.id.rate_button);
        share = findViewById(R.id.share_button);

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.vishaltank.noteup";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "android solved");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app right now! Create and keep track of notes in an easy way with NoteUp! NoteUp also helps you in reminding things you need to do through simple notifications. https://play.google.com/store/apps/details?id=com.vishaltank.noteup");
                startActivity(Intent.createChooser(sharingIntent, "share via"));
            }
        });


        facebook = findViewById(R.id.fb_button);
        github = findViewById(R.id.git_button);
        twitter = findViewById(R.id.twitter_button);
        google_plus = findViewById(R.id.google_plus_button);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.vishal_facebook);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.vishal_github);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.vishal_twitter);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        google_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.vishal_googleplus);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        facebook1 = findViewById(R.id.fb_button1);
        github1 = findViewById(R.id.git_button1);
        twitter1 = findViewById(R.id.twitter1);
        website = findViewById(R.id.website);


        facebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.shreedan_facebook);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        github1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.shreedan_github);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        twitter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.shreedan_twitter);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.shreedan_website);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

