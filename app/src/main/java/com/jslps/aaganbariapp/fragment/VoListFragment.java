package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.VoRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class VoListFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    RecyclerView recyclerViewPanchyat;
    ArrayList<VOListDataModelDb> panchyatDataModelDbs;
    static PanchyatDataModelDb panchyatDataModelDbrec;

    public VoListFragment() {
        // Required empty public constructor
    }

    public static VoListFragment newInstance(PanchyatDataModelDb panchyatDataModelDb) {
        panchyatDataModelDbrec = panchyatDataModelDb;
        return new VoListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.vo_choose)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.VO_LIST_FRAGNMENT);
        panchyatDataModelDbs = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class)
                .where(Condition.prop("clustercode").eq(panchyatDataModelDbrec.getClustercode())).list();
        updateList(panchyatDataModelDbs);
    }

    private void updateList(ArrayList<VOListDataModelDb> panchyatDataModelDbs) {
        VoRecyclerviewAdapter panchyatRecyclerviewAdapter = new VoRecyclerviewAdapter(getActivity(), panchyatDataModelDbs);
        panchyatRecyclerviewAdapter.setListner(this);
        recyclerViewPanchyat.setAdapter(panchyatRecyclerviewAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_panchyat, container, false);
        recyclerViewPanchyat = rootView.findViewById(R.id.recyclerviewPanchyat);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewPanchyat.setLayoutManager(mLayoutManager);
        return rootView;
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        mListener.onFragmentInteraction(Constant.AAGANWARI_LIST_FRAGNMENT, data);
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }
}

