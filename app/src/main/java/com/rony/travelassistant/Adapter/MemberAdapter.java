package com.rony.travelassistant.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Model.BalanceModel;
import com.rony.travelassistant.Model.MemberModel;
import com.rony.travelassistant.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    AppCompatButton saveButton;
    EditText nameEditText, numberEditText, amountEditText, dateEditText;
    TextView previousAmountTextView;
    DatabaseReference Balance;

    String new_value;
    double new_amount = 0, updated_amount;
    double total = 0;

    Calendar calendar;
    int year, month, day;

    Context context;
    List<MemberModel> memberModelList;

    public MemberAdapter() {
    }

    public MemberAdapter(Context context, List<MemberModel> memberModelList) {
        this.context = context;
        this.memberModelList = memberModelList;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_layout, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        MemberModel data = memberModelList.get(position);
        holder.nameTextView.setText(data.getName());
        holder.numberTextView.setText(data.getNumber());
        holder.amountTextView.setText(data.getAmount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new_amount = 0;

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.member_details_layout);
                dialog.show();

                previousAmountTextView = dialog.findViewById(R.id.totalAmountTextView);
                nameEditText = dialog.findViewById(R.id.nameEditText);
                numberEditText = dialog.findViewById(R.id.numberEditText);
                amountEditText = dialog.findViewById(R.id.amountEditText);
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
                                    /*THIS PART*/
                                    if (day < 10) {
                                        date = "0"+day+"/0"+month+"/"+year;
                                    }
                                }
                                else {
                                    date = day+"/"+month+"/"+year;

                                    /*THIS PART*/

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

                nameEditText.setText(data.getName());
                numberEditText.setText(data.getNumber());
                previousAmountTextView.setText(data.getAmount());

                new_value = amountEditText.getText().toString().trim();


                amountEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().isEmpty()){
                            updated_amount = Double.parseDouble(data.getAmount()) + Double.parseDouble(editable.toString());
                            System.out.println("Value is " + updated_amount);
                            previousAmountTextView.setText(String.valueOf(updated_amount));

                        }
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HashMap hashMap = new HashMap();
                        hashMap.put("amount", String.valueOf(updated_amount));
                        hashMap.put("date", dateEditText.getText().toString().trim());

                        DatabaseReference dbMember, dbBalance;
                        dbMember = FirebaseDatabase.getInstance().getReference().child("Member").child(data.getId());
                        dbBalance = FirebaseDatabase.getInstance().getReference().child("Balance");

                        new_amount = 0;

                        dbMember.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    String balance_id;
                                    balance_id = dbBalance.push().getKey();

                                    new_amount = new_amount + Double.parseDouble(data.getAmount());

                                    System.out.println("New amount is " + updated_amount);

                                    DatabaseReference databaseReference;

                                    databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            total = 0;

                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                BalanceModel model = dataSnapshot.getValue(BalanceModel.class);

                                                System.out.println("Balance is " + model
                                                        .getAmount());

                                                total = updated_amount + Double.parseDouble(model.getAmount());

                                                System.out.println("Total is " + total);

                                                HashMap balance = new HashMap();
                                                balance.put("amount", String.valueOf(total));
                                                balance.put("balance_id", balance_id);
                                                balance.put("tour_id", data.getTour_id());
                                                balance.put("added_by_id", FirebaseAuth.getInstance().getCurrentUser().getUid());


                                                dbBalance.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child(data.getTour_id()).updateChildren(balance).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else {
                                                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });




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

        TextView nameTextView, numberTextView, amountTextView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);

        }
    }

}
