package com.app.medicheck.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.medicheck.R;

public class NotificationsFragment extends Fragment {

   // private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
       // final TextView textView = root.findViewById(R.id.text_notifications);
        /*notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);


            }
        });*/

        Button notificationButton = root.findViewById(R.id.btnNotification);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationsFragment.this.getContext(), "Channel1")
//                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                        .setContentTitle("title")
//                        .setContentText("text")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setAutoCancel(true);
//
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationsFragment.this.getContext());
//
//                // notificationId is a unique int for each notification that you must define
//                notificationManager.notify(1, builder.build());
           }
        });

        return root;
    }
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }


}
