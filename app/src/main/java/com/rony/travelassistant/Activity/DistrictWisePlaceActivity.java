package com.rony.travelassistant.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rony.travelassistant.R;

public class DistrictWisePlaceActivity extends AppCompatActivity {

    String district_name;
    TextView titleTextView;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise_place);

        titleTextView = findViewById(R.id.titleTextView);
        imageBack = findViewById(R.id.imageBack);

        district_name = getIntent().getStringExtra("district_name");
        System.out.println("Receive name is " + district_name);
        titleTextView.setText(district_name);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}