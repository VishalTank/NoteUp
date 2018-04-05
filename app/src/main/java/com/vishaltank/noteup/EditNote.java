package com.vishaltank.noteup;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNote extends AppCompatActivity {

    private EditText etText, etTextTitle;
    private DatabaseHelper database = new DatabaseHelper(this);
    private String name, title;
    private static boolean flag = false;
    private MenuItem item;
    private SwitchCompat aSwitch;
    private DataModel dataModel = new DataModel();
    private Calendar calender;
    private boolean isAlarmSet;
    private TextView rem_time;
    private Bundle bundle;
    private long reminder_time,time,noti_time = 0;
    private int reminder_id,noti_id;
    private boolean isAlreadySet;
    private long creationTime;
    private Date calender_date;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Theme setter. */
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        /* toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar_EditNote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setElevation(4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setBackgroundDrawable((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? new ColorDrawable(Color.parseColor("#323232")) : new ColorDrawable(Color.parseColor("#fefefe")));

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#f2f2f2") : Color.parseColor("#303030"));

        etText = findViewById(R.id.etText);
        etTextTitle = findViewById(R.id.etTextTitle);
        aSwitch = findViewById(R.id.aSwitch);
        rem_time = findViewById(R.id.reminder_time);



        //Button bold = (Button) findViewById(R.id.bold);
        //Button italic = (Button) findViewById(R.id.italic);
        //Button un = (Button) findViewById(R.id.un);
        //un.setPaintFlags(un.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        /*bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                //text = Html.toHtml(new SpannableString(etText.getText()));
                text = String.valueOf(new SpannableString(etText.getText()));
                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }
                String selectedText = etText.getText().toString().substring(startSelection, endSelection);
                if (!selectedText.isEmpty()) {
                    //text = text.replace(selectedText, "<b>" + selectedText + "</b>");
                    text = text.substring(0,startSelection-1) + " <b>" + selectedText + "</b>" + text.substring(endSelection,text.length());
                    etText.setText(Html.fromHtml(text));
                }
            }
        });
        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                //text = Html.toHtml(new SpannableString(etText.getText().toString()));
                text = String.valueOf(new SpannableString(etText.getText()));
                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }
                String selectedText = etText.getText().toString().substring(startSelection, endSelection);
                if (!selectedText.isEmpty()) {
                    //text = text.replace(selectedText, "<i>" + selectedText + "</i>");
                    text = text.substring(0,startSelection-1) + " <i>" + selectedText + "</i>" + text.substring(endSelection,text.length());
                    etText.setText(Html.fromHtml(text));
                }
            }
        });
        un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                //text = Html.toHtml(new SpannableString(etText.getText()));
                text = String.valueOf(new SpannableString(etText.getText()));
                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }
                String selectedText = etText.getText().toString().substring(startSelection, endSelection);
                if (!selectedText.isEmpty()) {
                    //text = text.replace(selectedText, "<u>" + selectedText + "</u>");
                    text = text.substring(0,startSelection-1) + " <u>" + selectedText + "</u>" + text.substring(endSelection,text.length());
                    etText.setText(Html.fromHtml(text));
                }
            }
        });*/

        etText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateNoteSaveButton(s);
                invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /* switch's onChange */
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !isAlarmSet) {
                    //off->on when no reminder is set.
                    showDateTimePicker();
                }
                else if (isChecked && isAlarmSet) {

                    //off->on and alarm is already set.
                    reminder_time = Long.parseLong(String.valueOf(bundle.get("reminder_time")));
                    reminder_id = Integer.parseInt(String.valueOf(bundle.get("reminder_id")));

                    isAlreadySet = true;
                    rem_time.setText(new SimpleDateFormat("MMM dd',' yyyy  hh:mm").format(reminder_time));

                } else if(!isChecked && isAlarmSet) {

                    //on->off when alarm is set , cancels reminder if saved.
                    reminder_id = Integer.parseInt(String.valueOf(bundle.get("reminder_id")));

                    cancelReminder(reminder_id);

                    isAlarmSet = false;
                    isAlreadySet = false;
                    reminder_id = 0;
                    reminder_time = 0;
                    dataModel.setReminderID(0);
                    dataModel.setReminderTime((long) 0);
                    rem_time.setText(getString(R.string.reminder_Text));
                }
            }
        });


        /* Editing an existing Note. */
        bundle = getIntent().getExtras();

        if (bundle != null) {

            title = String.valueOf(bundle.get("title"));
            name = String.valueOf(bundle.get("name"));
            time = Long.parseLong(String.valueOf(bundle.get("time")));
            noti_time = Long.parseLong(String.valueOf(bundle.get("reminder_time")));
            noti_id = Integer.parseInt(String.valueOf(bundle.get("reminder_id")));

            if (name != null) {

                etTextTitle.setText(title);
                etText.setText((name));

                if (noti_time > System.currentTimeMillis()) {

                    isAlarmSet = true;
                    isAlreadySet = true;
                    aSwitch.setChecked(true);
                    reminder_time = noti_time;
                    reminder_id = noti_id;
                    rem_time.setText(new SimpleDateFormat("MMM dd',' yyyy  hh:mm a").format(reminder_time));

                } else {

                    isAlarmSet = false;
                    isAlreadySet = false;
                    aSwitch.setChecked(false);
                    reminder_time = 0;
                    rem_time.setText(R.string.reminder_Text);

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        item = menu.findItem(R.id.save_check);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        item.setEnabled(flag);
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        database = new DatabaseHelper(this);
        dataModel = new DataModel();

        switch (item.getItemId()) {

            case R.id.save_check:

                if(aSwitch.isChecked() && isAlreadySet) {

                    dataModel.setReminderTime(reminder_time);
                    dataModel.setReminderID(reminder_id);


                    if (name == null && title == null) {
                        database.insertNote(etTextTitle.getText().toString().trim(), etText.getText().toString(), reminder_time, reminder_id);
                        //originTime = new SimpleDateFormat("MMM dd',' yyyy  hh:mm").format(new Date(System.currentTimeMillis()));
                        creationTime = System.currentTimeMillis();
                    }else {
                        database.updateNote(etTextTitle.getText().toString().trim(), etText.getText().toString(), time, reminder_time, reminder_id);
                        //originTime = new SimpleDateFormat("MMM dd',' yyyy  hh:mm").format(new Date(System.currentTimeMillis()));
                        creationTime = System.currentTimeMillis();
                    }
                    //cancelReminder(reminder_id);

                    Intent intent = new Intent(EditNote.this, AlarmReceiver.class);
                    intent.putExtra("ARtitle", etTextTitle.getText().toString());
                    intent.putExtra("ARname", etText.getText().toString());
                    intent.putExtra("ARtime", creationTime);
                    intent.putExtra("ARreminder_id",reminder_id);
                    intent.putExtra("ARreminder_time",reminder_time);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reminder_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    if (alarmManager != null)
                        alarmManager.set(AlarmManager.RTC_WAKEUP, dataModel.getReminderTime(), pendingIntent);


                    isAlreadySet = true;
                    isAlarmSet = true;
                    flag = false;

                    this.finish();
                }
                /* Switch is on and proper time is set. */
                else if (aSwitch.isChecked() && calender.getTimeInMillis() > System.currentTimeMillis() && !isAlreadySet) {

                    reminder_time = calender.getTimeInMillis();
                    reminder_id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                    dataModel.setReminderTime(reminder_time);
                    dataModel.setReminderID(reminder_id);

                    /* new Note */
                    if (name == null && title == null) {
                        database.insertNote(etTextTitle.getText().toString().trim(), etText.getText().toString(), reminder_time,reminder_id);
                        //originTime = new SimpleDateFormat("MMM dd',' yyyy  hh:mm").format(new Date(System.currentTimeMillis()));
                        creationTime = System.currentTimeMillis();
                    }
                    /* Editing an existing Note since name and title are not null. */
                    else {
                        database.updateNote(etTextTitle.getText().toString().trim(), etText.getText().toString(), time, reminder_time,reminder_id);
                        //originTime = new SimpleDateFormat("MMM dd',' yyyy  hh:mm").format(new Date(System.currentTimeMillis()));
                        creationTime = System.currentTimeMillis();
                    }


                    Intent intent = new Intent(this, AlarmReceiver.class);
                    intent.putExtra("ARtitle", etTextTitle.getText().toString());
                    intent.putExtra("ARname", etText.getText().toString());
                    intent.putExtra("ARtime", creationTime);
                    intent.putExtra("ARreminder_id",reminder_id);
                    intent.putExtra("ARreminder_time",reminder_time);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reminder_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    if (alarmManager != null) {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, dataModel.getReminderTime(), pendingIntent);
                    }

                    isAlarmSet = true;
                    isAlreadySet = true;
                    flag = false;


                    this.finish();
                }


                /* Switch is on and improper time is set. */
                else if (aSwitch.isChecked() && calender.getTimeInMillis() < System.currentTimeMillis()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(this, (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? R.style.Theme_AppCompat_DayNight_Dialog : R.style.Theme_AppCompat_Light_Dialog);

                    alert.setTitle("Invalid Reminder Time");
                    alert.setMessage("\nPlease set proper Date and Time.\n");

                    final AlertDialog dialog = alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            aSwitch.setChecked(false);
                            rem_time.setText(getString(R.string.reminder_Text));
                        }

                    }).setPositiveButton("SET", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDateTimePicker();
                        }

                    }).create();

                    dialog.setOnShowListener( new DialogInterface.OnShowListener() {

                        @Override
                        public void onShow(DialogInterface arg0) {
                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#009688"));
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#009688"));
                        }

                    });
                    dialog.show();

                }

                /* Switch is not on. */
                else if (!aSwitch.isChecked()) {

                    reminder_time = 0;

                    if (name == null && title == null)
                        database.insertNote(etTextTitle.getText().toString().trim(),etText.getText().toString(), reminder_time,reminder_id);
                    else
                        database.updateNote(etTextTitle.getText().toString().trim(), etText.getText().toString(), time, reminder_time,reminder_id);


                    cancelReminder(reminder_id);
                    flag = false;


                    this.finish();
                }
                return true;


            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateNoteSaveButton(CharSequence s) {
        String text = null;

        if (s != null)
            text = s.toString();

        flag = (text != null && text.trim().length() != 0);

    }

    private void showDateTimePicker() {
        calender = Calendar.getInstance();

        new DatePickerDialog(EditNote.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calender.set(Calendar.YEAR, year);
                calender.set((Calendar.MONTH), monthOfYear);
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                new TimePickerDialog(EditNote.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        calender.set(Calendar.HOUR_OF_DAY, hour);
                        calender.set(Calendar.MINUTE, minute);
                        calender.set(Calendar.SECOND, 0);

                        calender_date = calender.getTime();

                        //Toast.makeText(EditNote.this,"Selected time : " + calender.get(Calendar.DAY_OF_MONTH) + "/" + calender.get(Calendar.MONTH) + "/" + calender.get(Calendar.YEAR) + " at " + calender.get(Calendar.HOUR_OF_DAY) + ":" + calender.get(Calendar.MINUTE) , Toast.LENGTH_LONG).show();
                        //String temp = calender.get(Calendar.DAY_OF_MONTH) + "/" + calender.get(Calendar.MONTH) + "/" + calender.get(Calendar.YEAR) + " at " + calender.get(Calendar.HOUR_OF_DAY) + ":" + calender.get(Calendar.MINUTE);
                        rem_time.setText(new SimpleDateFormat("MMM dd',' yyyy  hh:mm a").format(calender_date));

                    }
                }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show();

            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show();

    }

    //To cancel the reminder.
    private void cancelReminder(int remID) {

        Intent cancelServiceIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, remID, cancelServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (am != null)
            am.cancel(cancelPendingIntent);

        isAlreadySet = false;
        isAlarmSet = false;
        reminder_id = 0;
        reminder_time = 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
