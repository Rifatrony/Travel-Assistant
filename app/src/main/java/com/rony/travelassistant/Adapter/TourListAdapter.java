package com.rony.travelassistant.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rony.travelassistant.Model.TourModel;
import com.rony.travelassistant.R;

import java.util.List;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.TourListViewHolder> {

    Context context;
    List<TourModel> tourModelList;

    public TourListAdapter() {
    }

    public TourListAdapter(Context context, List<TourModel> tourModelList) {
        this.context = context;
        this.tourModelList = tourModelList;
    }

    @NonNull
    @Override
    public TourListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tour_list_layout, parent, false);
        return new TourListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tourNameTextView.setText(tourModelList.get(position).getTour_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, tourModelList.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourModelList.size();
    }

    public class TourListViewHolder extends RecyclerView.ViewHolder {

        TextView tourNameTextView;

        public TourListViewHolder(@NonNull View itemView) {
            super(itemView);
            tourNameTextView = itemView.findViewById(R.id.tourNameTextView);
        }
    }

}
