package com.thimu.grapevine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The main activity
 *
 * @author Android Studio, Kĩthia Ngigĩ
 * @version 16.07.2020
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Create the activity
     * @param savedInstanceState the last saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_reads, R.id.navigation_search, R.id.navigation_bookshops, R.id.navigation_conversation)
                .build(); */
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /* NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0); */
        NavigationUI.setupWithNavController(navView, navController);
    }

}