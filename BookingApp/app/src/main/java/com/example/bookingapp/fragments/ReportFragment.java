package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookingapp.R;

public class ReportFragment extends Fragment {


    public ReportFragment() {
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
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
        View returnView =  inflater.inflate(R.layout.fragment_report, container, false);

        Spinner dropdown = returnView.findViewById(R.id.spinner);
        String[] items = new String[]{"date interval report", "single accommodation report"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(0, true);
        View v = dropdown.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.dark_gray));

        //we show interval report initially
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView) view).setTextColor(getResources().getColor(R.color.dark_gray));
                if(position==0) {
                    //if interval report selected
                    returnView.findViewById(R.id.interval_report_layout).setVisibility(View.VISIBLE);
                    returnView.findViewById(R.id.accommodation_report_layout).setVisibility(View.GONE);
                } else {
                    returnView.findViewById(R.id.accommodation_report_layout).setVisibility(View.VISIBLE);
                    returnView.findViewById(R.id.interval_report_layout).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        dropdown.setSelection(0);
        returnView.findViewById(R.id.accommodation_report_layout).setVisibility(View.GONE);

        return returnView;
    }


}