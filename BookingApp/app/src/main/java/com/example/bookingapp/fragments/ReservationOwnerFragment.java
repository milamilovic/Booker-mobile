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
import com.example.bookingapp.adapters.ReservationOwnerAdapter;
import com.example.bookingapp.adapters.ReservationRequestOwnerAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentReservationOwnerBinding;
import com.example.bookingapp.databinding.FragmentReservationRequestOwnerBinding;
import com.example.bookingapp.model.AccommodationRequestDTO;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.Reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class ReservationOwnerFragment extends Fragment {

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    private FragmentReservationOwnerBinding binding;
    private ReservationOwnerAdapter adapter;
    ListView listView;

    private static final String ARG_PARAM = "param";
    private static final String USER_ID_KEY = "user_id";


    public ReservationOwnerFragment() {
        // Required empty public constructor
    }


    public static ReservationOwnerFragment newInstance() {
        ReservationOwnerFragment fragment = new ReservationOwnerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReservationOwnerAdapter(getActivity(), reservations);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservationOwnerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareReservationsList(reservations);
        listView = root.findViewById(R.id.reservation_list);
        adapter = new ReservationOwnerAdapter(getContext(), reservations);
        listView.setAdapter(adapter);
        return root;
    }

    private void prepareReservationsList(ArrayList<Reservation> reservations) {
        reservations.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        Call<List<ApproveAccommodationListing>> accommodations = ClientUtils.accommodationService.findAllForOwner(userID);
        try{
            Response<List<ApproveAccommodationListing>> response = accommodations.execute();
            ArrayList<ApproveAccommodationListing> listings = (ArrayList<ApproveAccommodationListing>) response.body();
            for(ApproveAccommodationListing a : listings) {
                StrictMode.setThreadPolicy(policy);
                Call<List<Reservation>> allReservationsForAccommodation = ClientUtils.reservationService.findReservationsForAccommodation(a.getId());
                try{
                    Response<List<Reservation>> response2 = allReservationsForAccommodation.execute();
                    ArrayList<Reservation> listings2 = (ArrayList<Reservation>) response2.body();
                    for(Reservation r : listings2) {
                        reservations.add(r);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("rez");
        System.out.println(reservations);



    }
}