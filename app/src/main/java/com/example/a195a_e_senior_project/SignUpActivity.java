package com.example.a195a_e_senior_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize mAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Cloud Firestore instance
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Registers user
     * @param view
     */
    public void signUp(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText firstName = (EditText) findViewById(R.id.firstName);
        EditText lastName = (EditText) findViewById(R.id.lastName);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        final String usernameString = username.getText().toString();
        final String passwordString = password.getText().toString();
        final String confirmPasswordString = confirmPassword.getText().toString();
        final String firstNameString = firstName.getText().toString();
        final String lastNameString = lastName.getText().toString();

        // Enforce constraints upon sign up.
        if (!passwordString.equals(confirmPasswordString)) {
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_LONG).show();
        }

        if (usernameString.matches("") || passwordString.matches("")
        || firstNameString.matches("") || lastNameString.matches("")) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show();
        }
        else {
            // Create new user with the information they provided
            mAuth.createUserWithEmailAndPassword(usernameString, passwordString)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success
                                Log.d("Create User: ", "createUserWithEmail:success");

                                // Add User into database
                                Map<String, Object> addUser = new HashMap<>();
                                addUser.put("first", firstNameString);
                                addUser.put("last", lastNameString);
                                db.collection("users").document(usernameString)
                                        .set(addUser)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Doc: ", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Doc: ", "Error writing document", e);
                                            }
                                        });

                                // Sign in user
                                FirebaseUser user = mAuth.getCurrentUser();

                                // Redirect user back to main activity
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w("Create User: ", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}