package com.example.bricole;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bricole.utils.LoadingOverlayUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    private TextView usernameTextView,
            emailTextView,
            numberTextView,
            cityTextView,
            roleTextView,
            genderTextView,
            addressTextView,
            postalCodeTextView;
    private ImageView profileImageView;
    private FrameLayout loadingOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Initialize the Loading spinner
        loadingOverlay = view.findViewById(R.id.loading_overlay);

        // Initialize the Logout button and set an event listener to it
        Button logoutButton = view.findViewById(R.id.logout_profile);
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());

        // Initialize the Logout button and set an event listener to it
        Button editButton = view.findViewById(R.id.edite_profile);
        editButton.setOnClickListener(v -> editProfileUser());

        // Initialize views
        usernameTextView = view.findViewById(R.id.username_profile);
        emailTextView = view.findViewById(R.id.email_profile);
        numberTextView = view.findViewById(R.id.number_profile);
        cityTextView = view.findViewById(R.id.city_profile);
        roleTextView = view.findViewById(R.id.role_profile);
        genderTextView = view.findViewById(R.id.gender_profile);
        addressTextView = view.findViewById(R.id.address_profile);
        postalCodeTextView = view.findViewById(R.id.postalCode_profile);
        profileImageView = view.findViewById(R.id.profileIMG_profile);
        // Fetch User info and update the UI
        fetchUserProfile();

        return view;
    }

    //Confirmation pop up box to confirm the logout presses
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> logoutUser())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
    //Logout function
    private void logoutUser() {
        // Firebase Authentication Sign-out
        FirebaseAuth.getInstance().signOut();

        // Redirect to LoginActivity
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    //Handel Click Edit profile button
    private void editProfileUser(){

    }
    private void fetchUserProfile() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Show loading overlay
        LoadingOverlayUtil.showLoading(loadingOverlay);

        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();

                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);

                        if (user != null) {
                            // Display user info or set default values
                            usernameTextView.setText(getValidString(user.getUsername(), "Unknown"));
                            emailTextView.setText(getValidString(user.getEmail(), "Unknown"));
                            numberTextView.setText(getValidString(String.valueOf(user.getNumber()), "00"));
                            cityTextView.setText(getValidString(user.getCity(), "Unknown"));
                            roleTextView.setText(getValidString(user.getRole(), "Unknown"));
                            genderTextView.setText(getValidString(user.getGender(), "Unknown"));
                            addressTextView.setText(getValidString(user.getAddress(), "Unknown"));
                            postalCodeTextView.setText(getValidString(user.getPostalCode(), "00"));

                            // Load profile image or set default
                            String profileImg = user.getProfileImg();
                            if (profileImg != null && !profileImg.isEmpty()) {
                                Picasso.get().load(profileImg).into(profileImageView);
                            } else {
                                Picasso.get().load(R.drawable.no_user_imagee).into(profileImageView);
                            }

                            // Hide loading overlay after operation
                            LoadingOverlayUtil.hideLoading(loadingOverlay);
                        }
                    } else {
                        Toast.makeText(requireContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private String getValidString(String value, String defaultValue) {
        return value != null && !value.isEmpty() ? value : defaultValue;
    }
}
