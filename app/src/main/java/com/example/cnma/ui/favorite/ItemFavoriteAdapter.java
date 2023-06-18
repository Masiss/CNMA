package com.example.cnma.ui.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.db.CookingGuideDB;
import com.example.cnma.ui.detail.ShowActivity;

import java.util.List;

public class ItemFavoriteAdapter extends RecyclerView.Adapter<ItemFavoriteAdapter.MyViewHolder> {

    private List<Dish> mDishes;
    private Context context;
    public CookingGuideDB cookingGuideDB;

    public ItemFavoriteAdapter(Context context, List<Dish> mDishes) {
        this.mDishes = mDishes;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemFavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFavoriteAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        //gán text cho tên và mô tả món ăn
        holder.name.setText(mDishes.get(position).getName());
        holder.desc.setText(mDishes.get(position).getDesc());
        Bitmap selectedImage = BitmapFactory.decodeByteArray(mDishes.get(position).getImage(), 0, mDishes.get(position).getImage().length);

        holder.imageView.setImageBitmap(selectedImage);
        cookingGuideDB = new CookingGuideDB(context);

        //set sự kiện onclick cho layout gồm tên và mô tả
        //để người dùng nhấn vào có thể show được chi tiết món ăn
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("dish", mDishes.get(position));
                context.startActivity(intent);
            }
        });
        //gán sự kiện cho nút yêu thích để người dùng có thể xóa khỏi mục yêu thích
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingGuideDB.deleteFavorite(mDishes.get(position));
                Toast.makeText(context, "Đã bỏ khỏi danh sách yêu thích", Toast.LENGTH_SHORT);
                resetData();
            }
        });
    }

    void resetData() {
        mDishes.clear();
        mDishes = cookingGuideDB.getFavorites();
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
        RelativeLayout relativeLayout;
        Button button;
        TextView name, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh Xạ
            imageView = itemView.findViewById(R.id.imageView2);
            relativeLayout = itemView.findViewById(R.id.detail);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            button = itemView.findViewById(R.id.heartBtn);


        }
    }

}
