package com.example.bookingapp.fragments;

import android.os.Bundle;

<<<<<<< Updated upstream:BookingApp/app/src/main/java/com/example/bookingapp/LoginFragment.java
import android.content.Context;
import android.os.Bundle;
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

>>>>>>> Stashed changes:BookingApp/app/src/main/java/com/example/bookingapp/fragments/RegisterFragment.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< Updated upstream:BookingApp/app/src/main/java/com/example/bookingapp/LoginFragment.java
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public LoginFragment() {
        // Required empty public constructor
=======
import com.example.bookingapp.R;
import com.example.bookingapp.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    public static RegisterFragment newInstance() {return new RegisterFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
>>>>>>> Stashed changes:BookingApp/app/src/main/java/com/example/bookingapp/fragments/RegisterFragment.java
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream:BookingApp/app/src/main/java/com/example/bookingapp/LoginFragment.java
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
=======
>>>>>>> Stashed changes:BookingApp/app/src/main/java/com/example/bookingapp/fragments/RegisterFragment.java
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
