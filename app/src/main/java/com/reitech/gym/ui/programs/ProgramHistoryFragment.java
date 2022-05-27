package com.reitech.gym.ui.programs;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.LayoutHelpers.LayoutHelper;
import com.reitech.gym.ui.data.DatabaseHelper;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.TrackerFragment;
import com.reitech.gym.ui.tracker.workout_input.WorkoutInputFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProgramHistoryFragment extends Fragment {

    String programName;

    public ProgramHistoryFragment(String programName){
        this.programName = programName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceStat) {

        final View view = inflater.inflate(R.layout.fragment_program_history, container, false);

        LinearLayout root = view.findViewById(R.id.program_history_root);
        List<WorkoutLine> lines = DatabaseHelper.getWorkoutsWithProgram(programName);


        TextView dateText = new TextView(getContext());
        //create a layout from recorded workouts with program tag and each date
        LocalDate date = LocalDate.MAX;
        int count = 1;
        for(int i = 0; i < lines.size(); i++){
            //new date section
            if(!date.isEqual(LocalDate.parse(lines.get(i).date))){
                LinearLayout section = new LinearLayout(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                section.setLayoutParams(params);
                section.setOrientation(LinearLayout.VERTICAL);
                dateText = new EditText(getContext());



                date = LocalDate.parse(lines.get(i).date);
                dateText.setText("Day " + count + " - " + LocalDate.parse(lines.get(i).date).format(DateTimeFormatter.ofPattern("dd MM yyyy")));
                dateText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                dateText.setTypeface(Typeface.DEFAULT_BOLD);
                section.addView(dateText);
                root.addView(section);
                count++;
            }
            LinearLayout line = LayoutHelper.addWorkoutLine(getContext(), lines.get(i));

            //means the weight and reps or distance and time etc
            EditText first = (EditText) line.findViewById(R.id.lineFirst);
            EditText second = (EditText) line.findViewById(R.id.lineSecond);
            int finalI = i;
            first.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
                        String name = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(lines.get(finalI).exerciseName).toString();
                        Workout workout = workoutDao.get(lines.get(finalI).wid);
                        switch (name) {
                            //order important
                            case "WEIGHT_AND_REPS":
                            case "WEIGHT_AND_TIME":
                                workout.weight = Double.parseDouble(first.getText().toString());
                                break;
                            case "TIME_AND_DISTANCE":
                                workout.time = first.getText().toString();
                                break;
                        }
                        workoutDao.updateWorkout(workout);
                    }
                }
            });

            second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
                        String name = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(lines.get(finalI).exerciseName).toString();
                        Workout workout = workoutDao.get(lines.get(finalI).wid);
                        switch (name) {
                            //order important
                            case "WEIGHT_AND_REPS":
                                workout.reps = Integer.parseInt(second.getText().toString());
                                break;
                            case "WEIGHT_AND_TIME":
                                workout.time = second.getText().toString();
                                break;
                            case "TIME_AND_DISTANCE":
                                workout.distance = Double.parseDouble(second.getText().toString());
                                break;
                        }

                        workoutDao.updateWorkout(workout);
                    }
                }
            });

            root.addView(line);

        }





        return view;

    }

}
