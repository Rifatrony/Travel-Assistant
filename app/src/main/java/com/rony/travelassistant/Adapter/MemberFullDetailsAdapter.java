package com.rony.travelassistant.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rony.travelassistant.Model.MemberModel;
import com.rony.travelassistant.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MemberFullDetailsAdapter extends RecyclerView.Adapter<MemberFullDetailsAdapter.MemberViewHolder> {

    AppCompatButton saveButton;
    EditText nameEditText, numberEditText, amountEditText, dateEditText;
    TextView previousAmountTextView,perPersonCostTextView, giveOrTakeAmountTextView, giveOrTakeAmountTextViewLabel;

    String new_value;
    int new_amount, updated_amount;

    Calendar calendar;
    int year, month, day;
    double perPersonCost, perPersonCostDialog, givenAmount, givenAmountDialog, extraAmount = 0, extraAmountDialog = 0;
    double extra = 0;

    Context context;
    List<MemberModel> memberModelList;

    public MemberFullDetailsAdapter() {
    }

    public MemberFullDetailsAdapter(Context context, List<MemberModel> memberModelList) {
        this.context = context;
        this.memberModelList = memberModelList;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_all_details_layout, parent, false);
        return new MemberViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        MemberModel data = memberModelList.get(position);
        if (!data.getPer_person_cost().isEmpty()){
            perPersonCost = Double.parseDouble(data.getPer_person_cost());
        }
        else {

        }
        givenAmount = Double.parseDouble(data.getAmount());
        holder.nameTextView.setText(data.getName());
        holder.numberTextView.setText(data.getNumber());
        holder.amountTextView.setText(": "+givenAmount +" Tk.");
        holder.perPersonCostTextView.setText(": "+ perPersonCost +" Tk.");


        if (givenAmount > perPersonCost){
            holder.giveOrTakeTextView.setText("Take Back");
            holder.giveOrTakeTextView.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.extraAmountTextView.setTextColor(context.getResources().getColor(R.color.yellow));
            extraAmount = givenAmount - perPersonCost;
            holder.extraAmountTextView.setText(": " + extraAmount + " Tk.");
        }
        else if (givenAmount < perPersonCost){
            extraAmount = perPersonCost - givenAmount;
            holder.extraAmountTextView.setText(": " + extraAmount + " Tk.");
        }
        else if (givenAmount == perPersonCost){
            holder.extraAmountTextView.setText(": Fully Paid");
            holder.giveOrTakeTextView.setText("Status");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, data.getId(), Toast.LENGTH_SHORT).show();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.give_or_take_money_layout);
                dialog.show();

                previousAmountTextView = dialog.findViewById(R.id.totalAmountTextView);
                nameEditText = dialog.findViewById(R.id.nameEditText);
                numberEditText = dialog.findViewById(R.id.numberEditText);
                amountEditText = dialog.findViewById(R.id.amountEditText);
                perPersonCostTextView = dialog.findViewById(R.id.perPersonCostTextView);
                giveOrTakeAmountTextView = dialog.findViewById(R.id.giveOrTakeAmountTextView);
                giveOrTakeAmountTextViewLabel = dialog.findViewById(R.id.giveOrTakeAmountTextViewLabel);
                dateEditText = dialog.findViewById(R.id.dateEditText);
                saveButton = dialog.findViewById(R.id.saveButton);

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                dateEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                String date;
                                month = month +1;

                                if (month < 10){
                                    date = day+"/0"+month+"/"+year;

                                    if (day < 10) {
                                        date = "0"+day+"/0"+month+"/"+year;
                                    }
                                }
                                else {
                                    date = day+"/"+month+"/"+year;

                                    if (day < 10) {
                                        date = "0"+day+"/0"+month+"/"+year;
                                    }
                                }
                                dateEditText.setText(date);
                            }
                        },year,month,day);

                        datePickerDialog.show();
                    }
                });


                perPersonCostDialog = Double.parseDouble(data.getPer_person_cost());
                givenAmountDialog = Double.parseDouble(data.getAmount());

                nameEditText.setText(data.getName());
                numberEditText.setText(data.getNumber());
                previousAmountTextView.setText(String.valueOf(givenAmountDialog));
                perPersonCostTextView.setText(String.valueOf(perPersonCostDialog));

                new_value = amountEditText.getText().toString().trim();

                System.out.println("Given amount " + data.getAmount());
                System.out.println("Per person cost amount " + perPersonCost);

                if (givenAmountDialog > perPersonCostDialog){

                    giveOrTakeAmountTextView.setTextColor(context.getResources().getColor(R.color.white));
                    giveOrTakeAmountTextViewLabel.setText("Get Back ");

                    extraAmountDialog = givenAmountDialog - perPersonCostDialog;
                    giveOrTakeAmountTextView.setText(String.valueOf(extraAmountDialog));
                    System.out.println(String.valueOf(extraAmountDialog));

                    amountEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            extra = 0;
                            if (!editable.toString().isEmpty()){
                                extra = extraAmountDialog - Double.parseDouble(editable.toString());
                                System.out.println("Extra is : " + extra);
                                giveOrTakeAmountTextView.setText(String.valueOf(extra));

                                double decrease = givenAmountDialog - Double.parseDouble(editable.toString());
                                previousAmountTextView.setText(String.valueOf(decrease));

                            }
                        }
                    });
                }

                else if (givenAmountDialog < perPersonCostDialog){

                    extraAmountDialog = perPersonCostDialog - givenAmountDialog;
                    giveOrTakeAmountTextView.setText(String.valueOf(extraAmountDialog));

                    amountEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (!editable.toString().isEmpty()){
                                extra = givenAmountDialog + Double.parseDouble(editable.toString());
                                System.out.println("Extra is : " + extra);
                                previousAmountTextView.setText(String.valueOf(extra));

                                double decrease = extraAmountDialog - Double.parseDouble(editable.toString());
                                giveOrTakeAmountTextView.setText(String.valueOf(decrease));

                            }
                        }
                    });
                }

                else if (givenAmountDialog ==  perPersonCostDialog){
                    giveOrTakeAmountTextViewLabel.setText("Status is");
                    giveOrTakeAmountTextView.setText("Paid");

                }

               /* amountEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().isEmpty()){
                            *//*updated_amount = Integer.parseInt(data.getAmount()) + Integer.parseInt(amountEditText.getText().toString());
                            System.out.println("Value is " + updated_amount);
                            previousAmountTextView.setText(""+ updated_amount);*//*

                        }
                    }
                });*/

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HashMap hashMap = new HashMap();
                        hashMap.put("amount", previousAmountTextView.getText().toString());
                        hashMap.put("date", dateEditText.getText().toString().trim());

                        DatabaseReference dbMember;
                        dbMember = FirebaseDatabase.getInstance().getReference().child("Member").child(data.getId());

                        dbMember.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return memberModelList.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, numberTextView, amountTextView,perPersonCostTextView, extraAmountTextView, giveOrTakeTextView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            perPersonCostTextView = itemView.findViewById(R.id.perPersonCostTextView);
            extraAmountTextView = itemView.findViewById(R.id.extraAmountTextView);
            giveOrTakeTextView = itemView.findViewById(R.id.giveOrTakeTextView);

        }
    }

}
