package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.SyncWithServerRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.HeaderData;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class SyncWithServerFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    RecyclerView recyclerViewBenifishery;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends;
    Button uploaddata;

    public SyncWithServerFragment() {
        // Required empty public constructor
    }

    public static SyncWithServerFragment newInstance() {
        return new SyncWithServerFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.sync_with_server)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT,Constant.FRAGMENT_SYNC_WITH_SERVER);
        benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class).list();
        /*ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
        ArrayList<String> arrayListString = new ArrayList<>();
        for (int i = 0; i < benifisheryDataModelDbSends.size(); i++) {
            BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
            String aaganbarcode = benifisheryDataModelDbSend.getAaganwaricode();

            if (!arrayListString.contains(aaganbarcode)) {
                arrayListString.add(aaganbarcode);
                arrayList.add(benifisheryDataModelDbSend);
                *//*if (!benifisheryDataModelDbSends.get(i).getAaganwaricode().equals(benifisheryDataModelDbSends.get(j).getAaganwaricode())) {
                    BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
                    benifisheryDataModelDbSend.setAaganwaricode(benifisheryDataModelDbSends.get(i).getAaganwaricode());
                    benifisheryDataModelDbSend.setPanchyatcode(benifisheryDataModelDbSends.get(i).getPanchyatcode());
                    benifisheryDataModelDbSend.setVocode(benifisheryDataModelDbSends.get(i).getVocode());
                    arrayList.add(benifisheryDataModelDbSend);
                }else {

                }*//*
            }
        }*/

        ArrayList<BenifisheryDataModelDbSend> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                .where(Condition.prop("month").eq(8)).list();
        for (int i = 0; i < panchyatDataModelDbs.size(); i++) {
            panchyatDataModelDbs.get(i).setVoname(null);
            panchyatDataModelDbs.get(i).setAaganwariname(null);
            panchyatDataModelDbs.get(i).setPanchyatname(null);
            panchyatDataModelDbs.get(i).setId(null);
            panchyatDataModelDbs.get(i).setCreatedon(null);
        }
        System.out.println("jdfjhjds" + new Gson().toJson(panchyatDataModelDbs));
        updateList(benifisheryDataModelDbSends);
    }

    private void updateList(ArrayList<BenifisheryDataModelDbSend> arrayList) {
        SyncWithServerRecyclerviewAdapter panchyatRecyclerviewAdapter = new SyncWithServerRecyclerviewAdapter(getActivity(), arrayList);
        panchyatRecyclerviewAdapter.setListner(this);
        recyclerViewBenifishery.setAdapter(panchyatRecyclerviewAdapter);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.syncwithserver, container, false);
        recyclerViewBenifishery = rootView.findViewById(R.id.syncwithserverrecyclerview);
        uploaddata = rootView.findViewById(R.id.uploadData);
        uploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("dhfudshnew Data"+new Gson().toJson(SyncWithServerRecyclerviewAdapter.benifisheryDataModelDbSendss));
            }
        });
        recyclerViewBenifishery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return rootView;
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }
}

