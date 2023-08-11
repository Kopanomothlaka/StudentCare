package com.example.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class login extends AppCompatActivity {
     EditText emailEditText , passwordEditextText;
    private Button loginButton;
    TextView forgotPassTextView , createAccTextView;
    FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.loginEmailId);
        passwordEditextText = findViewById(R.id.loginPassId);
        loginButton = (Button) findViewById(R.id.loginBtnID);
        forgotPassTextView = findViewById(R.id.forgotPassTextViewId);
        createAccTextView = findViewById(R.id.createAccTextviewID);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        forgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, reset_password.class);
                startActivity(intent);
                finish();
            }
        });
        createAccTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(login.this, create_account.class);
                startActivity(intent2);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });



    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditextText.getText().toString();

        if (email.isEmpty()){
            emailEditText.requestFocus();
            emailEditText.setError("THis filed cannot be empty");
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.requestFocus();
            emailEditText.setError("Invalid Email");

        }else if (password.length()<6) {
            passwordEditextText.requestFocus();
            passwordEditextText.setError("Password must have more than 6 characters");
            return;

        } else if (password.isEmpty()) {
            passwordEditextText.requestFocus();
            passwordEditextText.setError("This filed cannot be empty");
            return;

        }  else {
            //FIrebase sign in code here

            progressDialog.setMessage("Logging in.....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Intent taketomain = new Intent(login.this, nav.class);
                        startActivity(taketomain);
                        finish();
                    }
                    else {

                        if (task.getException() instanceof FirebaseAuthInvalidUserException){
                            Toast.makeText(login.this, "Uer does not exist", Toast.LENGTH_SHORT).show();
                        }
                        else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException
                                && task.getException().getMessage().equals("The password is invalid or the user does not have a password.")) {
                            Toast.makeText(login.this, "Invalid password.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException
                                && ((FirebaseAuthUserCollisionException) task.getException()).getErrorCode().equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                            Toast.makeText(login.this, "Email is already taken.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                    progressDialog.dismiss();
                }
            });
        }

    }
}