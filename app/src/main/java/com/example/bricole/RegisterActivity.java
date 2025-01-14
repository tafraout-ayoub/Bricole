package com.example.bricole;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    Button register ;
    EditText InputEmail, InputPassword, InputUsername;
    RadioGroup UserRole;
    private FirebaseAuth mAuth;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.layout_register);

        register = findViewById(R.id.RegisterBtn);
        InputEmail = findViewById(R.id.InputEmailLogin);
        InputUsername = findViewById(R.id.InputUsername);
        InputPassword = findViewById(R.id.InputPasswordLogin);
        UserRole = findViewById(R.id.UserRole);
//        InputUsername.setError("Username is required");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,username,password, role;
                email=InputEmail.getText().toString();
                username= InputUsername.getText().toString();
                password = InputPassword.getText().toString();
                role = getSelectedRole(UserRole);
                if (checkInputs()){
                    registerUser(username, email, password, role);
                }else{
                    checkInputs();
                }
//                createAccount(email,password);


            }
        });

//        MainApplication context = (MainApplication) this.getApplicationContext();
//
//        context.token = "vfdfd";
    }

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(RegisterActivity.this, GetStartedActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed: Try agian.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
}

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

//    private void register(String email, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//    }

    public String getSelectedRole(RadioGroup radioGroup){
        int selected = radioGroup.getCheckedRadioButtonId();
        if (selected == -1){return "";}
        RadioButton selectedRadioButton = radioGroup.findViewById(selected);
        return selectedRadioButton.getText().toString();
    }

    public void registerUser(String username, String email, String password, String role) {
        // Initialize Firebase Authentication and Realtime Database references
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        // Create user with email and password
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the newly created user's ID
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();

                            // Create a User object with additional information
                            User user = new User(username, email, role);

                            // Store the user in the Realtime Database under "Users" collection
                            databaseRef.child(userId).setValue(user)
                                    .addOnCompleteListener(databaseTask -> {
                                        if (databaseTask.isSuccessful()) {
                                            // Redirect to MainActivity
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish(); // Finish the current activity
                                        } else {
                                            // Show an error message if database write fails
                                            Toast.makeText(getApplicationContext(),
                                                    "Registration successful, but failed to save user data!",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        // Registration failed
                        Toast.makeText(getApplicationContext(),
                                "Registration failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean checkInputs() {
        // Check InputEmailLogin
        EditText emailInput = findViewById(R.id.InputEmailLogin);
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            return false;
        }

        // Check InputUsername
        EditText usernameInput = findViewById(R.id.InputUsername);
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            usernameInput.setError("Username is required");
            return false;
        }

        // Check InputPasswordLogin
        EditText passwordInput = findViewById(R.id.InputPasswordLogin);
        String password = passwordInput.getText().toString().trim();
        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            return false;
        }

        // Check UserRole (RadioGroup)
        RadioGroup userRole = findViewById(R.id.UserRole);
        if (userRole.getCheckedRadioButtonId() == -1) {
            // No radio button is selected
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        // If all inputs are valid
        return true;
    }

}

