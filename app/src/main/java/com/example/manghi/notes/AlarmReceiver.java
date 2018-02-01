package com.example.manghi.notes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Vishal on 09-01-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        //Toast.makeText(context, "Alert : " + random_gen, Toast.LENGTH_LONG).show();

        //notification builder
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle("A Note needs to be reviewed!")
                        .setContentText(intent.getStringExtra("title"));


        //View Note Action button in the notification.
        Intent viewIntent = new Intent(context,MainActivity.class);

        viewIntent.putExtra("setTitle",intent.getStringExtra("title"));
        viewIntent.putExtra("setName",intent.getStringExtra("name"));
        viewIntent.putExtra("setTime",intent.getStringExtra("time"));
        viewIntent.putExtra("setReminderID",intent.getIntExtra("reminder_id",0));
        viewIntent.putExtra("isSet","yes");

        //viewIntent.setAction("View Note");
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context,intent.getIntExtra("reminder_id",0),viewIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //mBuilder.addAction(0,"View Note",viewPendingIntent);
        mBuilder.setContentIntent(viewPendingIntent);


        //Add Note action button in the notification.
        Intent addIntent = new Intent(context,EditNote.class);
        //addIntent.setAction("Add Note");

        PendingIntent addpendingIntent = PendingIntent.getActivity(context, intent.getIntExtra("reminder_id",0), addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(0, "Add Note", addpendingIntent);


        //Delete Note action button in the notification.
        Intent deleteIntent = new Intent(context,EditNote.class);
        deleteIntent.setAction("Delete Note");

        PendingIntent deletependingIntent = PendingIntent.getActivity(context, intent.getIntExtra("reminder_id",0), addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(0, "Delete Note", deletependingIntent);


        //Throws notifications.
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(intent.getIntExtra("reminder_id",0), mBuilder.build());
    }
}