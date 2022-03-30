package com.reitech.gym.ui.exerciselist;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.TrackerFragment;
import com.reitech.gym.ui.tracker.Workout;
import com.reitech.gym.ui.tracker.WorkoutInputFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class AddExerciseFragment extends Fragment implements ExerciseSection.ClickListener, SearchView.OnQueryTextListener, Filterable{

    final Map<String, List<String>> exerciseMap = new LinkedHashMap<>();

    private SectionedRecyclerViewAdapter sectionedAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceStat) {

        final View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);

        initExerciseList();

        sectionedAdapter = new SectionedRecyclerViewAdapter();
        for (Map.Entry<String, List<String>> entry : exerciseMap.entrySet()){
                sectionedAdapter.addSection(new ExerciseSection(entry.getKey(), entry.getValue(), this));
        }

        SearchView searchView = view.findViewById(R.id.exerciseListSearch);
        searchView.setOnQueryTextListener(this);

        recyclerView = view.findViewById(R.id.exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionedAdapter);


        return view;
    }

    private void initExerciseList() {
        List<String> exerciseListAbs = new ArrayList<>();
        List<String> exerciseListBack = new ArrayList<>();
        List<String> exerciseListBiceps = new ArrayList<>();
        List<String> exerciseListCardio = new ArrayList<>();
        List<String> exerciseListChest = new ArrayList<>();
        List<String> exerciseListLegs = new ArrayList<>();
        List<String> exerciseListShoulders = new ArrayList<>();
        List<String> exerciseListTriceps = new ArrayList<>();


        exerciseListAbs.addAll(Arrays.asList("Ab-Wheel", "Cable Crunch", "Crunch", "Crunch Machine", "Dragon Flag", "Hanging Knee Raise", "Hanging Leg Raise", "Plank", "Side Plank"));
        exerciseListBack.addAll(Arrays.asList("Barbell Row", "Barbell Shrug", "Chin-Up", "Dead-lift", "Dip Assist", "Diverging Lat Pull-down", "Dumbbell Row", "Good Morning", "Hammer Strength Row", "Lat Pull-down", "Machine Shrug", "Neutral Chin Up", "Overhead Cable Pull", "Pendlay Row", "Pull-Up", "Pull-Up Assist", "Rack Pull", "Seated Cable Row", "Straight-Arm Cable Push-down", "T-Bar Row"));
        exerciseListBiceps.addAll(Arrays.asList("Barbell Curl", "Cable Curl", "Dumbbell Concentration Curl", "Dumbbell Curl", "Dumbbell Hammer Curl", "Dumbbell Preach Curl", "EZ-Bar Curl", "EZ-Bar Preacher Curl", "Seated Dip", "Seated Incline Dumbbell Curl", "Seated Machine Curl"));
        exerciseListCardio.addAll(Arrays.asList("Cycling", "Elliptical Trainer", "Rowing Machine", "Running (Outdoor)", "Running (Treadmill)", "Stair Climber", "Stationary Bike", "Swimming", "Walking"));
        exerciseListChest.addAll(Arrays.asList("Cable Crossover", "Converging Chest Press", "Decline Barbell Bench Press", "Decline Hammer Strength Chest Press", "Flat Barbell Bench Press", "Flat Dumbbell Bench Press", "Flat Dumbbell Fly", "Incline Dumbbell Fly", "Incline Hammer Strength Chest Press", "Push-Up", "Seated Machine Cable Fly", "Seated Machine Fly"));
        exerciseListLegs.addAll(Arrays.asList("Barbell Calf Raise", "Barbell Front Squat", "Barbell Glute Bridge", "Barbell Squat", "Donkey Calf Raise", "Glute-Ham Raise", "Hack Squat", "Leg Extension Machine", "Leg Press", "Lying Leg Curl Machine", "Romanian Dead-life", "Seated Calf Raise Machine", "Seated Leg Curl Machine", "Standing Calf Raise Machine", "Still-Legged Dead-lift", "Sumo Dead-lift"));
        exerciseListShoulders.addAll(Arrays.asList("Arnold Dumbbell Press", "Behind The Neck Barbell Press", "Cable Face Pull", "Front Dumbbell Raise", "Hammer Strength Shoulder Press", "Lateral Dumbbell Raise", "Lateral Machine Raise", "Log Press", "One-Arm Standing Dumbbell Press", "Overhead Press", "Push Press", "Rear Delt Dumbbell Raise", "Rear Delt Machine Fly", "Seated Dumbbell Lateral Raise", "Seated Dumbbell Press", "Smith Machine Overhead Press"));
        exerciseListTriceps.addAll(Arrays.asList("Cable Overhead Triceps Extension", "Close Grip Barbell Bench Press", "Dumbbell Overhead Triceps Extension", "EZ-Bar Skullcrusher", "Lying Triceps Extension", "Parallel Bar Triceps Dip", "Ring Dip", "Rope Push Down", "Smith Machine Close Grip Bench Press", "Triceps Extension Machine", "V-Bar Push Down"));

        exerciseMap.put("Abs", exerciseListAbs);
        exerciseMap.put("Back", exerciseListBack);
        exerciseMap.put("Biceps", exerciseListBiceps);
        exerciseMap.put("Cardio", exerciseListCardio);
        exerciseMap.put("Chest", exerciseListChest);
        exerciseMap.put("Legs", exerciseListLegs);
        exerciseMap.put("Shoulders", exerciseListShoulders);
        exerciseMap.put("Triceps", exerciseListTriceps);

    }

    @Override
    public void onHeaderRootViewClicked(@NonNull @NotNull ExerciseSection section) {
        final SectionAdapter sectionAdapter = sectionedAdapter.getAdapterForSection(section);

        // store info of current section state before changing its state
        final boolean wasExpanded = section.isExpanded();
        final int previousItemsTotal = section.getContentItemsTotal();

        section.setExpanded(!wasExpanded);
        sectionAdapter.notifyHeaderChanged();

        if (wasExpanded) {
            sectionAdapter.notifyItemRangeRemoved(0, previousItemsTotal);
        } else {
            sectionAdapter.notifyAllItemsInserted();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemRootViewClicked(@NonNull final ExerciseSection section, final int itemAdapterPosition, final String exerciseName){

        Fragment workout = new WorkoutInputFragment(exerciseName, Workout.WorkoutEnum.WEIGHT_AND_REPS);

        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("TRACKER");
        TrackerFragment trackerFragment = (TrackerFragment) f;
        List<WorkoutLine> toAdd = trackerFragment.getExerciseLinesFromExercise(exerciseName);

        Bundle bundle = new Bundle();
        for(int i = 0; i < toAdd.size(); i++){
            bundle.putSerializable("list" + i, toAdd.get(i));
        }

        workout.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, workout).addToBackStack(null).commit();
        onDestroy();



        //search for all lines in tracker for specific exercise name and return exercise lines that already exist

        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        boolean changed = false;
        List<String> filteredList = new ArrayList<>();
        sectionedAdapter.removeAllSections();

        for (Map.Entry<String, List<String>> entry : exerciseMap.entrySet()){
            for(String x : entry.getValue()){
                if(x.toLowerCase().contains(s.toLowerCase())) {
                    filteredList.add(x);
                    changed = true;
                }
            }
        }

        if(changed){
            sectionedAdapter.addSection(new ExerciseSection("Search Results", filteredList, this));
        }

        if(s.equals("")){
            sectionedAdapter.removeAllSections();
            for (Map.Entry<String, List<String>> entry : exerciseMap.entrySet()){
                sectionedAdapter.addSection(new ExerciseSection(entry.getKey(), entry.getValue(), this));
            }
        }

        sectionedAdapter.notifyDataSetChanged();
        return changed;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

}
