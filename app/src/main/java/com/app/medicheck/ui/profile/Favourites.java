package com.app.medicheck.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Favourites {
    private static List<String> data;

    public static void load(Context context) {
        System.out.println("Load method");
        data = new ArrayList<>();
        // use SharedPreferences to retrieve all your data
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        Map<String,?> allPrefs = pref.getAll();
        Set<String> set = allPrefs.keySet();
        Set<String> hashSet = new HashSet<>();
        for(String s : set){
            hashSet = (Set) allPrefs.get(s);
        }
        assert hashSet != null;
        data.addAll(hashSet);
    }

    public static void save(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Set<String> set = new HashSet<String>(data) {
        };
        editor.putStringSet("fav", set);
        editor.commit();
    }

    public static List<String> getData() {
        return data;
    }

     public static void setData(List<String> data) {
        Favourites.data = data;
    }

    public static void setData(String serialNumber) {
        Favourites.data.add(serialNumber);
    }

    public static void removeItem(String serialNumber){
        data.remove(serialNumber);
    }
}
