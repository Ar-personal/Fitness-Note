package com.reitech.gym.ui.LayoutHelpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.internal.FlowLayout;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.reitech.gym.R;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.programs.Program;

public class LayoutHelper extends AppCompatActivity {


    public static LinearLayout createWorkoutLayout(Context context){
        LinearLayout parent = new LinearLayout(context);
        LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firstParams.setMargins(15, 70, 15, 10);
        parent.setLayoutParams(firstParams);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setBackgroundResource(R.drawable.borderless_radial_corner);
        return parent;
    }


    public static LinearLayout addWorkout(Context context, String label) {
        LinearLayout parent = createWorkoutLayout(context);
        parent.setBackgroundResource(R.drawable.borderless_radial_corner);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        internalParams.setMargins(10, 5, 10, 5);

        LinearLayout child1 = new LinearLayout(context);
        child1.setLayoutParams(internalParams);
        child1.setOrientation(LinearLayout.HORIZONTAL);
        child1.setWeightSum(1);
        child1.setBackgroundColor(context.getResources().getColor(R.color.background));
        TextView excerciseName = new TextView(context);
        excerciseName.setText(label);
        excerciseName.setTextColor(context.getResources().getColor(R.color.white, context.getTheme()));
        excerciseName.setTextSize(20);
        excerciseName.setId(R.id.workout_name);
        excerciseName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        excerciseName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        child1.setBackgroundResource(R.drawable.home_border_dark);
        child1.addView(excerciseName);

        LinearLayout divider = new LinearLayout(context);
        LinearLayout.LayoutParams divideParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
        divider.setLayoutParams(divideParams);
        divider.setBackground(context.getResources().getDrawable(R.drawable.underlined));

        LinearLayout child2 = new LinearLayout(context);
        child2.setLayoutParams(internalParams);
        child2.setWeightSum(3);
        child2.setOrientation(LinearLayout.HORIZONTAL);
        child2.setBackgroundResource(R.drawable.home_border_dark);

        parent.addView(child1);
        parent.addView(divider);
        parent.addView(child2);


        return parent;
    }


    public static FlexboxLayout addProgressionBlocks(Context context, int amt){
        int buttonWidth = 40;
        int buttonMargin = 5;
        int buttonPadding = 3;
        FlexboxLayout flexboxLayout = new FlexboxLayout(context);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);
        FlexboxLayout.LayoutParams internalParams = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        internalParams.setMaxWidth(200);
        internalParams.setMargins(50, 20, 50, 20);
        FlexboxLayout.LayoutParams buttonParams = new FlexboxLayout.LayoutParams(buttonWidth, buttonWidth);
        buttonParams.setMargins(buttonMargin, buttonMargin ,0 ,0);
        flexboxLayout.setLayoutParams(internalParams);

        if (amt == 0) {
            Button button = new Button(context);
            button.setLayoutParams(buttonParams);
            button.setBackgroundColor(context.getResources().getColor(R.color.dark));
            flexboxLayout.addView(button);
            return flexboxLayout;
        }

        for (int i = 0; i < amt; i++){
            Button button = new Button(context);
            button.setLayoutParams(buttonParams);
            button.setBackgroundColor(context.getResources().getColor(R.color.white));
            flexboxLayout.addView(button);
        }

        return flexboxLayout;
    }

    public static LinearLayout addContinueButton(Context context){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(1);
        linearLayout.setLayoutParams(internalParams);


        Button cont = new Button(context);
        cont.setId(R.id.cont);
        cont.setTextSize(16);
        cont.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cont.setBackgroundColor(context.getResources().getColor(R.color.positive));
        cont.setText("Continue");
        cont.setTextColor(context.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cont.setLayoutParams(buttonParams);
        buttonParams.setMargins(200, 30, 200, 30);


        linearLayout.addView(cont);
        return linearLayout;
    }

    public static LinearLayout addHistorySettingsButtons(Context context){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(2);
        linearLayout.setLayoutParams(internalParams);

        Button history = new Button(context);
        history.setId(R.id.programHistory);
        history.setTextSize(12);
        history.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        history.setBackgroundColor(context.getResources().getColor(R.color.positive));
        history.setText("History");
        history.setTextColor(context.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        history.setLayoutParams(buttonParams);
        buttonParams.setMargins(30, 30, 30, 30);

        Button settings = new Button(context);
        settings.setId(R.id.programHistory);
        settings.setTextSize(12);
        settings.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        settings.setBackgroundColor(context.getResources().getColor(R.color.positive));
        settings.setText("Settings");
        settings.setTextColor(context.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams button2Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        settings.setLayoutParams(buttonParams);
        button2Params.setMargins(30, 30, 30, 30);

        linearLayout.addView(history);
        linearLayout.addView(settings);

        return linearLayout;
    }

    public static LinearLayout addWorkoutLine(Context context, WorkoutLine workoutLine){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(internalParams);
        linearLayout.setWeightSum(4);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        internalParams.setMargins(0, 0, 0, 0);

        TextView tag = new TextView(context);
        tag.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tag.setText(workoutLine.programTag);
        tag.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView name = new TextView(context);
        name.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setText(workoutLine.exerciseName);
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText first = new EditText(context);
        first.setId(R.id.lineFirst);
        first.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        first.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        first.setInputType(InputType.TYPE_CLASS_NUMBER);
        first.setMaxLines(1);

        EditText second = new EditText(context);
        second.setId(R.id.lineSecond);
        second.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        second.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        second.setInputType(InputType.TYPE_CLASS_NUMBER);
        second.setMaxLines(1);

        switch (workoutLine.category) {
            case "WEIGHT_AND_TIME":
                first.setText("" + workoutLine.weight + workoutLine);
                second.setText("" + workoutLine.time);
                break;
            case "TIME_AND_DISTANCE":
                first.setText("" + workoutLine.time);
                second.setText("" + workoutLine.distance + workoutLine.distanceUnit);
                break;
            default:
                first.setText("" + workoutLine.weight);
                second.setText("" + workoutLine.reps);
                break;
        }
        linearLayout.addView(tag);
        linearLayout.addView(name);
        linearLayout.addView(first);
        linearLayout.addView(second);

        return linearLayout;
    }


    public static LinearLayout addProgramImageAndDescription(Context context, int imageView, String text) {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(6);
        internalParams.setMargins(10, 10, 10 ,10);
        linearLayout.setLayoutParams(internalParams);

        ImageView icon = new ImageView(context);
        icon.setBackgroundResource(imageView);
        LinearLayout.LayoutParams iconpms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4);
        iconpms.setMargins(40, 0, 0, 0);
        icon.setLayoutParams(iconpms);


        TextView desc = new TextView(context);
        desc.setText(text);
        desc.setTextSize(18);
        desc.setTextColor(context.getResources().getColor(R.color.white));
        desc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        desc.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2));

        linearLayout.addView(icon);
        linearLayout.addView(desc);

        return linearLayout;
    }

    public static LinearLayout addWorkoutStatsToLayout(Context context, Program program){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(2);
        internalParams.setMargins(40, 10, 40 ,10);
        linearLayout.setLayoutParams(internalParams);

        TextView completed = new TextView(context);
        completed.setText("Completed workouts : " + Integer.toString(program.getDaysCompleted()));
        completed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        completed.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        completed.setTypeface(completed.getTypeface(), Typeface.BOLD);
        completed.setTextSize(16);

        TextView streak = new TextView(context);
        streak.setText("Workout streak : " + Integer.toString(program.getStreak()));
        streak.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        streak.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        streak.setTypeface(streak.getTypeface(), Typeface.BOLD);
        streak.setTextSize(16);

        linearLayout.addView(completed);
        linearLayout.addView(streak);

        return linearLayout;
    }

    public static LinearLayout addTitleRow(Context context){
        LinearLayout titles = new LinearLayout(context);
        titles.setOrientation(LinearLayout.HORIZONTAL);
        titles.setWeightSum(5);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titles.setLayoutParams(titleParams);


        TextView set = new TextView(context);
        set.setText("Set");
        set.setTextColor(context.getResources().getColor(R.color.white));
        set.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        set.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView target = new TextView(context);
        target.setText("Target");
        target.setTextColor(context.getResources().getColor(R.color.white));
        target.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        target.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView result = new TextView(context);
        result.setText("Result");
        result.setTextColor(context.getResources().getColor(R.color.white));
        result.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        result.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView l = new TextView(context);
        l.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        l.setText("");

        TextView completed = new TextView(context);
        completed.setTextColor(context.getResources().getColor(R.color.white));
        completed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        completed.setText("Done");

        titles.addView(set);
        titles.addView(target);
        titles.addView(result);
        titles.addView(l);
        titles.addView(completed);

        return titles;
    }

    @SuppressLint("ResourceAsColor")
    public static LinearLayout addT1Workout(Context context, String label, Program program, LinearLayout root){
        LinearLayout type = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        type.setOrientation(LinearLayout.HORIZONTAL);
        internalParams.setMargins(10, 10, 10 ,10);
        type.setLayoutParams(internalParams);

        TextView name = new TextView(context);
        name.setText(label);
        name.setId(R.id.programName);
        name.setTextColor(context.getResources().getColor(R.color.white));
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setTextSize(16);

        TextView tier = new TextView(context);
        tier.setId(R.id.tier);
        tier.setText("T1");
        tier.setTextColor(context.getResources().getColor(R.color.white));
        tier.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tier.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tier.setTypeface(name.getTypeface(), Typeface.BOLD);
        tier.setTextSize(16);

        LinearLayout titles = addTitleRow(context);

        LinearLayout addWarmup = addWarmup(context);
        LinearLayout warmUpButton = addWarmupButton(context, root);

        root.addView(name);
        root.addView(type);
        root.addView(tier);
        root.addView(titles);
        root.addView(addWarmup);
        root.addView(warmUpButton);

        double range;
        double upperIncrease = 2.5;
        double lowerIncrease = 5;

        if(upperIncrease > program.getMaxIncreaseDefault()){
            upperIncrease = program.getMaxIncreaseDefault();
        }
        if(lowerIncrease > program.getMaxIncreaseDefault()){
            lowerIncrease = program.getMaxIncreaseDefault();
        }
        switch (label){
            case "Flat Barbell Bench Press":
                double base = program.getBenchMax();
                int failsBench = program.getExerciseFail(label, 1);
                if(program.getBenchT1ThreeRep() == 0){
                    //calculate max
                    range = (base / 100) * 90;
                }else{
                    range = (program.getBenchT1ThreeRep() / 100) * 92;
                }
                range = roundToWeight(range, upperIncrease);
                for (int amt = 1; amt < 6 + program.getBenchFail(); amt++){
                    root.addView(addProgramSet(context, range, program.calculateT1RepsFromFails(failsBench, program.getName()), amt));
                }
                break;
            case "Barbell Squat":
                double squatBase = program.getSquatMax();
                int failsSquat = program.getExerciseFail(label, 1);
                if(program.getSquatT1ThreeRep() == 0){
                    //calculate max
                    range = (squatBase / 100) * 90;
                }else{
                    range = (program.getSquatT1ThreeRep() / 100) * 92;
                }
                range = roundToWeight(range, lowerIncrease);
                for (int amt = 1; amt < 6 + program.getBenchFail(); amt++){
                    root.addView(addProgramSet(context, range, program.calculateT1RepsFromFails(failsSquat, program.getName()),amt));
                }
                break;
            case "Overhead Press":
                double ohpBase = program.getOhpMax();
                int failsOhp = program.getExerciseFail(label, 1);
                if(program.getOhpT1ThreeRep() == 0){
                    //calculate max
                    range = (ohpBase / 100) * 90;

                }else{
                    range = (program.getOhpT1ThreeRep() / 100) * 92;
                }
                range = roundToWeight(range, upperIncrease);
                for (int amt = 1; amt < 6 + program.getOhpFail(); amt++){
                    root.addView(addProgramSet(context, range, program.calculateT1RepsFromFails(failsOhp, program.getName()), amt));
                }
                break;
            case "Dead-lift":
                double deadBase = program.getDeadliftMax();
                int failsDead = program.getExerciseFail(label, 1);
                if(program.getDeadT1ThreeRep() == 0){
                    //calculate max
                    range = (deadBase / 100) * 90;
                }else{
                    range = (program.getDeadT1ThreeRep() / 100) * 92;
                }
                range = roundToWeight(range, lowerIncrease);
                for (int amt = 1; amt < 6 + program.getDeadFail(); amt++){
                    root.addView(addProgramSet(context, range, program.calculateT1RepsFromFails(failsDead, program.getName()), amt));
                }
                break;
        }
        return root;
    }

    public static double roundToWeight(double weight, double roundAmt){

        weight = roundAmt*(Math.ceil(Math.abs(weight/roundAmt)));

        return weight;
    }



    @SuppressLint("ResourceAsColor")
    public static LinearLayout addT2Workout(Context context, String label, Program program, LinearLayout root){
        LinearLayout type = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        type.setOrientation(LinearLayout.HORIZONTAL);
        internalParams.setMargins(10, 10, 10 ,10);
        type.setLayoutParams(internalParams);

        TextView name = new TextView(context);
        name.setText(label);
        name.setId(R.id.programName);
        name.setTextColor(context.getResources().getColor(R.color.white));
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setTextSize(16);

        TextView tier = new TextView(context);
        tier.setId(R.id.tier);
        tier.setText("T2");
        tier.setTextColor(context.getResources().getColor(R.color.white));
        tier.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tier.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tier.setTypeface(name.getTypeface(), Typeface.BOLD);
        tier.setTextSize(16);

        LinearLayout titles = addTitleRow(context);

        LinearLayout addWarmup = addWarmup(context);
        LinearLayout warmUpButton = addWarmupButton(context, root);

        root.addView(name);
        root.addView(tier);
        root.addView(type);
        root.addView(titles);
        root.addView(addWarmup);
        root.addView(warmUpButton);

        double upperIncrease = 2.5;
        double lowerIncrease = 5;

        if(upperIncrease > program.getMaxIncreaseDefault()){
            upperIncrease = program.getMaxIncreaseDefault();
        }
        if(lowerIncrease > program.getMaxIncreaseDefault()){
            lowerIncrease = program.getMaxIncreaseDefault();
        }
        double range;
        switch (label){
            case "Flat Barbell Bench Press":
                double base = program.getBenchMax();
                int failsBench = program.getExerciseFail(label, 2);
                if(program.getBenchT2TenRep() == 0){
                    //calculate max
                    range = (base / 100) * 70;
                }else{
                    range = (program.getBenchT2TenRep() / 100) * 72;
                }
                range = roundToWeight(range, upperIncrease);
                for (int amt = 1; amt < 4; amt++){
                    root.addView(addProgramSet(context, range, program.calculateT2RepsFromFails(failsBench, program.getName()),amt));
                }
                break;
            case "Barbell Squat":
                double squatBase = program.getSquatMax();
                int failsSquat = program.getExerciseFail(label, 2);
                if(program.getSquatT2TenRep() == 0){
                    //calculate max
                    range = (squatBase / 100) * 70;
                }else{
                    range = (program.getSquatT2TenRep() / 100) * 72;
                }
                range = roundToWeight(range, lowerIncrease);
                for (int amt = 1; amt < 4; amt++){
                    root.addView(addProgramSet(context, range, program.calculateT2RepsFromFails(failsSquat, program.getName()), amt));
                }
                break;
            case "Overhead Press":
                double ohpBase = program.getOhpMax();
                int failsOhp = program.getExerciseFail(label, 2);
                if(program.getOhpT2TenRep() == 0){
                    //calculate max
                    range = (ohpBase / 100) * 70;
                }else{
                    range = (program.getOhpT2TenRep() / 100) * 72;
                }
                range = roundToWeight(range, upperIncrease);
                for (int amt = 1; amt < 4; amt++){
                    root.addView(addProgramSet(context, range, program.calculateT2RepsFromFails(failsOhp, program.getName()), amt));
                }
                break;
            case "Dead-lift":
                double deadBase = program.getDeadliftMax();
                int failsDead = program.getExerciseFail(label, 2);
                if(program.getDeadT2TenRep() == 0){
                    //calculate max
                    range = (deadBase / 100) * 70;
                }else{
                    range = (program.getDeadT2TenRep() / 100) * 72;
                }
                range = roundToWeight(range, lowerIncrease);
                for (int amt = 1; amt < 4; amt++){
                    root.addView(addProgramSet(context, range, program.calculateT2RepsFromFails(failsDead, program.getName()), amt));
                }
                break;
        }
        return root;
    }

    @SuppressLint("ResourceAsColor")
    public static LinearLayout addT3Workout(Context context, String label, Program program, LinearLayout root){
        LinearLayout type = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        type.setOrientation(LinearLayout.HORIZONTAL);
        type.setWeightSum(4);
        internalParams.setMargins(10, 10, 10 ,10);
        type.setLayoutParams(internalParams);

        TextView name = new TextView(context);
        name.setText(label);
        name.setId(R.id.programName);
        name.setTextColor(context.getResources().getColor(R.color.white));
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setTextSize(16);

        TextView tier = new TextView(context);
        tier.setId(R.id.tier);
        tier.setText("T3");
        tier.setTextColor(context.getResources().getColor(R.color.white));
        tier.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tier.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tier.setTypeface(name.getTypeface(), Typeface.BOLD);
        tier.setTextSize(16);

        LinearLayout titles = addTitleRow(context);

        root.addView(name);
        root.addView(tier);
        root.addView(type);
        root.addView(titles);

        root.addView(addProgramSet(context, 0, 15, 1));
        root.addView(addProgramSet(context, 0, 15, 2));
        root.addView(addProgramSet(context, 0, 25, 3));

        return root;
    }

    public static LinearLayout addWarmup(Context context){
        LinearLayout warmupLayout = new LinearLayout(context);
        warmupLayout.setId(R.id.warmUpSet);
        warmupLayout.setOrientation(LinearLayout.HORIZONTAL);
        warmupLayout.setWeightSum(5);
        warmupLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView warm = new TextView(context);
        warm.setText("Warmup");
        warm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        warm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t1 = new TextView(context);
        t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        t1.setText("");

        TextView t2 = new TextView(context);
        t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        t2.setText("");

        EditText resW = new EditText(context);
        resW.setHint("Kg");
        resW.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        resW.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText resR = new EditText(context);
        resR.setHint("reps");
        resR.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        resR.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        warmupLayout.addView(warm);
        warmupLayout.addView(t1);
        warmupLayout.addView(resW);
        warmupLayout.addView(resR);
        warmupLayout.addView(t2);

        return warmupLayout;
    }

    @SuppressLint("ResourceAsColor")
    public static LinearLayout addWarmupButton(Context context, LinearLayout root){
        LinearLayout addWarmup = new LinearLayout(context);
        addWarmup.setOrientation(LinearLayout.HORIZONTAL);
        addWarmup.setWeightSum(1);

        TextView addWarm = new TextView(context);
        addWarm.setText("Add Warmup Set");
        addWarm.setTextColor(context.getResources().getColor(R.color.white));
        addWarm.setBackgroundColor(R.color.white);
        addWarm.setBackground(context.getResources().getDrawable(R.drawable.warmup_button_border));
        addWarm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        addWarm.setPadding(20, 20, 20, 20);
        addWarm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        addWarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int warmUpSets = 0;
                for(int i = 0; i < root.getChildCount(); i++){
                    if (root.getChildAt(i).getId() == R.id.warmUpSet){
                        warmUpSets += 1 ;
                    }
                }

                root.addView(addWarmup(context), 4 + warmUpSets);
            }
        });

        addWarmup.addView(addWarm);

        return addWarmup;
    }

    public static LinearLayout addProgramSet(Context context, double weight, int reps, int setNo){
        LinearLayout set = new LinearLayout(context);
        set.setId(R.id.programSet);
        set.setOrientation(LinearLayout.HORIZONTAL);
        set.setWeightSum(5);
        set.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView warm = new TextView(context);
        warm.setText(String.valueOf(setNo));
        warm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        warm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView target = new TextView(context);
        target.setText(weight + " x " + reps);
        target.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        target.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText resW = new EditText(context);
        resW.setText(String.valueOf(weight));
        resW.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        resW.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText resR = new EditText(context);
        resR.setHint("reps");
        resR.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        resR.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        ImageButton tick = new ImageButton(context);

        LinearLayout.LayoutParams tickParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        tick.setLayoutParams(tickParams);
        tick.setId(R.id.tickNeutral);
        tick.setScaleType(ImageView.ScaleType.CENTER);
        tickParams.setMargins(0, 30, 0, 0);
        tick.setScaleX(0.3f);
        tick.setBackgroundResource(R.drawable.ic_baseline_tick);

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resW.setText(String.valueOf(weight));
                resR.setText(String.valueOf(reps));
            }
        });

        resR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(resR.getText().toString().isEmpty()){
                    tick.setBackgroundResource(R.drawable.ic_baseline_tick);
                }else if(Double.parseDouble(resW.getText().toString()) >= weight && Integer.parseInt(resR.getText().toString()) >= reps){
                    tick.setBackgroundResource(R.drawable.ic_tick_completed);
                    tick.setId(R.id.tickCompleted);
                }else{
                    tick.setBackgroundResource(R.drawable.ic_tick_failed);
                    tick.setId(R.id.tickFailed);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        set.addView(warm);
        set.addView(target);
        set.addView(resW);
        set.addView(resR);
        set.addView(tick);

        return set;

    }

}
