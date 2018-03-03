package com.vishaltank.noteup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Created by Vishal on 03-03-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //notification builder
        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.notification_icon)
                .setColor(Color.parseColor("#007d6d"))
                .setShowWhen(true)
                .setStyle(new Notification.BigTextStyle())
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("A Note needs to be reviewed!")
                .setContentText((intent.getStringExtra("ARtitle").trim().length() > 0) ? intent.getStringExtra("ARtitle").trim() : intent.getStringExtra("ARname")
                );

        mBuilder.setDefaults(Notification.DEFAULT_ALL);


        //View Note Action button in the notification.
        Intent viewIntent = new Intent(context,ShowNote.class);
        viewIntent.putExtra("title",intent.getStringExtra("ARtitle"));
        viewIntent.putExtra("name",intent.getStringExtra("ARname"));
        viewIntent.putExtra("time",intent.getLongExtra("ARtime",0));


        PendingIntent viewPendingIntent = PendingIntent.getActivity(context,intent.getIntExtra("ARreminder_id",0),viewIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(viewPendingIntent);


        //Add Note action button in the notification.
        Intent addIntent = new Intent(context,EditNote.class);
        PendingIntent addPendingIntent = PendingIntent.getActivity(context, intent.getIntExtra("ARreminder_id",0), addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(0, "ADD A NEW NOTE", addPendingIntent);



        //Throws notifications.
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(intent.getIntExtra("ARreminder_id",0), mBuilder.build());
    }
}
