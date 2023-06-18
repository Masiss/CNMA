package com.example.cnma;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Category;
import com.example.cnma.Model.Dish;
import com.example.cnma.db.CookingGuideDB;
import com.example.cnma.ui.favorite.ShowFavoriteActivity;
import com.example.cnma.ui.list.ListAdapter;
import com.example.cnma.ui.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView verticalRv;
    private ListAdapter adapter;
    private Button searchBtn;
    private EditText searchEd;
    private CookingGuideDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils utils = new Utils(this, getWindow().getDecorView());
        utils.menu();

        //khởi tạo Database SQLite
        db = new CookingGuideDB(this);


        searchBtn = findViewById(R.id.search);
        searchEd = findViewById(R.id.searchTB);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(searchEd.getText())) {
                    Toast.makeText(getApplicationContext(), "Hãy điền tên món ban muốn tìm", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("search", searchEd.getText().toString());
                    startActivity(intent);
                }

            }
        });
        //lấy dữ liệu đổ vào recycler view
        ArrayList<Category> categories = db.getCategories();

        verticalRv = findViewById(R.id.verticalRv);
        verticalRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ListAdapter(categories, this);
        verticalRv.setAdapter(adapter);
    }

}

