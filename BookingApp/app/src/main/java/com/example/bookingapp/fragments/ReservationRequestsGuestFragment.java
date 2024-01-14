package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ReservationRequestGuestAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentReservationRequestsGuestBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.AccommodationRequestDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReservationRequestsGuestFragment extends Fragment {


    private static final String USER_ID_KEY = "user_id";
    public static ArrayList<AccommodationRequestDTO> requests = new ArrayList<AccommodationRequestDTO>();
    private FragmentReservationRequestsGuestBinding binding;
    private ReservationRequestGuestAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static final String ARG_PARAM = "param";

    ListView listView;

    public ReservationRequestsGuestFragment() {
        // Required empty public constructor
    }

    public static ReservationRequestsGuestFragment newInstance() {
        ReservationRequestsGuestFragment fragment = new ReservationRequestsGuestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requests = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReservationRequestGuestAdapter(getActivity(), requests);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservationRequestsGuestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareRequestsList(requests);
        listView = root.findViewById(R.id.list);
        adapter = new ReservationRequestGuestAdapter(getContext(), requests);
        listView.setAdapter(adapter);

        EditText date = binding.dateSearch;

        DatePickerDialog.OnDateSetListener datePicker =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(date, myCalendar);
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),datePicker,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }
    private void updateLabel(EditText editText, Calendar myCalendar){
        String myFormat="dd.MM.yyyy.";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        editText.setTextColor(Color.parseColor("#603c3c3c"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareRequestsList(ArrayList<AccommodationRequestDTO> requests){
        requests.clear();   //in case it's not initialization but searching
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //call service and get accommodations that are adequate for search
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        Call<List<AccommodationRequestDTO>> accommodations = ClientUtils.reservationRequestService.findGuestsReservationRequests(userID);
        try{
            Response<List<AccommodationRequestDTO>> response = accommodations.execute();
            ArrayList<AccommodationRequestDTO> listings = (ArrayList<AccommodationRequestDTO>) response.body();
            for(AccommodationRequestDTO a : listings) {
                requests.add(a);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}