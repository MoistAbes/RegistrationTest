package com.example.registrationtest.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_EMAIL = "email";
    static final String PREF_PASS = "password";

    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setEmail(Context context, String email){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_EMAIL, email);
        editor.commit();
    }

    public static void setPassword(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_PASS, password);
        editor.commit();
    }

    public static String getEmail(Context context){
        return getSharedPreferences(context).getString(PREF_EMAIL, "");
    }

    public static String getPassword(Context context){
        return getSharedPreferences(context).getString(PREF_PASS,"");
    }
}
