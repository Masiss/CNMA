package com.example.cnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    public Switch switch1;
    public String PREFS_NAME = "SettingButton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Utils utils = new Utils(this, getWindow().getDecorView());
        utils.menu();


        switch1 = findViewById(R.id.switch1);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("switchkey", false);
        switch1.setChecked(silent);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(SettingActivity.this, "Đã chuyển sang DarkMode", Toast.LENGTH_SHORT).show();
                } else {
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(SettingActivity.this, "Đã chuyển sang LightMode", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey", b);
                editor.apply();
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }
}