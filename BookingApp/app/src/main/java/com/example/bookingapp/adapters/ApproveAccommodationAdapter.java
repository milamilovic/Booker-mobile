package com.example.bookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.bookingapp.R;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.ReportedUsersListing;

import java.util.ArrayList;

public class ApproveAccommodationAdapter extends ArrayAdapter<ApproveAccommodationListing> {
    private ArrayList<ApproveAccommodationListing> approveAccommodationListings;
    Context context;

    public ApproveAccommodationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ApproveAccommodationAdapter(Context context, ArrayList<ApproveAccommodationListing> approveAccommodationListing){
        super(context, R.layout.approve_accommodation_card, approveAccommodationListing);
        this.approveAccommodationListings = approveAccommodationListing;
        this.context = context;
    }

    @Override
    public int getCount() {
        return approveAccommodationListings.size();
    }

    @Nullable
    @Override
    public ApproveAccommodationListing getItem(int position) {
        return approveAccommodationListings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ApproveAccommodationListing accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approve_accommodation_card,
                    parent, false);
        }
        TextView title = convertView.findViewById(R.id.accommodation_title);
        TextView description = convertView.findViewById(R.id.accommodation_description);
        RatingBar ratingBar = convertView.findViewById(R.id.accommodation_rating);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        Button block = convertView.findViewById(R.id.approve_button);

        if(accommodation != null){
            title.setText(accommodation.getTitle());
            description.setText(accommodation.getDescription());
            ratingBar.setRating(accommodation.getRating());
            //image.setImageResource(accommodation.getImage());
        }

        return convertView;
    }
}
