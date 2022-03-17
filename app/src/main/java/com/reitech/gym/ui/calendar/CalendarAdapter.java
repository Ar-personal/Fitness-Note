package com.reitech.gym.ui.calendar;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.tracker.TrackerFragment;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>{

    private ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private LocalDate localDate;
    private int month, year;
    private Activity activity;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, LocalDate localDate, Activity activity) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.localDate = localDate;
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        monthYearFromDate(localDate);
        return new CalendarViewHolder(view, onItemListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull CalendarViewHolder holder, int position) {
        LocalDate l = null;
        //check if date is empty otherwise localdate error
        if (daysOfMonth.get(position) != "") {
            l = localDate.withDayOfMonth(Integer.parseInt(daysOfMonth.get(position)));
        }
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        LocalDate finalL = l;
        holder.dayOfMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(localDate != null) {
                    Fragment trackerFragment = new TrackerFragment(finalL);
                    ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction()
                            .add(R.id.nav_host_fragment_activity_main, trackerFragment, "TRACKER").addToBackStack(null).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, String sayText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void monthYearFromDate(LocalDate date){
        month = date.getMonthValue();
        year = date.getYear();
    }
}
