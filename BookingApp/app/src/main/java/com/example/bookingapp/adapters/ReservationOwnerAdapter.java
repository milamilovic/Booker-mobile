package com.example.bookingapp.adapters;

import android.content.Context;
import android.os.StrictMode;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.OwnerProfileFragment;
import com.example.bookingapp.ProfileFragment;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.ReservationRequestStatus;
import com.example.bookingapp.enums.ReservationStatus;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationRequestDTO;
import com.example.bookingapp.model.Reservation;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class ReservationOwnerAdapter extends ArrayAdapter<Reservation> {

    private ArrayList<Reservation> reservations;
    Context context;

    public ReservationOwnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReservationOwnerAdapter(Context context, ArrayList<Reservation> reservations){
        super(context, R.layout.owner_reservation_card, reservations);
        this.reservations = reservations;
        this.context = context;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return reservations.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public Reservation getItem(int position) {
        return reservations.get(position);
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
        Reservation reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owner_reservation_card,
                    parent, false);
        }
        //getting accommodation
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<Accommodation> acc = ClientUtils.accommodationService.findAccommodation(reservation.getAccommodation());
        Accommodation accommodation = null;
        try{
            Response<Accommodation> response = acc.execute();
            accommodation = (Accommodation) response.body();
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION");
            ex.printStackTrace();
        }
        LinearLayout card = convertView.findViewById(R.id.reservation_owner_card);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.title);
        TextView status = convertView.findViewById(R.id.reservation_status);
        TextView dates = convertView.findViewById(R.id.reservation_dates);
        TextView totalPrice = convertView.findViewById(R.id.price_total);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        TextView name = convertView.findViewById(R.id.name);
        TextView cancellations = convertView.findViewById(R.id.cancellations);
        LinearLayout guestProfile = convertView.findViewById(R.id.guest);


        if(reservation != null){
            title.setText(accommodation.getTitle());
            status.setText(reservation.getStatusFormated());
            totalPrice.setText(reservation.getPrice() + "$");
            dates.setText("from " + reservation.getFromDate() + " to " + reservation.getToDate());
            UserDTO guest = null;
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> userCall = ClientUtils.userService.getById(reservation.getGuestId());
            try{
                Response<UserDTO> response = userCall.execute();
                guest = (UserDTO) response.body();
            }catch(Exception ex){
            }
            name.setText(guest.getName());
            int numOfCancellations = 0;
            StrictMode.setThreadPolicy(policy);
            Call<Integer> cancellationCall = ClientUtils.userService.getNumOfCancellations(reservation.getGuestId());
            try{
                Response<Integer> response2 = cancellationCall.execute();
                numOfCancellations = response2.body();
            }catch(Exception ex){
            }
            cancellations.setText("Number of cancellations: " + numOfCancellations);
            guestProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransition.to(ProfileFragment.newInstance(reservation.getGuestId()), (FragmentActivity) context, true, R.id.fragment_placeholder);
                }
            });
        }

        return convertView;
    }

}
