package com.reitech.gym.ui.data;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Entity(tableName = "program")
public class Program {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "unit_default")
    public int unitDefault;

    @ColumnInfo(name = "weight_increment_default")
    public int weightIncrementDefault;

    @ColumnInfo(name = "max_increase_default")
    public double maxIncreaseDefault;

    @ColumnInfo(name = "bench_max")
    public double benchMax;

    @ColumnInfo(name = "squat_max")
    public double squatMax;

    @ColumnInfo(name = "deadlift_max")
    public double deadliftMax;

    @ColumnInfo(name = "ohp_max")
    public double ohpMax;

    @ColumnInfo(name = "days_completed")
    public int daysCompleted;

    @ColumnInfo(name = "streak")
    public int streak;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "image_resource_id")
    public int imageResourceID;

    @ColumnInfo(name = "bench_fail")
    public int benchFail;

    @ColumnInfo(name = "squat_fail")
    public int squatFail;

    @ColumnInfo(name = "dead_fail")
    public int deadFail;

    @ColumnInfo(name = "ohp_fail")
    public int ohpFail;

    @ColumnInfo(name = "bench_3_rep_max")
    public double bench3RepMax;

    @ColumnInfo(name = "squat_3_rep_max")
    public double squat3RepMax;

    @ColumnInfo(name = "dead_3_rep_max")
    public double dead3RepMax;

    @ColumnInfo(name = "ohp_3_rep_max")
    public double ohp3RepMax;

    @Dao
    public interface ProgramDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void insertProgram(Program... programs);

        @Delete
        public void deleteProgram(Program... programs);

        @Update
        public void updateProgram(Program... programs);

        @Query("SELECT * FROM program")
        List<Program> getAll();

    }
}
