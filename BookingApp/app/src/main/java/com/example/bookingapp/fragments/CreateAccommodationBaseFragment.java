package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.databinding.FragmentCreateAccommodationBaseBinding;


public class CreateAccommodationBaseFragment extends Fragment {

    private FragmentCreateAccommodationBaseBinding binding;

    public static CreateAccommodationBaseFragment newInstance() {

        return new CreateAccommodationBaseFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAccommodationBaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText editText = binding.editTextShortDescription;
        editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    FragmentTransition.to(CreateAccommodationAmenitiesFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
                    return true;
                }
                return false;
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
}