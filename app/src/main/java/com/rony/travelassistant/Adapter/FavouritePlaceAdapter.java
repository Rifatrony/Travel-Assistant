package com.rony.travelassistant.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rony.travelassistant.Activity.FavouritePlaceActivity;
import com.rony.travelassistant.Activity.FavouritePlaceDetailsActivity;
import com.rony.travelassistant.Model.FavouritePlaceModel;
import com.rony.travelassistant.R;

import java.util.List;

public class FavouritePlaceAdapter extends RecyclerView.Adapter<FavouritePlaceAdapter.FavouritePlaceViewHolder> {

    Context context;
    List<FavouritePlaceModel> favouritePlaceModelList;

    public FavouritePlaceAdapter() {
    }

    public FavouritePlaceAdapter(Context context, List<FavouritePlaceModel> favouritePlaceModelList) {
        this.context = context;
        this.favouritePlaceModelList = favouritePlaceModelList;
    }

    @NonNull
    @Override
    public FavouritePlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.favourite_place_layout, parent, false);
        return new FavouritePlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritePlaceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(favouritePlaceModelList.get(position).getImage_link()).into(holder.imageView);
        holder.placeNameTextView.setText(favouritePlaceModelList.get(position).getPlace_name());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FavouritePlaceDetailsActivity.class);
                intent.putExtra("place_name", favouritePlaceModelList.get(position).getPlace_name());
                context.startActivity(intent);
                Toast.makeText(context, favouritePlaceModelList.get(position).getPlace_name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouritePlaceModelList.size();
    }

    public class FavouritePlaceViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView placeNameTextView;

        public FavouritePlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            placeNameTextView = itemView.findViewById(R.id.placeNameTextView);

        }
    }

}
