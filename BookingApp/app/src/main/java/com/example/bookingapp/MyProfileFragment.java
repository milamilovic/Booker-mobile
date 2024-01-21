package com.example.bookingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.GuestDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.HomeFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Multipart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {

    private static Long id;
    private UserDTO user;
    private GuestDTO guest;
    private static final String ARG_PARAM = "param";
    private static final String USER_ID_KEY = "user_id";
    ImageView updateProfilePic;
    ImageView bigProfilePic;
    ImageView miniProfilePic;
    private ActivityResultLauncher<Intent> imageChooserLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        handleImageResult(data);
                    }
                }
            }
    );


    public MyProfileFragment() {
        // Required empty public constructor
    }

    public MyProfileFragment(UserDTO user){
        this.user = user;
    }


    public static MyProfileFragment newInstance(UserDTO user) {
        MyProfileFragment fragment = new MyProfileFragment(user);
        Bundle args = new Bundle();
        args.putLong("id", user.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            id = sharedPref.getLong(USER_ID_KEY, 0);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> call = ClientUtils.userService.getById(id);
            try{
                Response<UserDTO> response = call.execute();
                user = (UserDTO) response.body();
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING USER");
                ex.printStackTrace();
            }
        }
    }

    private void openImageChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose Image Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        imageChooserLauncher.launch(chooserIntent);
    }

    private void handleImageResult(Intent data) {
        Uri selectedImageUri = data.getData();
        if (selectedImageUri != null) {
            miniProfilePic.setImageURI(selectedImageUri);
            bigProfilePic.setImageURI(selectedImageUri);
        } else {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                miniProfilePic.setImageBitmap(imageBitmap);
                bigProfilePic.setImageBitmap(imageBitmap);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        //  profile picture
        bigProfilePic = view.findViewById(R.id.profile_pic);
        miniProfilePic = view.findViewById(R.id.mini_profile_pic);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<List<String>> imageCall = ClientUtils.userService.getImage(user.getId());
        try{
            Response<List<String>> response = imageCall.execute();
            List<String> images = (List<String>) response.body();
            if(images!=null && !images.isEmpty()) {
                byte[] bytes = Base64.decode(images.get(0), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                miniProfilePic.setImageBitmap(bitmap);
                bigProfilePic.setImageBitmap(bitmap);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING IMAGES");
            ex.printStackTrace();
        }


        EditText name = view.findViewById(R.id.name);
        name.setText(user.getName() + " " + user.getSurname());

        TextView role = view.findViewById(R.id.role);
        role.setText(user.getRole().toString());

        EditText email = view.findViewById(R.id.email_profile);
        email.setText(user.getEmail());

        EditText address = view.findViewById(R.id.address_profile);
        address.setText(user.getAddress());

        EditText phone = view.findViewById(R.id.phone_profile);
        phone.setText(user.getPhone());

        EditText password = view.findViewById(R.id.password_profile_field);
        EditText confirm_password = view.findViewById(R.id.password_profile_confirm_field);

        Button apply_changes_btn = view.findViewById(R.id.apply_profile_changes_button);
        apply_changes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getRole() == Role.GUEST){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Call<GuestDTO> call = ClientUtils.userService.getGuestById(user.getId());
                    try{
                        Response<GuestDTO> response = call.execute();
                        guest = (GuestDTO) response.body();
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE GETTING GUEST");
                        ex.printStackTrace();
                    }

                    guest.setName(name.getText().toString().split(" ")[0]);
                    guest.setSurname(name.getText().toString().split(" ")[1]);
                    guest.setEmail(email.getText().toString());
                    guest.setAddress(address.getText().toString());
                    guest.setPhone(phone.getText().toString());
                    if (!password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty() &&
                            password.getText().toString().equals(confirm_password.getText().toString())) {
                        guest.setPassword(password.getText().toString());
                        System.out.println("Ovo je sifra " + password.getText().toString());
                    } else if (!password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty() &&
                            !password.getText().toString().equals(confirm_password.getText().toString())) {
                        Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                    } else if ((password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty()) ||
                            (!password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty())) {
                        Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty()) {
                    }

                    Call<GuestDTO> stringCall = ClientUtils.userService.updateGuest(guest.getId(), guest);
                    try {
                        Response<GuestDTO> response = stringCall.execute();
                        System.out.println(response.body());
                    } catch (Exception ex) {
                        System.out.println("EXCEPTION WHILE UPDATING GUEST DATA");
                        ex.printStackTrace();
                    }
                    FragmentTransition.to(HomeFragment.newInstance(), (FragmentActivity) getContext(), true, R.id.fragment_placeholder);
                }

                else if (user.getRole() == Role.ADMIN) {
                    user.setName(name.getText().toString().split(" ")[0]);
                    user.setSurname(name.getText().toString().split(" ")[1]);
                    user.setEmail(email.getText().toString());
                    user.setAddress(address.getText().toString());
                    user.setPhone(phone.getText().toString());
                    if (!password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty() &&
                            password.getText().toString().equals(confirm_password.getText().toString())) {
                        user.setPassword(password.getText().toString());
                        System.out.println("Ovo je sifra " + password.getText().toString());
                    } else if (!password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty() &&
                            !password.getText().toString().equals(confirm_password.getText().toString())) {
                        Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                    } else if ((password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty()) ||
                            (!password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty())) {
                        Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty()) {
                    }
                    Call<UserDTO> stringCall = ClientUtils.userService.updateAdmin(user.getId(), user);
                    try {
                        Response<UserDTO> response = stringCall.execute();
                        System.out.println("dunja");
                        System.out.println(response.body());
                    } catch (Exception ex) {
                        System.out.println("EXCEPTION WHILE UPDATING ADMIN DATA");
                        ex.printStackTrace();
                    }
                    FragmentTransition.to(HomeFragment.newInstance(), (FragmentActivity) getContext(), true, R.id.fragment_placeholder);
                }

                // save image
                /*Bitmap bitmap = ((BitmapDrawable) miniProfilePic.getDrawable()).getBitmap();
                // Bitmap to byte array
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                List<String> picturesToSave = new ArrayList<>();
                picturesToSave.add(base64Image);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Call<String> imageCall = ClientUtils.userService.saveImage(user.getId(), picturesToSave);
                try{
                    Response<String> response = imageCall.execute();
                    String images = (String) response.body();
                    System.out.println("Odgovor: " + images);
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING IMAGES");
                    ex.printStackTrace();
                }*/
            }
        });


        Button delete_btn = view.findViewById(R.id.delete_profile_button);
        if (user.getRole() == Role.GUEST) {
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = LayoutInflater.from(requireContext());
                    View customLayout = inflater.inflate(R.layout.fragment_delete, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()).setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent i = new Intent(getActivity(), BaseActivity.class);
                            startActivity(i);
                            //TODO logout user and delete account
                            // redirection to home page for unregistered user
                            Toast.makeText(getActivity(), "Deleted!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setView(customLayout);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else if (user.getRole() == Role.ADMIN){
            ViewGroup parentView = (ViewGroup) delete_btn.getParent();
            parentView.removeView(delete_btn);
        }

        Button logout_btn = view.findViewById(R.id.logout_profile_button);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BaseActivity.class);
                startActivity(i);
                //TODO log out
            }
        });

        updateProfilePic = view.findViewById(R.id.edit_profile_pic);
        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        return view;
    }
}