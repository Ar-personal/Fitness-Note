package com.reitech.gym.ui.exerciselist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.tracker.Workout;

class ItemViewHolder extends RecyclerView.ViewHolder{
    final ImageView imgItem;
    final View rootView;
    final TextView tvItem;

    ItemViewHolder(@NonNull View view) {
        super(view);

        rootView = view;
        tvItem = view.findViewById(R.id.exercise_item);
        imgItem = view.findViewById(R.id.exercise_image);
    }
}
