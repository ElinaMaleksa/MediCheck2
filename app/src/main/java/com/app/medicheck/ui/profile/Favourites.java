package com.app.medicheck.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Favourites {
    private static List<String> data;

    public static void load(Context context) {
        data = new ArrayList<String>();
        // use SharedPreferences to retrieve all your data
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        Map<String,?> keys = pref.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            data.add(entry.getValue().toString());
        }
    }

    public static void save(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Iterator it = data.iterator();
        int i = 0;
        while (it.hasNext()){
            editor.putString("" + i, it.next().toString());
            editor.commit();
        }
    }

    public static List<String> getData() {
        return data;
    }

/*    public static void setData(List<String> data) {
        Favourites.data = data;
    }*/
    public static void setData(String serialNumber) {
            Favourites.data.add(serialNumber);
    }
}
