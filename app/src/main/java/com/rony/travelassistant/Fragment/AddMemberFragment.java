package com.rony.travelassistant.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Model.TourModel;
import com.rony.travelassistant.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AddMemberFragment extends Fragment {

    View view;

    Spinner selectTourSpinner;
    EditText nameEditText, numberEditText, emailEditText;
    AppCompatButton addMemberButton;

    String tour_name, name, number, email, uid, id;

    ArrayList<String> tourNameList;
    ArrayList<String> tourUidList;
    ArrayAdapter<String> tourNameAdapter;
    String tourUid = null;

    DatabaseReference dbTour, dbMember, dbMemberList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_member, container, false);

        initialization();

        tourNameList = new ArrayList<>();
        tourUidList = new ArrayList<>();

        fetchTourName();

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                number = numberEditText.getText().toString().trim();

                if (name.isEmpty()){
                    showToast("Enter Name");
                    return;
                }

                if (email.isEmpty()){
                    showToast("Enter Email");
                    return;
                }

                if (number.isEmpty()){
                    showToast("Enter Number");
                    return;
                }
                else {
                    // add member and save data to database
                    AddMember();
                }

            }
        });


        return view;
    }

    private void fetchTourName() {
        dbTour = FirebaseDatabase.getInstance().getReference().child("Tour");
        dbTour.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tourNameList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TourModel data = dataSnapshot.getValue(TourModel.class);
                    if (data.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        tourNameList.add(data.getTour_name());
                        tourUidList.add(data.getId());
                    }
                }

                setTourAdapter(tourNameList, tourUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setTourAdapter(ArrayList<String> tourNameList, ArrayList<String> tourUidList) {

        tourNameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tourNameList);
        selectTourSpinner.setAdapter(tourNameAdapter);

        selectTourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tourUid = tourUidList.get(i);
                showToast(tourUid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void AddMember() {

        id = dbMember.push().getKey();

        HashMap hashMap = new HashMap();

        hashMap.put("added_by_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("amount", "0");
        hashMap.put("date", "");
        hashMap.put("email", email);
        hashMap.put("name", name);
        hashMap.put("id", id);
        hashMap.put("per_person_cost", "");
        hashMap.put("number", number);
        hashMap.put("tour_id", tourUid);
        hashMap.put("tour_name", selectTourSpinner.getSelectedItem());

        dbMember.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    showToast("Member added successfully");

                    String id = dbMemberList.push().getKey();

                    HashMap memberList = new HashMap();
                    memberList.put("id", id);
                    memberList.put("tour_id", tourUid);
                    memberList.put("tour_name", selectTourSpinner.getSelectedItem());
                    memberList.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                    dbMemberList.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).updateChildren(memberList).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                showToast("Added to List");
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

    private void initialization() {

        dbMember = FirebaseDatabase.getInstance().getReference().child("Member");
        dbMemberList = FirebaseDatabase.getInstance().getReference().child("Member List");

        selectTourSpinner = view.findViewById(R.id.selectTourSpinner);
        nameEditText = view.findViewById(R.id.nameEditText);
        numberEditText = view.findViewById(R.id.numberEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        addMemberButton = view.findViewById(R.id.addMemberButton);
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}