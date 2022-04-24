package com.reitech.gym.ui.programs.gzclp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.LayoutHelpers.LayoutHelper;
import com.reitech.gym.ui.data.DatabaseHelper;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.programs.Program;
import com.reitech.gym.ui.tracker.TrackerFragment;

import org.w3c.dom.Text;

import java.sql.Driver;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GZCLP extends Fragment {

    Program program;

    public GZCLP(Program program) {
        this.program = program;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceStat) {

        final View view = inflater.inflate(R.layout.gzclp, container, false);

        ConstraintLayout holder = view.findViewById(R.id.gzclp_holder);

        LinearLayout t1 = view.findViewById(R.id.gzclp_t1);
        LinearLayout t2 = view.findViewById(R.id.gzclp_t2);
        LinearLayout t3 = view.findViewById(R.id.gzclp_t3);
        switch (program.getDaysCompleted() % 4){
            case 0:
                t1 = LayoutHelper.addT1Workout(getContext(), "Barbell Squat", program, t1);
                t2 = LayoutHelper.addT2Workout(getContext(), "Flat Barbell Bench Press", program, t2);
                t3  = LayoutHelper.addT3Workout(getContext(), "Lat Pull-down", program, t3);
                break;
            case 1:
                t1 = LayoutHelper.addT1Workout(getContext(), "Overhead Press", program, t1);
                t2 = LayoutHelper.addT2Workout(getContext(), "Dead-lift", program, t2);
                t3 = LayoutHelper.addT3Workout(getContext(), "Dumbbell Row", program, t3);
                break;
            case 2:
                t1 = LayoutHelper.addT1Workout(getContext(), "Flat Barbell Bench Press", program, t1);
                t2 = LayoutHelper.addT2Workout(getContext(), "Barbell Squat", program, t2);
                t3  = LayoutHelper.addT3Workout(getContext(), "Lat Pull-down", program, t3);
                break;
            case 3:
                t1 = LayoutHelper.addT1Workout(getContext(), "Dead-lift", program, t1);
                t2 = LayoutHelper.addT2Workout(getContext(), "Overhead Press", program, t2);
                t3 = LayoutHelper.addT3Workout(getContext(), "Dumbbell Row", program, t3);
                break;
        }

        ImageView save = view.findViewById(R.id.save_gzclp);

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Save and complete program?")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<Boolean> completedList = new ArrayList<>();
                                LocalDate today = LocalDate.now();
                                LinearLayout finalLayout = null;
                                String exerciseName = null;
                                double weightMax = 0;
                                int repsMax = 0;
                                String tierText = null;
                                //loop through the children of the layout, typically 3 exercise layouts
                                for(int exerciseAmt = 0; exerciseAmt < holder.getChildCount(); exerciseAmt++){
                                    finalLayout = (LinearLayout) holder.getChildAt(exerciseAmt);
                                    //loop through the children of each layout
                                    for(int k = 0; k < finalLayout.getChildCount(); k++) {
                                        //if the layout is an exercise segment then complete a workout line
                                        if (finalLayout.getChildAt(k).getId() == R.id.programSet) {
                                            WorkoutLine wl = new WorkoutLine();
                                            LinearLayout line = (LinearLayout) finalLayout.getChildAt(k);
                                            TextView nameLayout = (TextView) finalLayout.findViewById(R.id.programName);
                                            TextView tier = (TextView) finalLayout.findViewById(R.id.tier);
                                            tierText = tier.getText().toString();
                                            exerciseName = nameLayout.getText().toString();
                                            wl.exerciseName = exerciseName;
                                            wl.programTag = "GZCLP";
                                            wl.date = today.toString();
                                            wl.category = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(exerciseName).toString();

                                            EditText weight = (EditText) line.getChildAt(2);
                                            if (!weight.getText().toString().isEmpty()) {
                                                wl.weight = Double.parseDouble(weight.getText().toString());
                                                if(wl.weight > weightMax){
                                                    weightMax = wl.weight;
                                                }
                                            }

                                            EditText reps = (EditText) line.getChildAt(3);
                                            if (!reps.getText().toString().isEmpty()) {
                                                wl.reps = Integer.parseInt(reps.getText().toString());
                                            }

                                            //take workout line and save it, check if the workout results constitute a failure for the entire set
                                            addWorkoutToDatabase(wl, today);
                                            //based on tick state
                                            completedList.add(workoutCompleted(line));
                                        }
                                    }

                                    for (int j = 0; j < completedList.size(); j++){
                                        if(completedList.get(j) == false){
                                            //one of the sets was failed so perform failure logic
                                            failureLogic(exerciseName, program, weightMax, tierText);
                                            //dont perform failure logic more than once
                                            break;
                                        }
                                    }
                                    //no failures detected so perform completed logic
                                    completedLogic(exerciseName, program, weightMax, tierText);
                                }

                                program.setName("GZCLP");
                                program.setDaysCompleted(program.getDaysCompleted() + 1);

                                DatabaseHelper.saveProgram(program);
                                dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
//                                dismiss();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    private void completedLogic(String exerciseName, Program program, double weightMax, String tier){
        double upperIncrease = 2.5;
        double lowerIncrease = 5;

        //set maximum increase to user specified amount
        if(upperIncrease > program.getMaxIncreaseDefault()){
            upperIncrease = program.getMaxIncreaseDefault();
        }
        if(lowerIncrease > program.getMaxIncreaseDefault()){
            lowerIncrease = program.getMaxIncreaseDefault();
        }
        //if weight and sets match generated values then values go up for next week
        if(tier.equals("T1")){
            switch (exerciseName) {
                case "Barbell Squat":
                    program.setSquatT1ThreeRep(weightMax + lowerIncrease);
                    break;
                case "Dead-lift":
                    program.setDeadT1ThreeRep(weightMax + lowerIncrease);
                    break;
                case "Flat Barbell Bench Press":
                    program.setBenchT1ThreeRep(weightMax + upperIncrease);
                    break;
                case "Overhead Press":
                    program.setOhpT1ThreeRep(weightMax + upperIncrease);
                    break;
            }
        }else if (tier.equals("T2")){
            switch (exerciseName) {
                case "Barbell Squat":
                    program.setSquatT2TenRep(weightMax + upperIncrease);
                    break;
                case "Dead-lift":
                    program.setDeadT2TenRep(weightMax + upperIncrease);
                    break;
                case "Flat Barbell Bench Press":
                    program.setBenchT2TenRep(weightMax + upperIncrease);
                    break;
                case "Overhead Press":
                    program.setOhpT2TenRep(weightMax + upperIncrease);
                    break;
            }
        }
    }

    private void failureLogic(String exerciseName, Program program, double weightMax, String tier) {
        double upperIncrease = 2.5;
        double lowerIncrease = 5;

        //set maximum increase to user specified amount
        if(upperIncrease > program.getMaxIncreaseDefault()){
            upperIncrease = program.getMaxIncreaseDefault();
        }
        if(lowerIncrease > program.getMaxIncreaseDefault()){
            lowerIncrease = program.getMaxIncreaseDefault();
        }

        if(tier.equals("T1")) {
            switch (exerciseName) {
                case "Barbell Squat":
                    program.setSquatFail(program.getSquatFail() + 1);
                    program.setSquatT1ThreeRep(weightMax + lowerIncrease);
                    System.out.println("squat fail");
                    //maxium amount of fails reached
                    //recalculate 1 rep or 3 rep max
                    if (program.getSquatFail() > 2) {
                        program.setSquatFail(0);
                        System.out.println("resetting squat fail");
                    }
                    break;
                case "Dead-lift":
                    program.setDeadFail(program.getDeadFail() + 1);
                    program.setDeadT1ThreeRep(weightMax + lowerIncrease);
                    System.out.println("dead fail");
                    if (program.getDeadFail() > 2) {
                        program.setDeadFail(0);
                        System.out.println("resetting dead fail");
                    }
                    break;
                case "Flat Barbell Bench Press":
                    program.setBenchFail(program.getBenchFail() + 1);
                    program.setBenchT1ThreeRep(weightMax + upperIncrease);
                    System.out.println("bench fail");
                    if (program.getBenchFail() > 2) {
                        program.setBenchFail(0);
                        System.out.println("resetting bench fail");
                    }
                    break;
                case "Overhead Press":
                    System.out.println("ohp fail");
                    program.setOhpFail(program.getOhpFail() + 1);
                    program.setOhpT1ThreeRep(weightMax + upperIncrease);
                    if (program.getOhpFail() > 2) {
                        System.out.println("resetting ohp fail");
                        program.setOhpFail(0);
                    }
                    break;
            }
        }else if (tier.equals("T2")){
            switch (exerciseName) {
                case "Barbell Squat":
                    program.setSquatFailT2(program.getSquatFailT2() + 1);
                    program.setSquatT2TenRep(weightMax + lowerIncrease);
                    System.out.println("squat fail");
                    //maxium amount of fails reached
                    //recalculate 1 rep or 3 rep max
                    if (program.getSquatFailT2() > 2) {
                        program.setSquatFailT2(0);
                        System.out.println("resetting squat fail");
                    }
                    break;
                case "Dead-lift":
                    program.setDeadFailT2(program.getDeadFailT2() + 1);
                    program.setDeadT2TenRep(weightMax + lowerIncrease);
                    System.out.println("dead fail");
                    if (program.getDeadFailT2() > 2) {
                        program.setDeadFailT2(0);
                        System.out.println("resetting dead fail");
                    }
                    break;
                case "Flat Barbell Bench Press":
                    program.setBenchFailT2(program.getBenchFailT2() + 1);
                    program.setBenchT2TenRep(weightMax + upperIncrease);
                    System.out.println("bench fail");
                    if (program.getBenchFailT2() > 2) {
                        program.setBenchFailT2(0);
                        System.out.println("resetting bench fail");
                    }
                    break;
                case "Overhead Press":
                    System.out.println("ohp fail");
                    program.setOhpFailT2(program.getOhpFailT2() + 1);
                    program.setOhpT2TenRep(weightMax + upperIncrease);
                    if (program.getOhpFailT2() > 2) {
                        System.out.println("resetting ohp fail");
                        program.setOhpFailT2(0);
                    }
                    break;
            }
        }
    }

    private boolean workoutCompleted(LinearLayout line) {
        ImageButton tick = (ImageButton) line.getChildAt(4);

//        if(tick.getId() == R.id.tickNeutral){
//            Toast.makeText(getContext(), "Please ensure all text fields are filled", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if(tick.getId() == R.id.tickCompleted){
            return true;
        }

        if(tick.getId() == R.id.tickFailed){
            return false;
        }

        return false;
    }

    private void addWorkoutToDatabase(WorkoutLine wl, LocalDate date) {
        new Thread(() ->{
            com.reitech.gym.ui.data.Workout.WorkoutDao workoutDao = ((MainActivity)getActivity()).workoutDao;
            com.reitech.gym.ui.data.Workout workout = new Workout();
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


    public void dismiss(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
