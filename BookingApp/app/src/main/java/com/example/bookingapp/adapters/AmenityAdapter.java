package com.example.bookingapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapp.R;
import com.example.bookingapp.model.Amenity;

import java.io.File;
import java.util.ArrayList;

public class AmenityAdapter extends ArrayAdapter<Amenity> {

    private ArrayList<Amenity> amenities;
    Context context;

    public AmenityAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public AmenityAdapter(Context context, ArrayList<Amenity> amenities){
        super(context, R.layout.accommodation_card, amenities);
        this.amenities = amenities;
        this.context = context;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return amenities.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public Amenity getItem(int position) {
        return amenities.get(position);
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
        Amenity amenity = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.amenity,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.amenity_card);
        ImageView image = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.name);

        if(amenity != null){
            if(amenity.getName().equals("wifi")) {
                image.setImageResource(R.drawable.ic_action_network_wifi);
            } else if(amenity.getName().equals("free cancellation")) {
                image.setImageResource(R.drawable.icons8_dollar_32);
            }else if(amenity.getName().equals("AC")) {
                image.setImageResource(R.drawable.icons8_calendar_32);
            }else if(amenity.getName().equals("good location")) {
                image.setImageResource(R.drawable.icons8_location_32);
            }
            name.setText(amenity.getName());
        }

        return convertView;
    }

}
