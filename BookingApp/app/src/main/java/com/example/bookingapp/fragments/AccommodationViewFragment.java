package com.example.bookingapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener;
import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.OwnerProfileFragment;
import com.example.bookingapp.R;
import com.example.bookingapp.SplashScreen;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.AmenityAdapter;
import com.example.bookingapp.adapters.ImageAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.ReservationRequestStatus;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.ReservationRequest;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.datepicker.DayViewDecorator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationViewFragment extends Fragment {

    private Accommodation accommodation;
    private static Long id;

    public static ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    ListView listView;

    private static final String ARG_PARAM = "param";
    private static final String USER_ID_KEY = "user_id";
    Button showMap;
    public AccommodationViewFragment() {
        // Required empty public constructor
    }

    public AccommodationViewFragment(Accommodation accommodation) {
        this.accommodation = accommodation;
    }


    public static AccommodationViewFragment newInstance(Accommodation accommodation) {
        AccommodationViewFragment fragment = new AccommodationViewFragment(accommodation);
        Bundle args = new Bundle();
        args.putLong("id", accommodation.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            id = args.getLong("id");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<Accommodation> call = ClientUtils.accommodationService.findAccommodation(id);
            try{
                Response<Accommodation> response = call.execute();
                accommodation = (Accommodation) response.body();
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION");
                ex.printStackTrace();
            }
            amenities = getArguments().getParcelableArrayList(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_view, container, false);

        showMap = view.findViewById(R.id.show_map);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to( MapFragment.newInstance(accommodation.getAddress().getLatitude(), accommodation.getAddress().getLongitude()), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        TextView description = (TextView)view.findViewById(R.id.description);
        description.setText(accommodation.getDescription());
        TextView address = (TextView)view.findViewById(R.id.address);
        address.setText(accommodation.getAddress().toString());

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.apartment_image);
        images.add(R.drawable.paris_image);
        images.add(R.drawable.copenhagen_image);
        images.add(R.drawable.madrid_image);
        images.add(R.drawable.room_image);
        images.add(R.drawable.hotel_image);
        images.add(R.drawable.lisbon_image);
        images.add(R.drawable.london_image);

        ImageAdapter adapter = new ImageAdapter(getContext(), images);
        recyclerView.setAdapter(adapter);

        TextView ownerViewProfile = view.findViewById(R.id.view_profile);
        ownerViewProfile.setOnClickListener(v -> {
            FragmentTransition.to(OwnerProfileFragment.newInstance("profil", "vlasnikov profil"), getActivity(), false, R.id.fragment_placeholder);
        });

        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                //Do something like opening the image in new activity or showing it in full screen or something else.
            }
        });

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        EditText dates = view.findViewById(R.id.dates);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                List<Calendar> selectedDates = calendarView.getSelectedDates();
                if(selectedDates.size()==1) {
                    //set text box dates
                    Calendar firstDate = selectedDates.get(0);
                    SimpleDateFormat formattedDate
                            = new SimpleDateFormat("yyyy-MM-dd");
                    String firstDateString = formattedDate.format(firstDate.getTime());
                    Calendar secondDate = eventDay.getCalendar();
                    String secondDateString = formattedDate.format(secondDate.getTime());
                    String datesString = firstDateString + " - " + secondDateString;
                    dates.setText(datesString);
                }
            }
        });

        ScrollView scrollView = view.findViewById(R.id.scroll_view);

        amenities = (ArrayList<Amenity>) accommodation.getAmenities();
        listView = view.findViewById(R.id.amenity_list);
        AmenityAdapter amenityAdapter = new AmenityAdapter(getContext(), amenities);
        listView.setAdapter(amenityAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        view.findViewById(R.id.calculate_price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datesString = String.valueOf(dates.getText());
                if(datesString.contains(" - ")) {
                    //get dates
                    String firstDateString = datesString.split(" - ")[0];
                    String secondDateString = datesString.split(" - ")[1];

                    //get people
                    EditText people_edit = view.findViewById(R.id.people);
                    //calclulate price
                    EditText price = view.findViewById(R.id.price);
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    if(!people_edit.getText().equals("")) {
                        int people = Integer.parseInt(people_edit.getText().toString());
                        System.out.println(people);
                        if(people>0) {
                            Call<Double> call = ClientUtils.accommodationService.getPrice(accommodation.getId(), firstDateString, secondDateString, people);
                            try {
                                Response<Double> response = call.execute();
                                Double d = response.body();
                                price.setText(String.valueOf(d.doubleValue()) + "$");
                            } catch (Exception ex) {
                                System.out.println("EXCEPTION WHILE GETTING PRICE");
                                ex.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "Number of guests must be at least 1!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please input number of guests!", Toast.LENGTH_SHORT).show();
                    }
//                    price.setText(String.valueOf(priceNum) + "$");

                } else {
                    Toast.makeText(getContext(), "Please input dates!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //make reservation request button on click
        view.findViewById(R.id.make_reservation_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a reservation request
                String datesString = String.valueOf(dates.getText());
                if(datesString.contains(" - ")) {
                    //get dates
                    String firstDateString = datesString.split(" - ")[0];
                    String secondDateString = datesString.split(" - ")[1];
                    //get people
                    EditText people_edit = view.findViewById(R.id.people);
                    //make reservation
                    EditText price = view.findViewById(R.id.price);
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    if (!people_edit.getText().equals("")) {
                        int people = Integer.parseInt(people_edit.getText().toString());
                        System.out.println(people);
                        if (people > 0) {
                            Call<Double> call = ClientUtils.accommodationService.getPrice(accommodation.getId(), firstDateString, secondDateString, people);
                            try {
                                Response<Double> response = call.execute();
                                Double d = response.body();
                                double cost = d.doubleValue();
                                ReservationRequest reservationRequest = new ReservationRequest(
                                        1L, id, firstDateString, secondDateString, people,
                                        ReservationRequestStatus.WAITING, false, cost
                                );
                                Call<ReservationRequest> call2 = ClientUtils.accommodationService.makeRequest(reservationRequest);
                                try {
                                    Response<ReservationRequest> response2 = call2.execute();
                                    ReservationRequest request = response2.body();
                                    System.out.println("requset made!");
                                } catch (Exception ex) {
                                    System.out.println("EXCEPTION WHILE MAKING RESERVATION");
                                    ex.printStackTrace();
                                }
                            } catch (Exception ex) {
                                System.out.println("EXCEPTION WHILE GETTING PRICE");
                                ex.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "Number of guests must be at least 1!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please input number of guests!", Toast.LENGTH_SHORT).show();
                    }
//                    price.setText(String.valueOf(priceNum) + "$");

                } else {
                    Toast.makeText(getContext(), "Please input dates!", Toast.LENGTH_SHORT).show();
                }


                //successful
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()
                ).setNegativeButton("Stay on this page",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                }).setPositiveButton("Go to your reservation requests", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentTransition.to(ReservationRequestsGuestFragment.newInstance(), getActivity(), true, R.id.fragment_placeholder);

                    }
                });
                builder.setMessage("You made a reservation request!");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        if(userID!=0) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> userCall = ClientUtils.userService.getById(userID);
            try{
                Response<UserDTO> response = userCall.execute();
                UserDTO user = (UserDTO) response.body();
                if(user.getRole()!= Role.GUEST) {
                    //reservation not available
                    View reservationButton = view.findViewById(R.id.make_reservation_request);
                    reservationButton.setVisibility(View.GONE);
                    View reservation = view.findViewById(R.id.reservation);
                    reservation.setVisibility(View.GONE);
                    View reservation_title = view.findViewById(R.id.reservation_title);
                    reservation_title.setVisibility(View.GONE);
                    View price = view.findViewById(R.id.price);
                    price.setVisibility(View.GONE);
                    View people = view.findViewById(R.id.people);
                    people.setVisibility(View.GONE);
                    calendarView.setVisibility(View.GONE);
                    dates.setVisibility(View.GONE);
                    View priceButton = view.findViewById(R.id.calculate_price);
                    priceButton.setVisibility(View.GONE);
                }
            }catch(Exception ex){
                System.out.println("???");
                //reservation not available
                View reservationButton = view.findViewById(R.id.make_reservation_request);
                reservationButton.setVisibility(View.GONE);
                View reservation = view.findViewById(R.id.reservation);
                reservation.setVisibility(View.GONE);
                View reservation_title = view.findViewById(R.id.reservation_title);
                reservation_title.setVisibility(View.GONE);
                View price = view.findViewById(R.id.price);
                price.setVisibility(View.GONE);
                View people = view.findViewById(R.id.people);
                people.setVisibility(View.GONE);
                calendarView.setVisibility(View.GONE);
                dates.setVisibility(View.GONE);
                View priceButton = view.findViewById(R.id.calculate_price);
                priceButton.setVisibility(View.GONE);
            }
        } else {
            System.out.println("Not logged in");
            //reservation not available
            View reservationButton = view.findViewById(R.id.make_reservation_request);
            reservationButton.setVisibility(View.GONE);
            View reservation = view.findViewById(R.id.reservation);
            reservation.setVisibility(View.GONE);
            View reservation_title = view.findViewById(R.id.reservation_title);
            reservation_title.setVisibility(View.GONE);
            View price = view.findViewById(R.id.price);
            price.setVisibility(View.GONE);
            View people = view.findViewById(R.id.people);
            people.setVisibility(View.GONE);
            calendarView.setVisibility(View.GONE);
            dates.setVisibility(View.GONE);
            View priceButton = view.findViewById(R.id.calculate_price);
            priceButton.setVisibility(View.GONE);
        }

        return view;
    }
}