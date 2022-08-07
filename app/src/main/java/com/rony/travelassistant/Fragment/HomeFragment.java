package com.rony.travelassistant.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Activity.MembersActivity;
import com.rony.travelassistant.Adapter.CostAdapter;
import com.rony.travelassistant.Adapter.MemberAdapter;
import com.rony.travelassistant.Model.BalanceModel;
import com.rony.travelassistant.Model.CostModel;
import com.rony.travelassistant.Model.MemberListModel;
import com.rony.travelassistant.Model.MemberModel;
import com.rony.travelassistant.Model.TourModel;
import com.rony.travelassistant.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView createTourTextView, deleteTourTextView, titleTextView;

    ImageView addCostImageView;

    Calendar calendar;
    int year, month, day;

    EditText tourNameEditText, tourStartDateEditText, tourEndDateEditText;

    EditText costAmountEditText, costDateEditText, costReasonEditText;

    AppCompatButton saveTourButton, addCostButton;

    TextView startDateTextView, endDateTextView, totalMemberTextView, totalBalanceTextView, memberListTextView,
            costListTextView, totalCostTextView, perPersonCostTextView, remainingBalanceTextView, remainingAmountTextView;

    String tour_name, start_date, end_date;
    String cost_amount, cost_date, cost_reason;

    public String id, tour_id, member_id;
    int totalMember = 0, total_cost = 0;

    double total_balanceInt = 0, remaining_balance = 0;
    double perPersonCost, total_balance = 0, remain_balance = 0;
    double remaining_amount = 0;


    RecyclerView memberListRecyclerView, costListRecyclerView;
    MemberAdapter memberAdapter;
    List<MemberModel> memberModelList;

    CostAdapter costAdapter;
    List<CostModel> costModelList;


    DatabaseReference dbTour, dbMember, dbMemberList, dbCost, dbBalance, dbGetTour, dbTourID;

    DatabaseReference dbSpam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initialization();
        setListener();
        fetchTourName();
        FetchMemberList();
        FetchTotalMember();
        FetchCostList();


        //FetchTotalBalance();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dbTour.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        TourModel tourModel = dataSnapshot.getValue(TourModel.class);

                        // if found my uid and if i create any tour

                        assert tourModel != null;
                        if (tourModel.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            if (tourModel.getUid()!= null){
                                deleteTourTextView.setVisibility(View.VISIBLE);
                                createTourTextView.setVisibility(View.INVISIBLE);
                            }

                        }
                        // if not found my uid but other create some tour
                        else{
                            createTourTextView.setVisibility(View.VISIBLE);
                            deleteTourTextView.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                else {
                    createTourTextView.setVisibility(View.VISIBLE);
                    deleteTourTextView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (totalCostTextView.getText().toString().isEmpty()){

            dbMember.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    remain_balance = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        MemberModel data = dataSnapshot.getValue(MemberModel.class);
                        assert data != null;
                        if (data.getAdded_by_uid()!=null && data.getAdded_by_uid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            remain_balance += Double.parseDouble(data.getAmount());
                            remainingBalanceTextView.setText(String.valueOf(remain_balance));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        else {

        }

        return view;
    }


    private void fetchTourName() {
        dbTour.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TourModel data = dataSnapshot.getValue(TourModel.class);
                    assert data != null;
                    if (data.getUid()!= null && data.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        titleTextView.setText(data.getTour_name());
                        memberListTextView.setText(data.getTour_name() +" (Member List)");
                        costListTextView.setText(data.getTour_name() +" (Cost List)");
                        startDateTextView.setText(data.getStart_date());
                        endDateTextView.setText(data.getEnd_date());
                        tour_id = data.getId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void FetchTotalBalance(){

        dbBalance.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //remain_balance = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    BalanceModel data = dataSnapshot.getValue(BalanceModel.class);

                    if (!data.getTour_id().isEmpty()){
                        //remain_balance  += Double.parseDouble(data.getAmount());
                        //totalBalanceTextView.setText(total_balance +" Tk.");
                        remainingBalanceTextView.setText(String.valueOf(data.getAmount()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void FetchMemberList() {
        memberListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        memberListRecyclerView.setHasFixedSize(true);
        memberModelList = new ArrayList<>();
        memberAdapter = new MemberAdapter(getContext(), memberModelList);
        memberListRecyclerView.setAdapter(memberAdapter);

        dbMember.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                memberModelList.clear();
                total_balance = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MemberModel data = dataSnapshot.getValue(MemberModel.class);

                    assert data != null;
                    if (data.getAdded_by_uid() != null && data.getAdded_by_uid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        memberModelList.add(data);
                        total_balance  += Double.parseDouble(data.getAmount());
                        totalBalanceTextView.setText(total_balance +" Tk.");
                        member_id = data.getId();
                    }
                }
                memberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void FetchCostList() {

        costListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        costListRecyclerView.setHasFixedSize(true);
        costModelList =  new ArrayList<>();
        costAdapter = new CostAdapter(getContext(), costModelList);
        costListRecyclerView.setAdapter(costAdapter);

        dbCost.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                costModelList.clear();
                total_cost = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    CostModel data = dataSnapshot.getValue(CostModel.class);

                    assert data != null;
                    if (data.getUid() != null && data.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                        costModelList.add(data);
                        total_cost += Integer.parseInt(data.getAmount());
                        totalCostTextView.setText(total_cost+" Tk.");

                        dbMemberList.addValueEventListener(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                totalMember = 0 ;
                                if (snapshot.exists()){

                                    totalMember = (int) snapshot.getChildrenCount();

                                    System.out.println("totalMember" + totalMember);
                                    perPersonCost = total_cost/totalMember;
                                    perPersonCostTextView.setText(perPersonCost+" Tk.");

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        dbMember.addValueEventListener(new ValueEventListener() {
                            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                remaining_balance = 0;

                                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    MemberModel data = dataSnapshot.getValue(MemberModel.class);

                                    assert data != null;
                                    if (data.getAdded_by_uid() != null && data.getAdded_by_uid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                                        total_balanceInt += Double.parseDouble(data.getAmount());

                                        remaining_balance = total_balance - total_cost;
                                        remainingBalanceTextView.setText(String.valueOf(remaining_balance));
                                        System.out.println("remaining_balance" + total_balance);

                                        HashMap hashMap = new HashMap();
                                        hashMap.put("per_person_cost", String.valueOf(perPersonCost));
                                        hashMap.put("remaining_balance", String.valueOf(remaining_balance));

                                        dbMember.child(data.getId()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful()){

                                                }else {
                                                    showToast(task.getException().toString());
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
                costAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void FetchTotalMember() {
        dbMemberList.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    totalMember = (int) snapshot.getChildrenCount();
                    totalMemberTextView.setText(String.valueOf(totalMember));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialization() {

        dbBalance = FirebaseDatabase.getInstance().getReference().child("Balance")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dbMember = FirebaseDatabase.getInstance().getReference().child("Member");
        dbTour = FirebaseDatabase.getInstance().getReference().child("Tour");
        dbTourID = FirebaseDatabase.getInstance().getReference().child("Tour ID");
        dbGetTour = FirebaseDatabase.getInstance().getReference();
        dbCost = FirebaseDatabase.getInstance().getReference().child("Cost");
        dbMemberList = FirebaseDatabase.getInstance().getReference().child("Member List").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dbSpam = FirebaseDatabase.getInstance().getReference().child("Spam");

        createTourTextView = view.findViewById(R.id.createTourTextView);
        deleteTourTextView = view.findViewById(R.id.deleteTourTextView);
        addCostImageView = view.findViewById(R.id.addCostImageView);

        titleTextView = view.findViewById(R.id.titleTextView);
        totalMemberTextView = view.findViewById(R.id.totalMemberTextView);
        startDateTextView = view.findViewById(R.id.startDateTextView);
        endDateTextView = view.findViewById(R.id.endDateTextView);
        totalBalanceTextView = view.findViewById(R.id.totalBalanceTextView);
        memberListTextView = view.findViewById(R.id.memberListTextView);
        costListTextView = view.findViewById(R.id.costListTextView);
        totalCostTextView = view.findViewById(R.id.totalCostTextView);
        perPersonCostTextView = view.findViewById(R.id.perPersonCostTextView);
        remainingBalanceTextView = view.findViewById(R.id.remainingBalanceTextView);

        memberListRecyclerView = view.findViewById(R.id.memberListRecyclerView);
        costListRecyclerView = view.findViewById(R.id.costListRecyclerView);

    }

    private void setListener(){
        createTourTextView.setOnClickListener(this);
        deleteTourTextView.setOnClickListener(this);
        addCostImageView.setOnClickListener(this);
        memberListTextView.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createTourTextView:
                CreateTour();
                break;

            case R.id.deleteTourTextView:
                ClearTourDetails();
                break;

            case R.id.addCostImageView:

                if (!remainingBalanceTextView.getText().toString().isEmpty()){
                    AddCost();
                }
                else {
                    showToast("You have no balance");
                }
                break;

            case R.id.memberListTextView:
                startActivity(new Intent(getContext(), MembersActivity.class));
                break;
        }
    }

    private void ClearTourDetails() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Click 'YES' to delete all the record of this tour.")
                .setCancelable(false)
                .setIcon(R.drawable.button)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("TT ===========>"+tour_id);
                        showToast(tour_id);

                        dbMemberList.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    MemberListModel memberListModel = dataSnapshot.getValue(MemberListModel.class);
                                    if (memberListModel.getTour_id().equals(tour_id)){
                                        dbMemberList.child(memberListModel.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    HashMap hashMap = new HashMap();
                                                    hashMap.put("id", memberListModel.getId());
                                                    hashMap.put("tour_id", memberListModel.getTour_id());
                                                    hashMap.put("tour_name", memberListModel.getTour_name());
                                                    hashMap.put("uid", memberListModel.getUid());

                                                    dbSpam.child(memberListModel.getUid()).child(memberListModel.getTour_id())
                                                            .child("Member List").child(memberListModel.getUid())
                                                            .child(memberListModel.getId()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if (task.isSuccessful()){

                                                                    }else {

                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        dbMember.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    MemberModel memberModel = dataSnapshot.getValue(MemberModel.class);
                                    assert memberModel != null;
                                    if (memberModel.getTour_id() != null && memberModel.getTour_id().equals(tour_id)){
                                        dbMember.child(memberModel.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    HashMap hashMap = new HashMap();
                                                    hashMap.put("added_by_uid", memberModel.getAdded_by_uid());
                                                    hashMap.put("amount", memberModel.getAmount());
                                                    hashMap.put("date", memberModel.getDate());
                                                    hashMap.put("email", memberModel.getEmail());
                                                    hashMap.put("id", memberModel.getId());
                                                    hashMap.put("name", memberModel.getName());
                                                    hashMap.put("number", memberModel.getNumber());
                                                    hashMap.put("per_person_cost", memberModel.getPer_person_cost());
                                                    hashMap.put("tour_id", memberModel.getTour_id());
                                                    hashMap.put("tour_name", memberModel.getTour_name());


                                                    dbSpam.child(memberModel.getAdded_by_uid()).child(memberModel.getTour_id())
                                                            .child("Member").child(memberModel.getId())
                                                            .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if (task.isSuccessful()){
                                                                        showToast("Success");
                                                                    }
                                                                    else {
                                                                        showToast(task.getException().toString());
                                                                    }
                                                                }
                                                            });
                                                }
                                                else {
                                                    showToast(task.getException().toString());
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        dbCost.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    CostModel costModel = dataSnapshot.getValue(CostModel.class);
                                    if (costModel.getTour_id().equals(tour_id)){
                                        dbCost.child(costModel.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                    HashMap hashMap = new HashMap();
                                                    hashMap.put("amount", costModel.getAmount());
                                                    hashMap.put("date", costModel.getDate());
                                                    hashMap.put("id", costModel.getId());
                                                    hashMap.put("reason", costModel.getReason());
                                                    hashMap.put("tour_id", costModel.getTour_id());
                                                    hashMap.put("uid", costModel.getUid());

                                                    dbSpam.child(costModel.getUid()).child(costModel.getTour_id())
                                                            .child("Cost").child(costModel.getId())
                                                            .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()){
                                                                showToast("Remove and save to SPAM");
                                                            }else {
                                                                showToast(task.getException().toString());
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        dbTour.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    TourModel tourModel = dataSnapshot.getValue(TourModel.class);
                                    if (tourModel.getId().equals(tour_id)){
                                        dbTour.child(tour_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    HashMap hashMap = new HashMap();
                                                    hashMap.put("end_date", tourModel.getEnd_date());
                                                    hashMap.put("start_date", tourModel.getStart_date());
                                                    hashMap.put("id", tourModel.getId());
                                                    hashMap.put("tour_name", tourModel.getTour_name());
                                                    hashMap.put("uid", tourModel.getUid());

                                                    dbSpam.child(tourModel.getUid()).child(tourModel.getId())
                                                            .child("Tour").child(tourModel.getId())
                                                            .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()){
                                                                //showToast("Success");

                                                                String t_id;
                                                                t_id = dbTourID.push().getKey();

                                                                HashMap hashMap1 = new HashMap();
                                                                hashMap1.put("tour_id", tourModel.getId());
                                                                hashMap1.put("t_id", t_id);
                                                                dbSpam.child(tourModel.getUid()).child("Tour ID").child(t_id).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task task) {
                                                                        if (task.isSuccessful()){
                                                                            showToast("Added");
                                                                        }
                                                                    }
                                                                });

                                                            }
                                                            else {
                                                                showToast(task.getException().toString());
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Are you sure you want to Clear This Tour?");
        alert.show();
        //alertDialog = new AlertDialog(this);
    }


    private void CreateTour() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.create_tour_layout);
        dialog.show();

        tourNameEditText = dialog.findViewById(R.id.tourNameEditText);
        tourStartDateEditText = dialog.findViewById(R.id.tourStartDateEditText);
        tourEndDateEditText = dialog.findViewById(R.id.tourEndDateEditText);

        saveTourButton = dialog.findViewById(R.id.saveTourButton);

        tourStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        tourStartDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        tourEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        tourEndDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        saveTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tour_name = tourNameEditText.getText().toString().trim();
                start_date = tourStartDateEditText.getText().toString().trim();
                end_date = tourEndDateEditText.getText().toString().trim();

                if (tour_name.isEmpty()){
                    showToast("Enter Name");
                    return;
                }
                if (start_date.isEmpty()){
                    showToast("Enter Date");
                    return;
                }
                if (end_date.isEmpty()){
                    showToast("Enter End Date");
                    return;
                }
                else {
                    //add data to the firebase
                    // when create tour add yourself into that tour automatic
                    //AddTour();

                    id = dbTour.push().getKey();

                    HashMap hashMap = new HashMap();
                    hashMap.put("id", id);
                    hashMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    hashMap.put("tour_name", tour_name);
                    hashMap.put("start_date", start_date);
                    hashMap.put("end_date", end_date);

                    dbTour.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                showToast("Tour created successfully");
                                dialog.dismiss();

                            }
                            else {
                                dialog.dismiss();
                                showToast(task.getException().toString());
                            }
                        }
                    });
                }

            }
        });

    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void AddCost() {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_cost_layout);
        dialog.show();

        remainingAmountTextView = dialog.findViewById(R.id.remainingAmountTextView);
        costAmountEditText = dialog.findViewById(R.id.costAmountEditText);
        costDateEditText = dialog.findViewById(R.id.costDateEditText);
        costReasonEditText = dialog.findViewById(R.id.costReasonEditText);
        addCostButton = dialog.findViewById(R.id.addCostButton);

        remaining_amount = Double.parseDouble(remainingBalanceTextView.getText().toString());

        remainingAmountTextView.setText(String.valueOf(remaining_amount));

        costAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    double rest_remaining_amount = remaining_amount - Double.parseDouble(editable.toString());
                    System.out.println("\n\n" + rest_remaining_amount);
                    remainingAmountTextView.setText(String.valueOf(rest_remaining_amount));

                    if (remaining_amount < Double.parseDouble(editable.toString())){
                        showToast("You don't have sufficient balance");
                        remainingAmountTextView.setText("You don't have sufficient balance");
                        return;
                    }
                    else if (editable.toString().equals("0")){
                        showToast("Invalid");
                        remainingAmountTextView.setText("Invalid");
                        return;
                    }

                    else if (Double.parseDouble(editable.toString()) < 0){
                        showToast("Invalid");
                        remainingAmountTextView.setText("Invalid");
                        return;
                    }


                }
            }
        });

        costDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        costDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        addCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cost_amount = costAmountEditText.getText().toString().trim();
                cost_date = costDateEditText.getText().toString().trim();
                cost_reason = costReasonEditText.getText().toString().trim();

                System.out.println("Tour ID ....................." + tour_id);

                if (cost_amount.isEmpty()){
                    showToast("Enter Amount");
                    return;
                }

                if (cost_date.isEmpty()){
                    showToast("Enter Cost Date");
                    return;
                }

                if (cost_reason.isEmpty()){
                    showToast("Enter Cost Reason");
                    return;
                }

                else {

                    String id;
                    id = dbCost.push().getKey();

                    HashMap hashMap = new HashMap();
                    hashMap.put("amount", cost_amount);
                    hashMap.put("date", cost_date);
                    hashMap.put("id", id);
                    hashMap.put("reason", cost_reason);
                    hashMap.put("tour_id", tour_id);
                    hashMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                    //showToast(tour_name);

                    dbCost.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                showToast("New Cost Added");
                                dialog.dismiss();

                                dbBalance.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                                            BalanceModel data = dataSnapshot.getValue(BalanceModel.class);
                                                if (!data.getTour_id().isEmpty()){
                                                    HashMap balance = new HashMap();
                                                    balance.put("amount", String.valueOf(remainingAmountTextView.getText().toString()));

                                                    dbBalance.child(data.getTour_id()).updateChildren(balance).addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()){
                                                                showToast("Balance Updated");
                                                            }
                                                            else {
                                                                showToast(task.getException().toString());
                                                            }
                                                        }
                                                    });
                                                }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }
                    });
                }
            }
        });

    }

}
