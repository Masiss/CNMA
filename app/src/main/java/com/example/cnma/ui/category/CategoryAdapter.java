package com.example.cnma.ui.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.db.CookingGuideDB;
import com.example.cnma.ui.detail.ShowActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Dish> mDishes;
    private Context context;
    public CookingGuideDB cookingGuideDB;

    public CategoryAdapter(Context context, List<Dish> mDishes) {
        this.mDishes = mDishes;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new CategoryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Dish dish = mDishes.get(position);

        holder.name.setText(dish.getName());
        holder.desc.setText(dish.getDesc());
        holder.imageView.setImageBitmap(
                BitmapFactory.decodeByteArray(
                        dish.getImage(), 0, dish.getImage().length));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("dish", dish);
                context.startActivity(intent);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingGuideDB = new CookingGuideDB(context);
                cookingGuideDB.deleteFavorite(dish);
                Toast.makeText(context, "Đã bỏ khỏi danh sách yêu thích", Toast.LENGTH_SHORT);
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
        LinearLayout relativeLayout;
        ImageView button;
        TextView name, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh Xạ
            imageView = itemView.findViewById(R.id.searchImg);
            relativeLayout = itemView.findViewById(R.id.show);
            name = itemView.findViewById(R.id.searchName);
            desc = itemView.findViewById(R.id.searchDesc);
            button = itemView.findViewById(R.id.searchFavorite);


        }
    }
}
