package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class View_passout extends AppCompatActivity {

    private ImageView backhome;
    ProgressDialog progressDialog;
    private TextView itemNameTextView, serialNumberTextView,  userNameTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_passout);
        backhome=findViewById(R.id.back_home_3);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);

        itemNameTextView = findViewById(R.id.displayItem);
        serialNumberTextView = findViewById(R.id.displaySerial);
        userNameTextView = findViewById(R.id.displayname);
        button=findViewById(R.id.takeMeHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(View_passout.this, nav.class);
                startActivity(intent);
                finish();
            }
        });

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1  =new Intent(View_passout.this,PassOutActivity.class);
                startActivity(intent1);
            }
        });

        // Get the current user's ID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            loadPassoutData(userId);
            loadUserData(userId);
        }
    }

    private void loadUserData(String userId) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve user data
                            String userName = document.getString("name");
                            // Debugging: Print retrieved user data
                            Log.d("UserData", "User Name: " + userName);

                            // Populate TextView with user data
                            userNameTextView.setText(userName);
                        } else {
                            Log.d("UserData", "User document does not exist");

                            // Handle case where user document doesn't exist
                        }
                    } else {
                        // Handle exception
                    }
                });
    }

    private void loadPassoutData(String userId) {
        progressDialog.show();

        // Reference to the "passOuts" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference passoutRef = db.collection("passOuts").document(userId);
        passoutRef.get()
                .addOnCompleteListener(task -> {

                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve passout data
                            String itemName = document.getString("itemName");
                            String serialNumber = document.getString("serialNumber");
                            String name = document.getString("name");

                            // Debugging: Print retrieved data to Logcat
                            Log.d("PassOutData", "itemName: " + itemName);
                            Log.d("PassOutData", "serialNumber: " + serialNumber);
                            Log.d("PassOutData", "name: " + name);

                            // Populate TextViews with passout data
                            itemNameTextView.setText(itemName);
                            serialNumberTextView.setText(serialNumber);
                            userNameTextView.setText(name);
                        } else {
                            Log.d("PassOutData", "Document does not exist");
                            // Handle case where passout document doesn't exist
                        }
                    } else {
                        // Handle exception
                    }
                });



    }
}