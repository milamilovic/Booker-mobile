package com.example.bookingapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;
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
import com.example.bookingapp.clients.AccommodationRating;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.enums.ReservationRequestStatus;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.fragments.ReservationRequestsGuestFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationRequestDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReservationRequestGuestAdapter  extends ArrayAdapter<AccommodationRequestDTO> {

    private ArrayList<AccommodationRequestDTO> requests;
    Context context;

    public ReservationRequestGuestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReservationRequestGuestAdapter(Context context, ArrayList<AccommodationRequestDTO> requests){
        super(context, R.layout.reservation_request_guest_card, requests);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_request_guest_card,
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
        
        LinearLayout card = convertView.findViewById(R.id.reservation_request_guest_card);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.title);
        TextView status = convertView.findViewById(R.id.reservation_request_status);
        TextView dates = convertView.findViewById(R.id.reservation_request_dates);
        TextView totalPrice = convertView.findViewById(R.id.price_total);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        Button cancelButton = convertView.findViewById(R.id.cancel);

        if(request != null){
            //getting images
            StrictMode.setThreadPolicy(policy);
            Call<List<String>> imageCall = ClientUtils.accommodationService.getImages(accommodation.getId());
            try{
                Response<List<String>> response = imageCall.execute();
                List<String> images = (List<String>) response.body();
                if(images!=null && !images.isEmpty()) {
                    byte[] bytes = Base64.decode(images.get(0), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
                }
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING IMAGES");
                ex.printStackTrace();
            }
            title.setText(accommodation.getTitle());
            status.setText(request.getStatusFormated());
            totalPrice.setText(request.getPrice() + "$");
            StrictMode.setThreadPolicy(policy);
            Call<List<AccommodationRating>> call = ClientUtils.accommodationService.getRatings(request.getAccommodationId());
            try{
                Response<List<AccommodationRating>> response = call.execute();
                List<AccommodationRating> ratings = (List<AccommodationRating>) response.body();
                float rating_float = 0;
                for(AccommodationRating a : ratings) {
                    rating_float += a.getRate();
                }
                ratingBar.setRating(rating_float);
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING RATINGS");
                ex.printStackTrace();
            }
            dates.setText(request.getDateRange());
            if(!canReservationBeCanceled(request)) {
                cancelButton.setVisibility(View.GONE);
            } else {
                cancelButton.setVisibility(View.VISIBLE);
                cancelButton.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()
                    ).setNegativeButton("Go back",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("I'm sure!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // cancel and refresh
                            StrictMode.setThreadPolicy(policy);
                            Call<Void> call = ClientUtils.reservationRequestService.cancel(request.getGuestId(), request.getId());
                            try{
                                Response<Void> response = call.execute();
                                Toast.makeText(context, "Reservation request canceled!", Toast.LENGTH_SHORT).show();
                                FragmentTransition.to(ReservationRequestsGuestFragment.newInstance(), (FragmentActivity) context, true, R.id.fragment_placeholder);
                            }catch(Exception ex){
                                System.out.println("EXCEPTION WHILE CANCELLING REQUEST");
                                ex.printStackTrace();
                            }
                        }
                    });
                    builder.setMessage("Are you sure you want to cancel your reservation request?");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            }
        }

        return convertView;
    }

    private boolean canReservationBeCanceled(AccommodationRequestDTO request) {
        if(request.getFromDateDate().compareTo(new Date()) <= 0) return false;
        System.out.println("THIS RESERVATION DOES NOT HAVE A PASSED DATE AND STATUS IS: "  + request.getStatus());
        return request.getStatus() == ReservationRequestStatus.WAITING;
    }

}
