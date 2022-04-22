package com.reitech.gym.ui.data;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.reitech.gym.MainActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void readFromDatabase(LocalDate date) {
        Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
        List<Workout> dayWorkouts = workoutDao.getWorkoutsFromDate(date.toString());
        List<WorkoutLine> workoutLines = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                for (int i = 0; i < dayWorkouts.size(); i++) {
                    WorkoutLine workoutLine = new WorkoutLine();
                    try {
                        workoutLine.wid = dayWorkouts.get(i).wid;
                        workoutLine.date = dayWorkouts.get(i).date;
                        workoutLine.exerciseName = dayWorkouts.get(i).exerciseName;
                        workoutLine.category = dayWorkouts.get(i).category;
                        workoutLine.time = dayWorkouts.get(i).time;
                        workoutLine.weight = dayWorkouts.get(i).weight;
                        workoutLine.reps = dayWorkouts.get(i).reps;
                        workoutLine.distance = dayWorkouts.get(i).distance;
                        workoutLine.distanceUnit = dayWorkouts.get(i).distanceUnit;
                        workoutLine.programTag = dayWorkouts.get(i).programTag;
                        workoutLines.add(workoutLine);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        handler.sendEmptyMessage(1);
    }

    public static void saveProgram(com.reitech.gym.ui.programs.Program program) {
        com.reitech.gym.ui.data.Program.ProgramDao programDao = MainActivity.programDao;
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Program p = new Program();
                    try {
                        p.name = program.getName();
                        p.imageResourceID = program.getImageResouceID();
                        p.description = program.getDescription();
                        p.benchMax = program.getBenchMax();
                        p.deadliftMax = program.getDeadliftMax();
                        p.ohpMax = program.getOhpMax();
                        p.squatMax = program.getSquatMax();
                        p.benchFail = program.getBenchFail();
                        p.deadFail = program.getDeadFail();
                        p.ohpFail = program.getOhpFail();
                        p.squatFail = program.getSquatFail();
                        p.bench3RepMax = program.getBench3RepMax();
                        p.squat3RepMax = program.getSquat3RepMax();
                        p.dead3RepMax = program.getDead3RepMax();
                        p.ohp3RepMax = program.getOhp3RepMax();
                        p.streak = program.getStreak();
                        p.daysCompleted = program.getDaysCompleted();
                        p.maxIncreaseDefault = program.getMaxIncreaseDefault();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                programDao.insertProgram(p);
            }
        };
        handler.sendEmptyMessage(1);

    }


}
