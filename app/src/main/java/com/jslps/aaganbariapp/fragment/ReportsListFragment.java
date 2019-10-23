package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.adapter.ReprtsListRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSendNew;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.model.ReportDisplayFormModel;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class ReportsListFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    RecyclerView recyclerViewPanchyat;
    ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSends;

    public ReportsListFragment() {
        // Required empty public constructor
    }

    public static ReportsListFragment newInstance() {
        return new ReportsListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.edit)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.FRAGMENT_REPORTS);
        benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSendNew>) BenifisheryDataModelDbSend.listAll(BenifisheryDataModelDbSendNew.class);
        ArrayList<BenifisheryDataModelDbSendNew> arrayList = new ArrayList<>();
        ArrayList<ReportDisplayFormModel> arrayListReportDisplayFormModel = new ArrayList<>();
        ArrayList<String> arrayListString = new ArrayList<>();
        for (int i = 0; i < benifisheryDataModelDbSends.size(); i++) {
            BenifisheryDataModelDbSendNew benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
            String ymp = benifisheryDataModelDbSend.getAaganwaricode() + benifisheryDataModelDbSend.getYear() + benifisheryDataModelDbSend.getMonth();
            if (!arrayListString.contains(ymp)) {
                arrayListString.add(ymp);
                arrayList.add(benifisheryDataModelDbSend);
                ReportDisplayFormModel reportDisplayFormModel = new ReportDisplayFormModel();
                reportDisplayFormModel.setAaganwariname(benifisheryDataModelDbSend.getAaganwariname());
                reportDisplayFormModel.setPanchayatname(benifisheryDataModelDbSend.getPanchyatname());
                reportDisplayFormModel.setPancayatcode(benifisheryDataModelDbSend.getPanchyatcode());
                reportDisplayFormModel.setVoname(benifisheryDataModelDbSend.getVoname());
                reportDisplayFormModel.setMonth(benifisheryDataModelDbSend.getMonth());
                reportDisplayFormModel.setYear(benifisheryDataModelDbSend.getYear());
                reportDisplayFormModel.setIsuploadToServer(benifisheryDataModelDbSend.getIsuploadtoserver());
                reportDisplayFormModel.setVocode(benifisheryDataModelDbSend.getVocode());
                reportDisplayFormModel.setAaganwaricode(benifisheryDataModelDbSend.getAaganwaricode());
                arrayListReportDisplayFormModel.add(reportDisplayFormModel);

            }
        }

        // List<BenifisheryDataModelDbSend> name =BenifisheryDataModelDbSend.findWithQuery(BenifisheryDataModelDbSend.class, "SELECT DISTINCT aaganwaricode FROM BenifisheryDataModelDbSend");
        updateList(arrayListReportDisplayFormModel);
    }

    private void updateList(ArrayList<ReportDisplayFormModel> benifisheryDataModelDbSends) {
        if (benifisheryDataModelDbSends != null && benifisheryDataModelDbSends.size() > 0) {
            recyclerViewPanchyat.setVisibility(View.VISIBLE);
            ReprtsListRecyclerviewAdapter panchyatRecyclerviewAdapter = new ReprtsListRecyclerviewAdapter(getActivity(), benifisheryDataModelDbSends);
            panchyatRecyclerviewAdapter.setListner(this);
            recyclerViewPanchyat.setAdapter(panchyatRecyclerviewAdapter);
        } else {
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPanchyat.setLayoutManager(mLayoutManager);
        return rootView;
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        switch (itemId) {
            case R.id.delete:
                ReportDisplayFormModel benifisheryDataModelDbSend = (ReportDisplayFormModel) data;
                ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                        .where(Condition.prop("panchyatcode").eq(benifisheryDataModelDbSend.getPancayatcode()),
                                Condition.prop("vocode").eq(benifisheryDataModelDbSend.getVocode()),
                                Condition.prop("aaganwaricode").eq(benifisheryDataModelDbSend.getAaganwaricode())).list();
                ArrayList<ImageSaveModel> imageSaveModelArrayList = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class)
                        .where(Condition.prop("panchyatcode").eq(benifisheryDataModelDbSend.getPancayatcode()),
                                Condition.prop("vocode").eq(benifisheryDataModelDbSend.getVocode()),
                                Condition.prop("awccode").eq(benifisheryDataModelDbSend.getAaganwaricode())).list();
                for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                    benifisheryDataModelDbArrayList.get(i).delete();
                }
                for (int j = 0; j < imageSaveModelArrayList.size(); j++) {
                    imageSaveModelArrayList.get(j).delete();
                }
                Snackbar.with(getActivity(), null)
                        .type(Type.SUCCESS)
                        .message(getString(R.string.delete_sucessfully))
                        .duration(Duration.SHORT)
                        .fillParent(true)
                        .textAlign(Align.CENTER)
                        .show();
                //Toast.makeText(getActivity(), getString(R.string.delete_sucessfully), Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit:
                Constant.editFlag = true;
                MainActivity.newCall1 = true;
                ReportDisplayFormModel benifisheryDataModelDbSend2 = (ReportDisplayFormModel) data;
                mListener.onFragmentInteraction(Constant.FRAGMENT_ENTRY_EDIT_NEW, benifisheryDataModelDbSend2);
                break;
            default:
                Constant.editFlag = false;
                MainActivity.newCall1 = true;
                ReportDisplayFormModel benifisheryDataModelDbSend1 = (ReportDisplayFormModel) data;
                mListener.onFragmentInteraction(Constant.FRAGMENT_ENTRY_EDIT_NEW, benifisheryDataModelDbSend1);
                break;
        }


    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {

    }
}

