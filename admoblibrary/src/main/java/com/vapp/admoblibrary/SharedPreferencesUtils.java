package com.vapp.admoblibrary;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    public static final String admobPref = "ADMOB";
    public static void saveData(Context context, String key, String value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(admobPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String retrieveData(Context context, String key){
        return context.getSharedPreferences(admobPref,Context.MODE_PRIVATE).getString(key,"");
    }
}
