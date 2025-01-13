package com.example.bricole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button login ;
    EditText InputEmail, InputPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        mAuth = FirebaseAuth.getInstance();
        
        login = findViewById(R.id.RegisterBtn);
        InputEmail = findViewById(R.id.InputEmailLogin);
        InputPassword = findViewById(R.id.InputPasswordLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if user is signed in (non-null) and update UI accordingly.
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                if(currentUser != null){
//                    System.out.println(currentUser);
//                }
//                System.out.println(InputEmail.getText().toString());
//                System.out.println(InputPassword.getText().toString());
                String Email = InputEmail.getText().toString(),
                        Password = InputPassword.getText().toString();
                    loginUser(Email, Password);
            }
        });


    }

    public void loginUser(String email, String password) {
        // Initialize Firebase Authentication
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Attempt to sign in with email and password
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful, redirect to MainActivity
                        Intent intent = new Intent(getApplicationContext(), maina.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Finish the current activity
                    } else {
                        // Login failed, show toast message
                        Toast.makeText(getApplicationContext(),
                                "Failed login: Verify the info",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

}