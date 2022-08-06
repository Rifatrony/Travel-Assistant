package com.dpheapp.temporaryproject;

import static com.dpheapp.temporaryproject.MainActivity.apiResponseData;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {


    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        position = getIntent().getIntExtra("position", 0);
        System.out.println(apiResponseData.products.data.get(position).name);


    }
}