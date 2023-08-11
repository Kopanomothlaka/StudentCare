package com.example.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyStuffActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView itemNameTextView, serialNumberTextView;

    FrameLayout openPassOut;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stuff);
        imageView = findViewById(R.id.back_home_3);
        openPassOut=findViewById(R.id.open_passout);

        itemNameTextView = findViewById(R.id.displayItemMystuff);
        serialNumberTextView = findViewById(R.id.displaySerialMystuff);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();

        loadUserData();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyStuffActivity.this, nav.class);
                startActivity(intent);
                finish();
            }
        });
        openPassOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyStuffActivity.this,View_passout.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("passOuts").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String itemName = document.getString("itemName");
                                String serialNumber = document.getString("serialNumber");

                                // Set user and passout data in TextViews
                                itemNameTextView.setText("Item Name: " + itemName);
                                serialNumberTextView.setText("Serial Number: " + serialNumber);

                                // Hide progress dialog after loading data
                                progressDialog.dismiss();
                            } else {
                                Log.d("UserData", "User document does not exist");
                                progressDialog.dismiss(); // Hide progress dialog on error
                            }
                        } else {
                            Log.e("UserData", "Error getting user data", task.getException());
                            progressDialog.dismiss(); // Hide progress dialog on error
                        }
                    });
        }
    }
}