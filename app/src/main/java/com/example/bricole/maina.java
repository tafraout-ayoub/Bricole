package com.example.bricole;
import com.example.bricole.R.id.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class maina extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        //Initialize the FrameLayout first time to show the profile fragment
        ChangeFragmentContent(new ProfileFragment());
        //Initialize the bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

                if(item.getItemId()==R.id.profile_nav) {
                    selectedFragment = new ProfileFragment();
                }else if (item.getItemId()==R.id.products_nav) {
                    selectedFragment = new ProductsFragment();
                }
                // Add more cases if you have more fragments

                if (selectedFragment != null) {
                    ChangeFragmentContent(selectedFragment);
                }
            return true;
        });
    }

    //Change Fragment container view by this methode
    public void ChangeFragmentContent(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
