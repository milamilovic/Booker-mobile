package com.example.bookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationRequestOwnerDTO;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.GuestOwnerViewDTO;

import java.util.ArrayList;

public class ReservationRequestOwnerAdapter  extends ArrayAdapter<AccommodationRequestOwnerDTO> {

    private ArrayList<AccommodationRequestOwnerDTO> requests;
    Context context;

    public ReservationRequestOwnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReservationRequestOwnerAdapter(Context context, ArrayList<AccommodationRequestOwnerDTO> requests){
        super(context, R.layout.reservation_request_owner_card, requests);
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
    public AccommodationRequestOwnerDTO getItem(int position) {
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
        AccommodationRequestOwnerDTO request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_request_owner_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.reservation_request_owner_card);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.title);
        TextView status = convertView.findViewById(R.id.reservation_request_status);
        TextView dates = convertView.findViewById(R.id.reservation_request_dates);
        TextView totalPrice = convertView.findViewById(R.id.price_total);
        TextView pricePerDay = convertView.findViewById(R.id.price_per_day);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        Button approve = convertView.findViewById(R.id.approve);
        Button deny = convertView.findViewById(R.id.deny);
        ImageView profile_image = convertView.findViewById(R.id.profile_image);
        TextView name = convertView.findViewById(R.id.name);
        TextView cancellations = convertView.findViewById(R.id.cancellations);


        if(request != null){
            image.setImageResource(request.getImage());
            title.setText(request.getTitle());
            status.setText(request.getStatusFormated());
            totalPrice.setText(request.getTotalPrice() + "$");
            pricePerDay.setText(request.getPricePerDay() + "$/day");
            dates.setText(request.getDateRange());
            if(!canReservationBeApproved(request)) {
                approve.setBackgroundResource(R.drawable.round_corner_disabled_button);
                deny.setBackgroundResource(R.drawable.round_corner_disabled_button);
            } else {
                approve.setOnClickListener(v -> {
                    Toast.makeText(context, "Reservation request approved!", Toast.LENGTH_SHORT).show();
                });
                deny.setOnClickListener(v -> {
                    Toast.makeText(context, "Reservation request denied!", Toast.LENGTH_SHORT).show();
                });
            }
            GuestOwnerViewDTO guest = request.getGuest(request.getGuestId());
            profile_image.setImageResource(guest.getImage());
            name.setText(guest.getName());
            cancellations.setText("Number of cancellations: " + guest.getNumOfCancellations());
        }

        return convertView;
    }

    private boolean canReservationBeApproved(AccommodationRequestOwnerDTO request) {
        //TODO: update this function so that it checks if cancellation period passed
        return request.getGuestId()!=1L;
    }

}