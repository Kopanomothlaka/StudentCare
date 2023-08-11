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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class reset_password extends AppCompatActivity {
    private EditText emailRestEditText;
    private Button resetPassBtn;
    private FirebaseAuth auth;
    private ImageView tologinpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailRestEditText = findViewById(R.id.restEmailId);
        resetPassBtn = (Button) findViewById(R.id.restpassBtn);
        auth = FirebaseAuth.getInstance();
        tologinpage=findViewById(R.id.back_loginpage_reset);
        tologinpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(reset_password.this, login.class);
                startActivity(intent2);
            }
        });


        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUserPassword();
            }
        });
    }



    private void resetUserPassword() {

        String email = emailRestEditText.getText().toString();



        if (email.isEmpty()) {
            emailRestEditText.requestFocus();
            emailRestEditText.setError("Please enter your email");
            return;
        }else if (!email.endsWith("@keyaka.ul.ac.za")) {
            emailRestEditText.requestFocus();
            emailRestEditText.setError("Invalid Email domain. Use @keyaka.ul.ac.za");
            return;
        }
        else {

            // Update user's password
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        // if isSuccessful then done message will be shown
                        // and you can change the password
                        Toast.makeText(reset_password.this,"Email sent",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(reset_password.this, login.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(reset_password.this,"Error Occurred",Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

    }
}