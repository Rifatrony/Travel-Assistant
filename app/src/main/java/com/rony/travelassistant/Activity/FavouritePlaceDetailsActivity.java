package com.rony.travelassistant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Model.FavouritePlaceModel;
import com.rony.travelassistant.R;

public class FavouritePlaceDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    String place_name;
    TextView titleTextView, placeNameTextView, placeDetailsTextView, howToGoTextView, whereToStayTextView;
    ImageView imageBack, favouritePlaceImageView;

    DatabaseReference dbDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_place_details);

        initialization();
        setListener();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            place_name = bundle.getString("place_name");
            titleTextView.setText(place_name);
        }
        FetchDetails();
    }

    private void FetchDetails() {
        dbDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FavouritePlaceModel data = dataSnapshot.getValue(FavouritePlaceModel.class);
                    if (data.getPlace_name().equals(place_name)){
                        Glide.with(FavouritePlaceDetailsActivity.this).load(data.getImage_link()).into(favouritePlaceImageView);
                        placeNameTextView.setText(data.getPlace_name());
                        placeDetailsTextView.setText(data.getPlace_details());
                        howToGoTextView.setText(data.getHow_to_travel());
                        whereToStayTextView.setText(data.getWhere_to_stay());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initialization() {

        dbDetails = FirebaseDatabase.getInstance().getReference().child("Picture");

        titleTextView = findViewById(R.id.titleTextView);
        placeNameTextView = findViewById(R.id.placeNameTextView);
        placeDetailsTextView = findViewById(R.id.placeDetailsTextView);
        howToGoTextView = findViewById(R.id.howToGoTextView);
        whereToStayTextView = findViewById(R.id.whereToStayTextView);
        imageBack = findViewById(R.id.imageBack);
        favouritePlaceImageView = findViewById(R.id.favouritePlaceImageView);
    }

    private void setListener() {
        imageBack.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;
        }
    }
}