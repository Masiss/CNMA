package com.example.cnma.ui.list;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Category;
import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.db.CookingGuideDB;
import com.example.cnma.ui.category.CategoryShowActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<Category> categoryList;
    public Context context;

    public CookingGuideDB db;
    public RecyclerView.RecycledViewPool viewPool;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView list_title;
        public RecyclerView itemRv;
        public Button category;

        public MyViewHolder(View itemView) {
            super(itemView);
            list_title = (TextView) itemView.findViewById(R.id.list_title);
            itemRv = itemView.findViewById(R.id.itemRv);
            category = itemView.findViewById(R.id.category);
        }
    }

    public ListAdapter(List<Category> exampleList, Context context) {
        this.categoryList = exampleList;
        this.context = context;
        this.viewPool = new RecyclerView.RecycledViewPool();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();

        } else return 0;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category currentItem = categoryList.get(position);
        db = new CookingGuideDB(context);

        holder.list_title.setText(currentItem.getName().toUpperCase());

        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryShowActivity.class);
                intent.putExtra("category", currentItem);
                context.startActivity(intent);
            }
        });
        List<Dish> dishes = db.getDishesByCategory(currentItem.getId(),5);

        ItemAdapter itemAdapter = new ItemAdapter(this, dishes);

        holder.itemRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.itemRv.setAdapter(itemAdapter);
        holder.itemRv.setRecycledViewPool(viewPool);
    }


}
