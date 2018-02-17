package com.tank.vishal.noteup;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


/**
 * Created by Vishal on 13-02-2018.
 */

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        addSlide(AppIntroFragment.newInstance("Welcome to\nNoteUp!","Simplest way to take and keep track of your tasks and notes.", R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Make a new note","Use FAB to create a new note.",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Edit/Delete an existing note","Swipe right on a note to edit,\nand swipe left to delete",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Swiped left mistakenly?","Once you delete a note, a snackbar will appear from which you can UNDO delete and get your note back safe and sound!",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Set a reminder","Set a reminder which will remind you of the to-dos/notes/tasks you have written.",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("BONUS!","Click on the app's icon to toggle between Dark and Light themee for the app!!!",R.drawable.wiz, Color.parseColor("#404040")));
        addSlide(AppIntroFragment.newInstance("Done","Enjoy using NoteUp!",R.drawable.wiz, Color.parseColor("#404040")));

        showSkipButton(false);
        setFadeAnimation();

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
