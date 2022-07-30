package com.rony.travelassistant.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rony.travelassistant.Api.RetrofitClient;
import com.rony.travelassistant.Model.DistrictsResponseModel;
import com.rony.travelassistant.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView1, cardView2, cardView3, cardView4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initialization();
        setListener();



    }

    private void initialization() {
        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);
    }

    private void setListener(){
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView1:
                startActivity(new Intent(getApplicationContext(), DistrictListActivity.class));
                break;
            case R.id.cardView2:
                startActivity(new Intent(getApplicationContext(), FavouritePlaceActivity.class));
                break;

            case R.id.cardView3:
                showToast("ভ্রমন প্ল্যান করুন");
                break;

            case R.id.cardView4:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;



        }
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}