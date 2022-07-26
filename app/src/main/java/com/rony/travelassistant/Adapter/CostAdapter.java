package com.rony.travelassistant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rony.travelassistant.Model.CostModel;
import com.rony.travelassistant.R;

import java.util.List;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.CostViewHolder> {

    Context context;
    List<CostModel> costModelList;

    public CostAdapter(Context context, List<CostModel> costModelList) {
        this.context = context;
        this.costModelList = costModelList;
    }

    @NonNull
    @Override
    public CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cost_details_layout, parent, false);
        return new CostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CostViewHolder holder, int position) {
        CostModel data = costModelList.get(position);
        holder.costReasonTextView.setText(data.getReason());
        holder.costDateTextView.setText(data.getDate());
        holder.costAmountTextView.setText(data.getAmount());
    }

    @Override
    public int getItemCount() {
        return costModelList.size();
    }

    public class CostViewHolder extends RecyclerView.ViewHolder {

        TextView costReasonTextView, costAmountTextView, costDateTextView;

        public CostViewHolder(@NonNull View itemView) {
            super(itemView);

            costReasonTextView = itemView.findViewById(R.id.costReasonTextView);
            costAmountTextView = itemView.findViewById(R.id.costAmountTextView);
            costDateTextView = itemView.findViewById(R.id.costDateTextView);
        }
    }

}
