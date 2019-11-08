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
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportsDisplayFragment extends BaseFragment implements OnFragmentListItemSelectListener {
    Spinner yearSppiner, yearSppiner1, monthSppiner;
    private View rootView;
    RecyclerView recyclerviewMonth, recyclerviewVo;

    public ReportsDisplayFragment() {
        // Required empty public constructor
    }

    public static ReportsDisplayFragment newInstance() {
        return new ReportsDisplayFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, "Status of THR supply"));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.AAGANWARI_LIST_FRAGNMENT);

    }

    String yearSelect,yearSelect1 = null;
    int monthSeleted,monthSeleted1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerviewMonth = rootView.findViewById(R.id.recyclerviewMonth);

        recyclerviewVo = rootView.findViewById(R.id.voRecyclerview);
        /*recyclerviewNoOfAagnwadiTotal = rootView.findViewById(R.id.totalAwRecyclerview);*/
        yearSppiner = rootView.findViewById(R.id.sppinerYear);
        yearSppiner1 = rootView.findViewById(R.id.sppinerYear1);
        monthSppiner = rootView.findViewById(R.id.sppinermonth);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity());
        recyclerviewVo.setLayoutManager(mLayoutManager);
        /*recyclerviewNoOfAagnwadiTotal.setLayoutManager(mLayoutManager2);*/
        recyclerviewMonth.setLayoutManager(mLayoutManager3);
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
        if (year == 2019) {
            yearSppiner1.setSelection(0);
        } else yearSppiner1.setSelection(1);
        final ArrayList<AanganWariModelDb> aanganWariModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).list();
        final ArrayList<String> stringArrayList = new ArrayList<>();
       /* yearSppiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    yearSelect1 = "2019";
                } else {
                    yearSelect1 = "2020";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        yearSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    stringArrayList.clear();
                    for (int j = 0; j < arrayListMonth.size(); j++) {
                        ArrayList<BenifisheryDataModelDbSendNew> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("month").eq(j + 1), Condition.prop("year").eq(2019)).list();
                        stringArrayList.add(String.valueOf(panchyatDataModelDbs.size()));
                    }
                } else {
                    stringArrayList.clear();
                    for (int j = 0; j < arrayListMonth.size(); j++) {
                        ArrayList<BenifisheryDataModelDbSendNew> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("month").eq(j + 1), Condition.prop("year").eq(2020)).list();
                        stringArrayList.add(String.valueOf(panchyatDataModelDbs.size()));
                    }
                }

                ReportsMonthRecyclerviewAdapter reportsMonthRecyclerviewAdapter = new ReportsMonthRecyclerviewAdapter(getActivity(), arrayListMonth, aanganWariModelDbs, stringArrayList);
                recyclerviewMonth.setAdapter(reportsMonthRecyclerviewAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> arrayListMonth1 = new ArrayList<String>();
        arrayListMonth1.add("जनवरी");
        arrayListMonth1.add("फ़रवरी");
        arrayListMonth1.add("मार्च");
        arrayListMonth1.add("अप्रैल");
        arrayListMonth1.add("मई");
        arrayListMonth1.add("जून");
        arrayListMonth1.add("जुलाई");
        arrayListMonth1.add("अगस्त");
        arrayListMonth1.add("सितंबर");
        arrayListMonth1.add("अक्टूबर");
        arrayListMonth1.add("नवंबर");
        arrayListMonth1.add("दिसंबर");
        ArrayList<String> arrayListYear1 = new ArrayList<>();
        arrayListYear1.add("2019");
        arrayListYear1.add("2020");
        Calendar c1 = Calendar.getInstance();
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        System.out.println("" + year + "fvhfdd" + month);
        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListMonth);

        // Drop down layout style - list view with radio button
        dataAdapterMonth.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        monthSppiner.setAdapter(dataAdapterMonth);
        ArrayAdapter<String> dataAdapterYear1 = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListYear);

        // Drop down layout style - list view with radio button
        dataAdapterYear1.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        yearSppiner.setAdapter(dataAdapterYear1);
        if (year1 == 2019) {
            yearSppiner.setSelection(0);
        } else yearSppiner.setSelection(1);

        monthSppiner.setSelection(month1);
        yearSppiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int m, long l) {

                if (m == 0) {
                    yearSelect1 = "2019";
                    ArrayList<String> arrayList = new ArrayList<>();
                    ArrayList<VOListDataModelDb> voListDataModelDbArrayList = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
                    for (int i = 0; i < voListDataModelDbArrayList.size(); i++) {
                        panchyatDataModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class)
                                .where(Condition.prop("vocode").eq(voListDataModelDbArrayList.get(i).getVocode())).list();
                        arrayList.add(String.valueOf(panchyatDataModelDbs.size()));

                    }
                    ArrayList<String> stringArrayList1 = new ArrayList<>();
                    for (int j = 0; j < panchyatDataModelDbs.size(); j++) {
                        benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("vocode").eq(panchyatDataModelDbs.get(j).getVocode()),
                                        Condition.prop("aaganwaricode").eq(panchyatDataModelDbs.get(j).getAwid()),
                                        Condition.prop("month").eq(String.valueOf(monthSeleted1)),
                                        Condition.prop("year").eq(2019)).list();
                        System.out.println("dvdsbfvfhkvbf" + new Gson().toJson(benifisheryDataModelDbSendNews));
                        stringArrayList1.add(benifisheryDataModelDbSendNews.size() + "");
                    }
                    ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), voListDataModelDbArrayList, arrayList, stringArrayList1);
                    recyclerviewVo.setAdapter(reportsMonthRecyclerviewAdapter);
                } else {
                    yearSelect1 = "2020";
                    ArrayList<String> arrayList = new ArrayList<>();
                    ArrayList<VOListDataModelDb> voListDataModelDbArrayList = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
                    for (int i = 0; i < voListDataModelDbArrayList.size(); i++) {
                        panchyatDataModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class)
                                .where(Condition.prop("vocode").eq(voListDataModelDbArrayList.get(i).getVocode())).list();
                        arrayList.add(String.valueOf(panchyatDataModelDbs.size()));

                    }
                    ArrayList<String> stringArrayList1 = new ArrayList<>();
                    for (int j = 0; j < panchyatDataModelDbs.size(); j++) {
                        benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("vocode").eq(panchyatDataModelDbs.get(j).getVocode()),
                                        Condition.prop("aaganwaricode").eq(panchyatDataModelDbs.get(j).getAwid()),
                                        Condition.prop("month").eq(String.valueOf(monthSeleted1)),
                                        Condition.prop("year").eq(2020)).list();
                        System.out.println("dvdsbfvfhkvbf" + new Gson().toJson(benifisheryDataModelDbSendNews));
                        stringArrayList1.add(benifisheryDataModelDbSendNews.size() + "");
                    }
                    ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), voListDataModelDbArrayList, arrayList, stringArrayList1);
                    recyclerviewVo.setAdapter(reportsMonthRecyclerviewAdapter);
                }

                ReportsMonthRecyclerviewAdapter reportsMonthRecyclerviewAdapter = new ReportsMonthRecyclerviewAdapter(getActivity(), arrayListMonth, aanganWariModelDbs, stringArrayList);
                recyclerviewMonth.setAdapter(reportsMonthRecyclerviewAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        monthSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int m, long l) {
                monthSeleted1 = (m + 1);
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<VOListDataModelDb> voListDataModelDbArrayList = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();
                for (int i = 0; i < voListDataModelDbArrayList.size(); i++) {
                    panchyatDataModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class)
                            .where(Condition.prop("vocode").eq(voListDataModelDbArrayList.get(i).getVocode())).list();
                    arrayList.add(String.valueOf(panchyatDataModelDbs.size()));

                }
                ArrayList<String> stringArrayList1 = new ArrayList<>();
                for (int j = 0; j < panchyatDataModelDbs.size(); j++) {
                    benifisheryDataModelDbSendNews = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                            .where(Condition.prop("vocode").eq(panchyatDataModelDbs.get(j).getVocode()),
                                    Condition.prop("aaganwaricode").eq(panchyatDataModelDbs.get(j).getAwid()),
                                    Condition.prop("month").eq(String.valueOf(monthSeleted1)),
                                    Condition.prop("year").eq(yearSelect1)).list();
                    System.out.println("dvdsbfvfhkvbf" + new Gson().toJson(benifisheryDataModelDbSendNews));
                    stringArrayList1.add(benifisheryDataModelDbSendNews.size() + "");
                }
                ReportsVoListAdapter reportsMonthRecyclerviewAdapter = new ReportsVoListAdapter(getActivity(), voListDataModelDbArrayList, arrayList, stringArrayList1);
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

