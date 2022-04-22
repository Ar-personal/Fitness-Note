package com.reitech.gym.ui.programs.gzclp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                                LocalDate today = LocalDate.now();
                                LinearLayout finalLayout = null;
                                for(int exerciseAmt = 0; exerciseAmt < holder.getChildCount(); exerciseAmt++){
                                    finalLayout = (LinearLayout) holder.getChildAt(exerciseAmt);
                                    for(int k = 0; k < finalLayout.getChildCount(); k++) {
                                        if (finalLayout.getChildAt(k).getId() == R.id.programSet) {
                                            WorkoutLine wl = new WorkoutLine();
                                            LinearLayout line = (LinearLayout) finalLayout.getChildAt(k);
                                            TextView nameLayout = (TextView) finalLayout.findViewById(R.id.programName);
                                            String exerciseName = nameLayout.getText().toString();
                                            wl.exerciseName = exerciseName;
                                            wl.programTag = "GZCLP";
                                            wl.date = today.toString();
                                            wl.category = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(exerciseName).toString();


                                            EditText weight = (EditText) line.getChildAt(2);
                                            if (!weight.getText().toString().isEmpty()) {
                                                wl.weight = Double.parseDouble(weight.getText().toString());
                                            }

                                            EditText reps = (EditText) line.getChildAt(3);
                                            if (!reps.getText().toString().isEmpty()) {
                                                wl.reps = Integer.parseInt(reps.getText().toString());
                                            }
                                            addWorkoutToDatabase(wl, today);
                                        }
                                    }
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
