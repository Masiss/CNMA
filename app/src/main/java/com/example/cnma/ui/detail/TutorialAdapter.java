package com.example.cnma.ui.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnma.R;

import java.util.ArrayList;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialHolder> {
    private Context context;
    private ArrayList<String> tutorial;

    public TutorialAdapter(Context context, ArrayList<String> tutorial) {
        this.tutorial = tutorial;
        this.context = context;
    }

    @NonNull
    @Override
    public TutorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.tutorial, parent, false);
        return new TutorialAdapter.TutorialHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull TutorialAdapter.TutorialHolder holder, int position) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        layoutManager.setInitialPrefetchItemCount(getItemCount());
        Integer step=position+1;
        holder.step.setText("Bước " + step + ": " + tutorial.get(position));

    }

    @Override
    public int getItemCount() {
        if (tutorial != null) {
            return tutorial.size();

        }
        return 0;
    }

    public static class TutorialHolder extends RecyclerView.ViewHolder {

        TextView step;

        public TutorialHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh Xạ
            step = itemView.findViewById(R.id.step);

        }
    }
}
