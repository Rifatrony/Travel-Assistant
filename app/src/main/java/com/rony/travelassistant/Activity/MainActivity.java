package com.rony.travelassistant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Model.TourModel;
import com.rony.travelassistant.Model.UserModel;
import com.rony.travelassistant.R;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView imageMenu;
    NavigationView navigationView;

    TextView header_name, header_number;

    TextView titleTextView;

    DatabaseReference dbUser, dbTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        imageMenu = findViewById(R.id.imageMenu);
        titleTextView = findViewById(R.id.titleTextView);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = findViewById(R.id.navigationView);
        //this will make the icon color full if the icon is colored
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        header_name = navigationView.getHeaderView(0).findViewById(R.id.headerNameTExtView);
        header_number = navigationView.getHeaderView(0).findViewById(R.id.headerNumberTextView);

        dbUser = FirebaseDatabase.getInstance().getReference().child("User");

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel data = dataSnapshot.getValue(UserModel.class);
                    if (data.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        header_name.setText(data.getName());
                        header_number.setText(data.getNumber());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbTour = FirebaseDatabase.getInstance().getReference().child("Tour");

        dbTour.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TourModel data = dataSnapshot.getValue(TourModel.class);
                    if (data.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        titleTextView.setText(data.getTour_name());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}