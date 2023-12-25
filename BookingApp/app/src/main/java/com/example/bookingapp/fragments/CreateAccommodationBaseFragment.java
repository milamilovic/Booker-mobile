package com.example.bookingapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentCreateAccommodationBaseBinding;
import com.example.bookingapp.dto.accommodation.AccommodationViewDTO;
import com.example.bookingapp.dto.accommodation.CreateAccommodationDTO;
import com.example.bookingapp.enums.AccommodationType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAccommodationBaseFragment extends Fragment {
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
    private EditText editTextLocation;
    private EditText editTextBasicPrice;
    private EditText editTextMinCapacity;
    private EditText editTextMaxCapacity;
    private Button btnCreate;
    private Button btnCancel;
    private Spinner spinner;

    private List<String> selectedImagePaths = new ArrayList<>();
    private ArrayList<Uri> mArrayUri;
    private int position;

    public static CreateAccommodationBaseFragment newInstance() {

        return new CreateAccommodationBaseFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAccommodationBaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeUIComponents();
        setupAccommodationTypeSpinner();

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


        

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccommodationDTO createAccommodationDTO = new CreateAccommodationDTO();
                createAccommodationDTO.setTitle(editTextTitle.getText().toString());
                createAccommodationDTO.setDescription(editTextDescription.getText().toString());
                createAccommodationDTO.setShortDescription(editTextShortDescription.getText().toString());
                createAccommodationDTO.setMin_capacity(Integer.parseInt(editTextMinCapacity.getText().toString()));
                createAccommodationDTO.setMax_capacity(Integer.parseInt(editTextMaxCapacity.getText().toString()));
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
                Call<AccommodationViewDTO> call = ClientUtils.accommodationService.insert(createAccommodationDTO);
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
        editTextLocation = binding.editTextLocation;
        editTextBasicPrice = binding.basicPrice;
        editTextMinCapacity = binding.minCapacity;
        editTextMaxCapacity = binding.maxCapacity;
        btnCreate = binding.btnSubmit;
        btnCancel = binding.btnCancel;
        spinner = binding.spinner;
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



}