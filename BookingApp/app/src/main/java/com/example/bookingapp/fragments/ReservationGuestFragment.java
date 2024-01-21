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
import com.example.bookingapp.adapters.ReservationGuestAdapter;
import com.example.bookingapp.adapters.ReservationOwnerAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentReservationGuestBinding;
import com.example.bookingapp.databinding.FragmentReservationOwnerBinding;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.Reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReservationGuestFragment extends Fragment {
    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    private FragmentReservationGuestBinding binding;
    private ReservationGuestAdapter adapter;
    ListView listView;

    private static final String ARG_PARAM = "param";
    private static final String USER_ID_KEY = "user_id";


    public ReservationGuestFragment() {
        // Required empty public constructor
    }


    public static ReservationGuestFragment newInstance() {
        ReservationGuestFragment fragment = new ReservationGuestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReservationGuestAdapter(getActivity(), reservations);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservationGuestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareReservationsList(reservations);
        listView = root.findViewById(R.id.reservation_list);
        adapter = new ReservationGuestAdapter(getContext(), reservations);
        listView.setAdapter(adapter);
        return root;
    }

    private void prepareReservationsList(ArrayList<Reservation> reservations) {
        reservations.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        Call<List<Reservation>> reservationsForGuest = ClientUtils.reservationService.findReservationsForGuest(userID);
        try{
            Response<List<Reservation>> response = reservationsForGuest.execute();
            ArrayList<Reservation> listings = (ArrayList<Reservation>) response.body();
                for(Reservation r : listings) {
                    reservations.add(r);
                }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("rez");
        System.out.println(reservations);



    }
}