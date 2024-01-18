package com.example.bookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.ApproveAccommodationFragment;
import com.example.bookingapp.fragments.CreateAccommodationBaseFragment;
import com.example.bookingapp.fragments.FavouriteAccommodationsFragment;
import com.example.bookingapp.fragments.HomeFragment;
import com.example.bookingapp.fragments.LoginFragment;
import com.example.bookingapp.fragments.NotificationFragment;
import com.example.bookingapp.fragments.RegisterFragment;
import com.example.bookingapp.fragments.ReportFragment;
import com.example.bookingapp.fragments.ReportedUsersFragment;
import com.example.bookingapp.fragments.ReservationRequestOwnerFragment;
import com.example.bookingapp.fragments.OwnerAccommodationFragmentListing;
import com.example.bookingapp.fragments.ReservationRequestsGuestFragment;
import com.example.bookingapp.fragments.UpdateAccommodationDetailsFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity{
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    private static final String USER_ID_KEY = "user_id";

    private static final String USER_ROLE_KEY = "user_role";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);



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

    public void setNavigation() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();

        //TODO: set menu items not visible for each role and add listeners for everything
        //MenuItem item = menu.getItem(0);   ->   this is how you get item by position
        //item.setVisible(false);

        MenuItem item0 = menu.getItem(0).setVisible(true);
        MenuItem item1 = menu.getItem(1).setVisible(false);
        MenuItem item2 = menu.getItem(2).setVisible(false);
        MenuItem item3 = menu.getItem(3).setVisible(false);
        MenuItem item4 = menu.getItem(4).setVisible(false);
        MenuItem item5 = menu.getItem(5).setVisible(false);
        MenuItem item6 = menu.getItem(6).setVisible(false);
        MenuItem item7 = menu.getItem(7).setVisible(false);
        MenuItem item8 = menu.getItem(8).setVisible(false);
        MenuItem item9 = menu.getItem(9).setVisible(false);
        MenuItem item10 = menu.getItem(10).setVisible(false);
        MenuItem item11 = menu.getItem(11).setVisible(true);
        MenuItem item12 = menu.getItem(12).setVisible(false);
        MenuItem item13 = menu.getItem(13).setVisible(false);
        MenuItem item14 = menu.getItem(14).setVisible(false);
        MenuItem item15 = menu.getItem(15).setVisible(false);

        // menu items for unregistered user

        // home (visible always for everyone)
        item0.setOnMenuItemClickListener((v -> {
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

        // login
        item11.setVisible(true);
        item11.setOnMenuItemClickListener((v -> {
            FragmentTransition.to(LoginFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        // register
        item12.setVisible(true);
        item12.setOnMenuItemClickListener((v -> {
            //Intent i = new Intent(BaseActivity.this, RegisterScreen.class);
            //startActivity(i);


            Intent intent = new Intent(BaseActivity.this, RegisterScreen.class);
            startActivity(intent);
            finish();  // Optional: finish the current activity if needed

            // Close the drawer after selecting an option
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }));

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Long userId = sharedPreferences.getLong(USER_ID_KEY, 0);
        if (userId!=0) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> userCall = ClientUtils.userService.getById(userId);
            try {
                Response<UserDTO> response = userCall.execute();
                UserDTO user = (UserDTO) response.body();

                // login and register aren't visible for logged in user
                item11.setVisible(false);
                item12.setVisible(false);

                //for all user roles

                // my account
                item1.setVisible(true);
                item1.setOnMenuItemClickListener((v -> {

                    if (user.getRole() == Role.GUEST || user.getRole() == Role.ADMIN) {
                        FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.fragment_placeholder, MyProfileFragment.newInstance(user));
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (user.getRole() == Role.OWNER) {
                        FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.fragment_placeholder, OwnerMyProfileFragment.newInstance(user));
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    // Close the drawer after selecting an option
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));

                // logout
                item15.setVisible(true);
                item15.setOnMenuItemClickListener((v -> {
                    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(USER_ID_KEY);
                    editor.remove(USER_ROLE_KEY);
                    editor.commit();
                    setNavigation();
                    FragmentTransition.to(LoginFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));

                // visible for guest
                if (user.getRole() == Role.GUEST) {
                    item2.setVisible(true); // reservations
                    item3.setVisible(true); // notifications
                    item5.setVisible(true); // reservation requests
                    item9.setVisible(true); // favourite accommodations
                } else if (user.getRole() == Role.OWNER) {
                    item2.setVisible(true); // reservations
                    item3.setVisible(true); // notifications
                    item4.setVisible(true); // my accommodations
                    item5.setVisible(true); // reservation requests
                    item10.setVisible(true); // reports
                    item13.setVisible(true); // create accommodation
                } else if (user.getRole() == Role.ADMIN) {
                    item6.setVisible(true); // approve comments
                    item7.setVisible(true); // approve blocking
                    item8.setVisible(true); // approve accommodations
                } else{
                    item11.setVisible(true);
                    item12.setVisible(true);
                }

                item3.setOnMenuItemClickListener((v -> {
                    FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.fragment_placeholder, NotificationFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();

                    // Close the drawer after selecting an option
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));


                item4.setOnMenuItemClickListener((v -> {
                    FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.fragment_placeholder, OwnerAccommodationFragmentListing.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();

                    // Close the drawer after selecting an option
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));

                item5.setOnMenuItemClickListener((v -> {
                    if (user.getRole() == Role.GUEST) {
                        FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.fragment_placeholder, ReservationRequestsGuestFragment.newInstance());
                        transaction.addToBackStack("requests");
                        transaction.commit();
                    } else if (user.getRole() == Role.OWNER) {
                        FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.fragment_placeholder, ReservationRequestOwnerFragment.newInstance());
                        transaction.addToBackStack("requests");
                        transaction.commit();
                    }

                    // Close the drawer after selecting an option
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));

                item6.setOnMenuItemClickListener((v -> {
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

                item8.setOnMenuItemClickListener((v -> {
                    FragmentTransaction transaction = BaseActivity.this.getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.fragment_placeholder, ApproveAccommodationFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();

                    // Close the drawer after selecting an option
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));

                item9.setOnMenuItemClickListener((v -> {
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

                item10.setOnMenuItemClickListener((v -> {
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


                item13.setOnMenuItemClickListener((v -> {
                    FragmentTransition.to(CreateAccommodationBaseFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));

                item14.setOnMenuItemClickListener((v -> {
                    FragmentTransition.to(UpdateAccommodationDetailsFragment.newInstance(), BaseActivity.this, false, R.id.fragment_placeholder);

                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            item11.setVisible(true);
            item12.setVisible(true);
        }

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


    private void handleLoggedInUser(String token) {
        // Example: Display a message with the token
        Toast.makeText(this, "Logged in with token: " + token, Toast.LENGTH_SHORT).show();
    }

    private void handleLoggedInUserWithId(int userId) {
        // Example: Display a message with the user ID
        Toast.makeText(this, "Logged in with user ID: " + userId, Toast.LENGTH_SHORT).show();
    }
}