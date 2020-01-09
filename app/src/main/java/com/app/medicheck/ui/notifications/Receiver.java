package com.app.medicheck.ui.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    Notification notification = new Notification();
    @Override
    public void onReceive(Context context, Intent intent) {

        int id;
        String title = intent.getStringExtra("title");
        String name = intent.getStringExtra("name");
        String text = name + " will expire after " + intent.getStringExtra("days") + " days";
        try {
            id = Integer.getInteger(intent.getStringExtra("id"));
        }catch(Exception e){
            id = 1;
        }

        notification.createNotification(context, title, text, id);

    }

}