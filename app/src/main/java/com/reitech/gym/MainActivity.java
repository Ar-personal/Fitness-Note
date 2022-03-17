package com.reitech.gym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opencsv.CSVWriter;
import com.reitech.gym.databinding.ActivityMainBinding;
import com.reitech.gym.ui.calendar.CalendarAdapter;
import com.reitech.gym.ui.home.HomeFragment;
import com.reitech.gym.ui.tracker.TrackerFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        savedDataSetup();


        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, homeFragment).addToBackStack(null).commit();


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_programs)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);

//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NotNull NavController navController, @NotNull NavDestination navDestination, @Nullable Bundle bundle) {
//                if(navDestination.getId() == R.id.navigation_tracker){
//                    navView.setVisibility(View.INVISIBLE);
//                }else{
//                    navView.setVisibility(View.VISIBLE);
//                }
//            }
//        });


//        Fragment trackerFragment = new TrackerFragment(selectedDate);
//        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, trackerFragment).addToBackStack(null).commit();


    }


    private void savedDataSetup() {
        File folder = new File(getApplicationContext().getExternalFilesDir(null) + "/gains_tracker");
        boolean exists = false;

        if(!folder.exists())
            exists = folder.mkdir();

        final String trackedWorkouts = folder.toString() + "/" + "workouts.csv";

        new Thread(){
            public void run(){
                try {
                    CSVWriter csvWriter = new CSVWriter(new FileWriter(trackedWorkouts));
                    String[] workout = "2022-03-09,Flat Barbell Bench Press,Chest,10.0, 5".split(",");
                    csvWriter.writeNext(workout);
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 120);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 121);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initWidgets();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        initWidgets();
//    }
}