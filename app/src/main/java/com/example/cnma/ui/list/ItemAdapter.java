package com.example.cnma.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.ui.detail.ShowActivity;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Dish> mDishes;
    private ListAdapter listAdapter;

    public ItemAdapter(ListAdapter listAdapter, List<Dish> mList) {
        this.mDishes = mList;
        this.listAdapter = listAdapter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dish dish = mDishes.get(position);

        Bitmap selectedImage = BitmapFactory.decodeByteArray(dish.getImage(), 0, dish.getImage().length);
        holder.imageView.setImageBitmap(selectedImage);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(listAdapter.context, ShowActivity.class);
                intent.putExtra("dish", dish);
                listAdapter.context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mDishes != null) {
            return mDishes.size();

        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh Xạ
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
