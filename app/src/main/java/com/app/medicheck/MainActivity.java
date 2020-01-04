package com.app.medicheck;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import com.app.medicheck.ui.notifications.Receiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.Console;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destination
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        createNotificationChannel();

        setAlarm();

    }

    public void setAlarm(){
        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("param", "My scheduled action");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        //set alarm time (after that much ms it will trigger notification)
        //System.currentTimeMillis()+10000
        long timeInMillisec = calculateMilliseconds("2019-12-20").getTimeInMillis();
        System.out.println(System.currentTimeMillis());
        System.out.println(timeInMillisec);

        alarms.set(AlarmManager.RTC_WAKEUP, timeInMillisec, operation) ;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channelName);
            String description = getString(R.string.channelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Channel1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Calendar calculateMilliseconds(String expDate){
        String [] dateComponents = expDate.split("-");
        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int day = Integer.parseInt(dateComponents[2]);

        System.out.println("Year " + year);
        System.out.println("month " + month);
        System.out.println("day " + day);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 23);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        System.out.println("Year " + cal.get(Calendar.YEAR));
        System.out.println("month " + cal.get(Calendar.MONTH));
        System.out.println("day " + cal.get(Calendar.DAY_OF_MONTH));

        System.out.println("Year " + year);
        System.out.println("month " + month);
        System.out.println("day " + day);

        return cal;
    }
}
