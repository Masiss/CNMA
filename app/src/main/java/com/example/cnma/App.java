package com.example.cnma;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.cnma.db.CookingGuideDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class App extends Application {
    CookingGuideDB db;
    public String PREFS_NAME = "SettingButton";

    @Override
    public void onCreate() {

        super.onCreate();
        db = new CookingGuideDB(this);
        db.copyDatabase();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("switchkey", false);
        if (silent) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


    }

}
