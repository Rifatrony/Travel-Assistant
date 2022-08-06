package com.dpheapp.temporaryproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface;
    public static ApiResponseModel apiResponseData;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        apiInterface.getData().enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {

                if (response.body() != null) {


                    apiResponseData = response.body();
                    adapter = new RecyclerViewAdapter(MainActivity.this, apiResponseData);
                    recyclerView.setAdapter(adapter);


                }



            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {

            }
        });









    }
}