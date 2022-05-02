package com.reitech.gym;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reitech.gym.databinding.ActivityMainBinding;
import com.reitech.gym.ui.data.AppDatabase;
import com.reitech.gym.ui.data.ExerciseListSetup;
import com.reitech.gym.ui.data.Program;
import com.reitech.gym.ui.data.Workout;
import com.reitech.gym.ui.exerciselist.AddExerciseFragment;
import com.reitech.gym.ui.home.HomeFragment;
import com.reitech.gym.ui.home.HomeViewModel;
import com.reitech.gym.ui.programs.ProgramFragment;
import com.reitech.gym.ui.settings.SettingsFragment;
import com.reitech.gym.ui.tracker.TrackerFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener{

    private ActivityMainBinding binding;
    public Toolbar toolbar;
    public static Workout.WorkoutDao workoutDao;
    public static Program.ProgramDao programDao;
    public View fragment;
    public FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fab = findViewById(R.id.fab);
//        getSupportActionBar().hide();
        new ExerciseListSetup();
        dataBaseSetup();
        savedDataSetup();

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new TrackerFragment(LocalDate.now())).addToBackStack(null).commit();
            }
        });

        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, homeFragment, "HOME").addToBackStack(null).commit();

        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            Fragment settings = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, settings, "SETTINGS").addToBackStack(null).commit();
            return true;
        });

        fragment = findViewById(R.id.nav_host_fragment_activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().detach(homeFragment).attach(homeFragment).replace(R.id.nav_host_fragment_activity_main, homeFragment).addToBackStack(null).commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, homeFragment).addToBackStack(null).commit();
                        onResume();
                        break;
                    case R.id.navigation_programs:
                        getSupportFragmentManager().beginTransaction().remove(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new ProgramFragment(), "PROGRAMS").commit();
                        break;
                }
                return true;
            }
        });

    }

    private void dataBaseSetup(){
        new Thread(() ->{
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "fitboost_db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            workoutDao = db.userDao();
            programDao = db.programDao();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, settings, "SETTINGS").addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        if(currentFragment instanceof HomeFragment){
            System.out.println("home frag");
            onResume();
        }

        if(currentFragment instanceof TrackerFragment){
            System.out.println("tracker frag");
        }

        if(currentFragment instanceof AddExerciseFragment){
            System.out.println("add frag");
        }

        if(currentFragment instanceof ProgramFragment){
            System.out.println("program frag");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new TrackerFragment(LocalDate.now())).addToBackStack(null).commit();
            }
        });
    }
}