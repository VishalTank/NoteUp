package com.vishaltank.noteup;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Vishal on 03-03-2018.
 */

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        addSlide(AppIntroFragment.newInstance("Welcome to\nNoteUp!","Simplest way to take and keep track of your tasks and notes.", R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Make a new note","Create a new note using ADD button.",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Edit an existing note","Swipe from left to right on a note.",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Delete and Undo","Delete a note by swiping left\nand UNDO it from the snackbar.",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Set a reminder","Reminder reminds you of your notes and tasks at a specified time.",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Bonus!","Click on the app's icon to toggle between Dark and Light theme for the app!!!",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Done","Enjoy using NoteUp!",R.drawable.wiz, Color.parseColor("#404040")));

        showSkipButton(false);

        setZoomAnimation();

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {

        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {

        super.onDonePressed(currentFragment);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", false);

        editor.commit();
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {

        super.onSkipPressed(currentFragment);
        finish();
    }
}
