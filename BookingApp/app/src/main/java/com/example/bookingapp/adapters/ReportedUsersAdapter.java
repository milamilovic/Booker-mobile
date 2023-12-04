package com.example.bookingapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapp.R;
import com.example.bookingapp.model.ReportedUsersListing;

import java.util.ArrayList;

public class ReportedUsersAdapter extends ArrayAdapter<ReportedUsersListing> {

    private ArrayList<ReportedUsersListing> reportedUsersListing;
    Context context;

    public ReportedUsersAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReportedUsersAdapter(Context context, ArrayList<ReportedUsersListing> reportedUsersListing){
        super(context, R.layout.accommodation_card, reportedUsersListing);
        this.reportedUsersListing = reportedUsersListing;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reportedUsersListing.size();
    }

    @Nullable
    @Override
    public ReportedUsersListing getItem(int position) {
        return reportedUsersListing.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
 }
