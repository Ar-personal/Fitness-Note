package com.reitech.gym.ui.tracker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkoutInputFragment extends Fragment {

    private Workout.WorkoutEnum type;
    private boolean empty;
    private LinearLayout root;
    private Button save, clear;
    private EditText first, second;
    private List<String[]> workoutHistory;
    private RecyclerView workoutInputList;
    private String workoutName;

    public WorkoutInputFragment(String workoutName, Workout.WorkoutEnum type) {
        this.type = type;
        this.empty = empty;
        this.workoutName = workoutName;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_input, container, false);




        root = view.findViewById(R.id.workout_input_layout);
        save = view.findViewById(R.id.saveButton);
        clear = view.findViewById(R.id.clearButton);
        first = new EditText(getContext());
        second = new EditText(getContext());
        createLayout();

        workoutHistory = new ArrayList<>();
        workoutInputList = view.findViewById(R.id.workoutInputList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        workoutInputList.setLayoutManager(layoutManager);
        WorkoutInputAdapter workoutInputAdapter = new WorkoutInputAdapter(workoutHistory);
        workoutInputList.setAdapter(workoutInputAdapter);

        //start gathering old workout history of the day
        List<String[]> lines = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle != null){
            for(String key : bundle.keySet()){
                if(key.contains("list"))
                    lines.add((String[]) bundle.get(key));
            }
        }
        //add older workouts to recycler view
        // TODO: 20/03/2022 unify string indices across the board 
        for(int i = 0; i < lines.size(); i++){
            String[] toAdd = new String[8];
            String[] iterator = lines.get(i);
            toAdd[3] = iterator[1];
            toAdd[4] = iterator[2];
            workoutHistory.add(toAdd);
            workoutInputAdapter.notifyDataSetChanged();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] line = new String[8];
                //trophy code needs to be handled here
                line[2] = "trophy";
                line[3] = first.getText().toString();
                line[4] = second.getText().toString();
                //time units for now
                line[5] = "";
                line[6] = "";
                line[7] = "";

                if(!first.getText().toString().isEmpty() && !second.getText().toString().isEmpty()){
                    workoutHistory.add(line);

                    Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("TRACKER");

                    workoutInputAdapter.notifyDataSetChanged();

                    TrackerFragment trackerFragment = (TrackerFragment) f;
                    trackerFragment.setWorkout(workoutName, line, false);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first.setText("");
                second.setText("");
            }
        });

        return view;
    }

    private void createLayout() {
        switch (type){
            case WEIGHT_AND_REPS:
                createWeightAndRepsLayout();
        }


    }

    private void createWeightAndRepsLayout() {

        LinearLayout weightLabel = createInputLabel("Weight (Kgs)");
        LinearLayout weightInput = createIntInputLayout(first);
        LinearLayout repsLabel = createInputLabel("Reps");
        LinearLayout repsInput = createIntInputLayout(second);


        root.addView(weightLabel);
        root.addView(weightInput);
        root.addView(repsLabel);
        root.addView(repsInput);

    }

    private LinearLayout createInputLabel(String label){
        LinearLayout weight = new LinearLayout(getContext());
        weight.setOrientation(LinearLayout.HORIZONTAL);
        weight.setWeightSum(1);

        TextView weightText =  new TextView(getContext());
        weightText.setText(label);
        weightText.setTextSize(20);
        weightText.setTextColor(getResources().getColor(R.color.dark));
        weightText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        weight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        weightText.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));

        weight.addView(weightText);
        return weight;
    }

    private LinearLayout createIntInputLayout(final EditText weightInt) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setWeightSum(4);

        ImageButton left = new ImageButton(getContext());
        left.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down);
        left.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        left.setBackgroundColor(getResources().getColor(R.color.dark));

        weightInt.setText("");
        weightInt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        weightInt.setInputType(InputType.TYPE_CLASS_NUMBER);
        weightInt.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,2.0f));

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double i = 0;
                try {
                    i = Double.parseDouble(weightInt.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                i -= 2.5;
                weightInt.setText(Double.toString(i));
            }
        });

        ImageButton right = new ImageButton(getContext());
        right.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up);
        right.setBackgroundColor(getResources().getColor(R.color.dark));
        right.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double i = 0;
                try {
                    i = Double.parseDouble(weightInt.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                i += 2.5;
                weightInt.setText(Double.toString(i));

            }
        });

        linearLayout.addView(left);
        linearLayout.addView(weightInt);
        linearLayout.addView(right);
        return  linearLayout;
    }
}
