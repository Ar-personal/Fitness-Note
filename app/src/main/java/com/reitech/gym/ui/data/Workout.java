package com.reitech.gym.ui.data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.Update;


import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Entity(tableName = "workout_line")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    public int wid;

    @TypeConverters(LocalDateTimeConverter.class)
    @ColumnInfo(name = "date")
    @NonNull
    public String date;

    @ColumnInfo(name = "exercise_name")
    @NonNull
    public String exerciseName;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "weight")
    public double weight;

    @ColumnInfo(name = "reps")
    public int reps;

    @ColumnInfo(name = "distance")
    public double distance;

    @ColumnInfo(name = "distance_unit")
    public String distanceUnit;

    @ColumnInfo(name = "time")
    public Double time;

    @Dao
    public interface WorkoutDao{
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void insertWorkout(Workout... workouts);

        @Delete
        public void deleteWorkout(Workout... workouts);

        @Update
        public void updateWorkout(Workout... workouts);

        @Query("SELECT * FROM workout_line")
        List<Workout> getAll();

        @Query("SELECT * FROM workout_line WHERE date = :date")
        List<Workout> getWorkoutsFromDate(String date);

        @Query("DELETE FROM workout_line WHERE wid = :wid")
        void deleteById(int wid);

    }


}

