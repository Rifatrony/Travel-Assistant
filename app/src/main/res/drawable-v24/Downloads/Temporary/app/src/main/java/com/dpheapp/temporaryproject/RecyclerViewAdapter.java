package com.dpheapp.temporaryproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    ApiResponseModel data;

    public RecyclerViewAdapter(Context context, ApiResponseModel data) {
        this.context = context;
        this.data = data;
    }

    public RecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder,parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (data.products.data.size() > 0) {

            holder.name.setText("Name: "+data.products.data.get(position).name);
            holder.price.setText("Price: "+data.products.data.get(position).price);
            holder.discountPrice.setText("Discount Price: "+data.products.data.get(position).discounted_price.toString());

            Glide.with(context).load(data.products.data.get(position).thumbnail).into(holder.imageView);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("position", holder.getAdapterPosition());
                    context.startActivity(intent);


                }
            });



        }


    }

    @Override
    public int getItemCount() {
        return data.products.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView name, price, discountPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            discountPrice = itemView.findViewById(R.id.disPrice);

        }
    }


}
