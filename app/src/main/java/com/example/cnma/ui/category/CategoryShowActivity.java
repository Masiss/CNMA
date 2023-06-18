package com.example.cnma.ui.category;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Category;
import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.db.CookingGuideDB;

import java.util.ArrayList;
import java.util.Collections;

public class CategoryShowActivity extends AppCompatActivity {
    private ArrayList<Dish> dishes;
    private CookingGuideDB db;
    private RecyclerView recyclerView;
    private TextView category;
    private CategoryAdapter categoryAdapter;
    private Button sortBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.listRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        category = findViewById(R.id.searched);

        dishes = new ArrayList<>();
        Category category1 = (Category) getIntent().getSerializableExtra("category");
        category.setText("Danh sách các món " + category1.getName());
        db = new CookingGuideDB(this);
//        if (db.isEmptyCategory(category1.getId())) {
        dishes = db.getDishesByCategory(category1.getId(), null);
        categoryAdapter = new CategoryAdapter(this, dishes);
        recyclerView.setAdapter(categoryAdapter);
//        }
        sortBtn = findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (sortBtn.getText() == "A-Z") {
                    Collections.sort(dishes, Dish.StringAscComparator);
                    categoryAdapter.notifyDataSetChanged();
                    sortBtn.setText("Z-A");
                }else{
                    Collections.sort(dishes, Dish.StringDescComparator);
                    categoryAdapter.notifyDataSetChanged();
                    sortBtn.setText("A-Z");
                }

            }
        });
    }
}
