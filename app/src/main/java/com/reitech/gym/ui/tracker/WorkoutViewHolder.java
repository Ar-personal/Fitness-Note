package com.reitech.gym.ui.tracker;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;

import org.jetbrains.annotations.NotNull;

public class WorkoutViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout layout;
    public TextView trophy, first, second, date;

    public WorkoutViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        layout = itemView.findViewById(R.id.inputItemLayout);
        trophy = itemView.findViewById(R.id.trophyItem);
        first = itemView.findViewById(R.id.firstItem);
        second = itemView.findViewById(R.id.secondItem);
    }
}
