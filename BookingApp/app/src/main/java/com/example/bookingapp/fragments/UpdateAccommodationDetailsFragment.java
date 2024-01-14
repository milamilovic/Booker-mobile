package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.clients.AccommodationService;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentUpdateAccommodationDetailsBinding;
import com.example.bookingapp.dto.accommodation.AccommodationViewDTO;
import com.example.bookingapp.dto.accommodation.CreateAccommodationDTO;
import com.example.bookingapp.dto.accommodation.CreatePriceDTO;
import com.example.bookingapp.dto.accommodation.UpdateAvailabilityDTO;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.enums.PriceType;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateAccommodationDetailsFragment extends Fragment {

    private FragmentUpdateAccommodationDetailsBinding binding;

    private EditText editTextFrom;
    private EditText editTextUntil;
    private EditText basicPrice;
    private Spinner priceTypeSpinner;
    private EditText deadline;
    private Button btnSubmit;
    private Button btnCancel;
    final Calendar myCalendar= Calendar.getInstance();
    private static Date fromDate = new Date();
    private static Date toDate = new Date();
    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String ACCOMMODATION_ID = "accommodation_id";

    public static UpdateAccommodationDetailsFragment newInstance() {
        return new UpdateAccommodationDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateAccommodationDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialize();
        setupPriceTypePicker();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
                Long accommodationId = sharedPref.getLong(ACCOMMODATION_ID, 0);
                String jwtToken = jwt; // Replace with your actual JWT token
                String authorizationHeaderValue = "Bearer " + jwtToken;

                // Create a new instance of the OkHttpClient.Builder
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

                // Add an interceptor to the OkHttpClient to set the Authorization header
                clientBuilder.addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", authorizationHeaderValue)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

                // Build the OkHttpClient
                OkHttpClient client = clientBuilder.build();

                // Create a new Retrofit instance with the modified OkHttpClient
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ClientUtils.SERVICE_API_PATH) // Replace with your actual base URL
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

                // Create the service interface using Retrofit
                AccommodationService apiService = retrofit.create(AccommodationService.class);
                UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
                updateAvailabilityDTO.setStartDate(editTextFrom.getText().toString());
                updateAvailabilityDTO.setEndDate(editTextUntil.getText().toString());
                updateAvailabilityDTO.setDeadline(Integer.parseInt(deadline.getText().toString()));
                CreatePriceDTO createPriceDTO = new CreatePriceDTO();
                createPriceDTO.setFromDate(editTextFrom.getText().toString());
                createPriceDTO.setToDate(editTextUntil.getText().toString());
                createPriceDTO.setCost(Double.parseDouble(basicPrice.getText().toString()));
                Spinner priceTypeSpinner = binding.priceTypeSpinner;
                int selectedPriceTypePosition = priceTypeSpinner.getSelectedItemPosition();
                PriceType selectedPriceType = PriceType.values()[selectedPriceTypePosition];
                createPriceDTO.setType(selectedPriceType);
                updateAvailabilityDTO.setPrice(createPriceDTO);
                Log.d("REZ", createPriceDTO.toString());

                Call<AccommodationViewDTO> call = ClientUtils.accommodationService.updateAvailability(accommodationId, updateAvailabilityDTO);
                call.enqueue(new Callback<AccommodationViewDTO>() {
                    @Override
                    public void onResponse(Call<AccommodationViewDTO> call, Response<AccommodationViewDTO> response) {
                        if (response.code() == 200) {
                            Log.d("REZ", "Availability update successful!");
                            Toast.makeText(getContext(), "Availability update successful!", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else if (response.code() == 400) {
                            Log.d("REZ", "Validation failed!");
                            Toast.makeText(getContext(), "Validation failed!", Toast.LENGTH_SHORT);
                        } else {
                            Log.d("REZ", "Message received: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<AccommodationViewDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });


            }
        });

        DatePickerDialog.OnDateSetListener fromDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateEditText(editTextFrom, myCalendar);
            }
        };
        editTextFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),fromDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        DatePickerDialog.OnDateSetListener toDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateEditText(editTextUntil, myCalendar);
            }
        };
        editTextUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),toDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }

    public void initialize() {
        editTextFrom = binding.editTextFrom;
        editTextUntil = binding.editTextUntil;
        basicPrice = binding.basicPrice;
        priceTypeSpinner = binding.priceTypeSpinner;
        deadline = binding.deadline;
        btnSubmit = binding.btnSubmit;
        btnCancel = binding.btnCancel;
    }


    private void setupPriceTypePicker() {
        Spinner spinner = binding.priceTypeSpinner;
        List<PriceType> enumValues = Arrays.asList(PriceType.values());

        ArrayAdapter<PriceType> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                enumValues
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected value
                PriceType selectedPriceType = (PriceType) parentView.getItemAtPosition(position);

                // Do something with the selected value
                // For example, you can use selectedAccommodationType.name() to get the string representation
                String selectedValue = selectedPriceType.name();
                // or use selectedAccommodationType.ordinal() to get the index
                int selectedIndex = selectedPriceType.ordinal();

                // Print or use the selected value as needed
                Log.d("SelectedAccommodationType", selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void updateEditText(EditText editText, Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        editText.setText(formattedDate);
    }
}