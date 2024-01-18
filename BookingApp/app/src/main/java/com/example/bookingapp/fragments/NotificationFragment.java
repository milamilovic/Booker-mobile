package com.example.bookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.NotificationAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentNotificationBinding;
import com.example.bookingapp.dto.users.GuestDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.NotificationListing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;
    ArrayList<NotificationListing> notifications;
    ListView listView;
    private NotificationAdapter adapter;
    private static final String USER_ID_KEY = "user_id";

    private static final String USER_ROLE_KEY = "user_role";


    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        notifications = new ArrayList<>();
        prepareNotificationList(notifications);
        listView = root.findViewById(R.id.notification_list);
        adapter = new NotificationAdapter(getContext(), notifications, getActivity().getPreferences(Context.MODE_PRIVATE));
        listView.setAdapter(adapter);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long loggedId = sharedPref.getLong(USER_ID_KEY, -1);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        String loggedRole = sharedPref.getString(USER_ROLE_KEY, "");
        LinearLayout ownes_view = binding.ownerNotificationContainer;
        LinearLayout guest_view = binding.guestNotificationContainer;
        GuestDTO guestDTO = null;
        OwnerDTO ownerDTO = null;
        if(loggedRole.equals(Role.GUEST.toString())) {
            guest_view.setVisibility(View.VISIBLE);
            ownes_view.setVisibility(View.GONE);
            StrictMode.setThreadPolicy(policy);
            Call<GuestDTO> call = ClientUtils.userService.getGuestById(loggedId);
            try{
                Response<GuestDTO> response = call.execute();
                guestDTO = (GuestDTO) response.body();
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING GUEST");
                ex.printStackTrace();
            }

            ToggleButton toggle = binding.guestNotification;
            toggle.setChecked(!guestDTO.isNotificationEnabled());
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = toggle.isChecked();
                    System.out.println("CLICKED ENABLED: " + toggle.isChecked());
                    StrictMode.setThreadPolicy(policy);
                    Call<Void> call = ClientUtils.notificationService.changePreferences(loggedId, 0, toggle.isChecked());
                    try{
                        call.execute();
                        toggle.setChecked(!checked);
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE CHANGING PREFERENCE");
                        ex.printStackTrace();
                    }
                }
            });
        } else {
            guest_view.setVisibility(View.GONE);
            ownes_view.setVisibility(View.VISIBLE);

            StrictMode.setThreadPolicy(policy);
            Call<OwnerDTO> call = ClientUtils.userService.getOwnerById(loggedId);
            try{
                Response<OwnerDTO> response = call.execute();
                ownerDTO = (OwnerDTO) response.body();
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING OWNER");
                ex.printStackTrace();
            }
            ToggleButton toggle = binding.accNotifToggle;
            toggle.setChecked(!ownerDTO.isAccNotificationEnabled());
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = toggle.isChecked();
                    System.out.println("CLICKED ENABLED: " + toggle.isChecked());
                    StrictMode.setThreadPolicy(policy);
                    Call<Void> call = ClientUtils.notificationService.changePreferences(loggedId, 4, toggle.isChecked());
                    try{
                        call.execute();
                        toggle.setChecked(!checked);
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE CHANGING PREFERENCE");
                        ex.printStackTrace();
                    }
                }
            });
            ToggleButton toggle2 = binding.rateNotifToggle;
            toggle2.setChecked(!ownerDTO.isRatingNotificationEnabled());
            toggle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = toggle2.isChecked();
                    System.out.println("CLICKED ENABLED: " + toggle2.isChecked());
                    StrictMode.setThreadPolicy(policy);
                    Call<Void> call = ClientUtils.notificationService.changePreferences(loggedId, 3, toggle2.isChecked());
                    try{
                        call.execute();
                        toggle2.setChecked(!checked);
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE CHANGING PREFERENCE");
                        ex.printStackTrace();
                    }
                }
            });
            ToggleButton toggle3 = binding.cancelNotifToggle;
            toggle3.setChecked(!ownerDTO.isCancellationNotificationEnabled());
            toggle3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = toggle3.isChecked();
                    System.out.println("CLICKED ENABLED: " + toggle3.isChecked());
                    StrictMode.setThreadPolicy(policy);
                    Call<Void> call = ClientUtils.notificationService.changePreferences(loggedId, 2, toggle3.isChecked());
                    try{
                        call.execute();
                        toggle3.setChecked(!checked);
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE CHANGING PREFERENCE");
                        ex.printStackTrace();
                    }
                }
            });
            ToggleButton toggle4 = binding.reqNotifToggle;
            toggle4.setChecked(!ownerDTO.isRequestNotificationEnabled());
            toggle4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = toggle.isChecked();
                    System.out.println("CLICKED ENABLED: " + toggle4.isChecked());
                    StrictMode.setThreadPolicy(policy);
                    Call<Void> call = ClientUtils.notificationService.changePreferences(loggedId, 1, toggle4.isChecked());
                    try{
                        call.execute();
                        toggle4.setChecked(!checked);
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE CHANGING PREFERENCE");
                        ex.printStackTrace();
                    }
                }
            });
        };


        return root;
    }

    private void prepareNotificationList(ArrayList<NotificationListing> notifications) {
        notifications.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //call service and get accommodations that are adequate for search
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long loggedId = sharedPref.getLong(USER_ID_KEY, -1);
        Call<List<NotificationListing>> accommodations = ClientUtils.notificationService.getUsersNotifications(loggedId);
        try{
            Response<List<NotificationListing>> response = accommodations.execute();
            ArrayList<NotificationListing> listings = (ArrayList<NotificationListing>) response.body();
            for(NotificationListing a : listings) {
                notifications.add(a);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING NOTIFICATIONS");
            ex.printStackTrace();
        }

    }
}