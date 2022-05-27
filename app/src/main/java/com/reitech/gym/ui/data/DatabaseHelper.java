package com.reitech.gym.ui.data;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                        p.pid = program.getProgramID();
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
                        p.benchFailT2 = program.getBenchFailT2();
                        p.deadFailT2 = program.getDeadFailT2();
                        p.ohpFailT2 = program.getOhpFailT2();
                        p.squatFailT2 = program.getSquatFailT2();
                        p.benchT1ThreeRep = program.getBenchT1ThreeRep();
                        p.squatT1ThreeRep = program.getSquatT1ThreeRep();
                        p.deadT1ThreeRep = program.getDeadT1ThreeRep();
                        p.ohpT1ThreeRep = program.getOhpT1ThreeRep();
                        p.benchT2TenRep = program.getBenchT2TenRep();
                        p.squatT2TenRep = program.getSquatT2TenRep();
                        p.deadT2TenRep = program.getDeadT2TenRep();
                        p.ohpT2TenRep = program.getOhpT2TenRep();
                        p.streak = program.getStreak();
                        p.daysCompleted = program.getDaysCompleted();
                        p.maxIncreaseDefault = program.getMaxIncreaseDefault();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                programDao.insertOrUpdate(p);
            }
        };
        handler.sendEmptyMessage(1);

    }

    public static void addWorkoutToDatabase(WorkoutLine wl, LocalDate date) {
        new Thread(() ->{
            Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
            Workout workout = new Workout();
            workout.date = date.toString();
            workout.exerciseName = wl.exerciseName;

            //optional inputs
            try {
                workout.weight = wl.weight;
                workout.reps = wl.reps;
                workout.distanceUnit = wl.distanceUnit;
                workout.distance = wl.distance;
                workout.time = wl.time;
                workout.category = wl.category;
                workout.programTag = wl.programTag;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            workoutDao.insertWorkout(workout);
        }).start();
    }


    public static List<WorkoutLine> getWorkoutEntireHistory(String workoutName){
        Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
        List<Workout> dayWorkouts = workoutDao.getEntireWorkoutHistory(workoutName);
        List<WorkoutLine> workoutLines = new ArrayList<>();
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

//        ExecutorService es = Executors.newCachedThreadPool();
//        es.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        es.invokeAll()
        return workoutLines;
    }

    public static List<WorkoutLine> getWorkoutsWithProgram(String programName) {
        Workout.WorkoutDao workoutDao = MainActivity.workoutDao;
        List<Workout> dayWorkouts = workoutDao.getWorkoutsFromProgram(programName);
        List<WorkoutLine> workoutLines = new ArrayList<>();
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
        return workoutLines;
    }

}
