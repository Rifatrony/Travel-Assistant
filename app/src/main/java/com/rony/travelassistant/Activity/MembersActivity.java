package com.rony.travelassistant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rony.travelassistant.Adapter.MemberAdapter;
import com.rony.travelassistant.Adapter.MemberFullDetailsAdapter;
import com.rony.travelassistant.Model.MemberModel;
import com.rony.travelassistant.R;

import java.util.ArrayList;
import java.util.List;

public class MembersActivity extends AppCompatActivity {

    RecyclerView memberListRecyclerView;
    ProgressBar progressBar;
    ImageView imageBack;

    MemberFullDetailsAdapter memberAdapter;
    List<MemberModel> memberModelList;
    DatabaseReference dbMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        initialization();
        FetchMemberList();

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void FetchMemberList() {
        memberListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        memberListRecyclerView.setHasFixedSize(true);
        memberModelList = new ArrayList<>();
        memberAdapter = new MemberFullDetailsAdapter(this, memberModelList);
        memberListRecyclerView.setAdapter(memberAdapter);

        dbMember.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                memberModelList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MemberModel data = dataSnapshot.getValue(MemberModel.class);
                    if (data.getAdded_by_uid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        memberModelList.add(data);
                    }
                }
                memberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialization() {

        dbMember = FirebaseDatabase.getInstance().getReference().child("Member");

        imageBack = findViewById(R.id.imageBack);
        memberListRecyclerView = findViewById(R.id.memberListRecyclerView);
        progressBar = findViewById(R.id.progressBar);
    }
}