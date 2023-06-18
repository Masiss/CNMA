package com.example.cnma.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.Model.Dish;
import com.example.cnma.R;
import com.example.cnma.db.CookingGuideDB;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    Button favoriteBtn;
    ArrayList<String> tutorial;
    TextView title, desc, material;
    TutorialAdapter tutorialAdapter;
    RecyclerView recyclerView;
    CookingGuideDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        recyclerView = findViewById(R.id.tutorialRv);

        db = new CookingGuideDB(this);
        //lấy dữ liệu
        Dish dish = (Dish) getIntent().getSerializableExtra("dish");
        //ánh xạ
        title = findViewById(R.id.dish_title);
        desc = findViewById(R.id.dish_description);
        material = findViewById(R.id.dish_material);
        favoriteBtn = findViewById(R.id.favoriteBtn);
        //đổ dữ liệu
        title.setText(dish.getName());
        desc.setText(dish.getDesc());
        material.setText(dish.getMaterial());


        //tạo adapter để hiển thị các bước
        tutorial = db.getTutorials(dish);
        tutorialAdapter = new TutorialAdapter(this, tutorial);


        if (db.isInFavorite(dish)) {
            favoriteBtn.setText("Xóa khỏi mục yêu thích");
            favoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteFavorite(dish);
                    Toast.makeText(getApplicationContext(), "Đã xóa khỏi mục yêu thích", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            //nếu chưa có trong mục yêu thích thì cho set onclick để người dùng có thể thêm vào
            favoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.addFavorite(dish);
                    Toast.makeText(getApplicationContext(), "Đã thêm món ăn vào mục yêu thích", Toast.LENGTH_SHORT).show();
                }
            });
        }

        YouTubePlayerView youTubePlayerView = findViewById(R.id.videoView);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = dish.getVideo();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(tutorialAdapter);

    }
}