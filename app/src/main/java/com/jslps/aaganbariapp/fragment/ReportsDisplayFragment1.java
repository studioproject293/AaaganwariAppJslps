package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.ReportsMonthRecyclerviewAdapter;
import com.jslps.aaganbariapp.adapter.ReportsVoListAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSendNew;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ReportFilterModel;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportsDisplayFragment1 extends BaseFragment implements OnFragmentListItemSelectListener {
    Spinner yearSppiner1, monthSppiner;
    private View rootView;
    RecyclerView recyclerviewVo;

    public ReportsDisplayFragment1() {
        // Required empty public constructor
    }

    public static ReportsDisplayFragment1 newInstance() {
        return new ReportsDisplayFragment1();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, "Status of THR supply"));

    }

    String yearSelect1;
    int monthSeleted1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reports1, container, false);
        recyclerviewVo = rootView.findViewById(R.id.voRecyclerview);
        yearSppiner1 = rootView.findViewById(R.id.sppinerYear1);
        monthSppiner = rootView.findViewById(R.id.sppinermonth);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewVo.setLayoutManager(mLayoutManager);

        final ArrayList<String> arrayListMonth = new ArrayList<String>();
        arrayListMonth.add("जनवरी");
        arrayListMonth.add("फ़रवरी");
        arrayListMonth.add("मार्च");
        arrayListMonth.add("अप्रैल");
        arrayListMonth.add("मई");
        arrayListMonth.add("जून");
        arrayListMonth.add("जुलाई");
        arrayListMonth.add("अगस्त");
        arrayListMonth.add("सितंबर");
        arrayListMonth.add("अक्टूबर");
        arrayListMonth.add("नवंबर");
        arrayListMonth.add("दिसंबर");
        ArrayList<String> arrayListYear = new ArrayList<>();
        arrayListYear.add("2019");
        arrayListYear.add("2020");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        System.out.println("" + year + "fvhfdd" + month);

        ArrayAdapter<String> dataAdapterYear = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListYear);

        // Drop down layout style - list view with radio button
        dataAdapterYear.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        yearSppiner1.setAdapter(dataAdapterYear);
        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListMonth);

        // Drop down layout style - list view with radio button
        dataAdapterMonth.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        monthSppiner.setAdapter(dataAdapterMonth);
        monthSppiner.setSelection(month);
        if (year == 2019) {
            yearSppiner1.setSelection(0);
        } else yearSppiner1.setSelection(1);
        final ArrayList<AanganWariModelDb> aanganWariModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).list();
        final ArrayList<String> stringArrayList = new ArrayList<>();
        System.out.println("vefrag" + monthSppiner.getSelectedItemId());

        ArrayList<ReportFilterModel> reportFilterModelArrayList = new ArrayList<>();
        ArrayList<VOListDataModelDb> voListDataModelDbArrayList1 = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
        for (int i = 0; i < voListDataModelDbArrayList1.size(); i++) {
            ReportFilterModel reportFilterModel = new ReportFilterModel();
            reportFilterModel.setVoname(voListDataModelDbArrayList1.get(i).getVoshg());
            ArrayList<AanganWariModelDb> aanganWariModelDbArrayList = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).
                    where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode())).list();
            for (int j = 0; j < aanganWariModelDbArrayList.size(); j++) {
                reportFilterModel.setAaganwaricount(aanganWariModelDbArrayList.size() + "");

                 ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                        .where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode()),
                                Condition.prop("month").eq(monthSppiner.getSelectedItemId()+1),
                                Condition.prop("year").eq(yearSppiner1.getSelectedItem())).list();
                for (int k = 0; k < benifisheryDataModelDbSendNews.size(); k++) {
                    reportFilterModel.setAaganwaricountApplied(benifisheryDataModelDbSendNews.size() + "");
                }
            }
            reportFilterModelArrayList.add(reportFilterModel);
        }
        System.out.println("adhcjvdiu"+new Gson().toJson(reportFilterModelArrayList));
        ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), reportFilterModelArrayList);
        recyclerviewVo.setAdapter(reportsMonthRecyclerviewAdapter);
        yearSppiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int m, long l) {

                if (m == 0) {
                    yearSelect1 = "2019";
                    ArrayList<ReportFilterModel> reportFilterModelArrayList = new ArrayList<>();
                    ArrayList<VOListDataModelDb> voListDataModelDbArrayList1 = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
                    for (int i = 0; i < voListDataModelDbArrayList1.size(); i++) {
                        ReportFilterModel reportFilterModel = new ReportFilterModel();
                        reportFilterModel.setVoname(voListDataModelDbArrayList1.get(i).getVoshg());
                        ArrayList<AanganWariModelDb> aanganWariModelDbArrayList = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).
                                where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode())).list();
                        for (int j = 0; j < aanganWariModelDbArrayList.size(); j++) {
                            reportFilterModel.setAaganwaricount(aanganWariModelDbArrayList.size() + "");

                            ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                    .where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode()),
                                            Condition.prop("month").eq(monthSppiner.getSelectedItemId()+1),
                                            Condition.prop("year").eq(yearSppiner1.getSelectedItem())).list();
                            for (int k = 0; k < benifisheryDataModelDbSendNews.size(); k++) {
                                reportFilterModel.setAaganwaricountApplied(benifisheryDataModelDbSendNews.size() + "");
                            }
                        }
                        reportFilterModelArrayList.add(reportFilterModel);
                    }
                    System.out.println("adhcjvdiu"+new Gson().toJson(reportFilterModelArrayList));
                    ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), reportFilterModelArrayList);
                    recyclerviewVo.setAdapter(reportsMonthRecyclerviewAdapter);
                } else {
                    yearSelect1 = "2020";
                    ArrayList<ReportFilterModel> reportFilterModelArrayList = new ArrayList<>();
                    ArrayList<VOListDataModelDb> voListDataModelDbArrayList1 = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
                    for (int i = 0; i < voListDataModelDbArrayList1.size(); i++) {
                        ReportFilterModel reportFilterModel = new ReportFilterModel();
                        reportFilterModel.setVoname(voListDataModelDbArrayList1.get(i).getVoshg());
                        ArrayList<AanganWariModelDb> aanganWariModelDbArrayList = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).
                                where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode())).list();
                        for (int j = 0; j < aanganWariModelDbArrayList.size(); j++) {
                            reportFilterModel.setAaganwaricount(aanganWariModelDbArrayList.size() + "");

                            ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                    .where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode()),
                                            Condition.prop("month").eq(monthSppiner.getSelectedItemId()+1),
                                            Condition.prop("year").eq(yearSppiner1.getSelectedItem())).list();
                            for (int k = 0; k < benifisheryDataModelDbSendNews.size(); k++) {
                                reportFilterModel.setAaganwaricountApplied(benifisheryDataModelDbSendNews.size() + "");
                            }
                        }
                        reportFilterModelArrayList.add(reportFilterModel);
                    }
                    System.out.println("adhcjvdiu"+new Gson().toJson(reportFilterModelArrayList));
                    ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), reportFilterModelArrayList);
                    recyclerviewVo.setAdapter(reportsMonthRecyclerviewAdapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        monthSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int m, long l) {
                monthSeleted1 = (m + 1);
                ArrayList<ReportFilterModel> reportFilterModelArrayList = new ArrayList<>();
                ArrayList<VOListDataModelDb> voListDataModelDbArrayList1 = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
                for (int i = 0; i < voListDataModelDbArrayList1.size(); i++) {
                    ReportFilterModel reportFilterModel = new ReportFilterModel();
                    reportFilterModel.setVoname(voListDataModelDbArrayList1.get(i).getVoshg());
                    ArrayList<AanganWariModelDb> aanganWariModelDbArrayList = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).
                            where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode())).list();
                    for (int j = 0; j < aanganWariModelDbArrayList.size(); j++) {
                        reportFilterModel.setAaganwaricount(aanganWariModelDbArrayList.size() + "");

                        ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("vocode").eq(voListDataModelDbArrayList1.get(i).getVocode()),
                                        Condition.prop("month").eq(monthSppiner.getSelectedItemId()+1),
                                        Condition.prop("year").eq(yearSppiner1.getSelectedItem())).list();
                        for (int k = 0; k < benifisheryDataModelDbSendNews.size(); k++) {
                            reportFilterModel.setAaganwaricountApplied(benifisheryDataModelDbSendNews.size() + "");
                        }
                    }
                    reportFilterModelArrayList.add(reportFilterModel);
                }
                System.out.println("adhcjvdiu"+new Gson().toJson(reportFilterModelArrayList));
                ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), reportFilterModelArrayList);
                recyclerviewVo.setAdapter(reportsMonthRecyclerviewAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return rootView;
    }

    ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendNews;
    ArrayList<AanganWariModelDb> panchyatDataModelDbs;

    @Override
    public void onListItemSelected(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {

    }
}

