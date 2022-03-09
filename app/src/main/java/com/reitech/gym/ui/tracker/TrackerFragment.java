package com.reitech.gym.ui.tracker;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.reitech.gym.R;
import com.reitech.gym.databinding.FragmentDashboardBinding;
import com.reitech.gym.ui.programs.DashboardViewModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TrackerFragment extends Fragment {

    private LocalDate date;

    public TrackerFragment(LocalDate date){
        super(R.layout.fragment_tracker);
        this.date = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView trackerDate = view.findViewById(R.id.tackerDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

        trackerDate.setText(date.format(formatter));
    }



}
