package com.example.manghi.notes;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Manghi on 12/19/2017.
 */
public class EditNote extends AppCompatActivity {

    private EditText etText, etTextTitle;
    private DatabaseHelper database = new DatabaseHelper(this);
    private String name, title;
    private long time, noti_time = 0;
    private String text;
    private static boolean flag = false;
    private MenuItem item;
    private SwitchCompat aSwitch;
    private DataModel dataModel = new DataModel();
    private Calendar calender;
    private boolean isAlarmSet;
    private String originTime;
    private TextView rem_time;
    private Bundle bundle;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int random_num = 0;
    private long reminder_time;
    private int reminder_id;
    private int noti_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.WHITE : Color.parseColor("#404040"));
        //toolbar.setBackgroundColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.BLACK : Color.WHITE);


        etText = (EditText) findViewById(R.id.etText);
        etTextTitle = (EditText) findViewById(R.id.etTextTitle);
        Button bold = (Button) findViewById(R.id.bold);
        Button italic = (Button) findViewById(R.id.italic);

        Button un = (Button) findViewById(R.id.un);
        un.setPaintFlags(un.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button strike = (Button) findViewById(R.id.strike);
        strike.setPaintFlags(strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        aSwitch = (SwitchCompat) findViewById(R.id.aSwitch);
        rem_time = (TextView) findViewById(R.id.rem_time2);

        /*CharSequence text1 = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean readonly =
                getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        etText.setSelection(etText.getText().length());*/

        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                text = Html.toHtml(new SpannableString(etText.getText()));

                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }

                String selectedText = etText.getText().toString().substring(startSelection, endSelection);

                if (!selectedText.isEmpty())
                    text = text.replace(selectedText, "<b>" + selectedText + "</b>");

                if (!text.isEmpty())
                    etText.setText(Html.fromHtml(text));
            }
        });

        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                text = Html.toHtml(new SpannableString(etText.getText()));


                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }
                String selectedText = etText.getText().toString().substring(startSelection, endSelection);
                if (!selectedText.isEmpty()) {
                    text = text.replace(selectedText, "<i>" + selectedText + "</i>");
                }
                if (!text.isEmpty()) {
                    etText.setText(Html.fromHtml(text));
                }
            }
        });

        un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                text = Html.toHtml(new SpannableString(etText.getText()));

                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }

                String selectedText = etText.getText().toString().substring(startSelection, endSelection);
                if (!selectedText.isEmpty()) {
                    text = text.replace(selectedText, "<u>" + selectedText + "</u>");
                }
                if (!text.isEmpty()) {
                    etText.setText(Html.fromHtml(text));
                }
            }
        });

        strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection = etText.getSelectionStart();
                int endSelection = etText.getSelectionEnd();
                text = Html.toHtml(new SpannableString(etText.getText()));

                if (startSelection > endSelection) {
                    startSelection = etText.getSelectionEnd();
                    endSelection = etText.getSelectionStart();
                }

                String selectedText = etText.getText().toString().substring(startSelection, endSelection);
                if (!selectedText.isEmpty()) {
                    text = text.replace(selectedText, "<strike>" + selectedText + "</strike>");
                }
                if (!text.isEmpty()) {
                    etText.setText(Html.fromHtml(text));
                }
            }
        });

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

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !isAlarmSet) {
                    showDateTimePicker();
                } else if (isChecked && isAlarmSet) {
                    reminder_time = Long.parseLong(bundle.get("notitime").toString());
                    reminder_id = Integer.parseInt(bundle.get("reminder_id").toString());
                } else if(!isChecked && isAlarmSet) {
                    reminder_id = Integer.parseInt(bundle.get("reminder_id").toString());

                    Intent cancelServiceIntent = new Intent(EditNote.this, AlarmReceiver.class);
                    PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(EditNote.this, reminder_id, cancelServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    if (am != null)
                        am.cancel(cancelPendingIntent);
                    Toast.makeText(EditNote.this, "canceled : "+ reminder_id, Toast.LENGTH_SHORT).show();

                    isAlarmSet = false;
                    reminder_id = 0;
                }
            }
        });

        //Editing an existing Note.
        bundle = getIntent().getExtras();

        if (bundle != null) {
            title = bundle.get("title").toString();
            name = bundle.get("name").toString();
            time = Long.parseLong(bundle.get("time").toString());
            noti_time = Long.parseLong(bundle.get("notitime").toString());
            noti_id = Integer.parseInt(bundle.get("reminder_id").toString());

            if (name != null) {
                etTextTitle.setText(title);
                etText.setText(Html.fromHtml(name));

                if (noti_time > System.currentTimeMillis()) {
                    isAlarmSet = true;
                    aSwitch.setChecked(true);
                    reminder_time = noti_time;
                    reminder_id = noti_id;

                } else {
                    isAlarmSet = false;
                    aSwitch.setChecked(false);
                    reminder_time = 0;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        database = new DatabaseHelper(this);
        dataModel = new DataModel();

        switch (item.getItemId()) {

            case R.id.save_check:

                //Switch is on and proper time is set.
                if (aSwitch.isChecked() && calender.getTimeInMillis() > System.currentTimeMillis()) {

                    reminder_time = calender.getTimeInMillis();
                    reminder_id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                    dataModel.setNotiTime(reminder_time);
                    dataModel.setReminderID(reminder_id);

                    //new Note
                    if (name == null && title == null) {
                        database.insertNote(etTextTitle.getText().toString(), Html.toHtml(new SpannableString(etText.getText())), reminder_time,reminder_id);
                        originTime = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm").format(new Date(System.currentTimeMillis()));
                    }
                    //Editing an existing Note since name and title are not null.
                    else {
                        database.updateNote(etTextTitle.getText().toString(), Html.toHtml(new SpannableString(etText.getText())), time, reminder_time,reminder_id);
                        originTime = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm").format(new Date(System.currentTimeMillis()));
                    }


                    Intent intent = new Intent(this, AlarmReceiver.class);
                    intent.putExtra("title", etTextTitle.getText().toString());
                    intent.putExtra("name", Html.toHtml(etText.getText()));
                    intent.putExtra("time", originTime);
                    intent.putExtra("reminder_id",reminder_id);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reminder_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, dataModel.getNotiTime(), pendingIntent);

                    isAlarmSet = true;
                    Toast.makeText(this, "reminder_id : "+reminder_id + " " + dataModel.getReminderID(), Toast.LENGTH_SHORT).show();

                    this.finish();
                }
                //Switch is on and improper time is set.
                else if (aSwitch.isChecked() && reminder_time < System.currentTimeMillis()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_MinWidth);

                    alert.setTitle("Invalid Reminder Time selected!");
                    alert.setMessage("\nPlease set proper Date and Time.");

                    final AlertDialog dialog = alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            aSwitch.setChecked(false);
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

                //Switch is not set.
                else if (!aSwitch.isChecked()) {

                    if (name == null && title == null)
                        database.insertNote(etTextTitle.getText().toString(), Html.toHtml(new SpannableString(etText.getText())), reminder_time,reminder_id);
                    else
                        database.updateNote(etTextTitle.getText().toString(), Html.toHtml(new SpannableString(etText.getText())), time, reminder_time,reminder_id);

                        Intent cancelServiceIntent = new Intent(this, AlarmReceiver.class);
                        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, reminder_id, cancelServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        if (am != null)
                            am.cancel(cancelPendingIntent);

                    Toast.makeText(EditNote.this, "CANCELED: " + reminder_id + "  " + dataModel.getReminderID() , Toast.LENGTH_SHORT).show();

                    isAlarmSet = false;
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
                calender.set((Calendar.MONTH + 1), monthOfYear);
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                new TimePickerDialog(EditNote.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        calender.set(Calendar.HOUR_OF_DAY, hour);
                        calender.set(Calendar.MINUTE, minute);
                        calender.set(Calendar.SECOND, 0);
                        Toast.makeText(EditNote.this, calender.get(Calendar.HOUR) + ":" + calender.get(Calendar.MINUTE) + " " + calender.get(Calendar.YEAR) + "/" + calender.get(Calendar.MONTH) + 1 + "/" + calender.get(Calendar.DAY_OF_MONTH) + " " + (calender.getTimeInMillis() - System.currentTimeMillis()) / 1000, Toast.LENGTH_SHORT).show();
                    }
                }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show();

            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show();

        rem_time.setText(new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm").format(new Date(calender.getTimeInMillis())));
    }
}