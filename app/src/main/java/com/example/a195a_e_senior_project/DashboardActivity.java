package com.example.a195a_e_senior_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a195a_e_senior_project.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Display a welcome message for the user.
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUser = new User(user);
        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("Welcome " + currentUser.getName());
    }

    /**
     * Navigate to the register department activity.
     * @param view
     */
    public void registerDepartment(View view) {
        Intent intent = new Intent(this, DepartmentRegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to advising hub activity.
     */
    public void advisingHub(View view) {
        Intent intent = new Intent(this, AdvisingHubActivity.class);
        startActivity(intent);
    }

    /**
     * Signs the user out
     * @param view
     */
    public void signOut(View view) {
        // This signOut method is part of the FirebaseAuth object.
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
