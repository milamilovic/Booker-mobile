package com.example.bookingapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.bookingapp.R;
import com.example.bookingapp.clients.AccommodationService;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentCreateAccommodationBaseBinding;
import com.example.bookingapp.dto.accommodation.AccommodationViewDTO;
import com.example.bookingapp.dto.accommodation.AddressDTO;
import com.example.bookingapp.dto.accommodation.CreateAccommodationDTO;
import com.example.bookingapp.dto.accommodation.CreatePriceDTO;
import com.example.bookingapp.enums.AccommodationType;
import com.example.bookingapp.enums.PriceType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


public class CreateAccommodationBaseFragment extends Fragment implements MapFragment.OnMapMarkerClickListener{
    private static final int PICK_IMAGES_REQUEST = 2;
    private int PICK_IMAGE_MULTIPLE = 1;
    private FragmentCreateAccommodationBaseBinding binding;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextShortDescription;
    private CheckBox checkBoxWifi;
    private CheckBox checkBoxAC;
    private CheckBox checkBoxKitchen;
    private CheckBox checkBoxParking;
    private ImageButton btnPrevious;
    private ImageSwitcher imageSwitcher;
    private ImageButton btnNext;
    private Button addImages;
    private EditText editTextStreet;
    private EditText editTextCity;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText editTextBasicPrice;
    private EditText editTextMinCapacity;
    private EditText editTextMaxCapacity;
    private EditText editTextFrom;
    private EditText editTextUntil;
    private Button btnCreate;
    private Button btnCancel;
    private Button btnShowMap;
    private FrameLayout mapContainer;
    private Spinner spinner;

    private List<String> selectedImagePaths = new ArrayList<>();
    private ArrayList<Uri> mArrayUri;
    private int position;
    final Calendar myCalendar= Calendar.getInstance();
    private static Date fromDate = new Date();
    private static Date toDate = new Date();
    private static final String JWT_TOKEN_KEY = "jwt_token";

    public static CreateAccommodationBaseFragment newInstance() {

        return new CreateAccommodationBaseFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAccommodationBaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeUIComponents();
        setupAccommodationTypeSpinner();
        setupPriceTypeSpinner();
        openMap();

        Button pickImagesButton = binding.addImages;
        pickImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
//                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                imageView.setLayoutParams(new FrameLayout.LayoutParams(
//                        FrameLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT
//                ));
                return imageView;
            }
        });

        //imageSwitcher.setImageResource(imageList[0]);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageSwitcher.setInAnimation(getContext(), R.anim.from_right);
//                imageSwitcher.setOutAnimation(getContext(), R.anim.to_left);
//                --currentIndex;
//
//                if (currentIndex < 0)
//                    currentIndex = imageList.length - 1;
//                imageSwitcher.setImageResource(imageList[currentIndex]);

                if (position > 0) {
                    position--;
                    imageSwitcher.setImageURI(mArrayUri.get(position));
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageSwitcher.setInAnimation(getContext(), R.anim.from_left);
//                imageSwitcher.setOutAnimation(getContext(), R.anim.to_right);
//                currentIndex++;
//                if (currentIndex==count)
//                    currentIndex = 0;
//                imageSwitcher.setImageResource(imageList[currentIndex]);

                if (position < mArrayUri.size() - 1) {
                    position++;
                    imageSwitcher.setImageURI(mArrayUri.get(position));
                } else {
                    Toast.makeText(getContext(), "Last image already shown", Toast.LENGTH_SHORT).show();
                }
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



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
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
                CreateAccommodationDTO createAccommodationDTO = new CreateAccommodationDTO();
                createAccommodationDTO.setTitle(editTextTitle.getText().toString());
                createAccommodationDTO.setDescription(editTextDescription.getText().toString());
                createAccommodationDTO.setShortDescription(editTextShortDescription.getText().toString());
                createAccommodationDTO.setMin_capacity(Integer.parseInt(editTextMinCapacity.getText().toString()));
                createAccommodationDTO.setMax_capacity(Integer.parseInt(editTextMaxCapacity.getText().toString()));
                createAccommodationDTO.setStartDate(editTextFrom.getText().toString());
                createAccommodationDTO.setEndDate(editTextUntil.getText().toString());
                AccommodationType selectedAccommodationType = (AccommodationType) spinner.getSelectedItem();
                Spinner priceTypeSpinner = binding.priceTypeSpinner;
                PriceType selectedPriceType = (PriceType) priceTypeSpinner.getSelectedItem();
                CreatePriceDTO createPriceDTO = new CreatePriceDTO();
                createPriceDTO.setFromDate(editTextFrom.getText().toString());
                createPriceDTO.setToDate(editTextUntil.getText().toString());
                createPriceDTO.setCost(Double.parseDouble(editTextBasicPrice.getText().toString()));
                createAccommodationDTO.setType(selectedAccommodationType);
                createPriceDTO.setType(selectedPriceType);
                createAccommodationDTO.setPrice(createPriceDTO);



                List<String> amenityNames = new ArrayList<>();
                if (checkBoxWifi.isChecked()) {
                    amenityNames.add("wifi");
                }

                if (checkBoxAC.isChecked()) {
                    amenityNames.add("AC");
                }

                if (checkBoxKitchen.isChecked()) {
                    amenityNames.add("kitchen");
                }

                if (checkBoxParking.isChecked()) {
                    amenityNames.add("free parking");
                }

                createAccommodationDTO.setAmenities(amenityNames.toArray(new String[0]));
                createAccommodationDTO.setType((AccommodationType) spinner.getSelectedItem());



                // Save selected images to the file system
                saveImagesToFileSystem(mArrayUri);

                // Call your API with the DTO
                // ...

                // Alternatively, you can save the URIs in a separate list
                List<String> selectedImagePaths = new ArrayList<>();
                for (Uri uri : mArrayUri) {
                    selectedImagePaths.add(uri.toString());
                }

                createAccommodationDTO.setImages(selectedImagePaths.toArray(new String[0]));
                AddressDTO address = new AddressDTO();
                address.setStreet(editTextStreet.getText().toString());
                address.setCity(editTextCity.getText().toString());
                address.setLatitude(Double.parseDouble(editTextLatitude.getText().toString()));
                address.setLongitude(Double.parseDouble(editTextLongitude.getText().toString()));
                createAccommodationDTO.setAddress(address);

                Call<AccommodationViewDTO> call = apiService.insert(createAccommodationDTO);
                call.enqueue(new Callback<AccommodationViewDTO>() {
                    @Override
                    public void onResponse(Call<AccommodationViewDTO> call, Response<AccommodationViewDTO> response) {
                        if (response.code() == 200){
                            Log.d("REZ","Meesage recieved");
                            System.out.println(response.body());
                            AccommodationViewDTO product1 = response.body();
                            System.out.println(product1);
                            getActivity().getSupportFragmentManager().popBackStack();
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<AccommodationViewDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });

        return root;
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

    public void initializeUIComponents() {
        mArrayUri = new ArrayList<>();
        editTextTitle = binding.editTextTitle;
        editTextDescription = binding.editTextDescription;
        editTextShortDescription = binding.editTextShortDescription;
        checkBoxWifi = binding.cbWifi;
        checkBoxAC = binding.cbAC;
        checkBoxKitchen = binding.cbKitchen;
        checkBoxParking = binding.cbParking;
        btnPrevious = binding.btnPrevious;
        imageSwitcher = binding.imageSwitcher;
        btnNext = binding.btnNext;
        addImages = binding.addImages;
        editTextStreet = binding.editTextStreet;
        editTextCity = binding.editTextCity;
        editTextLatitude = binding.editTextLatitude;
        editTextLongitude = binding.editTextLongitude;
        btnShowMap = binding.btnShowMap;
        mapContainer = binding.mapContainer;
        editTextBasicPrice = binding.basicPrice;
        editTextMinCapacity = binding.minCapacity;
        editTextMaxCapacity = binding.maxCapacity;
        btnCreate = binding.btnSubmit;
        btnCancel = binding.btnCancel;
        spinner = binding.spinner;
        editTextFrom = binding.editTextFrom;
        editTextUntil = binding.editTextUntil;
    }

    private void openImagePicker() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//
//        startActivityForResult(intent, PICK_IMAGES_REQUEST);

        Intent intent = new Intent();
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                }
                // setting 1st selected image into image switcher
                imageSwitcher.setImageURI(mArrayUri.get(0));
                position = 0;
            } else {
                Uri imageurl = data.getData();
                mArrayUri.add(imageurl);
                imageSwitcher.setImageURI(mArrayUri.get(0));
                position = 0;
            }
        } else {
            // show this if no image is selected
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    // Function to save selected images to the file system
    private void saveImagesToFileSystem(ArrayList<Uri> imageUris) {
        for (int i = 0; i < imageUris.size(); i++) {
            Uri imageUri = imageUris.get(i);
            String imagePath = getRealPathFromURI(imageUri);
            if (imagePath != null) {
                // Copy the image file to your desired directory
                // You can create a directory using File.mkdir() if it doesn't exist
                // For simplicity, we'll use the application's files directory here
                File destinationDirectory = new File(requireContext().getFilesDir(), "selected_images");
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdir();
                }

                String destinationPath = destinationDirectory.getAbsolutePath() + File.separator + "image" + i + ".jpg";
                Log.d("REZ", destinationPath);

                try {
                    InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                    OutputStream outputStream = new FileOutputStream(destinationPath);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Function to get the real path from the URI
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    private void setupAccommodationTypeSpinner() {
        Spinner spinner = binding.spinner;
        List<AccommodationType> enumValues = Arrays.asList(AccommodationType.values());

        ArrayAdapter<AccommodationType> adapter = new ArrayAdapter<>(
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
                AccommodationType selectedAccommodationType = (AccommodationType) parentView.getItemAtPosition(position);

                // Do something with the selected value
                // For example, you can use selectedAccommodationType.name() to get the string representation
                String selectedValue = selectedAccommodationType.name();
                // or use selectedAccommodationType.ordinal() to get the index
                int selectedIndex = selectedAccommodationType.ordinal();

                // Print or use the selected value as needed
                Log.d("SelectedAccommodationType", selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
    }

    private void addMapFragment() {
        MapFragment mapFragment = new MapFragment(45.2396, 19.8227);
        mapFragment.setOnMapMarkerClickListener(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.mapContainer, mapFragment);
        transaction.commit();
    }

    private void openMap() {
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the MapFragment container
                if (mapContainer.getVisibility() == View.VISIBLE) {
                    mapContainer.setVisibility(View.GONE);
                } else {
                    mapContainer.setVisibility(View.VISIBLE);
                    // If the MapFragment is not added yet, add it dynamically
                    if (mapContainer.getChildCount() == 0) {
                        addMapFragment();
                    }
                }
            }
        });
    }


    @Override
    public void onMapMarkerClick(LatLng latLng, String address) {
        editTextLatitude.setText(String.valueOf(latLng.latitude));
        editTextLongitude.setText(String.valueOf(latLng.longitude));
        String[] addressElements = address.split(",");
        editTextStreet.setText(addressElements[0]);
        editTextCity.setText(addressElements[1]);
        Log.d("REZ", "Full Address: " + address);
    }


    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    private void setupPriceTypeSpinner() {
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

    private void updateEditText(EditText editText, Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        editText.setText(formattedDate);
    }

}