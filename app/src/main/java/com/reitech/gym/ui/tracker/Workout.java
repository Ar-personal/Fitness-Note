package com.reitech.gym.ui.tracker;

import com.reitech.gym.ui.data.ExerciseListSetup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout {





    public enum WorkoutEnum {
        WEIGHT_AND_REPS,
        TIME_AND_DISTANCE,
        WEIGHT_AND_TIME,
        INVERTEDWEIGHT_AND_REPS
    }

    String workoutName, category;
    WorkoutEnum type;

    public Workout(String workoutName, String category, WorkoutEnum type) {
        this.workoutName = workoutName;
        this.category = category;
    };

    public static WorkoutEnum getCategoryFromExerciseName(String exerciseName){
        Workout.WorkoutEnum category = null;
        for (Map.Entry<Enum, List<String>> entry: ExerciseListSetup.getWorkoutMap().entrySet())
        {
            for (int i = 0; i < entry.getValue().size(); i++){
                if (exerciseName.equals(entry.getValue().get(i))) {
                    category = (WorkoutEnum) entry.getKey();
                }
            }

        }
        return category;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public WorkoutEnum getType() {
        return type;
    }

    public void setType(WorkoutEnum type) {
        this.type = type;
    }
}


