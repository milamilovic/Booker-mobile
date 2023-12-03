package com.example.bookingapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ImageAdapter;
import com.example.bookingapp.databinding.FragmentCreateAmenitiesBinding;

import java.util.ArrayList;
import java.util.List;


public class CreateFragmentAmenitiesFragment extends Fragment {
    ImageButton btPrevious, btNext;
    Button select;
    private ImageSwitcher imageSwitcher;

    int imageList[] = {R.drawable.london_image, R.drawable.copenhagen_image, R.drawable.hotel_image};
    int count = imageList.length;
    private FragmentCreateAmenitiesBinding binding;
    private int currentIndex = 0;
    private int PICK_IMAGE_MULTIPLE = 1;
    private String imageEncoded;
    private ArrayList<Uri> mArrayUri;
    private int position;
    List<String> imageEncodedList;
    EditText location;



    public static CreateFragmentAmenitiesFragment newInstance() {
        return new CreateFragmentAmenitiesFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAmenitiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btPrevious = binding.btnPrevious;
        btNext = binding.btnNext;
        imageSwitcher = binding.imageSwitcher;
        select = binding.addImages;
        mArrayUri = new ArrayList<Uri>();

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
        btPrevious.setOnClickListener(new View.OnClickListener() {
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

        btNext.setOnClickListener(new View.OnClickListener() {
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

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_MULTIPLE);
            }
        });


        location = binding.editTextLocation;

        location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    FragmentTransition.to(CreateAccommodationAdditionalInfoFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
                    return true;
                }
                return false;
            }
        });



        return root;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}