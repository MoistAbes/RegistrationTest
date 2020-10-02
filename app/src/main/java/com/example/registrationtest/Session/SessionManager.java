package com.example.registrationtest.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    //inicjalizujemy zmienne
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //tworzymy konstruktor
    public  SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("AppKey",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //Tworzymy setter na login
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }

    //Tworzymy getter dla login
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    //tworzymy setter dla email
    public void setEmail(String email){
        editor.putString("KEY_EMAIL",email);
        editor.commit();
    }

    //tworzymy getter dla email
    public String getEmail(){
        return sharedPreferences.getString("KEY_EMAIL", "");
    }
}
