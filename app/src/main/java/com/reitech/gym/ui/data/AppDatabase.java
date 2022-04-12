package com.reitech.gym.ui.data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Workout.class}, version = 2)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract Workout.WorkoutDao userDao();
}

