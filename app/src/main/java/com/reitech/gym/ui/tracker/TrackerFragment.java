package com.reitech.gym.ui.tracker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVWriter;
import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.exerciselist.AddExerciseFragment;
import com.reitech.gym.ui.tracker.workout_input.WorkoutInputFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrackerFragment extends Fragment {

    private LocalDate date;
    private boolean deleteIcon = false;
    private Menu menu;
    private MenuItem delete;
    private List<LinearLayout> layoutsToDelete;
    private LinearLayout root;

    public TrackerFragment(LocalDate date){
        super(R.layout.fragment_tracker);
        this.date = date;
    }

    //need for delete icon on toolbar
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = ((MainActivity) getActivity()).toolbar;
        toolbar.inflateMenu(R.menu.toolbar_menu);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView trackerDate = view.findViewById(R.id.tackerDate);
        String day = getReadableDate(date.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        ConstraintLayout root = view.findViewById(R.id.trackerLayout);
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        GestureDetector gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());

        readFromDatabase();

        //mainly check for left and right swipes for day changes
//        root.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return gestureDetector.onTouchEvent(motionEvent);
//            }
//        });
//
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return gestureDetector.onTouchEvent(motionEvent);
//            }
//        });

        trackerDate.setText(day + " " + date.format(formatter));



        ImageView trackerLeft = view.findViewById(R.id.trackerLeft);
        trackerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToYesterday();
            }
        });


        ImageView trackerRight = view.findViewById(R.id.trackerRight);
        trackerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTomorrow();
            }
        });
        

//        FloatingActionButton fab = view.findViewById(R.id.fab_add_program);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.R)
//            @Override
//            public void onClick(View view) {
//                Fragment addExerciseFragment = new AddExerciseFragment();
//                getParentFragmentManager().beginTransaction()
//                        .add(R.id.nav_host_fragment_activity_main, addExerciseFragment, "EXERCISE").addToBackStack(null).commit();
//            }
//
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goToYesterday(){
        LocalDate yesterday = date.minusDays(1);
        Fragment trackerFragment = new TrackerFragment(yesterday);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, trackerFragment, "TRACKER").addToBackStack(null).commit();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goToTomorrow(){
        LocalDate tomorrow = date.plusDays(1);
        Fragment trackerFragment = new TrackerFragment(tomorrow);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, trackerFragment, "TRACKER").addToBackStack(null).commit();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setWorkout(WorkoutLine wl, boolean loaded){
        LinearLayout linearLayout = getWorkoutLayout(wl.exerciseName);

        //check of workout exists as part of the GUI already
        if(linearLayout == null){
            addWorkout(wl.exerciseName, wl.category);
            linearLayout = getWorkoutLayout(wl.exerciseName);

            //user click on a workout and input page will open
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment workout = new WorkoutInputFragment(wl.exerciseName);
                    List<WorkoutLine> toAdd = getExerciseLinesFromExercise(wl.exerciseName);
                    Bundle bundle = new Bundle();
                    for(int i = 0; i < toAdd.size(); i++){
                        bundle.putSerializable("list" + i, toAdd.get(i));
                    }

                    workout.setArguments(bundle);

                    getParentFragmentManager().beginTransaction()
                            .add(R.id.nav_host_fragment_activity_main, workout).addToBackStack(null).commit();
                    onDestroy();
                }
            });
        }

         addWorkoutLine(wl, linearLayout);
        if(!loaded) {
            addWorkoutToDatabase(wl, date);
        }


    }

    private void addWorkoutToDatabase(WorkoutLine wl, LocalDate date) {
        new Thread(() ->{
            Workout.WorkoutDao workoutDao = ((MainActivity)getActivity()).workoutDao;
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

    //Date,Exercise,Category,Weight (kgs),Reps,Distance,Distance Unit,Time
    public void saveData(String workoutName, String[] workoutArray, LocalDate date){
        File folder = new File(getView().getContext().getExternalFilesDir(null) + "/fitboost");
        final String trackedWorkouts = folder.toString() + "/" + "workouts.csv";
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(trackedWorkouts, true));
            String[] record = {date.toString(), workoutName, workoutArray[2], workoutArray[3],workoutArray[4], workoutArray[5], workoutArray[6], workoutArray[7]};
            csvWriter.writeNext(record);
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readFromDatabase() {
        Workout.WorkoutDao workoutDao = ((MainActivity) getActivity()).workoutDao;
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
                for (int j = 0; j < workoutLines.size(); j++){
                    setWorkout(workoutLines.get(j), true);
                }
            }
        };
        handler.sendEmptyMessage(1);
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void readCSVData() {
//    File folder = new File(getView().getContext().getExternalFilesDir(null) + "/fitboost");
//
//    final String trackedWorkouts = folder.toString() + "/" + "workouts.csv";
//            try {
//                CSVReader csvReader = new CSVReader(new FileReader(trackedWorkouts));
//                List<String[]> workouts = csvReader.readAll();
//                for(String[] line : workouts)
//                    if(LocalDate.parse(line[0]).equals(date)){
//                    }
//            } catch (IOException | CsvException e) {
//                e.printStackTrace();
//            }
//    }

    public LinearLayout getWorkoutLayout(String workout){
        LinearLayout l = null;
        List<TextView> loadedTextViews = new ArrayList<>();
        LinearLayout workoutHolder = getView().findViewById(R.id.workoutHolder);
        for(int i=0; i < workoutHolder.getChildCount(); i++){
            TextView t = (TextView) workoutHolder.getChildAt(i).findViewById(R.id.workout_name);
            loadedTextViews.add(t);
        }

        //trying to add a category that exists on the workout tracker already so don't add it
        if(loadedTextViews.size() <= 0) {
            return l;
        }

            for (TextView t : loadedTextViews) {
                if(t == null)
                    return l;
                if (t.getText().toString().toLowerCase().equals(workout.toLowerCase())) {
                    //maybe close the list fragment and scroll the user to the correct workout they are trying to add
                    l = (LinearLayout) t.getParent().getParent();
                    return l;
                }
            }

        return l;
    }

    public void addWorkout(String workout, String category){
        LinearLayout parent = createWorkoutLayout();
        parent.setBackgroundResource(R.drawable.borderless_radial_corner);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        internalParams.setMargins(10,5,10,5);

        LinearLayout child1 = new LinearLayout(getContext());
        child1.setLayoutParams(internalParams);
        child1.setOrientation(LinearLayout.HORIZONTAL);
        child1.setWeightSum(1);
        child1.setBackgroundColor(getResources().getColor(R.color.background));
        TextView excerciseName = new TextView(getContext());
        excerciseName.setText(workout);
        excerciseName.setTextColor(getResources().getColor(R.color.white));
        excerciseName.setTextSize(20);
        excerciseName.setId(R.id.workout_name);
        excerciseName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        excerciseName.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        child1.setBackgroundResource(R.drawable.home_border_dark);
        child1.addView(excerciseName);

        LinearLayout divider = new LinearLayout(getContext());
        LinearLayout.LayoutParams divideParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
        divider.setLayoutParams(divideParams);
        divider.setBackground(getResources().getDrawable(R.drawable.underlined));

        LinearLayout child2 = new LinearLayout(getContext());
        child2.setLayoutParams(internalParams);
        child2.setWeightSum(3);
        child2.setOrientation(LinearLayout.HORIZONTAL);
        child2.setBackgroundResource(R.drawable.home_border_dark);

        TextView trophy = new TextView(getContext());
        trophy.setText("PR");
        trophy.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        trophy.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        trophy.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView firstColumn = new TextView(getContext());
        firstColumn.setText("WEIGHT");
        firstColumn.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        firstColumn.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        firstColumn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView secondColumn = new TextView(getContext());
        secondColumn.setTypeface(trophy.getTypeface(), Typeface.BOLD);
        secondColumn.setLayoutParams(new LinearLayout.LayoutParams(0 ,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        secondColumn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        switch (category){
            case "WEIGHT_AND_TIME":
                firstColumn.setText("WEIGHT");
                secondColumn.setText("TIME");
                break;
            case "TIME_AND_DISTANCE":
                firstColumn.setText("TIME");
                secondColumn.setText("DISTANCE");
                break;
            default:
                firstColumn.setText("WEIGHT");
                secondColumn.setText("REPS");
                break;
        }
        child2.addView(trophy);
        child2.addView(firstColumn);
        child2.addView(secondColumn);

        parent.addView(child1);
        parent.addView(divider);
        parent.addView(child2);

        LinearLayout root = getView().findViewById(R.id.workoutHolder);
        root.addView(parent);

        excerciseName.setTypeface(excerciseName.getTypeface(), Typeface.BOLD);
        trophy.setTextColor(getResources().getColor(R.color.white));
        firstColumn.setTextColor(getResources().getColor(R.color.white));
        secondColumn.setTextColor(getResources().getColor(R.color.white));
    }

    public LinearLayout createWorkoutLayout(){
        LinearLayout parent = new LinearLayout(getContext());
        LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firstParams.setMargins(15, 70, 15, 10);
        parent.setLayoutParams(firstParams);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setBackgroundResource(R.drawable.borderless_radial_corner);
        return parent;
    }

    private void addWorkoutLine(WorkoutLine wl, LinearLayout parent){
        //Date,Exercise,Category,Weight (kgs),Reps,Distance,Distance Unit,Time

        // TODO: 17/03/2022 change complete reinstantiation of entire workout

        LinearLayout workoutLine = new LinearLayout(getContext());

        //if unloaded exercise then it does not exist in database until after this method currently
        //no id would have been set from reading from database
        try {
            workoutLine.setId(wl.wid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        workoutLine.setWeightSum(3);
        workoutLine.setOrientation(LinearLayout.HORIZONTAL);
        workoutLine.setBackgroundResource(R.drawable.borderless_radial_corner);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(30,5,30,15);
        TextView trophy = new TextView(getContext());
        trophy.setText("trophy");
        trophy.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        trophy.setLayoutParams(params);
        trophy.setTextColor(getResources().getColor(R.color.white));

        switch (wl.category) {
            case "WEIGHT_AND_TIME":
                TextView weight = new TextView(getContext());
                weight.setId(R.id.weight);
                weight.setText(wl.weight + " Kgs");
                weight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                weight.setLayoutParams(params);
                weight.setTextColor(getResources().getColor(R.color.white));


                TextView time = new TextView(getContext());
                time.setId(R.id.time);
                time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                time.setText(String.valueOf(wl.reps));
                time.setLayoutParams(params);
                time.setTextColor(getResources().getColor(R.color.white));

                workoutLine.addView(trophy);
                workoutLine.addView(weight);
                workoutLine.addView(time);
                break;
            case "TIME_AND_DISTANCE":


                TextView t = new TextView(getContext());
                t.setId(R.id.time);
                t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                t.setText(String.valueOf(wl.time));
                t.setLayoutParams(params);
                t.setTextColor(getResources().getColor(R.color.white));

                TextView distance = new TextView(getContext());
                distance.setId(R.id.distance);
                distance.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                distance.setText(String.valueOf(wl.distance + wl.distanceUnit));
                distance.setLayoutParams(params);
                distance.setTextColor(getResources().getColor(R.color.white));

                workoutLine.addView(trophy);
                workoutLine.addView(t);
                workoutLine.addView(distance);
                break;
            default:
                TextView w = new TextView(getContext());
                w.setId(R.id.weight);
                w.setText(wl.weight + " Kgs");
                w.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                w.setLayoutParams(params);
                w.setTextColor(getResources().getColor(R.color.white));

                TextView reps = new TextView(getContext());
                reps.setId(R.id.reps);
                reps.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                reps.setText(String.valueOf(wl.reps));
                reps.setLayoutParams(params);
                reps.setTextColor(getResources().getColor(R.color.white));

                workoutLine.addView(trophy);
                workoutLine.addView(w);
                workoutLine.addView(reps);
                break;
        }


        LinearLayout divider = new LinearLayout(getContext());
        LinearLayout.LayoutParams divideParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        divider.setLayoutParams(divideParams);
        divider.setBackground(getResources().getDrawable(R.drawable.item_underlined));
        parent.addView(divider);
        parent.addView(workoutLine);

        //detect if the user wants to delete a workout line with long press
        workoutLine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!deleteIcon){
                    layoutsToDelete = new ArrayList<>();
                    layoutsToDelete.add(workoutLine);
                    workoutLine.setBackgroundColor(getResources().getColor(R.color.negative));
                    showDelete();
                }
                return true;
            }
        });

        //code to delete a workout line based on if the long press to delete has been done
        workoutLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!deleteIcon){
                    return;
                }else{
                    if(layoutsToDelete != null){
                        int color = Color.TRANSPARENT;
                        Drawable background = workoutLine.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        //check if the view is selected already as it will have red delete background visuals
                        if(color == getResources().getColor(R.color.negative)){
                            layoutsToDelete.remove(workoutLine);
                            workoutLine.setBackgroundColor(getResources().getColor(R.color.background));
                            //cancel delete user has removed all objects
                            if(layoutsToDelete.size() == 0){
                                hideDelete();
                            }
                        }else{
                            layoutsToDelete.add(workoutLine);
                            workoutLine.setBackgroundColor(getResources().getColor(R.color.negative));
                        }
                    }
                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<WorkoutLine> getExerciseLinesFromExercise(String workoutName){
        LinearLayout layout = getWorkoutLayout(workoutName);
        ArrayList<WorkoutLine> lines = new ArrayList<>();
        if (layout !=null){
            for (int i = 0; i < layout.getChildCount(); i++){
                WorkoutLine workoutLine = new WorkoutLine();
                //if layout holds line text?
                TextView weight = (TextView) layout.getChildAt(i).findViewById(R.id.weight);
                TextView reps = (TextView) layout.getChildAt(i).findViewById(R.id.reps);

                //some children may not be textviews?
                if(weight == null || reps == null){
                    continue;
                }
                //workout line found
                String weightText = weight.getText().toString();
                //remove " kgs"
                workoutLine.weight = Double.parseDouble(weightText.substring(0, weightText.length() - 4));
                workoutLine.reps = Integer.parseInt(reps.getText().toString());
                workoutLine.category = com.reitech.gym.ui.tracker.Workout.getCategoryFromExerciseName(workoutName).toString();

                lines.add(workoutLine);
                continue;
            }
        }
        return lines;
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

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_tracker, menu);
        delete = menu.findItem(R.id.navigation_deleteRow);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_deleteRow:
                deleteRows();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void hideDelete(){
        delete.setVisible(false);
        deleteIcon = false;
    }

    public void showDelete(){
        delete.setVisible(true);
        deleteIcon = true;
    }

    public void deleteRows(){
        if(layoutsToDelete == null){
            System.err.println("why is delete list null tho");
            return;
        }

        for(LinearLayout l : layoutsToDelete){
            LinearLayout parent = (LinearLayout) l.getParent();

            deleteWorkoutFromDatabase(l.getId());


            try {
                parent.removeView(l);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    parent.removeAllViews();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        hideDelete();
    }


    public void deleteWorkoutFromDatabase(int wid){
        new Thread(() -> {
            Workout.WorkoutDao workoutDao = ((MainActivity) getActivity()).workoutDao;
            workoutDao.deleteById(wid);


        }).start();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d("TAG","onDown: ");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("TAG", "onSingleTapConfirmed: ");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("TAG", "onLongPress: ");
            return;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("TAG", "onDoubleTap: ");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.i("TAG", "onScroll: ");
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d("TAG", "onFling: ");
            if(velocityX > 0){
                goToYesterday();
            }else{
                goToTomorrow();
            }

            return true;
        }
    }

}
