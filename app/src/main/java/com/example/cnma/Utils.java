package com.example.cnma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.cnma.ui.favorite.ShowFavoriteActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Utils {
    private Context context;
    private View view;

    public Utils(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void menu() {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        if (context.getClass() == MainActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        } else if (context.getClass() == ShowFavoriteActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.favorite);
        } else if (context.getClass() == SettingActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.setting);
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        context.startActivity(new Intent(context, MainActivity.class));
                        ((Activity) context).overridePendingTransition(0,0);
                        return true;

                    case R.id.favorite:
                        context.startActivity(new Intent(context, ShowFavoriteActivity.class));
                        ((Activity) context).overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        context.startActivity(new Intent(context, SettingActivity.class));
                        ((Activity) context).overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
