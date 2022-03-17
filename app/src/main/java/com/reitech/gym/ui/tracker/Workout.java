package com.reitech.gym.ui.tracker;

public class Workout {

    public enum WorkoutEnum {
        WEIGHT_AND_REPS,
        TIME_AND_DISTANCE
    }

    String workoutName, category;
    WorkoutEnum type;

    public Workout(String workoutName, String category, WorkoutEnum type) {
        this.workoutName = workoutName;
        this.category = category;


        switch (type){
            case WEIGHT_AND_REPS:
                type = WorkoutEnum.WEIGHT_AND_REPS;
                break;
            case TIME_AND_DISTANCE:
                type = WorkoutEnum.TIME_AND_DISTANCE;
                break;
            default:
                System.out.println("unknown workout");
                break;
        }

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


