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

import com.app.medicheck.ui.home.HomeFragment;
import com.app.medicheck.ui.home.Products;
import com.app.medicheck.ui.notifications.ContentNotifications;
import com.app.medicheck.ui.notifications.Receiver;
import com.app.medicheck.ui.profile.Favourites;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static AppBarConfiguration appBarConfiguration;
    public static BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destination
        appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        createNotificationChannel();



        Favourites.load(this);

        /*setAlarm();*/

    }


    protected void onStop() {
        super.onStop();
        Favourites.save(this);

    }

    protected void onRestart() {
        Favourites.load(this);
        super.onRestart();
    }

    /*public void setAlarm(){
        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        //intent.putExtra("param", "My scheduled action");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        //set alarm time (after that much ms it will trigger notification)
        //System.currentTimeMillis()+10000

        ArrayList<Products> allProducts = HomeFragment.productList;
        List<String> fav = Favourites.getData();
        ContentNotifications cN;

        for (Products p : allProducts) {
            for (String s : fav) {
                if (p.getSerialNumber().equals(s)) {

                    String[] dateComponents = p.getBestBefore().split("-");
                    int year = Integer.parseInt(dateComponents[0]);
                    int month = Integer.parseInt(dateComponents[1]);
                    int day = Integer.parseInt(dateComponents[2]);

                    Calendar cal = Calendar.getInstance();
                    cal.set(java.util.Calendar.YEAR, year);
                    cal.set(java.util.Calendar.MONTH, month - 1);
                    cal.set(java.util.Calendar.DAY_OF_MONTH, day);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    cal.set(java.util.Calendar.MILLISECOND, 0);

                    long msDiff = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                    int expWarningTime = 105;

                    if (daysDiff < expWarningTime) {
                        cN = new ContentNotifications(p.getId(), p.getName(), p.getBestBefore(), daysDiff);
                        long timeInMilliSec = calculateMilliseconds(cN.getBestBeforeNot()).getTimeInMillis();

                        intent.putExtra("name", cN.getNameNot());
                        intent.putExtra("bestBefore", cN.getBestBeforeNot());
                        alarms.set(AlarmManager.RTC_WAKEUP, 60000, operation) ;
                    }


                }
            }
        }

    }*/
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

   /* private Calendar calculateMilliseconds(String expDate){
        String [] dateComponents = expDate.split("-");
        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int day = Integer.parseInt(dateComponents[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 23);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        return cal;
    }*/
}
