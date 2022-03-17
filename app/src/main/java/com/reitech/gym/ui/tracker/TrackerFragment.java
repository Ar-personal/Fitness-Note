package com.reitech.gym.ui.tracker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.reitech.gym.R;
import com.reitech.gym.ui.exerciselist.AddExerciseFragment;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrackerFragment extends Fragment {

    private LocalDate date;
    private ConstraintLayout layout;
    private Workout workout;

    public TrackerFragment(LocalDate date){
        super(R.layout.fragment_tracker);
        this.date = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView trackerDate = view.findViewById(R.id.tackerDate);
        String day = getReadableDate(date.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        trackerDate.setText(day + " " + date.format(formatter));

        readTodaysWorkout();

        layout = view.findViewById(R.id.trackerLayout);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_exercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
                Fragment addExerciseFragment = new AddExerciseFragment();
                getParentFragmentManager().beginTransaction()
                        .add(R.id.nav_host_fragment_activity_main, addExerciseFragment, "EXERCISE").addToBackStack(null).commit();
            }

        });
    }

    private void readTodaysWorkout() {
        File folder = new File(getView().getContext().getExternalFilesDir(null) + "/gains_tracker");

        final String trackedWorkouts = folder.toString() + "/" + "workouts.csv";
                try {
                    CSVReader csvReader = new CSVReader(new FileReader(trackedWorkouts));
                    String[] workout;
                    while((workout = csvReader.readNext()) != null){
                        addWorkoutLine(workout);
                    }


                } catch (IOException | CsvValidationException e) {
                    e.printStackTrace();
                }
    }


    public void addWorkout(String workout){
        //search through current fragments loaded exercises
        //if the workout already exists dont add new section

        List<TextView> loadedTextViews = new ArrayList<>();

        LinearLayout workoutHolder = getView().findViewById(R.id.workoutHolder);
        for(int i=0; i < workoutHolder.getChildCount(); i++){
            TextView t = (TextView) workoutHolder.getChildAt(i).findViewById(R.id.workout_name);
            loadedTextViews.add(t);
        }

        //trying to add a category that exists on the workout tracker already so don't add it
        for (TextView t : loadedTextViews){
            String test = t.getText().toString().toLowerCase();
            if(t.getText().toString().toLowerCase().equals(workout.toString().toLowerCase())){
                //maybe close the list fragment and scroll the user to the correct workout they are trying to add
                return;
            }
        }

        //now can safely add workout type also maybe allow same workout different types
        LinearLayout parent = createWorkoutLayout();

        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        internalParams.setMargins(5,5,5,5);

        LinearLayout child1 = new LinearLayout(getContext());
        child1.setLayoutParams(internalParams);
        child1.setOrientation(LinearLayout.HORIZONTAL);
        child1.setWeightSum(1);
        TextView excerciseName = new TextView(getContext());
        excerciseName.setText(workout);
        excerciseName.setId(R.id.workout_name);
        excerciseName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        excerciseName.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        child1.addView(excerciseName);


        LinearLayout child2 = new LinearLayout(getContext());
        child2.setLayoutParams(internalParams);
        child2.setWeightSum(3);
        child2.setOrientation(LinearLayout.HORIZONTAL);

        TextView trophy = new TextView(getContext());
        trophy.setText("PR");
        trophy.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        trophy.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        trophy.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView weightTitle = new TextView(getContext());
        weightTitle.setText("WEIGHT");
        weightTitle.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        weightTitle.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        weightTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView repsTitle = new TextView(getContext());
        repsTitle.setText("REPS");
        repsTitle.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        repsTitle.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        repsTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        parent.addView(child1);
        parent.addView(child2);

        LinearLayout root = getView().findViewById(R.id.workoutHolder);
        root.addView(parent);


        excerciseName.setTextColor(Color.WHITE);
        excerciseName.setTypeface(excerciseName.getTypeface(), Typeface.BOLD);
        trophy.setTextColor(Color.WHITE);
        weightTitle.setTextColor(Color.WHITE);
        repsTitle.setTextColor(Color.WHITE);

    }

    public LinearLayout createWorkoutLayout(){
        LinearLayout parent = new LinearLayout(getContext());
        LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firstParams.setMargins(0, 50, 5, 5);
        firstParams.setMarginStart(15);
        firstParams.setMarginEnd(15);
        parent.setLayoutParams(firstParams);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setBackgroundResource(R.drawable.home_border_dark);
        return parent;
    }


    private void addWorkoutLine(String[] workoutCSVLine){
        //Date,Exercise,Category,Weight (kgs),Reps,Distance,Distance Unit,Time
        LinearLayout parent = new LinearLayout(getContext());
        LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        firstParams.setMargins(0, 50, 5, 5);
        firstParams.setMarginStart(15);
        firstParams.setMarginEnd(15);
        internalParams.setMargins(5,5,5,5);

        parent.setLayoutParams(firstParams);
        parent.setOrientation(LinearLayout.VERTICAL);

        LinearLayout child1 = new LinearLayout(getContext());
        child1.setLayoutParams(internalParams);
        child1.setOrientation(LinearLayout.HORIZONTAL);
        child1.setWeightSum(1);
        TextView excerciseName = new TextView(getContext());
        excerciseName.setText(workoutCSVLine[1]);
        excerciseName.setId(R.id.workout_name);
        excerciseName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        excerciseName.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        child1.addView(excerciseName);


        LinearLayout child2 = new LinearLayout(getContext());
        child2.setLayoutParams(internalParams);
        child2.setWeightSum(3);
        child2.setOrientation(LinearLayout.HORIZONTAL);

        TextView trophy = new TextView(getContext());
        trophy.setText("PR");
        trophy.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        trophy.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        trophy.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView weightTitle = new TextView(getContext());
        weightTitle.setText("WEIGHT");
        weightTitle.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        weightTitle.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        weightTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView repsTitle = new TextView(getContext());
        repsTitle.setText("REPS");
        repsTitle.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        repsTitle.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        repsTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        child2.addView(trophy);
        child2.addView(weightTitle);
        child2.addView(repsTitle);

        LinearLayout child3 = new LinearLayout(getContext());
        child3.setLayoutParams(internalParams);
        child3.setWeightSum(3);
        child3.setOrientation(LinearLayout.HORIZONTAL);

        TextView weight = new TextView(getContext());
        weight.setText(workoutCSVLine[3] + " Kgs");
        weight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView reps = new TextView(getContext());
        reps.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        reps.setText(workoutCSVLine[4]);

        child3.addView(weight);
        child3.addView(reps);


        //styling
        parent.setBackgroundResource(R.drawable.home_border_dark);
        excerciseName.setTextColor(Color.WHITE);
        excerciseName.setTypeface(excerciseName.getTypeface(), Typeface.BOLD);
        trophy.setTextColor(Color.WHITE);
        weightTitle.setTextColor(Color.WHITE);
        repsTitle.setTextColor(Color.WHITE);
        weight.setTextColor(Color.WHITE);
        reps.setTextColor(Color.WHITE);

        parent.addView(child1);
        parent.addView(child2);
        parent.addView(child3);

        LinearLayout root = getView().findViewById(R.id.workoutHolder);
        root.addView(parent);
    }



    public static String getReadableDate(final int date){
        String suffix = "th";
        switch (date){
            case 1:
            case 21:
            case 31:
                suffix = "st";
                break;
            case 2:
            case 22:
                suffix = "nd";
                break;
            case 3:
            case 23:
                suffix = "rd";
                break;
        }
        return date + suffix;
    }

}
