package com.app.medicheck.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            System.out.println("Load method " + "<" + Objects.requireNonNull(allPrefs.get(s)).getClass().getSimpleName() +"> =  "
                    + Objects.requireNonNull(allPrefs.get(s)).toString());

        }

        for (String h:hashSet) {
            data.add(h);
        }

        /*for(Map.Entry<String,?> entry : keys.entrySet()){


            data.add(entry.getValue().toString());
            System.out.println("Load method " + entry.getValue().toString());
        }*/
    }

    public static void save(Context context) {
        System.out.println("Save method ");
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Set<String> set = new HashSet<String>(data) {
        };
        editor.putStringSet("fav", set);
/*        Iterator it = data.iterator();
        int i = 0;
        while (it.hasNext()){
            editor.putString("" + i, it.next().toString());
            System.out.println("Save method " + it.next().toString());
            i++;
        }*/
        editor.commit();
    }

    public static List<String> getData() {
        System.out.println("GetData method");
        return data;
    }

/*    public static void setData(List<String> data) {
        Favourites.data = data;
    }*/
    public static void setData(String serialNumber) {
        System.out.println("setData method ");
        Favourites.data.add(serialNumber);
    }

    public static void removeItem(String serialNumber){
        data.remove(serialNumber);
    }
}
