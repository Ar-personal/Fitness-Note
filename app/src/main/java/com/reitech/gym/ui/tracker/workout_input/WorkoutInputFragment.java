package com.reitech.gym.ui.tracker.workout_input;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.data.DatabaseHelper;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.TrackerFragment;
import com.reitech.gym.ui.tracker.Workout;
import com.reitech.gym.ui.tracker.workout_input.WorkoutInputAdapter;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutInputFragment extends Fragment {

    private Workout.WorkoutEnum type;
    private String workoutName;
    private LocalDate date;


    public WorkoutInputFragment(String workoutName, LocalDate date) {
        this.type = Workout.getCategoryFromExerciseName(workoutName);
        this.workoutName = workoutName;
        this.date = date;
    }




    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_input, container, false);

        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setVisibility(View.INVISIBLE);

        List<WorkoutLine> entireHistory = DatabaseHelper.getWorkoutEntireHistory(workoutName);



        TabLayout tabLayout = view.findViewById(R.id.tracker_tab);

        Fragment input = new InputTab(workoutName, type, date);
        List<WorkoutLine> lines = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle != null){
            for(String key : bundle.keySet()){
                if(key.contains("list"))
                    lines.add((WorkoutLine) bundle.get(key));
            }
        }

        input.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.simpleFrameLayout, input).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;

                switch (tab.getPosition()) {
                    case 0:
                        fragment = new InputTab(workoutName, type, date);
                        fragment.setArguments(bundle);
                        break;
                    case 1:
                        fragment = new HistoryTab(workoutName, type);
                        break;
                    case 2:
                        fragment = new StatsTab(workoutName, type);
                        break;
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.simpleFrameLayout, fragment).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }
}
