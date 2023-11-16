package com.example.bookingapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookingapp.fragments.HomeFragment;
import com.example.bookingapp.fragments.LoginFragment;
import com.example.bookingapp.fragments.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);

        FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_placeholder, HomeFragment.newInstance("Fragment 1", "Ovo je fragment 1"));
        transaction.commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        setup();
        setNavigation();

    }
    public void setup() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.hamburger_menu);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setNavigation() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();

        //TODO: set menu items not visible for each role and add listeners for everything
        //MenuItem item = menu.getItem(0);   ->   this is how you get item by position
        //item.setVisible(false);
        menu.getItem(0).setOnMenuItemClickListener((v -> {
            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_placeholder, HomeFragment.newInstance("Fragment 1", "Ovo je fragment 1"));
            transaction.addToBackStack(null);
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(10).setOnMenuItemClickListener((v -> {
            FragmentTransition.to(LoginFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(11).setOnMenuItemClickListener((v -> {
            //Intent i = new Intent(BaseActivity.this, RegisterScreen.class);
            //startActivity(i);


            FragmentTransition.to(RegisterFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final int help = R.id.help;
        final int settings = R.id.settings;
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == help) {
            //show help
            Toast.makeText(BaseActivity.this, "Help", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == settings) {
            //show settings
            Toast.makeText(BaseActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setNavigation();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}