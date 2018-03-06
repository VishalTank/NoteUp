package com.vishaltank.noteup;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toolbar;

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

        addSlide(AppIntroFragment.newInstance("Welcome to\nNoteUp!","Simplest way to take and keep track of your thoughts and notes.", R.drawable.create_ss, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Create a new note","Simply create a new note using Add button.",R.drawable.create_ss, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Edit a note","Swipe right on a note to edit.",R.drawable.edit_ss, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Delete / Undo","Swipe left on a note to delete\nretrieve it back using UNDO.",R.drawable.delete_ss, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Something new!","Click on the NoteUp's icon to change theme!!!",R.drawable.themechanger_ss, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("That's it","Enjoy using NoteUp!",R.drawable.themechanger_ss, Color.parseColor("#404040")));

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

        editor.apply();
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {

        super.onSkipPressed(currentFragment);
        finish();
    }
}
