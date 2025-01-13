package com.example.bricole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class GetStartedActivity extends AppCompatActivity {
    Button getStartedBtn;
    TextView redirectLogin;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_get_started);
        checkUserIsLogged();

        getStartedBtn = findViewById(R.id.getStartedBtn);
        redirectLogin = findViewById(R.id.redirectLogin);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStartedActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStartedActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkUserIsLogged() {
        // Initialize Firebase Authentication
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Check if a user is currently logged in
        if (auth.getCurrentUser() != null) {
            // User is logged in, redirect to MainActivity
            Intent intent = new Intent(getApplicationContext(), maina.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish the current activity
        }
        // If no user is logged in, do nothing
    }

}
