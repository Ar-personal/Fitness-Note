package com.reitech.gym.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.reitech.gym.MainActivity;
import com.reitech.gym.R;
import com.reitech.gym.ui.tracker.WorkoutViewHolder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

import static android.content.Intent.ACTION_GET_CONTENT;
import static android.os.Parcelable.CONTENTS_FILE_DESCRIPTOR;

public class SettingsFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setVisibility(View.INVISIBLE);

        LinearLayout linearLayout = view.findViewById(R.id.import_csv);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(ACTION_GET_CONTENT);
            intent.setType("csv");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select CSV file to import"),1);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                ex.printStackTrace();
            }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Content","In onactivity");
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String path = data.getData().getPath();
            Log.e("Pathb",path);
            readCSV(new File(data.getData().getPath()));
        }
    }


    public void readCSV(File uri){
        InputStream inputStream = null;
        try {
             inputStream = getView().getContext().getContentResolver().openInputStream(Uri.fromFile(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));

        try {
            List<String[]> lines = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }



    }

}
