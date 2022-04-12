package com.reitech.gym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.CSVWriter;
import com.reitech.gym.databinding.ActivityMainBinding;
import com.reitech.gym.ui.calendar.CalendarAdapter;
import com.reitech.gym.ui.data.AppDatabase;
import com.reitech.gym.ui.data.ExerciseListSetup;
import com.reitech.gym.ui.data.LocalDateTimeConverter;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.home.HomeFragment;
import com.reitech.gym.ui.programs.DashboardFragment;
import com.reitech.gym.ui.settings.SettingsFragment;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener{

    private ActivityMainBinding binding;
    public Toolbar toolbar;
    public Workout.WorkoutDao workoutDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().hide();
        new ExerciseListSetup();
        dataBaseSetup();
        savedDataSetup();


        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, homeFragment).addToBackStack(null).commit();

        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            Fragment settings = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, settings, "SETTINGS").addToBackStack(null).commit();
            return true;
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, homeFragment).commit();
                        break;
                    case R.id.navigation_programs:
                        getSupportFragmentManager().beginTransaction().remove(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new DashboardFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }

    private void dataBaseSetup(){
        new Thread(() ->{
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "fitboost_db").allowMainThreadQueries().build();
            workoutDao = db.userDao();
            List<Workout> l = workoutDao.getAll();
        }).start();
    }

    private void savedDataSetup() {
        File folder = new File(getApplicationContext().getExternalFilesDir(null) + "/fitboost");
        boolean exists = false;

        if(!folder.exists())
            exists = folder.mkdir();

        final String trackedWorkouts = folder.toString() + "/" + "workouts.csv";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_settings:
                Fragment settings = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, settings, "SETTINGS").addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {

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