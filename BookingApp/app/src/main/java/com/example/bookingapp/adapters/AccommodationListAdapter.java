package com.example.bookingapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.bookingapp.R;
import com.example.bookingapp.model.AccommodationListing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class AccommodationListAdapter extends ArrayAdapter<AccommodationListing> {

    private ArrayList<AccommodationListing> aAccommodationListings;

    public AccommodationListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public AccommodationListAdapter(Context context, ArrayList<AccommodationListing> AccommodationListings){
        super(context, R.layout.accommodation_card, AccommodationListings);
        this.aAccommodationListings = AccommodationListings;

    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return aAccommodationListings.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public AccommodationListing getItem(int position) {
        return aAccommodationListings.get(position);
    }

    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene poziciuje (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.AccommodationListing_card.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationListing AccommodationListing = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.accommodation_listing_card_item);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.AccommodationListing_title);
        TextView description = convertView.findViewById(R.id.AccommodationListing_description);
        TextView totalPrice = convertView.findViewById(R.id.accommodation_listing_price_total);
        TextView pricePerDay = convertView.findViewById(R.id.accommodation_listing_price_per_day);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        ImageButton favorite = convertView.findViewById(R.id.favorite);

        if(AccommodationListing != null){
            image.setImageResource(AccommodationListing.getImage());
            title.setText(AccommodationListing.getTitle());
            description.setText(AccommodationListing.getDescription());
            totalPrice.setText(AccommodationListing.getTotalPrice() + "$");
            pricePerDay.setText(AccommodationListing.getTotalPrice() + "$/day");
            ratingBar.setRating(AccommodationListing.getRating());
            if(AccommodationListing.getFavorite()) {
                favorite.setBackgroundResource(R.drawable.icons8_heart_30_selected_favourite);
            } else {
                favorite.setBackgroundResource(R.drawable.icons8_heart_30_selected_favourite);
            }
            card.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Clicked: " + AccommodationListing.getTitle()  +
                        ", id: " + AccommodationListing.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }

}
