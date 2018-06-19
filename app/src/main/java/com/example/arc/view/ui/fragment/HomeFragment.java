package com.example.arc.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.arc.R;
import com.example.arc.view.ui.activity.LunchRegistrationActivity;
import com.example.arc.view.ui.activity.TimeKeepingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initLayout(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initLayout(View view){
        view.findViewById(R.id.rlNote).setOnClickListener(this);
        view.findViewById(R.id.rlLunch).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlNote:
                TimeKeepingActivity.start(getContext(),1);
                break;
            case R.id.rlLunch:
                LunchRegistrationActivity.start(getContext(),1);
                break;

        }
    }
}
