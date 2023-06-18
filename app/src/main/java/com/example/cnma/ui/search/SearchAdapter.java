package com.example.cnma.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private List<Dish> mDishes;
    private Context context;
    public CookingGuideDB db;
    public String searchData;

    public SearchAdapter(Context context, List<Dish> mDishes, String searchData) {
        this.mDishes = mDishes;
        this.context = context;
        this.searchData = searchData;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dish dish = mDishes.get(position);
        db = new CookingGuideDB(context);
        Bitmap selectedImage = BitmapFactory.decodeByteArray(mDishes.get(position).getImage(), 0, mDishes.get(position).getImage().length);
        holder.imageView.setImageBitmap(selectedImage);

        holder.name.setText(dish.getName());
        holder.desc.setText(dish.getDesc());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("dish", mDishes.get(position));
                context.startActivity(intent);
            }
        });

        if (db.isInFavorite(dish)) {
            Drawable drawable = this.context.getDrawable(R.drawable.baseline_favorite_24);
            holder.button.setImageDrawable(drawable);
        } else {
            Drawable drawable = this.context.getDrawable(R.drawable.favorite_icon);
            holder.button.setImageDrawable(drawable);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.isInFavorite(dish)) {
                    db.deleteFavorite(mDishes.get(position));
                    Toast.makeText(context, "Đã bỏ khỏi danh sách yêu thích", Toast.LENGTH_SHORT);
                    resetData();

                } else {
                    db.addFavorite(mDishes.get(position));
                    Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT);
                    resetData();
                }
            }
        });
    }

    void resetData() {
        mDishes.clear();
        db = new CookingGuideDB(context);
        mDishes = db.getDishesByString(searchData);
        this.notifyDataSetChanged();
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
