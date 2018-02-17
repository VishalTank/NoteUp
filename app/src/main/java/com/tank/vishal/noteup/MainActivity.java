package com.tank.vishal.noteup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DataAdapter adapter;
    private RecyclerView recyclerView;
    private Paint p = new Paint();
    private DatabaseHelper database;
    private List<DataModel> item_list;
    private List<Integer> reminder_id_list;
    private String rem,rem_title;
    private Context context;
    private Toolbar toolbar;
    private boolean isClicked;
    private Integer rem_reminder_id;
    private long rem_reminder_time;
    private long rem_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme Setter
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);

        if(useDarkTheme) {
            setTheme(R.style.DarkTheme);
            isClicked = useDarkTheme;
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);

        if(firstRun) {
            Intent i = new Intent(this,AppIntroActivity.class);
            startActivity(i);
        }


        initViews();

        //Custom Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);
        getDelegate().setHandleNativeActionModesEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.app_icon);
        getSupportActionBar().setBackgroundDrawable((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? new ColorDrawable(Color.parseColor("#00897B")) : new ColorDrawable(Color.parseColor("#26A69A")));

        toolbar.setTitleTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#f2f2f2") : Color.parseColor("#EE000000"));


        //When User clicks on the notification , Control will flow to here.
        /*isSet = getIntent().getStringExtra("isSet");

        if(isSet != null && isSet.equalsIgnoreCase("yes")) {
            isSet = null;
            showChangeLangDialog(getIntent().getStringExtra("setTitle"), Html.fromHtml(getIntent().getStringExtra("setName")),getIntent().getStringExtra("setTime"), 0);
        }*/
    }



    //Main content view, RecyclerView and its row's onClick and onLongClick methods
    private void initViews() {


        item_list = new ArrayList<>();
        database = new DatabaseHelper(this);
        item_list = database.getData();
        adapter = new DataAdapter(item_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        initSwipe();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    DataModel dm;

                    @Override
                    public void onItemClick(View view, int position) {
                        dm = item_list.get(position);
                        //showChangeLangDialog(dm.getTitle(),Html.fromHtml(dm.getName()), dm.getDate(),dm.getReminderTime());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        dm = item_list.get(position);

                        Intent show_note = new Intent(MainActivity.this,ShowNote.class);

                        show_note.putExtra("date",dm.getDate());
                        show_note.putExtra("title",dm.getTitle());
                        show_note.putExtra("name",dm.getName());
                        show_note.putExtra("reminder_time",dm.getReminderTime());
                        startActivity(show_note);

                        /*database = new DatabaseHelper(MainActivity.this);

                        int bm = (dm.getBookmark() == 0) ? 1 : 0;
                        database.bookM(dm.getTime(), bm);

                        item_list = database.getData();
                        adapter = new DataAdapter(item_list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();*/
                    }
                })
        );
    }


    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                DataModel dm;

                //If user swipes to left , Delete
                if (direction == ItemTouchHelper.LEFT){

                    dm = item_list.get(position);
                    //Store REMOVED items temporarily.
                    rem_title = dm.getTitle();
                    rem = dm.getName();
                    rem_reminder_time = dm.getReminderTime();
                    rem_reminder_id = dm.getReminderID();

                    adapter.removeItem(position);

                    Snackbar sb = Snackbar.make(viewHolder.itemView, "Removed " + dm.getTitle(), Snackbar.LENGTH_LONG).setDuration(5000).setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            database.insertNote(rem_title,rem,rem_reminder_time,rem_reminder_id);
                            rem_time = System.currentTimeMillis();

                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                            intent.putExtra("reminder_id",rem_reminder_id);

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, rem_reminder_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                            if(alarmManager != null && rem_reminder_time > System.currentTimeMillis()) {
                                alarmManager.set(AlarmManager.RTC_WAKEUP, rem_reminder_time, pendingIntent);
                                Toast.makeText(MainActivity.this, "RESET "+ rem_reminder_id + " " + rem_reminder_time, Toast.LENGTH_SHORT).show();
                            }

                            Intent i = new Intent(MainActivity.this,ShowNote.class);
                            i.putExtra("title", rem_title);
                            i.putExtra("name", rem);
                            i.putExtra("time", rem_time);



                            item_list = database.getData();
                            adapter = new DataAdapter(item_list);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    View sbView = sb.getView();

                    int sbID = android.support.design.R.id.snackbar_text;
                    TextView tv = sbView.findViewById(sbID);

                    sbView.setBackgroundColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#dddddd") : Color.parseColor("#303030"));
                    tv.setTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#303030") : Color.parseColor("#dddddd"));
                    sb.setActionTextColor((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? Color.parseColor("#303030") : Color.parseColor("#dddddd"));
                    sb.show();



                    Intent cancelServiceIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(MainActivity.this, rem_reminder_id, cancelServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    if (am != null) {
                        am.cancel(cancelPendingIntent);
                        Toast.makeText(MainActivity.this, "CANCELED "+ rem_reminder_id + " " + rem_reminder_time, Toast.LENGTH_SHORT).show();
                    }


                    database.deleteNote(dm);
                    item_list = database.getData();
                    adapter = new DataAdapter(item_list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                //If user swipes to right , EditActivity opens.
                else {

                    dm = item_list.get(position);

                    Intent i = new Intent(MainActivity.this,EditNote.class);
                    i.putExtra("title", dm.getTitle());
                    i.putExtra("name", dm.getName());
                    i.putExtra("time", dm.getTime());
                    i.putExtra("reminder_time",dm.getReminderTime());
                    i.putExtra("reminder_id",dm.getReminderID());
                    startActivity(i);

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                p = new Paint();
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    //Displacement of X-axis is measured , if it is >0 Edit icon is shown as it is technically a swipe to right direction
                    if(dX > 0){
                        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mode_edit_white_48dp);
                            p.setColor(Color.parseColor("#262626"));
                        }
                        else{
                            p.setColor(Color.parseColor("#ffffff"));
                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mode_edit_black_48dp);
                        }
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_48dp);
                            p.setColor(Color.parseColor("#262626"));
                        }
                        else{
                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_black_48dp);
                            p.setColor(Color.parseColor("#ffffff"));
                        }
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    //Used to change Theme of the app in the onCreate of MainActivity
    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putBoolean("dark_theme", darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }


    //Floating action button's onClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent i = new Intent(MainActivity.this,EditNote.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Menu item's onClick
            case android.R.id.home:
                toggleTheme(!isClicked);
                return true;


            case R.id.clear_db:
                AlertDialog.Builder alert = new AlertDialog.Builder(this, (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) ? R.style.Theme_AppCompat_DayNight_Dialog : R.style.Theme_AppCompat_Light_Dialog);
                alert.setMessage("Are you sure you want to clear the whole database?");

                final AlertDialog dialog = alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        reminder_id_list = database.getAllReminderIDs();

                        for(int x : reminder_id_list) {
                            Intent cancelServiceIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                            PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(MainActivity.this, x, cancelServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                            if (am != null) {
                                am.cancel(cancelPendingIntent);
                                Toast.makeText(MainActivity.this, "CANCELED "+ x, Toast.LENGTH_SHORT).show();
                            }
                        }
                        database.truncate();
                        initViews();

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

                return true;


            case R.id.about:
                Intent i = new Intent(this,AboutActivity.class);
                startActivity(i);
                return true;


            case R.id.help:
                Intent i1 = new Intent(this,AppIntroActivity.class);
                startActivity(i1);
                return true;


             default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //This portion runs whenever the MainActivity is started
    @Override
    protected void onStart() {
        super.onStart();
        item_list = database.getData();
        adapter = new DataAdapter(item_list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}