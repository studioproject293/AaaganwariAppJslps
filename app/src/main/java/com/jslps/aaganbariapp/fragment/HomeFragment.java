package com.jslps.aaganbariapp.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.activity.WelcomeActivity;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.HeaderData;

public class HomeFragment extends BaseFragment  {

    private View rootView;
    LinearLayout layoutStart, logout,feedbackFragment,reports,sysncwithserverLayout;

    public HomeFragment() { }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.dashboard)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT,Constant.HOME_FRAGMENT);
        MainActivity.radioGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        layoutStart = rootView.findViewById(R.id.layoutStart);
        feedbackFragment = rootView.findViewById(R.id.feedbackFragment);
        sysncwithserverLayout = rootView.findViewById(R.id.sysncwithserverLayout);
        reports = rootView.findViewById(R.id.reports);
        logout = rootView.findViewById(R.id.logout);
        feedbackFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_FEEDBACK, null);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_REPORTS, null);
            }
        });
        layoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.PANCHYAT_FRAGNMENT, null);
            }
        });
        sysncwithserverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_SYNC_WITH_SERVER, null);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userName", "");
                editor.putString("Password", "");
                editor.apply();
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return rootView;
    }


}

