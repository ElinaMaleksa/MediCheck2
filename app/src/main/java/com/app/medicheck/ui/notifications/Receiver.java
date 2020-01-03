package com.app.medicheck.ui.notifications;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.medicheck.R;

public class Receiver extends BroadcastReceiver {

    Notification notification = new Notification();
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, intent.getStringExtra("param"), Toast.LENGTH_SHORT).show();


        notification.createNotification(context, "title", "text");

    }

}