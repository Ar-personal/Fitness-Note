package com.reitech.gym.ui.data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalDate;

public class WorkoutLine implements Serializable {

    public int wid;

    public String date;

    public String exerciseName;

    public String category;

    public double weight;

    public int reps;

    public double distance;

    public String distanceUnit;

    public String time;

    public String programTag;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
