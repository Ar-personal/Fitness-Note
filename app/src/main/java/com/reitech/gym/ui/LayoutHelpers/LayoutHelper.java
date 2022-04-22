package com.reitech.gym.ui.LayoutHelpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
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
import com.reitech.gym.R;
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
        internalParams.setMargins(20, 20, 20, 20);
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
            button.setBackgroundColor(context.getResources().getColor(R.color.primary));
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
        cont.setBackgroundColor(context.getResources().getColor(R.color.background));
        cont.setText("Continue");
        cont.setTextColor(context.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cont.setLayoutParams(buttonParams);
        buttonParams.setMargins(200, 30, 200, 30);


        linearLayout.addView(cont);
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
        icon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 5));

        TextView desc = new TextView(context);
        desc.setText(text);
        desc.setTextSize(18);
        desc.setTextColor(context.getResources().getColor(R.color.white));
        desc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        desc.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        linearLayout.addView(icon);
        linearLayout.addView(desc);

        return linearLayout;
    }

    public static LinearLayout addWorkoutStatsToLayout(Context context, Program program){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(2);
        internalParams.setMargins(10, 10, 10 ,10);
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

    @SuppressLint("ResourceAsColor")
    public static LinearLayout addT1Workout(Context context, String label, Program program, LinearLayout root){
        LinearLayout type = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        type.setOrientation(LinearLayout.HORIZONTAL);
        type.setWeightSum(4);
        internalParams.setMargins(10, 10, 10 ,10);
        type.setLayoutParams(internalParams);

        TextView name = new TextView(context);
        name.setText(label);
        name.setId(R.id.programName);
        name.setTextColor(R.color.primary);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setTextSize(16);

        TextView tier = new TextView(context);
        tier.setText("T1");
        tier.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tier.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tier.setTypeface(name.getTypeface(), Typeface.BOLD);
        tier.setTextSize(16);

        LinearLayout titles = addTitleRow(context, label, program);

        LinearLayout addWarmup = addWarmup(context);
        LinearLayout warmUpButton = addWarmupButton(context, root);

        root.addView(name);
        root.addView(type);
        root.addView(tier);
        root.addView(titles);
        root.addView(addWarmup);
        root.addView(warmUpButton);

        double range;
        switch (label){
            case "Flat Barbell Bench Press":
                double base = program.getBenchMax();
                if(program.getBench3RepMax() == 0){
                    //calculate max
                    range = (base / 100) * 90;
                }else{
                    range = (program.getBench3RepMax() / 100) * 90;
                }
                for (int amt = 0; amt < 5 + program.getBenchFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getBenchFail(), amt));
                }
                break;
            case "Barbell Squat":
                double squatBase = program.getSquatMax();
                if(program.getBench3RepMax() == 0){
                    //calculate max
                    range = (squatBase / 100) * 90;
                }else{
                    range = (program.getSquat3RepMax() / 100) * 90;
                }
                for (int amt = 0; amt < 5 + program.getBenchFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getSquatFail(), amt));
                }
                break;
            case "Overhead Press":
                double ohpBase = program.getOhpMax();
                if(program.getOhp3RepMax() == 0){
                    //calculate max
                    range = (ohpBase / 100) * 90;
                }else{
                    range = (program.getSquat3RepMax() / 100) * 90;
                }
                for (int amt = 0; amt < 5 + program.getOhpFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getOhpFail(), amt));
                }
                break;
            case "Dead-lift":
                double deadBase = program.getDeadliftMax();
                if(program.getDead3RepMax() == 0){
                    //calculate max
                    range = (deadBase / 100) * 90;
                }else{
                    range = (program.getDead3RepMax() / 100) * 90;
                }
                for (int amt = 0; amt < 5 + program.getDeadFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getDeadFail(), amt));
                }
                break;
        }
        return root;
    }

    public static LinearLayout addTitleRow(Context context, String label, Program program){
        LinearLayout titles = new LinearLayout(context);
        titles.setOrientation(LinearLayout.HORIZONTAL);
        titles.setWeightSum(4);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titles.setLayoutParams(titleParams);


        TextView set = new TextView(context);
        set.setText("Set");
        set.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        set.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView target = new TextView(context);
        target.setText("Target");
        target.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        target.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView result = new TextView(context);
        result.setText("Result");
        result.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        result.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView l = new TextView(context);
        l.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        l.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        titles.addView(set);
        titles.addView(target);
        titles.addView(result);
        titles.addView(l);

        return titles;
    }

    @SuppressLint("ResourceAsColor")
    public static LinearLayout addT2Workout(Context context, String label, Program program, LinearLayout root){
        LinearLayout type = new LinearLayout(context);
        LinearLayout.LayoutParams internalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        type.setOrientation(LinearLayout.HORIZONTAL);
        type.setWeightSum(4);
        internalParams.setMargins(10, 10, 10 ,10);
        type.setLayoutParams(internalParams);

        TextView name = new TextView(context);
        name.setText(label);
        name.setId(R.id.programName);
        name.setTextColor(R.color.primary);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setTextSize(16);

        TextView tier = new TextView(context);
        tier.setText("T2");
        tier.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tier.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tier.setTypeface(name.getTypeface(), Typeface.BOLD);
        tier.setTextSize(16);

        LinearLayout titles = addTitleRow(context, label, program);

        LinearLayout addWarmup = addWarmup(context);
        LinearLayout warmUpButton = addWarmupButton(context, root);

        root.addView(name);
        root.addView(type);
        root.addView(titles);
        root.addView(addWarmup);
        root.addView(warmUpButton);


        double range;
        switch (label){
            case "Flat Barbell Bench Press":
                double base = program.getBenchMax();
                if(program.getBench3RepMax() == 0){
                    //calculate max
                    range = (base / 100) * 70;
                }else{
                    range = (program.getBench3RepMax() / 100) * 70;
                }
                for (int amt = 0; amt < 5 + program.getBenchFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getBenchFail(), amt));
                }
                break;
            case "Barbell Squat":
                double squatBase = program.getSquatMax();
                if(program.getBench3RepMax() == 0){
                    //calculate max
                    range = (squatBase / 100) * 70;
                }else{
                    range = (program.getSquat3RepMax() / 100) * 70;
                }
                for (int amt = 0; amt < 5 + program.getBenchFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getSquatFail(), amt));
                }
                break;
            case "Overhead Press":
                double ohpBase = program.getOhpMax();
                if(program.getOhp3RepMax() == 0){
                    //calculate max
                    range = (ohpBase / 100) * 70;
                }else{
                    range = (program.getSquat3RepMax() / 100) * 70;
                }
                for (int amt = 0; amt < 5 + program.getOhpFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getOhpFail(), amt));
                }
                break;
            case "Dead-lift":
                double deadBase = program.getDeadliftMax();
                if(program.getDead3RepMax() == 0){
                    //calculate max
                    range = (deadBase / 100) * 70;
                }else{
                    range = (program.getDead3RepMax() / 100) * 70;
                }
                for (int amt = 0; amt < 5 + program.getDeadFail(); amt++){
                    root.addView(addProgramSet(context, range, 3 - program.getDeadFail(), amt));
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
        name.setTextColor(R.color.primary);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setTextSize(16);

        TextView tier = new TextView(context);
        tier.setText("T3");
        tier.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tier.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tier.setTypeface(name.getTypeface(), Typeface.BOLD);
        tier.setTextSize(16);

        LinearLayout titles = addTitleRow(context, label, program);

        root.addView(name);
        root.addView(type);
        root.addView(titles);


        root.addView(addProgramSet(context, 0, 15, 1));
        root.addView(addProgramSet(context, 0, 15, 2));
        root.addView(addProgramSet(context, 0, 25, 3));


        return root;
    }

    public static LinearLayout addWarmup(Context context){
        LinearLayout warmupLayout = new LinearLayout(context);
        warmupLayout.setOrientation(LinearLayout.HORIZONTAL);
        warmupLayout.setWeightSum(4);
        warmupLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView warm = new TextView(context);
        warm.setText("Warmup");
        warm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        warm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t1 = new TextView(context);
        t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        EditText r = new EditText(context);
        r.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        ImageButton tick = new ImageButton(context);
        tick.setBackgroundResource(R.drawable.ic_baseline_tick);
        LinearLayout.LayoutParams tickParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        tickParams.setMargins(90, 40, 90 ,10);
        tick.setLayoutParams(tickParams);


        warmupLayout.addView(warm);
        warmupLayout.addView(t1);
        warmupLayout.addView(r);
        warmupLayout.addView(tick);

        return warmupLayout;
    }

    @SuppressLint("ResourceAsColor")
    public static LinearLayout addWarmupButton(Context context, LinearLayout root){
        LinearLayout addWarmup = new LinearLayout(context);
        addWarmup.setOrientation(LinearLayout.HORIZONTAL);
        addWarmup.setWeightSum(1);

        Button addWarm = new Button(context);
        addWarm.setText("Add Warmup Set");
        addWarm.setHintTextColor(R.color.primary);
        addWarm.setBackgroundColor(R.color.background);
        addWarm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addWarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeView(addWarmup);
                root.addView(addWarmup(context));
                root.addView(addWarmup);
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
        resW.setHint("kg");
        resW.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        resW.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText resR = new EditText(context);
        resR.setHint("reps");
        resR.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        resR.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        ImageButton tick = new ImageButton(context);
        tick.setBackgroundResource(R.drawable.ic_baseline_tick);
        LinearLayout.LayoutParams tickParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        tickParams.setMargins(90, 30, 90 ,10);
        tick.setLayoutParams(tickParams);

        set.addView(warm);
        set.addView(target);
        set.addView(resW);
        set.addView(resR);
        set.addView(tick);



        return set;

    }




}
