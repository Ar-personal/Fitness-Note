package com.reitech.gym.ui.programs.gzclp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.reitech.gym.R;
import com.reitech.gym.ui.programs.AddProgramFragment;
import com.reitech.gym.ui.programs.Program;
import com.reitech.gym.ui.programs.ProgramFragment;
import com.reitech.gym.ui.tracker.TrackerFragment;

public class ConfigureGZCLP extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceStat) {

        final View view = inflater.inflate(R.layout.congfigure_gzclp, container, false);

        Button submit = view.findViewById(R.id.gzclp_submit);





        Switch cunit = view.findViewById(R.id.chip_unit);
        Switch cweight = view.findViewById(R.id.chip_weight);

        EditText benchEdit = view.findViewById(R.id.gzclp_bench);
        EditText squatEdit = view.findViewById(R.id.gzclp_squat);
        EditText deadEdit = view.findViewById(R.id.gzclp_deadlift);
        EditText ophEdit = view.findViewById(R.id.gzclp_ohp);

        EditText maxEdit = view.findViewById(R.id.gzclp_max);

        submit.setOnClickListener(new View.OnClickListener() {
            int unit = 0;
            int weight = 0;
            double bench;
            double squat;
            double deadlift;
            double ohp;
            double max;
            @Override
            public void onClick(View view) {
                if (cunit.isChecked()){
                    unit = 1;
                }

                if(cweight.isChecked()){
                    weight = 1;
                }

                if(benchEdit.getText().toString().equals("") || squatEdit.getText().toString().equals("") ||
                        deadEdit.getText().toString().equals("") || ophEdit.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //get variables for adding the program to the program page root linear layout
                bench = Double.parseDouble(benchEdit.getText().toString());
                squat = Double.parseDouble(squatEdit.getText().toString());
                deadlift = Double.parseDouble(deadEdit.getText().toString());
                ohp = Double.parseDouble(ophEdit.getText().toString());

                max = Double.parseDouble(maxEdit.getText().toString());

                Program program = new Program("GZCLP", unit, weight);
                program.setDescription(getString(R.string.gzclp_desc));
                program.setImageResouceID(R.drawable.weight);

                Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("PROGRAMS");

                ProgramFragment programFragment = (ProgramFragment) f;
                programFragment.addProgramToLayout(program);
                dismiss();
            }
        });

        return view;
    }

    public void dismiss(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
