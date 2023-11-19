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
import com.example.bookingapp.databinding.FragmentReservationRequestsGuestBinding;
import com.example.bookingapp.model.AccommodationRequestGuestDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReservationRequestsGuestFragment extends Fragment {


    public static ArrayList<AccommodationRequestGuestDTO> requests = new ArrayList<AccommodationRequestGuestDTO>();
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

    private void prepareRequestsList(ArrayList<AccommodationRequestGuestDTO> requests){
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy.");
        try {
            requests.add(new AccommodationRequestGuestDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024.")));
            requests.add(new AccommodationRequestGuestDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024.")));
            requests.add(new AccommodationRequestGuestDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024.")));
            requests.add(new AccommodationRequestGuestDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024.")));
            requests.add(new AccommodationRequestGuestDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(1L, "Flower Apartment", R.drawable.apartment_image, 180, 60, 4.1f, "waiting", formater.parse("12.12.2023."), formater.parse("14.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(2L, "Lovely Apartment", R.drawable.room_image, 450, 150, 4.6f, "approved", formater.parse("08.02.2024."), formater.parse("10.02.2024.")));
            requests.add(new AccommodationRequestGuestDTO(3L, "Today Home", R.drawable.apartment_image, 110, 20, 5f, "denied", formater.parse("01.12.2023."), formater.parse("04.12.2023.")));
            requests.add(new AccommodationRequestGuestDTO(4L, "Example Hotel", R.drawable.room_image, 360, 120, 4.2f, "waiting", formater.parse("25.12.2023."), formater.parse("28.12.2023.")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}