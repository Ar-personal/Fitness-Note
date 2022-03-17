package com.reitech.gym.ui.exerciselist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;

class HeaderViewHolder extends RecyclerView.ViewHolder {

    final View rootView;
    final TextView tvTitle;
    final ImageView imgArrow;

    HeaderViewHolder(@NonNull View view) {
        super(view);

        rootView = view;
        tvTitle = view.findViewById(R.id.exercise_header);
        imgArrow = view.findViewById(R.id.exercise_image);
    }
}
