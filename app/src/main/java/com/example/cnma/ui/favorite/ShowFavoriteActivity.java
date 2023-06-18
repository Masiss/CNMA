package com.example.cnma.ui.favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cnma.MainActivity;
import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.SettingActivity;
import com.example.cnma.Utils;
import com.example.cnma.db.CookingGuideDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ShowFavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CookingGuideDB db;
    ArrayList<Dish> dishList;
    ItemFavoriteAdapter itemFavoriteAdapter;
    Button deleteAllBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite);


        //khởi tạo navigationview
        Utils utils = new Utils(this, getWindow().getDecorView());
        utils.menu();

        // Perform item selected listener

        //khởi tạo database
        db = new CookingGuideDB(this);
        //ánh xạ recycler view
        recyclerView = findViewById(R.id.favoriteRv);
        //lấy các mục yêu thích từ database
        dishList = db.getFavorites();
        //khởi tạo adapter và đổ dữ liệu các mục yêu thích vào adapter
        itemFavoriteAdapter = new ItemFavoriteAdapter(this, dishList);
        //set adapter cho recycler view
        recyclerView.setAdapter(itemFavoriteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        deleteAllBtn = findViewById(R.id.deleteAllBtn);
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllFavorites();
                dishList.clear();
                dishList = db.getFavorites();
                itemFavoriteAdapter.notifyDataSetChanged();
                Toast.makeText(ShowFavoriteActivity.this, "Xóa các mục yêu thích thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }


}