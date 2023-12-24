package com.example.bookingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookingapp.fragments.CreateAccommodationBaseFragment;
import com.example.bookingapp.fragments.FavouriteAccommodationsFragment;
import com.example.bookingapp.fragments.HomeFragment;
import com.example.bookingapp.fragments.LoginFragment;
import com.example.bookingapp.fragments.RegisterFragment;
import com.example.bookingapp.fragments.ReportFragment;
import com.example.bookingapp.fragments.ReportedUsersFragment;
import com.example.bookingapp.fragments.ReservationRequestOwnerFragment;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    public static String SYNC_DATA = "SYNC_DATA";
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;



    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String USER_ID_KEY = "user_id";

    public void saveJwtToken(String token) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(JWT_TOKEN_KEY, token);
        editor.apply();
    }

    public String getJwtToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString(JWT_TOKEN_KEY, null);
    }

    public void saveUserId(long userId) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, (int) userId);
        editor.apply();
    }

    // Retrieve user ID from SharedPreferences
    public int getUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getInt(USER_ID_KEY, -1); // -1 is a default value if the key is not found
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);

        String storedToken = getJwtToken();
//        if (storedToken != null) {
//            // Token exists, you can use it as needed
//            // Example: Call a method to handle a logged-in user
//            //handleLoggedInUser(storedToken);
//        }

        int storedUserId = getUserId();
        if (storedUserId != -1) {
            // User ID exists, you can use it as needed
            // Example: Call a method to handle a logged-in user
            handleLoggedInUserWithId(storedUserId);
        }

        FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_placeholder, LoginFragment.newInstance());
        transaction.commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        ImageView toolbarLogo = findViewById(R.id.toolbar_logo);
        toolbarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(HomeFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);
            }
        });

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
                    .replace(R.id.fragment_placeholder, HomeFragment.newInstance());
            transaction.addToBackStack("home");
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(1).setOnMenuItemClickListener((v -> {
            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_placeholder, MyProfileFragment.newInstance("Fragment 1", "Ovo je fragment 1"));
            transaction.addToBackStack(null);
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(5).setOnMenuItemClickListener((v -> {
//            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
//                    .beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .replace(R.id.fragment_placeholder, ReservationRequestsGuestFragment.newInstance());
            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_placeholder, ReservationRequestOwnerFragment.newInstance());
            transaction.addToBackStack("requests");
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(7).setOnMenuItemClickListener((v -> {
            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_placeholder, ReportedUsersFragment.newInstance());
            transaction.addToBackStack(null);
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(9).setOnMenuItemClickListener((v -> {
            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_placeholder, FavouriteAccommodationsFragment.newInstance());
            transaction.addToBackStack("favourites+");
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(10).setOnMenuItemClickListener((v -> {
            FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_placeholder, ReportFragment.newInstance());
            transaction.addToBackStack("reports");
            transaction.commit();

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(11).setOnMenuItemClickListener((v -> {
            FragmentTransition.to(LoginFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(12).setOnMenuItemClickListener((v -> {
            //Intent i = new Intent(BaseActivity.this, RegisterScreen.class);
            //startActivity(i);


            FragmentTransition.to(RegisterFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        menu.getItem(13).setOnMenuItemClickListener((v-> {
            FragmentTransition.to(CreateAccommodationBaseFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {

    }

    private void handleLoggedInUser(String token) {
        // Example: Display a message with the token
        Toast.makeText(this, "Logged in with token: " + token, Toast.LENGTH_SHORT).show();
    }

        private void handleLoggedInUserWithId(int userId) {
            // Example: Display a message with the user ID
            Toast.makeText(this, "Logged in with user ID: " + userId, Toast.LENGTH_SHORT).show();
        }
}