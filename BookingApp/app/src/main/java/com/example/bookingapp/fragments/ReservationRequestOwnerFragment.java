package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ReservationRequestGuestAdapter;
import com.example.bookingapp.adapters.ReservationRequestOwnerAdapter;
import com.example.bookingapp.databinding.FragmentReservationRequestOwnerBinding;
import com.example.bookingapp.databinding.FragmentReservationRequestsGuestBinding;
import com.example.bookingapp.model.AccommodationRequestOwnerDTO;
import com.example.bookingapp.model.AccommodationRequestOwnerDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ReservationRequestOwnerFragment extends Fragment {


    public static ArrayList<AccommodationRequestOwnerDTO> requests = new ArrayList<AccommodationRequestOwnerDTO>();
    private FragmentReservationRequestOwnerBinding binding;
    private ReservationRequestOwnerAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static final String ARG_PARAM = "param";

    ListView listView;

    public ReservationRequestOwnerFragment() {
        // Required empty public constructor
    }


    public static ReservationRequestOwnerFragment newInstance() {
        ReservationRequestOwnerFragment fragment = new ReservationRequestOwnerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requests = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReservationRequestOwnerAdapter(getActivity(), requests);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservationRequestOwnerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareRequestsList(requests);
        listView = root.findViewById(R.id.list);
        adapter = new ReservationRequestOwnerAdapter(getContext(), requests);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareRequestsList(ArrayList<AccommodationRequestOwnerDTO> requests){
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy.");
        try {
            requests.add(new AccommodationRequestOwnerDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023."), 4, 1L));
            requests.add(new AccommodationRequestOwnerDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024."), 2, 2L));
            requests.add(new AccommodationRequestOwnerDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023."), 1, 3L));
            requests.add(new AccommodationRequestOwnerDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023."), 5, 4L));
            requests.add(new AccommodationRequestOwnerDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023."), 4, 1L));
            requests.add(new AccommodationRequestOwnerDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024."), 2, 2L));
            requests.add(new AccommodationRequestOwnerDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023."), 1, 3L));
            requests.add(new AccommodationRequestOwnerDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023."), 5, 4L));
            requests.add(new AccommodationRequestOwnerDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023."), 4, 1L));
            requests.add(new AccommodationRequestOwnerDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024."), 2, 2L));
            requests.add(new AccommodationRequestOwnerDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023."), 1, 3L));
            requests.add(new AccommodationRequestOwnerDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023."), 5, 4L));
            requests.add(new AccommodationRequestOwnerDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023."), 4, 1L));
            requests.add(new AccommodationRequestOwnerDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024."), 2, 2L));
            requests.add(new AccommodationRequestOwnerDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023."), 1, 3L));
            requests.add(new AccommodationRequestOwnerDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023."), 5, 4L));
            requests.add(new AccommodationRequestOwnerDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023."), 4, 1L));
            requests.add(new AccommodationRequestOwnerDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024."), 2, 2L));
            requests.add(new AccommodationRequestOwnerDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023."), 1, 3L));
            requests.add(new AccommodationRequestOwnerDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023."), 5, 4L));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    private void updateLabel(EditText editText, Calendar myCalendar){
        String myFormat="dd.MM.yyyy.";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        editText.setTextColor(Color.parseColor("#603c3c3c"));
    }
}