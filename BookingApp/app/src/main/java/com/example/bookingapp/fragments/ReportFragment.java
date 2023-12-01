package com.example.bookingapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.db.williamchart.data.AxisType;
import com.db.williamchart.data.Label;
import com.db.williamchart.view.BarChartView;
import com.example.bookingapp.BaseActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.SplashScreen;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ReportFragment extends Fragment {

    private static final LinkedHashMap<String, Float> data = new LinkedHashMap<String, Float>() {{
        put("jan", (float) 100);
        put("feb", (float) 80);
        put("mar", (float) 120);
        put("apr", (float) 110);
        put("may", (float) 130);
        put("jun", (float) 105);
    }};

    private static final LinkedHashMap<String, Float> data2 = new LinkedHashMap<String, Float>() {{
        put("jan", (float) 100);
        put("feb", (float) 80);
        put("mar", (float) 120);
        put("apr", (float) 110);
        put("may", (float) 130);
        put("jun", (float) 105);
    }};

    private static final LinkedHashMap<String, Float> data3 = new LinkedHashMap<String, Float>() {{
        put("jan", (float) 100);
        put("feb", (float) 80);
        put("mar", (float) 120);
        put("apr", (float) 110);
        put("may", (float) 130);
        put("jun", (float) 105);
    }};

    private static final LinkedHashMap<String, Float> data4 = new LinkedHashMap<String, Float>() {{
        put("jan", (float) 100);
        put("feb", (float) 80);
        put("mar", (float) 120);
        put("apr", (float) 110);
        put("may", (float) 130);
        put("jun", (float) 105);
    }};

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

        BarChartView intervalChartPrices = returnView.findViewById(R.id.interval_chart_reservations);
        intervalChartPrices.getAnimation().setDuration(1200L);
        intervalChartPrices.animate(data);

        intervalChartPrices.setLabelsSize(20f);
        intervalChartPrices.setScaleY(3f);
        intervalChartPrices.setAxis(AxisType.X);

        BarChartView intervalChartProfit = returnView.findViewById(R.id.interval_chart_profit);
        intervalChartProfit.getAnimation().setDuration(1200L);
        intervalChartProfit.animate(data2);

        intervalChartProfit.setLabelsSize(20f);
        intervalChartProfit.setScaleY(3f);
        intervalChartProfit.setAxis(AxisType.X);

        //we show interval report initially

        BarChartView accommodationChartPrices = returnView.findViewById(R.id.accommodation_chart_reservations);
        accommodationChartPrices.getAnimation().setDuration(1200L);
        accommodationChartPrices.animate(data3);

        accommodationChartPrices.setLabelsSize(20f);
        accommodationChartPrices.setScaleY(3f);
        accommodationChartPrices.setAxis(AxisType.X);

        BarChartView accommodationChartProfit = returnView.findViewById(R.id.accommodation_chart_profit);
        accommodationChartProfit.getAnimation().setDuration(1200L);
        accommodationChartProfit.animate(data4);

        accommodationChartProfit.setLabelsSize(20f);
        accommodationChartProfit.setScaleY(3f);
        accommodationChartProfit.setAxis(AxisType.X);


        Spinner accommodations = returnView.findViewById(R.id.spinner_accommodation);
        String[] acc = new String[]{"Flower apartment", "Modern Hotel"};
        ArrayAdapter<String> adapter_acc = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, acc);
        accommodations.setAdapter(adapter_acc);
        accommodations.setSelection(0, true);
        View v2 = accommodations.getSelectedView();
        ((TextView)v2).setTextColor(getResources().getColor(R.color.dark_gray));

        accommodations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView) view).setTextColor(getResources().getColor(R.color.dark_gray));
                //TODO: change data here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        accommodations.setSelection(0);

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
                    //if accommodation report is selected
                    returnView.findViewById(R.id.accommodation_report_layout).setVisibility(View.VISIBLE);
                    returnView.findViewById(R.id.interval_report_layout).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                returnView.findViewById(R.id.accommodation_report_layout).setVisibility(View.GONE);
            }
        }, 100);
        return returnView;
    }


}