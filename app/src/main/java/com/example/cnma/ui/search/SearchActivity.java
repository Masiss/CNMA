package com.example.cnma.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.db.CookingGuideDB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Dish> dishes;
    private RecyclerView recyclerView;
    private TextView searched;
    private ImageView reset;
    private String searchData;
    private SearchAdapter searchAdapter;
    private CookingGuideDB db;
    private Button sortBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent intent = getIntent();
        searchData = intent.getStringExtra("search");

        db = new CookingGuideDB(this);

        recyclerView = findViewById(R.id.listRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sortBtn = findViewById(R.id.sortBtn);
sortBtn.setVisibility(View.GONE);

        searched = findViewById(R.id.searched);
        searched.setText("Bạn đã tìm kiếm " + searchData);
        dishes = new ArrayList<>();
        dishes = db.getDishesByString(searchData);
        searchAdapter = new SearchAdapter(this, dishes, searchData);
        recyclerView.setAdapter(searchAdapter);


    }

}