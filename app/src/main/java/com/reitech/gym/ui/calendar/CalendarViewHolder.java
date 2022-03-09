package com.reitech.gym.ui.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDaytext);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    public void onClick(View view){
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
