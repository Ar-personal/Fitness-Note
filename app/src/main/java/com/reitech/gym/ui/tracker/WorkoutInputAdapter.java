package com.reitech.gym.ui.tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkoutInputAdapter extends RecyclerView.Adapter<WorkoutViewHolder>{
    List<String[]> lines;

    public WorkoutInputAdapter(List<String[]> lines) {
        this.lines = lines;
    }

    @NonNull
    @NotNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_input_item, parent, false);

        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkoutViewHolder holder, int position) {
        String[] list = lines.get(position);
        holder.trophy.setText(list[2]);
        holder.first.setText(list[3]);
        holder.second.setText(list[4]);
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }
}
