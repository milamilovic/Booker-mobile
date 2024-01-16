package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.db.williamchart.data.AxisType;
import com.db.williamchart.view.BarChartView;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.model.AccommodationName;
import com.example.bookingapp.model.ReportDataUnit;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReportFragment extends Fragment {
    private static final String USER_ID_KEY = "user_id";

    private static LinkedHashMap<String, Float> intervalPrices = null;

    private static LinkedHashMap<String, Float> intervalReservations = null;

    private static LinkedHashMap<String, Float> accommodationPrices = null;

    private static LinkedHashMap<String, Float> accommodationReservations = null;
    final Calendar myCalendar= Calendar.getInstance();
    private static Date fromDate = null;
    private static Date toDate = null;
    Spinner accommodations;

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

        EditText from = returnView.findViewById(R.id.from);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = formatter.parse("2023-01-01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        from.setText(formatter.format(fromDate));

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

        EditText until = returnView.findViewById(R.id.until);
        try {
            toDate = formatter.parse("2024-01-01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        until.setText(formatter.format(toDate));

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

        Button generateReportInterval = returnView.findViewById(R.id.generateReportInterval);
        generateReportInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                Long userID = sharedPref.getLong(USER_ID_KEY, 0);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                ArrayList<ReportDataUnit> data = null;
                Call<List<ReportDataUnit>> call = ClientUtils.accommodationService.getIntervalReport(userID, from.getText().toString(), until.getText().toString());
                try{
                    Response<List<ReportDataUnit>> response = call.execute();
                    data = (ArrayList<ReportDataUnit>) response.body();
                }catch(Exception ex){
                    System.out.println("ERROR WHILE GETTING DATA FOR INTERVAL REPORT");
                }

                ArrayList<ReportDataUnit> finalData = data;
                intervalPrices = new LinkedHashMap<String, Float>() {{
                    for(int i = 0; i< finalData.size(); i++) {
                        //put("jan", (float) 100);
                        put(finalData.get(i).getName(), (float) finalData.get(i).getProfit());
                    }

                }};
                BarChartView intervalChartPrices = returnView.findViewById(R.id.interval_chart_profit);
                intervalChartPrices.animate(intervalPrices);
                intervalReservations = new LinkedHashMap<String, Float>() {{
                    for(int i = 0; i< finalData.size(); i++) {
                        //put("jan", (float) 100);
                        put(finalData.get(i).getName(), (float) finalData.get(i).getReservations() / 100);
                    }
                }};
                BarChartView intervalChartReservations = returnView.findViewById(R.id.interval_chart_reservations);
                intervalChartReservations.animate(intervalReservations);
            }
        });

        Spinner dropdown = returnView.findViewById(R.id.spinner);
        String[] items = new String[]{"date interval report", "single accommodation report"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(0, true);
        View v = dropdown.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.dark_gray));

        EditText year_edit = returnView.findViewById(R.id.year);
        year_edit.setText("2023");

        accommodations = returnView.findViewById(R.id.spinner_accommodation);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<AccommodationName> accommodationNames = null;
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        Call<List<AccommodationName>> call = ClientUtils.accommodationService.getAccNames(userID);
        try{
            Response<List<AccommodationName>> response = call.execute();
            accommodationNames = (ArrayList<AccommodationName>) response.body();
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION NAMES");
            ex.printStackTrace();
        }
        String[] acc = new String[accommodationNames.size()];
        for(int i = 0; i < accommodationNames.size(); i++) {
            acc[i] = accommodationNames.get(i).getTitle();
        }
        ArrayAdapter<String> adapter_acc = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, acc);
        accommodations.setAdapter(adapter_acc);
        accommodations.setSelection(0, true);
        View v2 = accommodations.getSelectedView();
        ((TextView)v2).setTextColor(getResources().getColor(R.color.dark_gray));

        Button generateReportAcc = returnView.findViewById(R.id.generateReportAccommodation);
        generateReportAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accName = (String) accommodations.getSelectedItem();
                Long accId = 0L;
                StrictMode.setThreadPolicy(policy);
                Call<Long> call3 = ClientUtils.accommodationService.getAccId(accName);
                try{
                    Response<Long> response3 = call3.execute();
                    accId = (Long) response3.body();
                }catch(Exception ex){
                    System.out.println("ERROR WHILE GETTING ACCOMMODATION ID");
                }
                StrictMode.setThreadPolicy(policy);
                ArrayList<ReportDataUnit> data2 = null;
                int year = Integer.parseInt(year_edit.getText().toString());
                Call<List<ReportDataUnit>> call2 = ClientUtils.accommodationService.getAccReport(userID, year, accId);
                try{
                    Response<List<ReportDataUnit>> response2 = call2.execute();
                    data2 = (ArrayList<ReportDataUnit>) response2.body();
                }catch(Exception ex){
                    System.out.println("ERROR WHILE GETTING DATA FOR ACCOMMODATION REPORT");
                }

                ArrayList<ReportDataUnit> finalData2 = data2;
                accommodationPrices = new LinkedHashMap<String, Float>() {{
                    for(int i = 0; i< finalData2.size(); i++) {
                        //put("jan", (float) 100);
                        put(finalData2.get(i).getName(), (float) finalData2.get(i).getProfit());
                    }
                }};
                BarChartView accommodationChartPrices = returnView.findViewById(R.id.accommodation_chart_profit);
                accommodationChartPrices.animate(accommodationPrices);
                accommodationReservations = new LinkedHashMap<String, Float>() {{
                    for(int i = 0; i< finalData2.size(); i++) {
                        //put("jan", (float) 100);
                        put(finalData2.get(i).getName(), (float) finalData2.get(i).getReservations() / 100);
                    }
                }};

                BarChartView accommodationChartReservations = returnView.findViewById(R.id.accommodation_chart_reservations);
                accommodationChartReservations.animate(accommodationReservations);
            }
        });

        accommodations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView) view).setTextColor(getResources().getColor(R.color.dark_gray));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        accommodations.setSelection(0);
        initializeData();

        BarChartView intervalChartPrices = returnView.findViewById(R.id.interval_chart_profit);
        intervalChartPrices.getAnimation().setDuration(1200L);
        intervalChartPrices.animate(intervalPrices);

        intervalChartPrices.setLabelsSize(20f);
        intervalChartPrices.setScaleY(3f);
        intervalChartPrices.setAxis(AxisType.XY);

        BarChartView intervalChartReservations = returnView.findViewById(R.id.interval_chart_reservations);
        intervalChartReservations.getAnimation().setDuration(1200L);
        intervalChartReservations.animate(intervalReservations);

        intervalChartReservations.setLabelsSize(20f);
        intervalChartReservations.setScaleY(3f);
        intervalChartReservations.setAxis(AxisType.XY);

        //we show interval report initially

        BarChartView accommodationChartPrices = returnView.findViewById(R.id.accommodation_chart_profit);
        accommodationChartPrices.getAnimation().setDuration(1200L);
        accommodationChartPrices.animate(accommodationPrices);

        accommodationChartPrices.setLabelsSize(20f);
        accommodationChartPrices.setScaleY(3f);
        accommodationChartPrices.setScaleX(0.5f);
        accommodationChartPrices.setAxis(AxisType.XY);

        BarChartView accommodationChartReservations = returnView.findViewById(R.id.accommodation_chart_reservations);
        accommodationChartReservations.getAnimation().setDuration(1200L);
        accommodationChartReservations.animate(accommodationReservations);

        accommodationChartReservations.setLabelsSize(20f);
        accommodationChartReservations.setScaleY(3f);
        accommodationChartPrices.setScaleX(1f);
        accommodationChartReservations.setAxis(AxisType.XY);


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

        Button generatePdfInterval = returnView.findViewById(R.id.pdf);
        generatePdfInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnView.findViewById(R.id.interval_text).setVisibility(View.GONE);
                tackeAndSaveScreenShot(getActivity());
                returnView.findViewById(R.id.interval_text).setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "pdf downloaded", Toast.LENGTH_SHORT).show();
            }
        });

        Button generatePdfAccommodation = returnView.findViewById(R.id.pdf2);
        generatePdfAccommodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnView.findViewById(R.id.accommodation_text).setVisibility(View.GONE);
                tackeAndSaveScreenShot(getActivity());
                returnView.findViewById(R.id.accommodation_text).setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "pdf downloaded", Toast.LENGTH_SHORT).show();
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

    private void updateLabel(EditText editText, Calendar myCalendar){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        editText.setTextColor(Color.parseColor("#3c3c3c"));
    }

    private void initializeData() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ArrayList<ReportDataUnit> data = null;
            Call<List<ReportDataUnit>> call = ClientUtils.accommodationService.getIntervalReport(userID, "2023-01-01", "2024-01-01");
            try{
                Response<List<ReportDataUnit>> response = call.execute();
                data = (ArrayList<ReportDataUnit>) response.body();
            }catch(Exception ex){
                System.out.println("ERROR WHILE GETTING DATA FOR INTERVAL REPORT");
            }

        ArrayList<ReportDataUnit> finalData = data;
        intervalPrices = new LinkedHashMap<String, Float>() {{
            for(int i = 0; i< finalData.size(); i++) {
                //put("jan", (float) 100);
                put(finalData.get(i).getName(), (float) finalData.get(i).getProfit());
            }

        }};
        intervalReservations = new LinkedHashMap<String, Float>() {{
            for(int i = 0; i< finalData.size(); i++) {
                //put("jan", (float) 100);
                put(finalData.get(i).getName(), (float) finalData.get(i).getReservations() / 100);
            }
        }};


            String accName = (String) this.accommodations.getItemAtPosition(0);
            Long accId = 0L;
            StrictMode.setThreadPolicy(policy);
            Call<Long> call3 = ClientUtils.accommodationService.getAccId(accName);
            try{
                Response<Long> response3 = call3.execute();
                accId = (Long) response3.body();
            }catch(Exception ex){
                System.out.println("ERROR WHILE GETTING ACCOMMODATION ID");
            }
            StrictMode.setThreadPolicy(policy);
            ArrayList<ReportDataUnit> data2 = null;
            Call<List<ReportDataUnit>> call2 = ClientUtils.accommodationService.getAccReport(userID, 2023, accId);
            try{
                Response<List<ReportDataUnit>> response2 = call2.execute();
                data2 = (ArrayList<ReportDataUnit>) response2.body();
            }catch(Exception ex){
                System.out.println("ERROR WHILE GETTING DATA FOR ACCOMMODATION REPORT");
            }

        ArrayList<ReportDataUnit> finalData2 = data2;
        accommodationPrices = new LinkedHashMap<String, Float>() {{
            for(int i = 0; i< finalData2.size(); i++) {
                //put("jan", (float) 100);
                put(finalData2.get(i).getName(), (float) finalData2.get(i).getProfit());
            }
        }};
        accommodationReservations = new LinkedHashMap<String, Float>() {{
            for(int i = 0; i< finalData2.size(); i++) {
                //put("jan", (float) 100);
                put(finalData2.get(i).getName(), (float) finalData2.get(i).getReservations() / 100);
            }
        }};
    }

    public void tackeAndSaveScreenShot(FragmentActivity myFragment) {
        View MainView = myFragment.getWindow().getDecorView();
        MainView.setDrawingCacheEnabled(true);
        MainView.buildDrawingCache();
        Bitmap MainBitmap = MainView.getDrawingCache();
        Rect frame = new Rect();

        myFragment.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //to remove statusBar from the taken sc
        int statusBarHeight = frame.top;
        //using screen size to create bitmap
        int width = myFragment.getWindowManager().getDefaultDisplay().getWidth();
        int height = myFragment.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap OutBitmap = Bitmap.createBitmap(MainBitmap, 0, statusBarHeight, width, height - statusBarHeight);
        MainView.destroyDrawingCache();
        try {

            File pathDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            OutputStream fOut = null;
            //you can also using current time to generate name
            String name="Report";
            pathDir.mkdirs();
            File file = File.createTempFile(
                    name,  // prefix
                    ".png",         // suffix
                    pathDir      // directory
            );
            fOut = new FileOutputStream(file);

           OutBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();

            Document document = new Document();

            File pdffile = File.createTempFile(
                    name,  // prefix
                    ".pdf",         // suffix
                    pathDir      // directory
            );
            PdfWriter.getInstance(document, new FileOutputStream(pdffile)); //  Change pdf's name.

            document.open();

            Image image = Image.getInstance(file.getAbsolutePath());

            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 50) / image.getWidth()) * 80; // 0 means you have no indentation. If you have any, change it.
            image.scalePercent(scaler);
            image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

            document.add(image);
            document.close();

            file.delete();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}