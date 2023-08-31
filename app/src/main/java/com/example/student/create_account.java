package com.example.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class create_account extends AppCompatActivity {
    private EditText etName,etInstitution , etStudentNumber ,etcourse, etEmail ,etPassword ,etConfirmPAssword;
    private Button createAccountbtn;
    private TextView loginText;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseDatabase iclinic;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // initialising all the views
        mAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.nameId);
        etInstitution = findViewById(R.id.institutionId);
        etStudentNumber = findViewById(R.id.studentId);
        etcourse=findViewById(R.id.courseId);
        etEmail = findViewById(R.id.emailId);
        etPassword= findViewById(R.id.passwordId);
        etConfirmPAssword= findViewById(R.id.confirmpasswordId);
        createAccountbtn =(Button) findViewById(R.id.accountBtnId);
        loginText = findViewById(R.id.loginTextviewId);
        progressDialog = new ProgressDialog(this);


        //set on Click LListener for registration btn and log in Textview
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(create_account.this , login.class);
                startActivity(intent);
            }
        });

        //set on click listener for user registration
        createAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerNewUser();
            }
        });

    }

    private void registerNewUser() {


        //take the editText values to string
        String name = etName.getText().toString();
        String varsity = etInstitution.getText().toString();
        String studentNUmber = etStudentNumber.getText().toString();
        String course =etcourse.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPAssword.getText().toString();




        if (name.length()==0){
            etName.requestFocus();
            etName.setError("Enter your name !");
            return;
        }
        else if (name.matches("[a-zA-Z]")) {
            etName.requestFocus();
            etName.setError("Enter only alphabetical characters");
            return;

        }
        else if (varsity.length()==0) {
            etInstitution.requestFocus();
            etInstitution.setError("Enter the name of the institution");
            return;

        }
        else if (varsity.matches("[a-zA-Z]")) {
            etInstitution.requestFocus();
            etInstitution.setError("Enter only alphabetical characters");
            return;
        }
        else if (studentNUmber.isEmpty()) {

            etStudentNumber.requestFocus();
            etStudentNumber.setError("Enter student NUmber");
            return;

        } if (course.length()==0){
            etName.requestFocus();
            etName.setError("Enter course you are doing  !");
            return;
        }
        else if (course.matches("[a-zA-Z]")) {
            etName.requestFocus();
            etName.setError("Enter only alphabetical characters");
            return;
        }
        else if (email.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Enter Email");
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.requestFocus();
            etEmail.setError("Invalid Email");
            return;
        }else if (!email.endsWith("@keyaka.ul.ac.za")) {
            etEmail.requestFocus();
            etEmail.setError("Invalid Email domain. Use @keyaka.ul.ac.za");
            return;
        }

        else if (password.isEmpty() ) {
            etPassword.requestFocus();
            etPassword.setError("Field cannot be empty");
            return;

        } else if (password.length()<6) {
            etPassword.requestFocus();
            etPassword.setError("Your password must have more 6 characters");
            return;

        } else if (!confirmPassword.equals(password)) {
            etConfirmPAssword.requestFocus();
            etConfirmPAssword.setError("Passwords don't match");

        }
        else {
            //the firebase registration steps
            progressDialog.setMessage("Registration in progress");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();





            mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                   /* users.getReference("lee")
                            .setValue(new UserClass("",""));*/



                    if(task.isSuccessful()){

                        String userId = mAuth.getCurrentUser().getUid();
                        storeUserDataInFirestore(userId, name,varsity,studentNUmber,course, email);

                        //take to main activity
                        Intent mainActivity = new Intent(create_account.this,nav.class);
                        startActivity(mainActivity);
                        finish();
                    }
                    else {
                        Toast.makeText(create_account.this, "Registration failed please try again ", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                }
            });


        }


    }

    private void storeUserDataInFirestore(String userId, String name,String varsity,String studentNUmber,String course, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a Map to store user data
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("varsity",varsity);
        userMap.put("studentNumber",studentNUmber);
        userMap.put("course",course);
        userMap.put("email", email);

        // Reference to the "users" collection in Firestore
        DocumentReference userRef = db.collection("users").document(userId);
        // Set user data in Firestore
        userRef.set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // User data added to Firestore successfully
                        Toast.makeText(create_account.this, "Data added", Toast.LENGTH_SHORT).show();
                        // Proceed with further steps, if needed
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Toast.makeText(create_account.this, "Failed to store user data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}