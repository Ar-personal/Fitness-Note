package com.reitech.gym.ui.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import java.io.Serializable;

public class WorkoutLine implements Serializable {

    public int wid;

    public String date;

    public String exerciseName;

    public String category;

    public double weight;

    public int reps;

    public double distance;

    public String distanceUnit;

    public Double time;




}
