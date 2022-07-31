package com.rony.travelassistant.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rony.travelassistant.Adapter.FavouritePlaceAdapter;
import com.rony.travelassistant.Model.FavouritePlaceModel;
import com.rony.travelassistant.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FavouritePlaceActivity extends AppCompatActivity {

    ImageView imageBack;
    ProgressBar progressBar;

    RecyclerView favouritePlaceRecyclerView;
    FavouritePlaceAdapter favouritePlaceAdapter;
    List<FavouritePlaceModel> favouritePlaceModelList;

    Dialog dialog;

    FloatingActionButton fab;
    ImageView imageView;
    TextView selectImageTextView;
    AppCompatButton saveFavouritePlaceButton;
    private static final int SELECT_IMAGE_CODE = 1;
    Uri uri;

    EditText placeNameEditText, placeHistoryEditText, whenToTravelEditText, howToTravelEditText, whereToStayEditText, estimateTourCostEditText;

    String place_name, place_details, travel_time, how_to_travel, where_to_stay, estimate_cost;

    DatabaseReference dbPicture;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_place);

        dialog = new Dialog(FavouritePlaceActivity.this);
        imageBack = findViewById(R.id.imageBack);
        progressBar = findViewById(R.id.progressBar);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = format.format(now);


        dbPicture = FirebaseDatabase.getInstance().getReference().child("Picture");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images/"+fileName);

        favouritePlaceRecyclerView= findViewById(R.id.favouritePlaceRecyclerView);

        favouritePlaceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favouritePlaceRecyclerView.setHasFixedSize(true);
        favouritePlaceModelList =  new ArrayList<>();
        favouritePlaceAdapter = new FavouritePlaceAdapter(this, favouritePlaceModelList);
        favouritePlaceRecyclerView.setAdapter(favouritePlaceAdapter);

        progressBar.setVisibility(View.VISIBLE);

        dbPicture.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favouritePlaceModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    progressBar.setVisibility(View.INVISIBLE);
                    FavouritePlaceModel data = dataSnapshot.getValue(FavouritePlaceModel.class);
                    favouritePlaceModelList.add(data);
                }
                favouritePlaceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        fab = findViewById(R.id.fab);
        imageView = findViewById(R.id.imageView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setContentView(R.layout.add_favourite_place_layout);
                dialog.show();

                imageView = dialog.findViewById(R.id.imageView);
                selectImageTextView = dialog.findViewById(R.id.selectImageTextView);
                placeNameEditText = dialog.findViewById(R.id.placeNameEditText);
                placeHistoryEditText = dialog.findViewById(R.id.placeHistoryEditText);
                whenToTravelEditText = dialog.findViewById(R.id.whenToTravelEditText);
                howToTravelEditText = dialog.findViewById(R.id.howToTravelEditText);
                whereToStayEditText = dialog.findViewById(R.id.whereToStayEditText);
                estimateTourCostEditText = dialog.findViewById(R.id.estimateTourCostEditText);
                saveFavouritePlaceButton = dialog.findViewById(R.id.saveFavouritePlaceButton);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);

                    }
                });

                saveFavouritePlaceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        place_name = placeNameEditText.getText().toString().trim();
                        place_details = placeHistoryEditText.getText().toString().trim();
                        travel_time = whenToTravelEditText.getText().toString().trim();
                        how_to_travel = howToTravelEditText.getText().toString().trim();
                        where_to_stay = whereToStayEditText.getText().toString().trim();
                        estimate_cost = estimateTourCostEditText.getText().toString().trim();

                        if (place_name.isEmpty()){
                            showToast("Enter PLace Name");
                            return;
                        }

                        if (place_details.isEmpty()){
                            showToast("Enter PLace Details");
                            return;
                        }

                        if (travel_time.isEmpty()){
                            showToast("Enter Time");
                            return;
                        }

                        if (how_to_travel.isEmpty()){
                            showToast("Enter Travel Way");
                            return;
                        }


                        if (where_to_stay.isEmpty()){
                            showToast("Enter Hotel Details");
                            return;
                        }

                        if (estimate_cost.isEmpty()){
                            showToast("Enter Approximate cost");
                            return;
                        }

                        else {
                            AddPlace();
                        }


                    }
                });



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null){
            uri = data.getData();
            imageView.setImageURI(uri);
            selectImageTextView.setVisibility(View.INVISIBLE);

        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void AddPlace(){
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(FavouritePlaceActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String id = dbPicture.push().getKey();

                        String url = String.valueOf(uri);
                        HashMap hashMap = new HashMap();
                        hashMap.put("image_link", url);
                        hashMap.put("id", id);
                        hashMap.put("place_name", place_name);
                        hashMap.put("place_details", place_details);
                        hashMap.put("travel_time", travel_time);
                        hashMap.put("how_to_travel", how_to_travel);
                        hashMap.put("where_to_stay", where_to_stay);
                        hashMap.put("estimate_cost", estimate_cost);

                        dbPicture.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FavouritePlaceActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        });
    }
}