package com.rony.travelassistant.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rony.travelassistant.Activity.DistrictWisePlaceActivity;
import com.rony.travelassistant.Model.DistrictsResponseModel;
import com.rony.travelassistant.R;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {

    Context context;
    DistrictsResponseModel districtsResponseModel;

    public DistrictAdapter() {
    }

    public DistrictAdapter(Context context, DistrictsResponseModel districtsResponseModel) {
        this.context = context;
        this.districtsResponseModel = districtsResponseModel;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.district_layoyt, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.districtNameTextView.setText(districtsResponseModel.data.get(position).districtbn);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DistrictWisePlaceActivity.class);
                intent.putExtra("district_name", districtsResponseModel.data.get(position).districtbn);
                context.startActivity(intent);
                Toast.makeText(context, districtsResponseModel.data.get(position).districtbn, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return districtsResponseModel.data.size();
    }

    public class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView districtNameTextView;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);

            districtNameTextView = itemView.findViewById(R.id.districtNameTextView);

        }
    }

}
