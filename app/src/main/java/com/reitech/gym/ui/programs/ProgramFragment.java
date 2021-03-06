package com.reitech.gym.ui.programs;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.LayoutHelpers.LayoutHelper;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.data.WorkoutLine;
import com.reitech.gym.ui.exerciselist.AddExerciseFragment;
import com.reitech.gym.ui.programs.gzclp.GZCLP;
import com.reitech.gym.ui.tracker.TrackerFragment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ProgramFragment extends Fragment {

    private ProgramsViewModel programsViewModel;
    private LinearLayout programHolder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_programs, container, false);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        //load programs or say add program
        LoadPrograms();

        programHolder = view.findViewById(R.id.programHolder);

        return view;
    }



    private void LoadPrograms() {
        com.reitech.gym.ui.data.Program.ProgramDao programDao = MainActivity.programDao;
        List<com.reitech.gym.ui.data.Program> programs = new ArrayList<>();
        try {
            programs = programDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        List<com.reitech.gym.ui.data.Program> finalPrograms = programs;
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                for (int i = 0; i < finalPrograms.size(); i++) {
                    try {
                        Program program = new Program(finalPrograms.get(i).name, finalPrograms.get(i).unitDefault, finalPrograms.get(i).weightIncrementDefault);
                        program.setProgramID(finalPrograms.get(i).pid);
                        program.setName(finalPrograms.get(i).name);
                        program.setImageResouceID(finalPrograms.get(i).imageResourceID);
                        program.setDescription(finalPrograms.get(i).description);
                        program.setBenchMax(finalPrograms.get(i).benchMax);
                        program.setDeadliftMax(finalPrograms.get(i).deadliftMax);
                        program.setOhpMax(finalPrograms.get(i).ohpMax);
                        program.setSquatMax(finalPrograms.get(i).squatMax);
                        program.setBenchFail(finalPrograms.get(i).benchFail);
                        program.setDeadFail(finalPrograms.get(i).deadFail);
                        program.setSquatFail(finalPrograms.get(i).squatFail);
                        program.setOhpFail(finalPrograms.get(i).ohpFail);
                        program.setBenchFailT2(finalPrograms.get(i).benchFailT2);
                        program.setDeadFailT2(finalPrograms.get(i).deadFailT2);
                        program.setSquatFailT2(finalPrograms.get(i).squatFailT2);
                        program.setOhpFailT2(finalPrograms.get(i).ohpFailT2);
                        program.setBenchT1ThreeRep(finalPrograms.get(i).benchT1ThreeRep);
                        program.setSquatT1ThreeRep(finalPrograms.get(i).squatT1ThreeRep);
                        program.setOhpT1ThreeRep(finalPrograms.get(i).ohpT1ThreeRep);
                        program.setDeadT1ThreeRep(finalPrograms.get(i).deadT1ThreeRep);
                        program.setBenchT2TenRep(finalPrograms.get(i).benchT2TenRep);
                        program.setSquatT2TenRep(finalPrograms.get(i).squatT2TenRep);
                        program.setOhpT2TenRep(finalPrograms.get(i).ohpT2TenRep);
                        program.setDeadT2TenRep(finalPrograms.get(i).deadT2TenRep);
                        program.setStreak(finalPrograms.get(i).streak);
                        program.setDaysCompleted(finalPrograms.get(i).daysCompleted);
                        program.setMaxIncreaseDefault(finalPrograms.get(i).maxIncreaseDefault);
                        addProgramToLayout(program);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        handler.sendEmptyMessage(1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @SuppressLint("ResourceAsColor")
    public void addProgramToLayout(Program program){
            LinearLayout child = LayoutHelper.addWorkout(getContext(), program.getName());
            child.setBackgroundResource(R.drawable.borderless_radial_corner);
            LinearLayout imgAndDesc = LayoutHelper.addProgramImageAndDescription(getContext(), program.getImageResouceID(), program.getDescription());
            FlexboxLayout progressionBlocks = LayoutHelper.addProgressionBlocks(getContext(), program.getDaysCompleted());
            LinearLayout continueButton = LayoutHelper.addContinueButton(getContext());
            LinearLayout historySettingsButton = LayoutHelper.addHistorySettingsButtons(getContext());
            Button history = (Button) historySettingsButton.getChildAt(0);
            Button settings = (Button) historySettingsButton.getChildAt(1);

            history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main,
                        new ProgramHistoryFragment("GZCLP"), "PROGRAM_HISTORY").addToBackStack(null).commit();
                }
            });

            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            Button cont = continueButton.findViewById(R.id.cont);
            cont.setTextColor(R.color.black);
            cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(program.getName()){
                        case "GZCLP":
                            getParentFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, new GZCLP(program)).addToBackStack(null).commit();
                            break;
                        default:
                            Toast.makeText(getContext(), "Program name unknown", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            });

            LinearLayout streaks = LayoutHelper.addWorkoutStatsToLayout(getContext(), program);
            child.addView(imgAndDesc);
            child.addView(progressionBlocks);
            child.addView(streaks);
            child.addView(continueButton);
            child.addView(historySettingsButton);

            programHolder.addView(child);

    }

    public void dismiss(){
        getParentFragmentManager().beginTransaction().remove(this)
                .commit();
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setVisibility(View.VISIBLE);
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addProgramFragment = new AddProgramFragment();
                getParentFragmentManager().beginTransaction()
                        .add(R.id.nav_host_fragment_activity_main, addProgramFragment, "ADDPROGRAM").addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onPause() {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        super.onPause();

    }
}