package com.rony.travelassistant.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rony.travelassistant.Adapter.DistrictAdapter;
import com.rony.travelassistant.Api.RetrofitClient;
import com.rony.travelassistant.Model.DistrictsResponseModel;
import com.rony.travelassistant.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictListActivity extends AppCompatActivity {

    RecyclerView districtRecyclerView;
    DistrictsResponseModel model;
    ArrayList<DistrictsResponseModel> responseModelArrayList;
    DistrictAdapter adapter;

    ProgressBar progressBar;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);

        imageBack = findViewById(R.id.imageBack);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        districtRecyclerView = findViewById(R.id.districtRecyclerView);
        districtRecyclerView.setHasFixedSize(true);
        districtRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        responseModelArrayList = new ArrayList<>();


        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getDistrict().enqueue(new Callback<DistrictsResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DistrictsResponseModel> call, Response<DistrictsResponseModel> response) {
                responseModelArrayList.clear();
                if (response.body() != null){
                    progressBar.setVisibility(View.INVISIBLE);
                    model = response.body();
                    adapter = new DistrictAdapter(DistrictListActivity.this, model);
                    districtRecyclerView.setAdapter(adapter);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DistrictsResponseModel> call, Throwable t) {
                showToast(t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}