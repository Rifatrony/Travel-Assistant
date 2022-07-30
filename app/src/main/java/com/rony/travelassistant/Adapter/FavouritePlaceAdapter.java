package com.rony.travelassistant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    public void onBindViewHolder(@NonNull FavouritePlaceViewHolder holder, int position) {
        Glide.with(context).load(favouritePlaceModelList.get(position).getImage_link()).into(holder.imageView);
        holder.placeNameTextView.setText(favouritePlaceModelList.get(position).getPlace_name());
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
