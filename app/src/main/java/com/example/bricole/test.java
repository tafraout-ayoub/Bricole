package com.example.bricole;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class test extends AppCompatActivity implements View.OnClickListener{


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        TextView textView = findViewById(R.id.TextOne);
        Button button = findViewById(R.id.BtnTest);
        // Get the currently logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        button.setOnClickListener(this::onClick);
        String username = user.getDisplayName(); // If you have set the display name
        String email = user.getEmail();          // Or use the email as an alternative

        // Update the TextView with a welcome message
        textView.setText("Hello, " + (username != null ? username : email));
    }
    
    @Override
    public void onClick(View v) {
        Toast.makeText(test.this, "Hello from the file", Toast.LENGTH_SHORT).show();
    }
}
