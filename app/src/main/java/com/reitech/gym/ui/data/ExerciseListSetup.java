package com.reitech.gym.ui.data;

import com.reitech.gym.ui.tracker.Workout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExerciseListSetup {
    final static Map<Enum, List<String>> workoutMap = new HashMap<>();

    public ExerciseListSetup(){
        workoutMap.put(Workout.WorkoutEnum.WEIGHT_AND_REPS, Arrays.asList("Ab-Wheel", "Cable Crunch", "Barbell Row", "Crunch", "Crunch Machine", "Dragon Flag", "Hanging Knee Raise", "Hanging Leg Raise", "Barbell Shrug", "Chin-Up",
                "Dead-lift",  "Diverging Lat Pull-down", "Dumbbell Row", "Good Morning", "Hammer Strength Row", "Lat Pull-down", "Machine Shrug", "Neutral Chin Up", "Overhead Cable Pull",
                "Barbell Curl",  "Cable Curl", "Pendlay Row", "Pull-Up", "Rack Pull", "Seated Cable Row", "Straight-Arm Cable Push-down", "T-Bar Row", "Dumbbell Concentration Curl", "Dumbbell Curl",
                "Dumbbell Hammer Curl", "Dumbbell Preach Curl", "EZ-Bar Curl", "EZ-Bar Preacher Curl", "Seated Dip", "Seated Incline Dumbbell Curl", "Seated Machine Curl", "Cable Crossover",
                "Converging Chest Press", "Decline Barbell Bench Press", "Decline Hammer Strength Chest Press", "Flat Barbell Bench Press", "Flat Dumbbell Bench Press",
                "Flat Dumbbell Fly", "Incline Dumbbell Fly", "Incline Hammer Strength Chest Press", "Push-Up", "Seated Machine Cable Fly", "Seated Machine Fly", "Barbell Calf Raise",
                "Barbell Front Squat", "Barbell Glute Bridge", "Barbell Squat", "Donkey Calf Raise", "Glute-Ham Raise", "Hack Squat", "Leg Extension Machine", "Leg Press",
                "Lying Leg Curl Machine", "Romanian Dead-life", "Seated Calf Raise Machine", "Seated Leg Curl Machine", "Standing Calf Raise Machine", "Still-Legged Dead-lift",
                "Lying Leg Curl Machine", "Romanian Dead-life", "Seated Calf Raise Machine", "Seated Leg Curl Machine", "Standing Calf Raise Machine", "Still-Legged Dead-lift",
                "Sumo Dead-lift", "Arnold Dumbbell Press", "Behind The Neck Barbell Press", "Cable Face Pull", "Front Dumbbell Raise", "Hammer Strength Shoulder Press", "Lateral Dumbbell Raise",
                "Lateral Machine Raise", "Log Press", "One-Arm Standing Dumbbell Press", "Overhead Press", "Push Press", "Rear Delt Dumbbell Raise", "Rear Delt Machine Fly",
                "Seated Dumbbell Lateral Raise", "Seated Dumbbell Press", "Smith Machine Overhead Press", "Cable Overhead Triceps Extension", "Close Grip Barbell Bench Press",
                "Dumbbell Overhead Triceps Extension", "EZ-Bar Skullcrusher", "Lying Triceps Extension", "Parallel Bar Triceps Dip", "Ring Dip", "Rope Push Down", "Smith Machine Close Grip Bench Press", "Triceps Extension Machine", "V-Bar Push Down"));
        workoutMap.put(Workout.WorkoutEnum.WEIGHT_AND_TIME, Arrays.asList("Plank"));
        workoutMap.put(Workout.WorkoutEnum.INVERTEDWEIGHT_AND_REPS, Arrays.asList("Dip Assist", "Pull-Up Assist"));
        workoutMap.put(Workout.WorkoutEnum.TIME_AND_DISTANCE, Arrays.asList("Cycling", "Elliptical Trainer", "Rowing Machine", "Running (Outdoor)", "Running (Treadmill)", "Stair Climber", "Stationary Bike", "Swimming", "Walking"));
    }

    public static Map<Enum, List<String>> getWorkoutMap() {
        return workoutMap;
    }
}
