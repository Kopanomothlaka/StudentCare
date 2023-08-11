package com.example.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private ImageView back;
    ProgressDialog progressDialog;
    private TextView nameTextView,surnameTextview, emailTextView,  studentNumberTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);
        setContentView(R.layout.activity_profile);
        back=findViewById(R.id.back_home_profile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,nav.class);
                startActivity(intent);
            }
        });

        // Initialize your TextViews
        nameTextView = findViewById(R.id.nameTextView);
        surnameTextview=findViewById(R.id.surnameTextview);
        emailTextView = findViewById(R.id.emailTextView);
        studentNumberTextView = findViewById(R.id.studentNumberTextView);

        //Get the current user's ID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            loadUserData(userId);
        }


    }

    private void loadUserData(String userId) {
        progressDialog.show();

        // Reference to the "users" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Log.d("ProfileActivity", "Document snapshot retrieved successfully");

                        if (document.exists()) {
                            Log.d("ProfileActivity", "Document exists");
                            // Retrieve user data
                            String name = document.getString("name");
                            String surname = document.getString("surname");
                            String email = document.getString("email");
                            String studentNumber = document.getString("studentNumber");

                            // Populate TextViews with user data
                            nameTextView.setText(name);
                            surnameTextview.setText(surname);
                            emailTextView.setText(email);
                            studentNumberTextView.setText(studentNumber);
                            progressDialog.dismiss();
                        }
                        else {
                            progressDialog.dismiss();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                    }
                });
    }


}