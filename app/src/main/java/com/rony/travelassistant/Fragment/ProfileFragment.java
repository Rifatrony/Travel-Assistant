package com.rony.travelassistant.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rony.travelassistant.Activity.LoginActivity;
import com.rony.travelassistant.R;

public class ProfileFragment extends Fragment {

    View view;
    TextView logoutTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialization();

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return  view;
    }

    private void initialization() {
        logoutTextView = view.findViewById(R.id.logoutTextView);
    }
}