package com.example.bookingapp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationTypeSpinnerAdapter;
import com.example.bookingapp.databinding.FragmentCreateAccommodationAdditionalInfoBinding;
import com.example.bookingapp.enums.AccommodationType;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class CreateAccommodationAdditionalInfoFragment extends Fragment {

    private FragmentCreateAccommodationAdditionalInfoBinding binding;
    private EditText minPeople;
    private EditText maxPeople;
    private Context context;

    public static CreateAccommodationAdditionalInfoFragment newInstance() {
        return new CreateAccommodationAdditionalInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAccommodationAdditionalInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupDatePickers();
        setupNumberPickers();
        setupAccommodationTypeSpinner();
        return root;
    }

    private void setupSubmitListener() {
        Button submit = binding.btnSubmit;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Accommodation will be created after admin's check", Toast.LENGTH_SHORT).show();
                FragmentTransition.to(CreateAccommodationBaseFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });
    }

    private void setupDatePickers() {
        EditText from = binding.editTextFrom;
        from.setOnClickListener(v -> showDatePickerDialog(from));

        EditText until = binding.editTextUntil;
        until.setOnClickListener(v -> showDatePickerDialog(until));
    }

    private void showDatePickerDialog(EditText targetEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1;
                    targetEditText.setText(date);
                }, year, month, day);
        dialog.show();
    }

    private void setupNumberPickers() {
        minPeople = binding.minPeople;
        maxPeople = binding.maxPeople;

        minPeople.setOnClickListener(v -> showNumberPickerDialog(minPeople));
        maxPeople.setOnClickListener(v -> showNumberPickerDialog(maxPeople));
    }


    private void showNumberPickerDialog(final EditText targetEditText) {
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setValueChangeListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Handle the selected value, e.g., set it to the EditText
                targetEditText.setText(String.valueOf(newVal));
            }
        });

        numberPickerDialog.show(getChildFragmentManager(), "numberPicker");


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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}