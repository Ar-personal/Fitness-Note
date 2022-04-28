package com.reitech.gym.ui.tracker.workout_input;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.LayoutHelpers.TextDrawable;
import com.reitech.gym.ui.data.DatabaseHelper;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.Workout;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HistoryTab extends Fragment {
    private String workoutName;
    private Workout.WorkoutEnum type;
    private RecyclerView workoutInputList;
    private HistoryAdapter historyAdapter;
    List<WorkoutLine> workoutHistory;
    public HistoryTab(String workoutName, Workout.WorkoutEnum type) {
        this.workoutName = workoutName;
        this.type = type;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.history_tab, container, false);


        workoutHistory = new ArrayList<>();
        workoutInputList = view.findViewById(R.id.workout_full_history);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        workoutInputList.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(workoutHistory);
        workoutInputList.setAdapter(historyAdapter);

        List<WorkoutLine> lines = DatabaseHelper.getWorkoutEntireHistory(workoutName);
        lines.sort(Comparator.comparing(WorkoutLine::getDate));
        LocalDate dateSection = LocalDate.now();
        LocalDate oldDate = LocalDate.MAX;
        for(int i = 0; i < lines.size(); i++){
            dateSection = LocalDate.parse(lines.get(i).date);
            if(!dateSection.isEqual(oldDate)) {
                dateDivider(dateSection, lines.get(i));
                oldDate = dateSection;
            }
            workoutHistory.add(lines.get(i));
            historyAdapter.notifyDataSetChanged();
        }

        return view;
    }

    //hacky way of adding divider by editing workoutline item
    public void dateDivider(LocalDate date, WorkoutLine wl){
        WorkoutLine workoutLine = new WorkoutLine();
        workoutLine.category = "DIVIDER";

        workoutLine.date = date.toString();
        workoutHistory.add(workoutLine);

        historyAdapter.notifyDataSetChanged();
    }
}
