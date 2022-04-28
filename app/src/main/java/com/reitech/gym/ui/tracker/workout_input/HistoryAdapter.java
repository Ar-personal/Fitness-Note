package com.reitech.gym.ui.tracker.workout_input;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.WorkoutViewHolder;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<WorkoutViewHolder>{
    List<WorkoutLine> lines;

    public HistoryAdapter(List<WorkoutLine> lines) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
            case "DIVIDER":
                holder.trophy.setText("");
                holder.first.setText(LocalDate.parse(list.date).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                holder.second.setText("");
                break;
            default:
                holder.first.setText(String.valueOf(list.weight));
                holder.second.setText(String.valueOf(list.reps));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }
}

