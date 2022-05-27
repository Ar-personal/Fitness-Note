package com.reitech.gym.ui.tracker.workout_input;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.tracker.WorkoutViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkoutInputAdapter extends RecyclerView.Adapter<WorkoutViewHolder>{
    List<WorkoutLine> lines;

    public WorkoutInputAdapter(List<WorkoutLine> lines) {
        this.lines = lines;
    }
    @NonNull
    @NotNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_input_item, parent, false);

        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkoutViewHolder holder, int position) {
        WorkoutLine list = lines.get(position);

        holder.first.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
                    String name = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(lines.get(position).exerciseName).toString();
                    Workout workout = workoutDao.get(lines.get(position).wid);
                    switch (name) {
                        //order important
                        case "WEIGHT_AND_REPS":
                        case "WEIGHT_AND_TIME":
                            workout.weight = Double.parseDouble(holder.first.getText().toString());
                            break;
                        case "TIME_AND_DISTANCE":
                            workout.time = holder.first.getText().toString();
                            break;
                    }

                    workoutDao.updateWorkout(workout);
                }
            }
        });



        holder.second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
                    String name = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(lines.get(position).exerciseName).toString();
                    Workout workout = workoutDao.get(lines.get(position).wid);
                    switch (name) {
                        //order important
                        case "WEIGHT_AND_REPS":
                            workout.reps = Integer.parseInt(holder.second.getText().toString());
                            break;
                        case "WEIGHT_AND_TIME":
                            workout.time = holder.second.getText().toString();
                            break;
                        case "TIME_AND_DISTANCE":
                            workout.distance = Double.parseDouble(holder.second.getText().toString());
                            break;
                    }
                    workoutDao.updateWorkout(workout);
                }
            }
        });

        switch (list.category){
            case "WEIGHT_AND_TIME":
                holder.first.setText(String.valueOf(list.weight));
                holder.second.setText(String.valueOf(list.time));
                break;
            case "TIME_AND_DISTANCE":
                holder.first.setText(String.valueOf(list.time));
                holder.second.setText(String.valueOf(list.distance + list.distanceUnit));
                break;
            default:
                holder.first.setText(String.valueOf(list.weight));
                holder.second.setText(String.valueOf(list.reps));
                break;
        }
        holder.trophy.setText(String.valueOf(list.wid));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }
}
