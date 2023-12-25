package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.databinding.FragmentHomeBinding;
import com.example.bookingapp.databinding.FragmentLoginBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    FragmentHomeBinding binding;
    final Calendar myCalendar= Calendar.getInstance();


    Button searchButton;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button logIn = binding.search;
        logIn.setOnClickListener(v -> {
            //collect data
            String location = binding.location.getText().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
            Date fromDate = null;
            Date toDate = null;
            try{
                String from=(binding.from.getText().toString());
                String to=(binding.until.getText().toString());
                fromDate = formatter.parse(from);
                toDate = formatter.parse(to);
            }
            catch (java.text.ParseException e)
            {
                Toast.makeText(getContext(), "Please input all the fields correctly", Toast.LENGTH_SHORT).show();
                return;
            }
            int people = Integer.parseInt(binding.people.getText().toString());
            //validate
            boolean valid = validateSearch(location, fromDate, toDate, people);
            //transition
            if(valid) {
                FragmentTransition.to(AccommodationListingFragment.newInstance(location, fromDate, toDate, people), getActivity(), true, R.id.fragment_placeholder);
            } else {
                Toast.makeText(getContext(), "Please input all the fields correctly", Toast.LENGTH_SHORT).show();
            }
        });


        EditText from = binding.from;

        DatePickerDialog.OnDateSetListener fromDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(from, myCalendar);
            }
        };
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),fromDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditText until = binding.until;

        DatePickerDialog.OnDateSetListener toDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(until, myCalendar);
            }
        };
        until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),toDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }

    private boolean validateSearch(String location, Date fromDate, Date toDate, int people) {
        if(location.equals("") || fromDate==null || toDate==null || fromDate.after(toDate) || people<1 || fromDate.before(new Date())) {
            return false;
        }
        return true;
    }

    private void updateLabel(EditText editText, Calendar myCalendar){
        String myFormat="dd.MM.yyyy.";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        editText.setTextColor(Color.parseColor("#603c3c3c"));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}