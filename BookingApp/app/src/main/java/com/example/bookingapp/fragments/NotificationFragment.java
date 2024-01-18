package com.example.bookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.NotificationAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.NotificationListing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    FragmentAccommodationListingBinding binding;
    ArrayList<NotificationListing> notifications;
    ListView listView;
    private NotificationAdapter adapter;
    private static final String USER_ID_KEY = "user_id";

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
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
        binding = FragmentAccommodationListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        notifications = new ArrayList<>();
        prepareNotificationList(notifications);
        listView = root.findViewById(R.id.notification_list);
        adapter = new NotificationAdapter(getContext(), notifications, getActivity().getPreferences(Context.MODE_PRIVATE));
        listView.setAdapter(adapter);
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