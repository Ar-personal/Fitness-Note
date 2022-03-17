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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.tracker.TrackerFragment;
import com.reitech.gym.ui.tracker.Workout;
import com.reitech.gym.ui.tracker.WorkoutInputFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class AddExerciseFragment extends Fragment implements ExerciseSection.ClickListener, SearchView.OnQueryTextListener, Filterable {

    final Map<String, List<String>> exerciseMap = new LinkedHashMap<>();

    private SectionedRecyclerViewAdapter sectionedAdapter;
    private List<String> searchList;
    private RecyclerView recyclerView;
    private Fragment invoker;
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
        List<String> exerciseListChest = new ArrayList<>();
        exerciseListAbs.add("Push-up");

        exerciseListChest.add("Bench Press");

        exerciseMap.put("Abs", exerciseListAbs);
        exerciseMap.put("Chest", exerciseListChest);

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

    @Override
    public void onItemRootViewClicked(@NonNull final ExerciseSection section, final int itemAdapterPosition, final String exerciseName){
//        Bundle bundle = getArguments();
//        bundle.putString("workoutname", exerciseName);

//        Fragment f = getParentFragmentManager().findFragmentByTag("addExercise");
//        TrackerFragment ff = (TrackerFragment) f;
        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("TRACKER");


        TrackerFragment trackerFragment = (TrackerFragment) f;
        trackerFragment.addWorkout(exerciseName);

        Fragment workout = new WorkoutInputFragment(Workout.WorkoutEnum.WEIGHT_AND_REPS);
        getParentFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, workout).addToBackStack(null).commit();

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
