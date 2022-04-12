package com.reitech.gym.ui.tracker;

import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.data.WorkoutLine;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkoutInputFragment extends Fragment {

    private Workout.WorkoutEnum type;
    private boolean empty;
    private LinearLayout root;
    private Button save, clear;
    private EditText weight, reps, hour, minute, second, distance;
    private Spinner unit;
    private List<WorkoutLine> workoutHistory;
    private RecyclerView workoutInputList;
    private String workoutName;

    public WorkoutInputFragment(String workoutName, Workout.WorkoutEnum type) {
        this.type = type;
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

        createLayout();

        workoutHistory = new ArrayList<>();
        workoutInputList = view.findViewById(R.id.workoutInputList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        workoutInputList.setLayoutManager(layoutManager);
        WorkoutInputAdapter workoutInputAdapter = new WorkoutInputAdapter(workoutHistory);
        workoutInputList.setAdapter(workoutInputAdapter);

        //start gathering old workout history of the day
        List<WorkoutLine> lines = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle != null){
            for(String key : bundle.keySet()){
                if(key.contains("list"))
                    lines.add((WorkoutLine) bundle.get(key));
            }
        }
        //add older workouts to recycler view
        // TODO: 20/03/2022 unify string indices across the board 
        for(int i = 0; i < lines.size(); i++){
            //to add is the order the text is added to the adapter
            WorkoutLine workoutLine = new WorkoutLine();
            //iterator is simple list of bundled data weight at 1 reps at 2
            workoutLine.distanceUnit = lines.get(i).distanceUnit;
            workoutLine.distance = lines.get(i).distance;
            workoutLine.weight = lines.get(i).weight;
            workoutLine.reps = lines.get(i).reps;
            workoutLine.time = lines.get(i).time;
            workoutLine.category = lines.get(i).category;

            workoutHistory.add(workoutLine);
            workoutInputAdapter.notifyDataSetChanged();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                WorkoutLine workoutLine = new WorkoutLine();
                //trophy code needs to be handled here
                workoutLine.exerciseName = workoutName;
                switch (type){
                    case WEIGHT_AND_REPS:
                        workoutLine.weight = Double.parseDouble(weight.getText().toString());
                        workoutLine.reps = Integer.parseInt(reps.getText().toString());
                        workoutLine.category = "WEIGHT_AND_REPS";

                        if(!weight.getText().toString().isEmpty() || !reps.getText().toString().isEmpty()){
                            workoutHistory.add(workoutLine);

                            Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("TRACKER");

                            workoutInputAdapter.notifyDataSetChanged();

                            TrackerFragment trackerFragment = (TrackerFragment) f;
                            trackerFragment.setWorkout(workoutLine, false);
                        }
                        break;
                    case WEIGHT_AND_TIME:
                        workoutLine.weight = Double.parseDouble(weight.getText().toString());
                        String time = "";
                        time += hour.getText().toString().trim();
                        time += ":";
                        time += minute.getText().toString().trim();
                        time += ":";
                        time += second.getText().toString().trim();
                        workoutLine.time = time;
                        workoutLine.category = "WEIGHT_AND_TIME";

                        if(!weight.getText().toString().isEmpty()){
                            workoutHistory.add(workoutLine);

                            Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("TRACKER");

                            workoutInputAdapter.notifyDataSetChanged();

                            TrackerFragment trackerFragment = (TrackerFragment) f;
                            trackerFragment.setWorkout(workoutLine, false);
                        }

                        break;
                    case TIME_AND_DISTANCE:
                        String t = "";
                        t += hour.getText().toString().trim();
                        t += ":";
                        t += minute.getText().toString().trim();
                        t += ":";
                        t += second.getText().toString().trim();
                        workoutLine.time = t;
                        workoutLine.distance = Double.parseDouble(distance.getText().toString().trim());
                        workoutLine.distanceUnit = unit.getSelectedItem().toString().trim();
                        workoutLine.category = "TIME_AND_DISTANCE";

                        if(!distance.getText().toString().isEmpty()){
                            workoutHistory.add(workoutLine);

                            Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("TRACKER");

                            workoutInputAdapter.notifyDataSetChanged();

                            TrackerFragment trackerFragment = (TrackerFragment) f;
                            trackerFragment.setWorkout(workoutLine, false);
                        }

                        break;
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case WEIGHT_AND_REPS:
                        weight.setText("");
                        reps.setText("");
                        break;
                    case TIME_AND_DISTANCE:
                        hour.setText("");
                        minute.setText("");
                        second.setText("");
                        break;
                    case WEIGHT_AND_TIME:
                        weight.setText("");
                        hour.setText("");
                        minute.setText("");
                        second.setText("");
                        break;
                    case INVERTEDWEIGHT_AND_REPS:
                        weight.setText("");
                        reps.setText("");
                        break;
                }

            }
        });

        return view;
    }

    private void createLayout() {
        switch (type){
            case WEIGHT_AND_REPS:
                createWeightAndRepsLayout();
                break;
            case WEIGHT_AND_TIME:
                createWeightAndTimeLayout();
                break;
            case TIME_AND_DISTANCE:
                createTimeAndDistanceLayout();
                break;
            case INVERTEDWEIGHT_AND_REPS:
                createWeightAndRepsLayout();
                break;
            default:
                createWeightAndRepsLayout();
                break;
        }
    }

    private void createWeightAndRepsLayout() {
        weight = new EditText(getContext());
        reps = new EditText(getContext());
        LinearLayout weightLabel = createInputLabel("Weight (Kgs)");
        LinearLayout weightInput = createIntInputLayout(weight);
        LinearLayout repsLabel = createInputLabel("Reps");
        LinearLayout repsInput = createIntInputLayout(reps);

        root.addView(weightLabel);
        root.addView(weightInput);
        root.addView(repsLabel);
        root.addView(repsInput);
    }

    private void createWeightAndTimeLayout() {
        weight = new EditText(getContext());
        LinearLayout weightLabel = createInputLabel("Weight (Kgs)");
        LinearLayout weightInput = createIntInputLayout(weight);
        LinearLayout timeLabel = createInputLabel("Time");
        LinearLayout timeInput = createTimeInputLayout();

        root.addView(weightLabel);
        root.addView(weightInput);
        root.addView(timeLabel);
        root.addView(timeInput);
    }

    private void createTimeAndDistanceLayout() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setWeightSum(3);


        LinearLayout timeLabel = createInputLabel("Time");
        LinearLayout timeInput = createTimeInputLayout();
        LinearLayout distanceLabel = createInputLabel("Distance");
        LinearLayout distanceInput = createDistanceInputLayout();


        root.addView(timeLabel);
        root.addView(timeInput);
        root.addView(distanceLabel);
        root.addView(distanceInput);
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

    private LinearLayout createTimeInputLayout() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setWeightSum(3);

        hour = new EditText(getContext());
        minute = new EditText(getContext());
        second = new EditText(getContext());

        hour.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        minute.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        second.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));

        hour.setInputType(InputType.TYPE_CLASS_NUMBER);
        minute.setInputType(InputType.TYPE_CLASS_NUMBER);
        second.setInputType(InputType.TYPE_CLASS_NUMBER);
        
        hour.setHint("hh");
        minute.setHint("mm");
        second.setHint("ss");

        linearLayout.addView(hour);
        linearLayout.addView(minute);
        linearLayout.addView(second);

        return linearLayout;

    }

    private LinearLayout createDistanceInputLayout() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setWeightSum(4);

        LinearLayout gap = new LinearLayout(getContext());
        distance = new EditText(getContext());
        unit = new Spinner(getContext());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.distance_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(adapter);

        gap.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        distance.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        unit.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));

        distance.setInputType(InputType.TYPE_CLASS_NUMBER);

        linearLayout.addView(gap);
        linearLayout.addView(distance);
        linearLayout.addView(unit);

        return linearLayout;
    }

    private LinearLayout createIntInputLayout(EditText editText) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setWeightSum(4);

        ImageButton left = new ImageButton(getContext());
        left.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down);
        left.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        left.setBackgroundColor(getResources().getColor(R.color.dark));

        editText.setText("");
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,2.0f));

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double i = 0;
                try {
                    i = Double.parseDouble(weight.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                i -= 2.5;
                weight.setText(Double.toString(i));
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
                    i = Double.parseDouble(weight.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                i += 2.5;
                editText.setText(Double.toString(i));

            }
        });

        linearLayout.addView(left);
        linearLayout.addView(editText);
        linearLayout.addView(right);
        return  linearLayout;
    }
}
