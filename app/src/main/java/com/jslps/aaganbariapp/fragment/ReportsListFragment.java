package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.AanganWariRecyclerviewAdapter;
import com.jslps.aaganbariapp.adapter.ReprtsListRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.HeaderData;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class ReportsListFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    RecyclerView recyclerViewPanchyat;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends;

    public ReportsListFragment() {
        // Required empty public constructor
    }

    public static ReportsListFragment newInstance() {
        return new ReportsListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, "Edit"));
        benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) BenifisheryDataModelDbSend.listAll(BenifisheryDataModelDbSend.class);
        ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
        ArrayList<String> arrayListString = new ArrayList<>();
        for (int i = 0; i < benifisheryDataModelDbSends.size(); i++) {
            BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
            String aaganbarcode = benifisheryDataModelDbSend.getAaganwaricode();

            if (!arrayListString.contains(aaganbarcode)) {
                arrayListString.add(aaganbarcode);
                arrayList.add(benifisheryDataModelDbSend);
                /*if (!benifisheryDataModelDbSends.get(i).getAaganwaricode().equals(benifisheryDataModelDbSends.get(j).getAaganwaricode())) {
                    BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
                    benifisheryDataModelDbSend.setAaganwaricode(benifisheryDataModelDbSends.get(i).getAaganwaricode());
                    benifisheryDataModelDbSend.setPanchyatcode(benifisheryDataModelDbSends.get(i).getPanchyatcode());
                    benifisheryDataModelDbSend.setVocode(benifisheryDataModelDbSends.get(i).getVocode());
                    arrayList.add(benifisheryDataModelDbSend);
                }else {

                }*/
            }
        }

        // List<BenifisheryDataModelDbSend> name =BenifisheryDataModelDbSend.findWithQuery(BenifisheryDataModelDbSend.class, "SELECT DISTINCT aaganwaricode FROM BenifisheryDataModelDbSend");
        updateList(arrayList);
    }

    private void updateList(ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends) {
        ReprtsListRecyclerviewAdapter panchyatRecyclerviewAdapter = new ReprtsListRecyclerviewAdapter(getActivity(), benifisheryDataModelDbSends);
        panchyatRecyclerviewAdapter.setListner(this);
        recyclerViewPanchyat.setAdapter(panchyatRecyclerviewAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_panchyat, container, false);
        recyclerViewPanchyat = rootView.findViewById(R.id.recyclerviewPanchyat);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPanchyat.setLayoutManager(mLayoutManager);
        return rootView;
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        switch (itemId) {
            case R.id.delete:
                BenifisheryDataModelDbSend benifisheryDataModelDbSend = (BenifisheryDataModelDbSend) data;
                ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                        .where(Condition.prop("panchyatcode").eq(benifisheryDataModelDbSend.getPanchyatcode()),
                                Condition.prop("vocode").eq(benifisheryDataModelDbSend.getVocode()),
                                Condition.prop("aaganwaricode").eq(benifisheryDataModelDbSend.getAaganwaricode())).list();
                for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                    benifisheryDataModelDbArrayList.get(i).delete();
                }
                Toast.makeText(getActivity(), "Data delete successfully", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit:
                Constant.editFlag = true;
                BenifisheryDataModelDbSend benifisheryDataModelDbSend2 = (BenifisheryDataModelDbSend) data;
                mListener.onFragmentInteraction(Constant.FRAGMENT_ENTRY_EDIT, benifisheryDataModelDbSend2);
                break;
            default:
                Constant.editFlag = false;
                BenifisheryDataModelDbSend benifisheryDataModelDbSend1 = (BenifisheryDataModelDbSend) data;
                mListener.onFragmentInteraction(Constant.FRAGMENT_ENTRY_EDIT, benifisheryDataModelDbSend1);
                break;
        }


    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }
}

