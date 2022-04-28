package com.reitech.gym.ui.programs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.reitech.gym.R;
import com.reitech.gym.ui.programs.gzclp.ConfigureGZCLP;
import com.reitech.gym.ui.tracker.Workout;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class AddProgramFragment extends Fragment{

    final Map<String, List<Workout>> exerciseMap = new LinkedHashMap<>();

    private SectionedRecyclerViewAdapter sectionedAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceStat) {

        final View view = inflater.inflate(R.layout.fragment_add_program, container, false);


        RelativeLayout gzcl = view.findViewById(R.id.gzcl_container);
        gzcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Start and configure GZCLP program?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getParentFragmentManager().beginTransaction()
                                        .add(R.id.nav_host_fragment_activity_main, new ConfigureGZCLP()).addToBackStack(null).commit();
                                dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                dismiss();
                            }
                        })
                        .show();
            }
        });

        return view;
    }



    public void dismiss(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


}

