package com.rony.travelassistant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Adapter.TourListAdapter;
import com.rony.travelassistant.Model.TourIDModel;
import com.rony.travelassistant.Model.TourModel;
import com.rony.travelassistant.R;

import java.util.ArrayList;
import java.util.List;

public class RecentTourActivity extends AppCompatActivity {

    ImageView imageBack;
    TextView titleTextView;
    RecyclerView recentTourRecyclerView;
    TourListAdapter adapter;
    List<TourModel> tourModelList;

    DatabaseReference dbSpam, dbTour, dbTourID;
    public String tour_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_tour);

        imageBack = findViewById(R.id.imageBack);
        recentTourRecyclerView = findViewById(R.id.recentTourRecyclerView);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recentTourRecyclerView.setHasFixedSize(true);
        recentTourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourModelList = new ArrayList<>();
        adapter = new TourListAdapter(this, tourModelList);
        recentTourRecyclerView.setAdapter(adapter);

        dbSpam = FirebaseDatabase.getInstance().getReference().child("Spam").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dbTourID = FirebaseDatabase.getInstance().getReference().child("Spam").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tour ID");

        dbTourID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tourModelList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    TourIDModel model = dataSnapshot.getValue(TourIDModel.class);

                    assert model != null;
                    if (model.getTour_id() != null){

                        tour_id = model.getTour_id();
                        System.out.println("ID is ========== "+tour_id);


                        dbSpam.child(tour_id).child("Tour").addValueEventListener(new ValueEventListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                tourModelList.clear();

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    TourModel tourModel1 = dataSnapshot.getValue(TourModel.class);
                                    assert tourModel1 != null;
                                    if (tourModel1.getId() != null && tourModel1.getId().equals(tour_id)){

                                        tourModelList.add(tourModel1);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*dbSpam = FirebaseDatabase.getInstance().getReference().child("Spam").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dbSpam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TourModel tourModel = dataSnapshot.getValue(TourModel.class);

                    if (snapshot.exists()){
                        Toast.makeText(RecentTourActivity.this, (int) snapshot.getChildrenCount() + "", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        System.out.println("ID is ==========..... "+tour_id);

    }
}