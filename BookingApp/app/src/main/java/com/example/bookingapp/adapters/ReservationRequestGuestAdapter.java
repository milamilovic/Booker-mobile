package com.example.bookingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.AccommodationRequest;
import com.example.bookingapp.model.Amenity;

import java.util.ArrayList;
import java.util.Random;

public class ReservationRequestGuestAdapter  extends ArrayAdapter<AccommodationRequest> {

    private ArrayList<AccommodationRequest> requests;
    Context context;

    public ReservationRequestGuestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReservationRequestGuestAdapter(Context context, ArrayList<AccommodationRequest> requests){
        super(context, R.layout.accommodation_card, requests);
        this.requests = requests;
        this.context = context;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return requests.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public AccommodationRequest getItem(int position) {
        return requests.get(position);
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
        AccommodationRequest request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_request_guest_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.reservation_request_guest_card);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.title);
        TextView status = convertView.findViewById(R.id.reservation_request_status);
        TextView dates = convertView.findViewById(R.id.reservation_request_dates);
        TextView totalPrice = convertView.findViewById(R.id.price_total);
        TextView pricePerDay = convertView.findViewById(R.id.price_per_day);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        Button cancelButton = convertView.findViewById(R.id.cancel);

        if(request != null){
            image.setImageResource(request.getImage());
            title.setText(request.getTitle());
            status.setText(request.getStatusFormated());
            totalPrice.setText(request.getTotalPrice() + "$");
            pricePerDay.setText(request.getPricePerDay() + "$/day");
            ratingBar.setRating(request.getRating());
            dates.setText(request.getDateRange());
            if(!canReservationBeCanceled(request)) {
                cancelButton.setBackgroundResource(R.drawable.round_corner_disabled_button);
            }
            card.setOnClickListener(v -> {
                ArrayList<Integer> images = new ArrayList<Integer>();
                images.add(R.drawable.apartment_image);
                images.add(R.drawable.paris_image);
                images.add(R.drawable.copenhagen_image);
                images.add(R.drawable.madrid_image);
                images.add(R.drawable.room_image);
                images.add(R.drawable.hotel_image);
                images.add(R.drawable.lisbon_image);
                images.add(R.drawable.london_image);
                ArrayList<Amenity> amenities = new ArrayList<Amenity>();
                amenities.add(new Amenity(1L, "Wi-Fi", R.drawable.icons8_settings_24));
                amenities.add(new Amenity(2L, "AC", R.drawable.icons8_calendar_32));
                amenities.add(new Amenity(3L, "popular location", R.drawable.icons8_location_32));
                amenities.add(new Amenity(4L, "clean", R.drawable.icons8_help_24));
                FragmentTransition.to(AccommodationViewFragment.newInstance(new Accommodation(request.getId(),
                        request.getTitle(), "The units come with parquet floors and feature a fully equipped kitchen with a microwave, a dining area, a flat-screen TV with streaming services, and a private bathroom with walk-in shower and a hair dryer. A toaster, a fridge and stovetop are also available, as well as a coffee machine and a kettle.\n" +
                        "\u2028Eventim Apollo is 2.4 km from the apartment, while South Kensington Underground Station is 3 km from the property. The nearest airport is London Heathrow Airport, 21 km from Central London Luxury Studios Fulham Close to Underground Newly Refurbished.",
                        images, false, 360, 120, 4.8f, "my address", amenities)), (FragmentActivity) context, false, R.id.fragment_placeholder);
            });
        }

        return convertView;
    }

    private boolean canReservationBeCanceled(AccommodationRequest request) {
        //TODO: update this function so that it checks if cancellation period passed
        return request.getStatus().equals("waiting");
    }

}
