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
import com.reitech.gym.ui.data.WorkoutLine;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkoutInputAdapter extends RecyclerView.Adapter<WorkoutViewHolder>{
    List<WorkoutLine> lines;

    public WorkoutInputAdapter(List<WorkoutLine> lines) {
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
        WorkoutLine list = lines.get(position);
        switch (list.category){
            case "WEIGHT_AND_TIME":
                holder.first.setText(String.valueOf(list.weight));
                holder.second.setText(String.valueOf(list.time));
                break;
            case "TIME_AND_DISTANCE":
                holder.first.setText(String.valueOf(list.time));
                holder.second.setText(String.valueOf(list.distance + list.distanceUnit));
                break;
            default:
                holder.first.setText(String.valueOf(list.weight));
                holder.second.setText(String.valueOf(list.reps));
                break;
        }
        holder.trophy.setText(String.valueOf(list.wid));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }
}
