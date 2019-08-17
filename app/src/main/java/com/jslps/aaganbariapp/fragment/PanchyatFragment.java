package com.jslps.aaganbariapp.fragment;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.adapter.PanchyatRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;

import java.util.ArrayList;
import java.util.Locale;

public class PanchyatFragment extends BaseFragment implements OnFragmentListItemSelectListener {
    PrefManager prefManager;
    private View rootView;
    RecyclerView recyclerViewPanchyat;
    ArrayList<PanchyatDataModelDb> panchyatDataModelDbs;

    public PanchyatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.choose_panchyat)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT,Constant.PANCHYAT_FRAGNMENT);
        panchyatDataModelDbs = (ArrayList<PanchyatDataModelDb>) PanchyatDataModelDb.listAll(PanchyatDataModelDb.class);
        updateList(panchyatDataModelDbs);
    }

    private void updateList(ArrayList<PanchyatDataModelDb> panchyatDataModelDbs) {
        if (panchyatDataModelDbs != null && panchyatDataModelDbs.size() > 3) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerViewPanchyat.setLayoutManager(mLayoutManager);
        } else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewPanchyat.setLayoutManager(mLayoutManager);
        }
        PanchyatRecyclerviewAdapter panchyatRecyclerviewAdapter = new PanchyatRecyclerviewAdapter(getActivity(), panchyatDataModelDbs);
        panchyatRecyclerviewAdapter.setListner(this);
        recyclerViewPanchyat.setAdapter(panchyatRecyclerviewAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_panchyat, container, false);
        prefManager = PrefManager.getInstance();
        recyclerViewPanchyat = rootView.findViewById(R.id.recyclerviewPanchyat);

        return rootView;
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        mListener.onFragmentInteraction(Constant.VO_LIST_FRAGNMENT, data);
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }
}

