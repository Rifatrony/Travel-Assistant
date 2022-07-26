package com.rony.travelassistant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.rony.travelassistant.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText  emailEditText, passwordEditText;

    TextView noAccountTextView;
    AppCompatButton loginButton;

    ProgressBar progressBar;

    String email, password;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();
        setListener();

    }
    private void initialization(){

        mAuth = FirebaseAuth.getInstance();

        noAccountTextView = findViewById(R.id.noAccountTextView);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

    }

    private void setListener() {
        noAccountTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.noAccountTextView:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;

            case R.id.loginButton:
                CheckValidation();
        }

    }

    private void CheckValidation() {

        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()){
            showToast("Email Required");
            return;
        }

        if (password.isEmpty()){
            showToast("Password Required");
            return;
        }

        else {
            Login();
        }

    }

    private void Login() {

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    showToast("Login Successful");
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    showToast(task.getException().toString());
                }
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }
}