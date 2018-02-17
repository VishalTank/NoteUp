package com.tank.vishal.noteup;

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

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        //Toast.makeText(context, "Alert : " + random_gen, Toast.LENGTH_LONG).show();

        //notification builder
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("A Note needs to be reviewed!")
                .setContentText(intent.getStringExtra("ARtitle"));



        //View Note Action button in the notification.
        Intent viewIntent = new Intent(context,ShowNote.class);
        viewIntent.putExtra("title",intent.getStringExtra("ARtitle"));
        viewIntent.putExtra("name",intent.getStringExtra("ARname"));
        viewIntent.putExtra("date",intent.getStringExtra("ARdate"));
        viewIntent.putExtra("reminder_id",intent.getIntExtra("ARreminder_id",0));

        PendingIntent viewPendingIntent = PendingIntent.getActivity(context,intent.getIntExtra("ARreminder_id",0),viewIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(viewPendingIntent);



        //Add Note action button in the notification.
        Intent addIntent = new Intent(context,EditNote.class);
        PendingIntent addPendingIntent = PendingIntent.getActivity(context, intent.getIntExtra("ARreminder_id",0), addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(0, "Add Note", addPendingIntent);



        //Throws notifications.
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(intent.getIntExtra("ARreminder_id",0), mBuilder.build());
    }
}