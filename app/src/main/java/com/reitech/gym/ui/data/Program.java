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

    @ColumnInfo(name = "bench_failT2")
    public int benchFailT2;

    @ColumnInfo(name = "squat_failT2")
    public int squatFailT2;

    @ColumnInfo(name = "dead_failT2")
    public int deadFailT2;

    @ColumnInfo(name = "ohp_failT2")
    public int ohpFailT2;

    @ColumnInfo(name = "benchT1ThreeRep")
    public double benchT1ThreeRep;

    @ColumnInfo(name = "benchT2TenRep")
    public double benchT2TenRep;

    @ColumnInfo(name = "squatT1ThreeRep")
    public double squatT1ThreeRep;

    @ColumnInfo(name = "squatT2TenRep")
    public double squatT2TenRep;

    @ColumnInfo(name = "deadT1ThreeRep")
    public double deadT1ThreeRep;

    @ColumnInfo(name = "deadT2TenRep")
    public double deadT2TenRep;

    @ColumnInfo(name = "ohpT1ThreeRep")
    public double ohpT1ThreeRep;

    @ColumnInfo(name = "ohpT2TenRep")
    public double ohpT2TenRep;

    @Dao
    public interface ProgramDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void insertProgram(Program... programs);

        @Delete
        public void deleteProgram(Program... programs);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        public void updateProgram(Program... programs);

        @Query("SELECT * from program WHERE pid= :id")
        List<Program> getItemByPid(int id);

        @Query("SELECT * FROM program")
        List<Program> getAll();

        default void insertOrUpdate(Program program){
            List<Program> programsFromDB = getItemByPid(program.pid);
            if(programsFromDB.isEmpty())
                insertProgram(program);
            else
                updateProgram(program);
        }

    }
}
