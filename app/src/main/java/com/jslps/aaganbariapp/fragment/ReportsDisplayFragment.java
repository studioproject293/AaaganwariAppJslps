package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Spinner yearSppiner;
    private View rootView;
    RecyclerView recyclerviewMonth;
    Button reports2;

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

    String yearSelect, yearSelect1 = null;
    int monthSeleted, monthSeleted1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerviewMonth = rootView.findViewById(R.id.recyclerviewMonth);
        reports2 = rootView.findViewById(R.id.reports2);
        reports2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_REPORTS_DISPLAY1, null);
            }
        });
        /*recyclerviewNoOfAagnwadiTotal = rootView.findViewById(R.id.totalAwRecyclerview);*/
        yearSppiner = rootView.findViewById(R.id.sppinerYear);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity());
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


        final ArrayList<AanganWariModelDb> aanganWariModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).list();
        final ArrayList<String> stringArrayList = new ArrayList<>();

        if (year == 2019) {
            yearSppiner.setSelection(0);
        } else yearSppiner.setSelection(1);
        yearSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    stringArrayList.clear();
                    for (int j = 0; j < arrayListMonth.size(); j++) {
                        ArrayList<BenifisheryDataModelDbSendNew> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("month").eq(j + 1), Condition.prop("year").eq(2019)).list();
                        if (panchyatDataModelDbs.size() > 3)
                            stringArrayList.add(String.valueOf(panchyatDataModelDbs.size() / 4));
                        else
                            stringArrayList.add(String.valueOf(panchyatDataModelDbs.size()));
                    }
                } else {
                    stringArrayList.clear();
                    for (int j = 0; j < arrayListMonth.size(); j++) {
                        ArrayList<BenifisheryDataModelDbSendNew> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                                .where(Condition.prop("month").eq(j + 1), Condition.prop("year").eq(2020)).list();
                        if (panchyatDataModelDbs.size() > 3)
                            stringArrayList.add(String.valueOf(panchyatDataModelDbs.size() / 4));
                        else
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


        ArrayAdapter<String> dataAdapterYear1 = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListYear);

        // Drop down layout style - list view with radio button
        dataAdapterYear1.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        yearSppiner.setAdapter(dataAdapterYear1);

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

