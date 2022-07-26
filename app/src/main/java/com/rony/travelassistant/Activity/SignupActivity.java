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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rony.travelassistant.R;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEditText, emailEditText, numberEditText, passwordEditText, confirmPasswordEditText;

    TextView alreadyHaveAccount;
    AppCompatButton signUpButton;
    ProgressBar progressBar;

    DatabaseReference dbUser;
    FirebaseAuth mAuth;

    String name, email, number, password, confirm_password, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialization();
        setListener();

    }


    private void initialization(){

        //dbUser = FirebaseDatabase.getInstance().getReference().child("User");

        mAuth = FirebaseAuth.getInstance();
        //uid = mAuth.getCurrentUser().getUid();

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        numberEditText = findViewById(R.id.numberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        signUpButton = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar);

    }

    private void setListener() {
        alreadyHaveAccount.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.alreadyHaveAccount:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.signUpButton:
                CheckValidation();
        }
    }

    private void CheckValidation() {

        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        number = numberEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        confirm_password = confirmPasswordEditText.getText().toString().trim();


        System.out.println("Name is =====>" + name);
        System.out.println("Email is =====>" + email);
        System.out.println("Number is =====>" + number);
        System.out.println("password is =====>" + password);

        if (name.isEmpty()){
            showToast("Name Required");
            return;
        }

        if (email.isEmpty()){
            showToast("Email Required");
            return;
        }

        if (number.isEmpty()){
            showToast("Number Required");
            return;
        }
        if (password.isEmpty()){
            showToast("Password Required");
            return;
        }
        if (confirm_password.isEmpty()){
            showToast("Confirm Password Required");
            return;
        }
        if (!confirm_password.equals(password)){
            showToast("Password and Confirm Password Should be Same");
            return;
        }
        else {
            RegisterNewUser();
        }
    }

    private void RegisterNewUser() {

        progressBar.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    signUpButton.setVisibility(View.VISIBLE);

                    showToast("Account Created Successfully");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();

                    HashMap userInfo = new HashMap();
                    userInfo.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    userInfo.put("name", name);
                    userInfo.put("email", email);
                    userInfo.put("number", number);
                    userInfo.put("password", password);
                    userInfo.put("confirm_password", confirm_password);

                    dbUser = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    dbUser.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){

                            }else {
                                showToast("Can not insert value");
                            }
                        }
                    });


                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    signUpButton.setVisibility(View.VISIBLE);
                    System.out.println("Exception is ======>" + task.getException().toString());
                    showToast(task.getException().toString());
                }
            }
        });
    }
}