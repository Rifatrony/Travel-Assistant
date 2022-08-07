package com.rony.travelassistant.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rony.travelassistant.Activity.DashBoardActivity;
import com.rony.travelassistant.Activity.LoginActivity;
import com.rony.travelassistant.Activity.RecentTourActivity;
import com.rony.travelassistant.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView logoutTextView, recentTourTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Initialization();
        SetListener();


        return  view;
    }

    private void SetListener() {
        logoutTextView.setOnClickListener(this);
        recentTourTextView.setOnClickListener(this);
    }

    private void Initialization() {
        logoutTextView = view.findViewById(R.id.logoutTextView);
        recentTourTextView = view.findViewById(R.id.recentTourTextView);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoutTextView:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), DashBoardActivity.class));
                getActivity().finish();
                break;

            case R.id.recentTourTextView:
                //startActivity(new Intent(getContext(), RecentTourActivity.class));
                break;
        }
    }
}