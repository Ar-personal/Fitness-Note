package com.reitech.gym.ui.tracker.workout_input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reitech.gym.R;
import com.reitech.gym.ui.tracker.Workout;

import org.jetbrains.annotations.NotNull;

public class StatsTab extends Fragment {
    private Workout.WorkoutEnum type;
    private String workoutName;
    public StatsTab(String workoutName, Workout.WorkoutEnum type) {
        this.workoutName = workoutName;
        this.type = type;
    }
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.history_tab, container, false);




        return view;
    }
}
