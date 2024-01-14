package com.example.bookingapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.ReservationRequestStatus;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationRequestDTO;
import com.example.bookingapp.model.GuestOwnerViewDTO;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class ReservationRequestOwnerAdapter  extends ArrayAdapter<AccommodationRequestDTO> {

    private ArrayList<AccommodationRequestDTO> requests;
    Context context;

    public ReservationRequestOwnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReservationRequestOwnerAdapter(Context context, ArrayList<AccommodationRequestDTO> requests){
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
    public AccommodationRequestDTO getItem(int position) {
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
        AccommodationRequestDTO request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_request_owner_card,
                    parent, false);
        }
        //getting accommodation
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<Accommodation> acc = ClientUtils.accommodationService.findAccommodation(request.getAccommodationId());
        Accommodation accommodation = null;
        try{
            Response<Accommodation> response = acc.execute();
            accommodation = (Accommodation) response.body();
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION");
            ex.printStackTrace();
        }
        LinearLayout card = convertView.findViewById(R.id.reservation_request_owner_card);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.title);
        TextView status = convertView.findViewById(R.id.reservation_request_status);
        TextView dates = convertView.findViewById(R.id.reservation_request_dates);
        TextView totalPrice = convertView.findViewById(R.id.price_total);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        Button approve = convertView.findViewById(R.id.approve);
        Button deny = convertView.findViewById(R.id.deny);
        TextView name = convertView.findViewById(R.id.name);
        TextView cancellations = convertView.findViewById(R.id.cancellations);


        if(request != null){
            title.setText(accommodation.getTitle());
            status.setText(request.getStatusFormated());
            totalPrice.setText(request.getPrice() + "$");
            dates.setText(request.getDateRange());
            if(!canReservationBeApproved(request)) {
                approve.setVisibility(View.GONE);
                deny.setVisibility(View.GONE);
            } else {
                approve.setVisibility(View.VISIBLE);
                deny.setVisibility(View.VISIBLE);
                approve.setOnClickListener(v -> {
                    Toast.makeText(context, "Reservation request approved!", Toast.LENGTH_SHORT).show();
                });
                deny.setOnClickListener(v -> {
                    Toast.makeText(context, "Reservation request denied!", Toast.LENGTH_SHORT).show();
                });
            }
            UserDTO guest = null;
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> userCall = ClientUtils.userService.getById(request.getGuestId());
            try{
                Response<UserDTO> response = userCall.execute();
                guest = (UserDTO) response.body();
            }catch(Exception ex){
            }
            name.setText(guest.getName());
            int numOfCancellations = 0;
            StrictMode.setThreadPolicy(policy);
            Call<Integer> cancellationCall = ClientUtils.userService.getNumOfCancellations(request.getGuestId());
            try{
                Response<Integer> response2 = cancellationCall.execute();
                numOfCancellations = response2.body();
            }catch(Exception ex){
            }
            cancellations.setText("Number of cancellations: " + numOfCancellations);
        }

        return convertView;
    }

    private boolean canReservationBeApproved(AccommodationRequestDTO request) {
        if(request.getFromDate().compareTo(new Date()) <= 0) return false;
        System.out.println("THIS RESERVATION DOES NOT HAVE A PASSED DATE AND STATUS IS: "  + request.getStatus());
        return request.getStatus() == ReservationRequestStatus.WAITING;
    }

}