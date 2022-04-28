package com.reitech.gym.ui.exerciselist;

import android.os.Build;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.TrackerFragment;
import com.reitech.gym.ui.tracker.Workout;
import com.reitech.gym.ui.tracker.workout_input.WorkoutInputFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class AddExerciseFragment extends Fragment implements ExerciseSection.ClickListener, SearchView.OnQueryTextListener, Filterable{

    final Map<String, List<Workout>> exerciseMap = new LinkedHashMap<>();

    private SectionedRecyclerViewAdapter sectionedAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceStat) {

        final View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);

        initExerciseList();

        sectionedAdapter = new SectionedRecyclerViewAdapter();
        for (Map.Entry<String, List<Workout>> entry : exerciseMap.entrySet()){
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
        List<Workout> exerciseListAbs = new ArrayList<>();
        List<Workout> exerciseListBack = new ArrayList<>();
        List<Workout> exerciseListBiceps = new ArrayList<>();
        List<Workout> exerciseListCardio = new ArrayList<>();
        List<Workout> exerciseListChest = new ArrayList<>();
        List<Workout> exerciseListLegs = new ArrayList<>();
        List<Workout> exerciseListShoulders = new ArrayList<>();
        List<Workout> exerciseListTriceps = new ArrayList<>();


        exerciseListAbs.addAll(Arrays.asList(new Workout("Ab-Wheel", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Cable Crunch", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Crunch", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Crunch Machine", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dragon Flag", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Hanging Knee Raise", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Hanging Leg Raise", "Abs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Plank", "Abs", Workout.WorkoutEnum.WEIGHT_AND_TIME), new Workout("Side Plank", "Abs", Workout.WorkoutEnum.WEIGHT_AND_TIME)));
        exerciseListBack.addAll(Arrays.asList(new Workout("Barbell Row", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Barbell Shrug", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Chin-Up", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dead-lift", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dip Assist", "Back", Workout.WorkoutEnum.INVERTEDWEIGHT_AND_REPS), new Workout("Diverging Lat Pull-down", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dumbbell Row", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Good Morning", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Hammer Strength Row", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Lat Pull-down", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Machine Shrug", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Neutral Chin Up", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Overhead Cable Pull", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Pendlay Row", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Pull-Up", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Pull-Up Assist", "Back", Workout.WorkoutEnum.INVERTEDWEIGHT_AND_REPS), new Workout("Rack Pull", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Cable Row", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Straight-Arm Cable Push-down", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("T-Bar Row", "Back", Workout.WorkoutEnum.WEIGHT_AND_REPS)));
        exerciseListBiceps.addAll(Arrays.asList(new Workout("Barbell Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Cable Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dumbbell Concentration Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dumbbell Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dumbbell Hammer Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dumbbell Preach Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("EZ-Bar Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("EZ-Bar Preacher Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Dip", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Incline Dumbbell Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Machine Curl", "Biceps", Workout.WorkoutEnum.WEIGHT_AND_REPS)));
        exerciseListCardio.addAll(Arrays.asList(new Workout("Cycling", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Elliptical Trainer", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Rowing Machine", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Running (Outdoor)", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Running (Treadmill)", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Stair Climber", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Stationary Bike", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Swimming", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE), new Workout("Walking", "Cardio", Workout.WorkoutEnum.TIME_AND_DISTANCE)));
        exerciseListChest.addAll(Arrays.asList(new Workout("Cable Crossover", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Converging Chest Press", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Decline Barbell Bench Press", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Decline Hammer Strength Chest Press", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Flat Barbell Bench Press", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Flat Dumbbell Bench Press", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Flat Dumbbell Fly", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Incline Dumbbell Fly", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Incline Hammer Strength Chest Press", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Push-Up", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Machine Cable Fly", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Machine Fly", "Chest", Workout.WorkoutEnum.WEIGHT_AND_REPS)));
        exerciseListLegs.addAll(Arrays.asList(new Workout("Barbell Calf Raise", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS) , new Workout("Barbell Front Squat", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Barbell Glute Bridge", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Barbell Squat", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Donkey Calf Raise", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Glute-Ham Raise", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Hack Squat", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Leg Extension Machine", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Leg Press", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Lying Leg Curl Machine", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Romanian Dead-life", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Calf Raise Machine", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Leg Curl Machine", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Standing Calf Raise Machine", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Still-Legged Dead-lift", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Sumo Dead-lift", "Legs", Workout.WorkoutEnum.WEIGHT_AND_REPS)));
        exerciseListShoulders.addAll(Arrays.asList(new Workout("Arnold Dumbbell Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Behind The Neck Barbell Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Cable Face Pull", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Front Dumbbell Raise", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Hammer Strength Shoulder Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Lateral Dumbbell Raise", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Lateral Machine Raise", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Log Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("One-Arm Standing Dumbbell Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Overhead Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Push Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Rear Delt Dumbbell Raise", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Rear Delt Machine Fly", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Seated Dumbbell Lateral Raise", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS) , new Workout("Seated Dumbbell Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Smith Machine Overhead Press", "Shoulders", Workout.WorkoutEnum.WEIGHT_AND_REPS)));
        exerciseListTriceps.addAll(Arrays.asList(new Workout("Cable Overhead Triceps Extension", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Close Grip Barbell Bench Press", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Dumbbell Overhead Triceps Extension", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("EZ-Bar Skullcrusher", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Lying Triceps Extension", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Parallel Bar Triceps Dip", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Ring Dip", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Rope Push Down", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Smith Machine Close Grip Bench Press", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("Triceps Extension Machine", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS), new Workout("V-Bar Push Down", "Triceps", Workout.WorkoutEnum.WEIGHT_AND_REPS)));

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


        Fragment workout = new WorkoutInputFragment(exerciseName);

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
        List<Workout> filteredList = new ArrayList<>();
        sectionedAdapter.removeAllSections();

        for (Map.Entry<String, List<Workout>> entry : exerciseMap.entrySet()){
            for(Workout x : entry.getValue()){
                if(x.getWorkoutName().toLowerCase().contains(s.toLowerCase())) {
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
            for (Map.Entry<String, List<Workout>> entry : exerciseMap.entrySet()){
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
