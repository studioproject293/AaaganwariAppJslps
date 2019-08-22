package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.gson.Gson;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.AanganWariRecyclerviewAdapter;
import com.jslps.aaganbariapp.adapter.VoRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Collections;

public class AaganWariListFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    RecyclerView recyclerViewPanchyat;
    ArrayList<AanganWariModelDb> panchyatDataModelDbs;
    static VOListDataModelDb voListDataModelDbRec;

    public AaganWariListFragment() {
        // Required empty public constructor
    }

    public static AaganWariListFragment newInstance(VOListDataModelDb voListDataModelDb) {
        voListDataModelDbRec = voListDataModelDb;
        return new AaganWariListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.choose_aaganwari)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT,Constant.AAGANWARI_LIST_FRAGNMENT);
        panchyatDataModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class)
                .where(Condition.prop("vocode").eq(voListDataModelDbRec.getVocode())).list();
        updateList(panchyatDataModelDbs);
    }

    private void updateList(ArrayList<AanganWariModelDb> panchyatDataModelDbs) {

        if (panchyatDataModelDbs!=null && panchyatDataModelDbs.size()>0){
            recyclerViewPanchyat.setVisibility(View.VISIBLE);
            AanganWariRecyclerviewAdapter panchyatRecyclerviewAdapter = new AanganWariRecyclerviewAdapter(getActivity(), panchyatDataModelDbs,voListDataModelDbRec);
            panchyatRecyclerviewAdapter.setListner(this);
            recyclerViewPanchyat.setAdapter(panchyatRecyclerviewAdapter);
        }else {
            recyclerViewPanchyat.setVisibility(View.GONE);
            Snackbar.with(getActivity(), null)
                    .type(Type.ERROR)
                    .message(getString(R.string.no_record))
                    .duration(Duration.SHORT)
                    .fillParent(true)
                    .textAlign(Align.CENTER)
                    .show();
        }
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
        mListener.onFragmentInteraction(Constant.ENTRY_FORM_FRAGNMENT, data);
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {

    }
}

