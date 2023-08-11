package com.example.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PassOutActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText  itemNameEditText, serialNumberEditText, studentAddressEditText;

    Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_out);

        itemNameEditText = findViewById(R.id.Item);
        serialNumberEditText = findViewById(R.id.surnameId);
        studentAddressEditText = findViewById(R.id.institutionId);

        imageView= findViewById(R.id.back_home);
        generateButton=findViewById(R.id.ge);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(PassOutActivity.this,nav.class);
                startActivity(intent);
                finish();
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1  =new Intent(PassOutActivity.this,View_passout.class);
//                startActivity(intent1);
//                finish();

                generatePassOut();
                
            }
        });

    }

    private void generatePassOut() {

        String itemName = itemNameEditText.getText().toString();
        String serialNumber = serialNumberEditText.getText().toString();
        String studentAddress = studentAddressEditText.getText().toString();

        if (itemName.isEmpty()){
            itemNameEditText.requestFocus();
            itemNameEditText.setError("THis filed cannot be empty");
            return;
        }
         if(serialNumber.isEmpty()){
            serialNumberEditText.requestFocus();
            serialNumberEditText.setError("Enter Serial number");
            return;
        }  if (studentAddress.isEmpty()) {
            studentAddressEditText.requestFocus();
            studentAddressEditText.setError("Enter Serial number");
            return;

        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            Map<String, Object> passOutData = new HashMap<>();
            passOutData.put("itemName", itemName);
            passOutData.put("serialNumber", serialNumber);
            passOutData.put("studentAddress", studentAddress);


            // Store data in Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("passOuts")
                    .document(userId) // Use the user's ID as the document ID
                    .set(passOutData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PassOutActivity.this, "Pass Out generated", Toast.LENGTH_SHORT).show();
                            clearFields();

                            // Navigate to the next activity here
                            Intent intent = new Intent(PassOutActivity.this, View_passout.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PassOutActivity.this, "Failed to generate pass-out", Toast.LENGTH_SHORT).show();
                        }
                    });

        }

        }

        private void clearFields () {

            itemNameEditText.setText("");
            serialNumberEditText.setText("");
            studentAddressEditText.setText("");

        }

}