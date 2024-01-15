package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.FavouriteAccommodationAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentFavouriteAccommodationsBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.FavouriteAccommodationListing;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FavouriteAccommodationsFragment extends Fragment {
    private static final String USER_ID_KEY = "user_id";

    public static ArrayList<FavouriteAccommodationListing> accommodations = new ArrayList<FavouriteAccommodationListing>();
    private FragmentFavouriteAccommodationsBinding binding;
    private FavouriteAccommodationAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static final String ARG_PARAM = "param";

    ListView listView;

    public static FavouriteAccommodationsFragment newInstance() {
        return new FavouriteAccommodationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new FavouriteAccommodationAdapter(getActivity(), accommodations, getActivity().getPreferences(Context.MODE_PRIVATE));
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouriteAccommodationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareAccommodationList(accommodations);
        listView = root.findViewById(R.id.list);
        adapter = new FavouriteAccommodationAdapter(getContext(), accommodations, getActivity().getPreferences(Context.MODE_PRIVATE));
        listView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareAccommodationList(ArrayList<FavouriteAccommodationListing> products){
        products.clear();   //in case it's not initialization but searching
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        Call<List<FavouriteAccommodationListing>> accommodations = ClientUtils.accommodationService.findFavourite(userID);
        try{
            Response<List<FavouriteAccommodationListing>> response = accommodations.execute();
            ArrayList<FavouriteAccommodationListing> listings = (ArrayList<FavouriteAccommodationListing>) response.body();
            for(FavouriteAccommodationListing a : listings) {
                products.add(a);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING FAVOURITE ACCOMMODATIONS");
            ex.printStackTrace();
        }
    }
}