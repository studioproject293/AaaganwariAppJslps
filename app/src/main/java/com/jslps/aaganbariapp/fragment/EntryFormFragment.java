package com.jslps.aaganbariapp.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.gson.Gson;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.GalleryActivity;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.adapter.BenifisheryRowRecyclerviewAdapter;
import com.jslps.aaganbariapp.adapter.CreateAppointmentAttachmentAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.model.LoginModelDb;
import com.jslps.aaganbariapp.model.UnitRateModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EntryFormFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    private EditText editTextRemarks;
    private ArrayList<String> arrayListMonth;
    private ArrayList<String> arrayListYear;
    private Button uploadImage;
    public static RecyclerView attachmentRecycler;
    public static LinearLayout imageLayout;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int REQUEST_PERMISSIONS_CAMERA = 101;
    RecyclerView recyclerViewBenifishery;
    ArrayList<BenifisheryDataModelDb> benifisheryDataModelDbArrayList;
    public static TextView textViewtotalAll;
    Button saveData;
    PrefManager prefManager;
    static AanganWariModelDb aanganWariModelDbrec;
    Switch switchtrue;
    LinearLayout totalLyout, tableLayout;
    BenifisheryRowRecyclerviewAdapter benifisheryRowRecyclerviewAdapter;
    Spinner yearSppiner;
    Spinner monthSpiner;
    int year = 0, month = 0;
    Double value1, value2, value3, value4;
    public static CheckBox checkBoxRice, checkBoxDal, checkBoxPenauts, checkBoxJaggery, checkBoxPotato,
            checkBoxChickpea;

    public EntryFormFragment() {
        // Required empty public constructor
    }

    public static EntryFormFragment newInstance(AanganWariModelDb aanganWariModelDb) {
        aanganWariModelDbrec = aanganWariModelDb;
        return new EntryFormFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayListMonth = new ArrayList<String>();
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
        arrayListYear = new ArrayList<>();
        arrayListYear.add("2019");
        arrayListYear.add("2020");
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        System.out.println("" + year + "fvhfdd" + month);

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListMonth);

        // Drop down layout style - list view with radio button
        dataAdapterMonth.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        monthSpiner.setAdapter(dataAdapterMonth);
        ArrayAdapter<String> dataAdapterYear = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListYear);

        // Drop down layout style - list view with radio button
        dataAdapterYear.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        yearSppiner.setAdapter(dataAdapterYear);
        if (year == 2019) {
            yearSppiner.setSelection(0);
        } else yearSppiner.setSelection(1);

        monthSpiner.setSelection(month);
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, aanganWariModelDbrec.getAnganwadiname()));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.ENTRY_FORM_FRAGNMENT);
        if (MainActivity.newCall) {
            MainActivity.newCall=false;
            benifisheryDataModelDbArrayList = new ArrayList<>();
            ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                    .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                            Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                            Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode()),
                            Condition.prop("month").eq(month + 1),
                            Condition.prop("year").eq(year)).list();
            System.out.println("hfgsdsvffdhvghfd" + new Gson().toJson(benifisheryDataModelDbSendss));
            if (benifisheryDataModelDbSendss != null && benifisheryDataModelDbSendss.size() > 0) {
                for (int i = 0; i < benifisheryDataModelDbSendss.size(); i++) {
                    BenifisheryDataModelDb benifisheryDataModelDb = new BenifisheryDataModelDb();
                    benifisheryDataModelDb.setAmount(benifisheryDataModelDbSendss.get(i).getAmount());
                    benifisheryDataModelDb.setNoofbenf(benifisheryDataModelDbSendss.get(i).getNoofbenf());
                    benifisheryDataModelDb.setNoofmeal(benifisheryDataModelDbSendss.get(i).getNoofmeal());
                    benifisheryDataModelDb.setBenfid(benifisheryDataModelDbSendss.get(i).getBenfid());
                    benifisheryDataModelDb.setBenfname(benifisheryDataModelDbSendss.get(i).getBenfname());
                    benifisheryDataModelDb.setCreatedby(benifisheryDataModelDbSendss.get(i).getCreatedby());
                    benifisheryDataModelDb.setCreatedon(benifisheryDataModelDbSendss.get(i).getCreatedon());
                    benifisheryDataModelDb.setUnitrateofmeal(benifisheryDataModelDbSendss.get(i).getUnitrateofmeal());
                    benifisheryDataModelDbArrayList.add(benifisheryDataModelDb);
                }

            } else {
                benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDb>) BenifisheryDataModelDb.listAll(BenifisheryDataModelDb.class);

            }
            final ArrayList<UnitRateModelDb> unitRateModelDbArrayList = (ArrayList<UnitRateModelDb>) Select.from(UnitRateModelDb.class).list();

            if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                        + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                        + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                        + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                        + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


            }
        }

        if (Constant.finalbytes.size() != 0) {
            Constant.editFlag = true;
            imageLayout.setVisibility(View.VISIBLE);
            CreateAppointmentAttachmentAdapter createAppointmentAttachmentAdapter = new CreateAppointmentAttachmentAdapter(getContext(), Constant.finalbytes,
                    Constant.finalnames, Constant.finalsizes, Constant.finaltypes);
            attachmentRecycler.setAdapter(createAppointmentAttachmentAdapter);
        } else {
            imageLayout.setVisibility(View.GONE);
        }



        updateList(benifisheryDataModelDbArrayList);

    }

    Double totalAll = 0.0;

    private void updateList(ArrayList<BenifisheryDataModelDb> benifisheryDataModelDbArrayList) {

        BenifisheryRowRecyclerviewAdapter benifisheryRowRecyclerviewAdapter = new BenifisheryRowRecyclerviewAdapter(getActivity(), benifisheryDataModelDbArrayList);

        totalAll = 0.0;
        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
            totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                    Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
        }
        recyclerViewBenifishery.setAdapter(benifisheryRowRecyclerviewAdapter);
        textViewtotalAll.setText(new DecimalFormat("##.##").format(totalAll).toString());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.layout_input_form, container, false);
        prefManager = PrefManager.getInstance();

        monthSpiner = rootView.findViewById(R.id.sppinermonth);
        totalLyout = (LinearLayout) rootView.findViewById(R.id.totalLyout);
        tableLayout = (LinearLayout) rootView.findViewById(R.id.tableLayout);
        imageLayout = (LinearLayout) rootView.findViewById(R.id.image_layout);
        editTextRemarks = (EditText) rootView.findViewById(R.id.editTextRemarks);
        switchtrue = (Switch) rootView.findViewById(R.id.switchtrue);
        saveData = (Button) rootView.findViewById(R.id.saveData);
        checkBoxRice = (CheckBox) rootView.findViewById(R.id.rice);
        checkBoxDal = (CheckBox) rootView.findViewById(R.id.Dal);
        checkBoxPenauts = (CheckBox) rootView.findViewById(R.id.Peanuts);
        checkBoxPotato = (CheckBox) rootView.findViewById(R.id.Potato);
        checkBoxJaggery = (CheckBox) rootView.findViewById(R.id.Jaggery);
        checkBoxChickpea = (CheckBox) rootView.findViewById(R.id.Chickpea);
        textViewtotalAll = (TextView) rootView.findViewById(R.id.totalAll);
        attachmentRecycler = (RecyclerView) rootView.findViewById(R.id.attachmentRecycler);
        recyclerViewBenifishery = (RecyclerView) rootView.findViewById(R.id.recyclerviewBenifishry);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        yearSppiner = rootView.findViewById(R.id.sppinerYear);
        uploadImage = rootView.findViewById(R.id.uploadImage);
        recyclerViewBenifishery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final ArrayList<UnitRateModelDb> unitRateModelDbArrayList = (ArrayList<UnitRateModelDb>) Select.from(UnitRateModelDb.class).list();
        System.out.println("dsvnhdsgfhf" + new Gson().toJson(unitRateModelDbArrayList));
        checkBoxChickpea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /* for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {*/
                        /*benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));
*/
//                        }
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) + unitRateModelDbArrayList.get(5).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) + unitRateModelDbArrayList.get(5).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) + unitRateModelDbArrayList.get(5).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) + unitRateModelDbArrayList.get(5).getB4()));
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {

//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) - unitRateModelDbArrayList.get(5).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) - unitRateModelDbArrayList.get(5).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) - unitRateModelDbArrayList.get(5).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) - unitRateModelDbArrayList.get(5).getB4()));

                          /*  benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2()));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3()));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4()));*/
                        if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                && !checkBoxPenauts.isChecked()) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });
        checkBoxRice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) + unitRateModelDbArrayList.get(0).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) + unitRateModelDbArrayList.get(0).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) + unitRateModelDbArrayList.get(0).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) + unitRateModelDbArrayList.get(0).getB4()));
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                       /* for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));
                            if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                    && !checkBoxPenauts.isChecked()) {
                                benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                            }
                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) - unitRateModelDbArrayList.get(0).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) - unitRateModelDbArrayList.get(0).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) - unitRateModelDbArrayList.get(0).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) - unitRateModelDbArrayList.get(0).getB4()));
                        if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                && !checkBoxPenauts.isChecked()) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });
        checkBoxDal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                       /* for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) + unitRateModelDbArrayList.get(1).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) + unitRateModelDbArrayList.get(1).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) + unitRateModelDbArrayList.get(1).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) + unitRateModelDbArrayList.get(1).getB4()));
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                            if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                    && !checkBoxPenauts.isChecked()) {
                                benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                            }
                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) - unitRateModelDbArrayList.get(1).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) - unitRateModelDbArrayList.get(1).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) - unitRateModelDbArrayList.get(1).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) - unitRateModelDbArrayList.get(1).getB4()));
                        if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                && !checkBoxPenauts.isChecked()) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });
        checkBoxJaggery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) + unitRateModelDbArrayList.get(3).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) + unitRateModelDbArrayList.get(3).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) + unitRateModelDbArrayList.get(3).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) + unitRateModelDbArrayList.get(3).getB4()));
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                            if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                    && !checkBoxPenauts.isChecked()) {
                                benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                            }
                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) - unitRateModelDbArrayList.get(3).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) - unitRateModelDbArrayList.get(3).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) - unitRateModelDbArrayList.get(3).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) - unitRateModelDbArrayList.get(3).getB4()));
                        if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                && !checkBoxPenauts.isChecked()) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });
        checkBoxPenauts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) + unitRateModelDbArrayList.get(2).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) + unitRateModelDbArrayList.get(2).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) + unitRateModelDbArrayList.get(2).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) + unitRateModelDbArrayList.get(2).getB4()));
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                            if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                    && !checkBoxPenauts.isChecked()) {
                                benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                                benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                            }
                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) - unitRateModelDbArrayList.get(2).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) - unitRateModelDbArrayList.get(2).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) - unitRateModelDbArrayList.get(2).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) - unitRateModelDbArrayList.get(2).getB4()));
                        if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                && !checkBoxPenauts.isChecked()) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });
        checkBoxPotato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) + unitRateModelDbArrayList.get(4).getB1()));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) + unitRateModelDbArrayList.get(4).getB2()));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) + unitRateModelDbArrayList.get(4).getB3()));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) + unitRateModelDbArrayList.get(4).getB4()));
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        /*for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                                benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                        + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                                benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                        + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(5).getB2()));

                                benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                        + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                                benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                        + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                                if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                        && !checkBoxPenauts.isChecked()) {
                                    benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                                    benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                                    benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                                    benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));


                                }
                        }*/
                        benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf((new DecimalFormat("##.##").format(Math.abs(Double.parseDouble(benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal()) - unitRateModelDbArrayList.get(4).getB1())))));
                        benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf((new DecimalFormat("##.##").format(Math.abs(Double.parseDouble(benifisheryDataModelDbArrayList.get(1).getUnitrateofmeal()) - unitRateModelDbArrayList.get(4).getB2())))));
                        benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf((new DecimalFormat("##.##").format(Math.abs(Double.parseDouble(benifisheryDataModelDbArrayList.get(2).getUnitrateofmeal()) - unitRateModelDbArrayList.get(4).getB3())))));
                        benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf((new DecimalFormat("##.##").format(Math.abs(Double.parseDouble(benifisheryDataModelDbArrayList.get(3).getUnitrateofmeal()) - unitRateModelDbArrayList.get(4).getB4())))));
                        if (!checkBoxPotato.isChecked() && !checkBoxRice.isChecked() && !checkBoxDal.isChecked() && !checkBoxJaggery.isChecked() && !checkBoxChickpea.isChecked()
                                && !checkBoxPenauts.isChecked()) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(0.0));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(0.0));


                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.newCall = false;
                if ((ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    Constant.maxAttachment = 2;
                    attchmemntPopup(getActivity());
                }

            }
        });
        yearSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    yearSelect = "2019";
                } else {
                    yearSelect = "2020";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        monthSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSeleted = (i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        switchtrue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    tableLayout.setVisibility(View.GONE);
                    totalLyout.setVisibility(View.GONE);
                    uploadImage.setVisibility(View.GONE);
                } else {
                    tableLayout.setVisibility(View.VISIBLE);
                    totalLyout.setVisibility(View.VISIBLE);
                    uploadImage.setVisibility(View.VISIBLE);
                }
            }
        });
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxPotato.isChecked() || checkBoxRice.isChecked() || checkBoxDal.isChecked() || checkBoxJaggery.isChecked() || checkBoxChickpea.isChecked()
                        || checkBoxPenauts.isChecked()) {
                    if (Integer.parseInt(yearSelect) <= year) {
                        if (monthSeleted - 1 <= month) {
                            if (switchtrue.isChecked()) {
                                ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                        .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                                Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                                Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode()),
                                                Condition.prop("month").eq(monthSeleted),
                                                Condition.prop("year").eq(yearSelect)).list();
                                System.out.println("hfgsdsvffdhvghfd" + new Gson().toJson(benifisheryDataModelDbSendss));
                                if (benifisheryDataModelDbSendss != null && benifisheryDataModelDbSendss.size() > 0) {
                                    Snackbar.with(getActivity(), null)
                                            .type(Type.ERROR)
                                            .message(getString(R.string.already_record))
                                            .duration(Duration.SHORT)
                                            .fillParent(true)
                                            .textAlign(Align.CENTER)
                                            .show();
                                } else {
                                    String id = UUID.randomUUID().toString();
                                    BenifisheryDataModelDbSend benifisheryDataModelDbSend = new BenifisheryDataModelDbSend();
                                    benifisheryDataModelDbSend.setAaganwaricode(prefManager.getPrefAaganwariCode());
                                    benifisheryDataModelDbSend.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                    benifisheryDataModelDbSend.setVocode(prefManager.getPREF_VOCode());
                                    benifisheryDataModelDbSend.setAaganwariname(prefManager.getPrefAaganwariNAME());
                                    benifisheryDataModelDbSend.setPanchyatname(prefManager.getPrefPanchyatNAME());
                                    benifisheryDataModelDbSend.setVoname(prefManager.getPREF_VONAME());
                                    benifisheryDataModelDbSend.setMonth(monthSeleted + "");
                                    benifisheryDataModelDbSend.setYear(yearSelect);
                                    benifisheryDataModelDbSend.setRemarks(editTextRemarks.getText().toString());
                                    benifisheryDataModelDbSend.setGuid(id);
                                    benifisheryDataModelDbSend.setIsuploadtoserver("false");
                                    ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                    benifisheryDataModelDbSend.setCreatedby(loginModelDbs.get(0).getUsername());
                                    benifisheryDataModelDbSend.save();
                           /* Snackbar.with(getActivity(), null)
                                    .type(Type.SUCCESS)
                                    .message(getString(R.string.save_message))
                                     .duration(Duration.SHORT)
                                    .fillParent(true)
                                    .textAlign(Align.CENTER)
                                    .show();*/
                                    Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            } else {
                                if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {

                                    ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
                                    for (int j = 0; j < Constant.finalbytes.size(); j++) {
                                        String id = UUID.randomUUID().toString();
                                        ImageSaveModel imageSaveModel = new ImageSaveModel();
                                        imageSaveModel.setAwccode(prefManager.getPrefAaganwariCode());
                                        imageSaveModel.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                        imageSaveModel.setVocode(prefManager.getPREF_VOCode());
                                        imageSaveModel.setIsuploadtoserver("false");
                                        imageSaveModel.setImagename(Constant.finalnames.get(j));
                                        imageSaveModel.setImagebyte(Constant.finalbytes.get(j));
                                        imageSaveModel.setMonth(monthSeleted + "");
                                        imageSaveModel.setGuid(id);
                                        ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                        imageSaveModel.setCreatedby(loginModelDbs.get(0).getUsername());
                                        imageSaveModel.setYear(yearSelect);
                                        imageSaveModel.save();

                                    }
                                    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                            .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                                    Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                                    Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode()),
                                                    Condition.prop("month").eq(monthSeleted),
                                                    Condition.prop("year").eq(yearSelect)).list();
                                    System.out.println("hfgsdsvffdhvghfd" + new Gson().toJson(benifisheryDataModelDbSendss));
                                    if (benifisheryDataModelDbSendss != null && benifisheryDataModelDbSendss.size() > 0) {
                                        Snackbar.with(getActivity(), null)
                                                .type(Type.ERROR)
                                                .message(getString(R.string.already_record))
                                                .duration(Duration.SHORT)
                                                .fillParent(true)
                                                .textAlign(Align.CENTER)
                                                .show();
                                    } else {
                                        String id1 = UUID.randomUUID().toString();
                                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {

                                            BenifisheryDataModelDbSend benifisheryDataModelDbSend = new BenifisheryDataModelDbSend();
                                            if (checkBoxChickpea.isChecked())
                                                benifisheryDataModelDbSend.setChexkboxchickpea("checkBoxChickpea");
                                            if (checkBoxRice.isChecked())
                                                benifisheryDataModelDbSend.setCheckboxrice("checkBoxRice");
                                            if (checkBoxPenauts.isChecked())
                                                benifisheryDataModelDbSend.setCheckboxpenauts("checkBoxPenauts");
                                            if (checkBoxDal.isChecked())
                                                benifisheryDataModelDbSend.setCheckboxdal("checkBoxDal");
                                            if (checkBoxJaggery.isChecked())
                                                benifisheryDataModelDbSend.setCheckboxjaggery("checkBoxJaggery");
                                            if (checkBoxPotato.isChecked())
                                                benifisheryDataModelDbSend.setCheckboxpotato("checkBoxPotato");
                                            benifisheryDataModelDbSend.setAaganwaricode(prefManager.getPrefAaganwariCode());
                                            benifisheryDataModelDbSend.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                            benifisheryDataModelDbSend.setVocode(prefManager.getPREF_VOCode());
                                            benifisheryDataModelDbSend.setAaganwariname(prefManager.getPrefAaganwariNAME());
                                            benifisheryDataModelDbSend.setPanchyatname(prefManager.getPrefPanchyatNAME());
                                            benifisheryDataModelDbSend.setVoname(prefManager.getPREF_VONAME());
                                            benifisheryDataModelDbSend.setMonth(monthSeleted + "");
                                            benifisheryDataModelDbSend.setYear(yearSelect);
                                            benifisheryDataModelDbSend.setRemarks(editTextRemarks.getText().toString());
                                            benifisheryDataModelDbSend.setGuid(id1);
                                            System.out.println("new Gson" + getDate(System.currentTimeMillis()));
                                            benifisheryDataModelDbSend.setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                            benifisheryDataModelDbSend.setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                            benifisheryDataModelDbSend.setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                            benifisheryDataModelDbSend.setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                            benifisheryDataModelDbSend.setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                            ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                            benifisheryDataModelDbSend.setCreatedby(loginModelDbs.get(0).getUsername());
                                            benifisheryDataModelDbSend.setCreatedon(getDate(System.currentTimeMillis()));
                                            benifisheryDataModelDbSend.setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
                                            benifisheryDataModelDbSend.setIsuploadtoserver("false");
                                            benifisheryDataModelDbSend.save();
                                            arrayList.add(benifisheryDataModelDbSend);

                                        }
                                        Constant.finalbytes.clear();
                                        Constant.finalnames.clear();
                                        Constant.finalsizes.clear();
                                        Constant.finaltypes.clear();
                                        Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        System.out.println("djfdsjdjvcndk" + new Gson().toJson(arrayList));
                                    }


                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.select_image_validation), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please Select current month", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Please Select current Year", Toast.LENGTH_SHORT).show();

                    }
                /*if (switchtrue.isChecked()) {
                    String id = UUID.randomUUID().toString();
                    BenifisheryDataModelDbSend benifisheryDataModelDbSend = new BenifisheryDataModelDbSend();
                    benifisheryDataModelDbSend.setAaganwaricode(prefManager.getPrefAaganwariCode());
                    benifisheryDataModelDbSend.setPanchyatcode(prefManager.getPrefPanchyatCode());
                    benifisheryDataModelDbSend.setVocode(prefManager.getPREF_VOCode());
                    benifisheryDataModelDbSend.setAaganwariname(prefManager.getPrefAaganwariNAME());
                    benifisheryDataModelDbSend.setPanchyatname(prefManager.getPrefPanchyatNAME());
                    benifisheryDataModelDbSend.setVoname(prefManager.getPREF_VONAME());
                    benifisheryDataModelDbSend.setMonth(monthSeleted + "");
                    benifisheryDataModelDbSend.setYear(yearSelect);
                    benifisheryDataModelDbSend.setRemarks(editTextRemarks.getText().toString());
                    benifisheryDataModelDbSend.setGuid(id);
                    benifisheryDataModelDbSend.setIsuploadtoserver("false");
                    ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                    benifisheryDataModelDbSend.setCreatedby(loginModelDbs.get(0).getUsername());
                    benifisheryDataModelDbSend.save();
                    Snackbar.with(getActivity(), null)
                            .type(Type.SUCCESS)
                            .message(getString(R.string.save_message))
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.CENTER)
                            .show();
                    //Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                        String id = UUID.randomUUID().toString();
                        ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
                        for (int j = 0; j < Constant.finalbytes.size(); j++) {
                            ImageSaveModel imageSaveModel = new ImageSaveModel();
                            imageSaveModel.setAwccode(prefManager.getPrefAaganwariCode());
                            imageSaveModel.setPanchyatcode(prefManager.getPrefPanchyatCode());
                            imageSaveModel.setVocode(prefManager.getPREF_VOCode());
                            imageSaveModel.setIsuploadtoserver("false");
                            imageSaveModel.setImagename(Constant.finalnames.get(j));
                            imageSaveModel.setImagebyte(Constant.finalbytes.get(j));
                            imageSaveModel.setMonth(monthSeleted + "");
                            imageSaveModel.setGuid(id);
                            ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                            imageSaveModel.setCreatedby(loginModelDbs.get(0).getUsername());
                            imageSaveModel.setYear(yearSelect);
                            imageSaveModel.save();

                        }
                        ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                        Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                        Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode()),
                                        Condition.prop("month").eq(monthSeleted),
                                        Condition.prop("year").eq(yearSelect)).list();
                        System.out.println("hfgsdsvffdhvghfd" + new Gson().toJson(benifisheryDataModelDbSendss));
                        if (benifisheryDataModelDbSendss != null && benifisheryDataModelDbSendss.size() > 0) {
                            Snackbar.with(getActivity(), null)
                                    .type(Type.ERROR)
                                    .message(getString(R.string.already_record))
                                    .duration(Duration.SHORT)
                                    .fillParent(true)
                                    .textAlign(Align.CENTER)
                                    .show();
                        } else {
                            String id1 = UUID.randomUUID().toString();
                            for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {

                                BenifisheryDataModelDbSend benifisheryDataModelDbSend = new BenifisheryDataModelDbSend();
                                benifisheryDataModelDbSend.setAaganwaricode(prefManager.getPrefAaganwariCode());
                                benifisheryDataModelDbSend.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                benifisheryDataModelDbSend.setVocode(prefManager.getPREF_VOCode());
                                benifisheryDataModelDbSend.setAaganwariname(prefManager.getPrefAaganwariNAME());
                                benifisheryDataModelDbSend.setPanchyatname(prefManager.getPrefPanchyatNAME());
                                benifisheryDataModelDbSend.setVoname(prefManager.getPREF_VONAME());
                                benifisheryDataModelDbSend.setMonth(monthSeleted + "");
                                benifisheryDataModelDbSend.setYear(yearSelect);
                                benifisheryDataModelDbSend.setRemarks(editTextRemarks.getText().toString());
                                benifisheryDataModelDbSend.setGuid(id1);
                                System.out.println("new Gson" + getDate(System.currentTimeMillis()));
                                benifisheryDataModelDbSend.setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                benifisheryDataModelDbSend.setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                benifisheryDataModelDbSend.setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                benifisheryDataModelDbSend.setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                benifisheryDataModelDbSend.setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                benifisheryDataModelDbSend.setCreatedby(loginModelDbs.get(0).getUsername());
                                benifisheryDataModelDbSend.setCreatedon(getDate(System.currentTimeMillis()));
                                benifisheryDataModelDbSend.setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());

                                benifisheryDataModelDbSend.setIsuploadtoserver("false");
                                benifisheryDataModelDbSend.save();
                                arrayList.add(benifisheryDataModelDbSend);

                            }
                            Constant.finalbytes.clear();
                            Constant.finalnames.clear();
                            Constant.finalsizes.clear();
                            Constant.finaltypes.clear();
                            Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            System.out.println("djfdsjdjvcndk" + new Gson().toJson(arrayList));
                        }


                    } else {
                        Toast.makeText(getActivity(), getString(R.string.select_image_validation), Toast.LENGTH_SHORT).show();
                    }
                }*/
                }else {
                    Snackbar.with(getActivity(), null)
                            .type(Type.ERROR)
                            .message("Please Select any product list")
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.CENTER)
                            .show();
                }
            }
        });
        return rootView;
    }

    int monthSeleted;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Constant.maxAttachment = 2;
                        attchmemntPopup(getActivity());
                    } else {
                        Toast.makeText(getActivity(), "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case REQUEST_PERMISSIONS_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if (Constant.finalbytes.size() < Constant.maxAttachment) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.image_validation), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
                break;

        }
    }

    String yearSelect = null;

    private void attchmemntPopup(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.attachment_popup, null);
        final android.app.AlertDialog alertD = new android.app.AlertDialog.Builder(context).create();
        alertD.setCancelable(true);
        //Making alert as bottom
        Window window = alertD.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        LinearLayout camera = (LinearLayout) layout.findViewById(R.id.camera);
        LinearLayout gallery = (LinearLayout) layout.findViewById(R.id.gallery);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
                if ((ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                REQUEST_PERMISSIONS_CAMERA
                        );
                    }
                } else {
                    if (Constant.finalbytes.size() < Constant.maxAttachment) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.image_validation), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
                Intent intent = new Intent(context, GalleryActivity.class);
                startActivity(intent);
               /* Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        SELECT_FILE);*/
            }
        });
        alertD.setView(layout);
        alertD.show();
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {

    }

    private File getFile() {
        File folder = new File("sdcard/Aaaganbari");

        if (!folder.exists()) {
            folder.mkdir();
        }
        File image = new File(folder, "image.jpeg");
        return image;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == -1) {

            //1st method
            String encodedBase64 = "";
            long fileSizeInBytes = 0;

            Bitmap bmp = decodeFile(getFile(), 500);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            encodedBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            FileInputStream fileInputStreamReader = null;
            try {
                fileInputStreamReader = new FileInputStream(getFile());
                fileSizeInBytes = fileInputStreamReader.available();
            } catch (Exception e) {
                //Log.d(CommonConstants.TAG,TAG+"onActivityResult exception check it");
                e.printStackTrace();
            }

            //2nd mehotd
            /*Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            encodedBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);*/
            //Storing data
            Constant.finalbytes.add(encodedBase64);
            Constant.finalnames.add(System.currentTimeMillis() + ".jpg");
            Constant.finaltypes.add("jpeg");
            Constant.finalsizes.add(fileSizeInBytes);
            Constant.finalBitmap.add(bmp);
            System.out.println("dchHIU" + new Gson().toJson(Constant.finalbytes));
        } else if (requestCode == 1 && resultCode == -1) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //userImage.setImageBitmap(photo);

            Uri tempUri = getImageUri(getActivity(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            Log.i("gbhvhjcbvhjcvbv:", finalFile.toString());

            if (finalFile.exists()) {
                FileInputStream fileInputStreamReader = null;
                try {
                    fileInputStreamReader = new FileInputStream(finalFile);
                    int fileSizeInBytes = fileInputStreamReader.available();
                    byte[] bytes = new byte[(int) finalFile.length()];
                    fileInputStreamReader.read(bytes);
                    String encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                   /* if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                        for (int j = 0; j < Constant.finalbytes.size(); j++) {
                            if (arrayListVillage1 != null && arrayListVillage1.size() > 0) {
                                if (arrayListVillage1.size()==1){
                                    arrayListVillage1.get(1).setAaganwaricode(prefManager.getPrefAaganwariCode());
                                    arrayListVillage1.get(1).setPanchyatcode(prefManager.getPrefPanchyatCode());
                                    arrayListVillage1.get(1).setVocode(prefManager.getPREF_VOCode());
                                    arrayListVillage1.get(1).setFinaltypes("jpeg");
                                    arrayListVillage1.get(1).setFinalnames(System.currentTimeMillis() + "");
                                    arrayListVillage1.get(1).setFinalsizes((long) fileSizeInBytes);
                                    arrayListVillage1.get(1).setImgebytes(encodedBase64);
                                    arrayListVillage1.get(1).save();
                                }else {
                                    arrayListVillage1.get(j).setAaganwaricode(prefManager.getPrefAaganwariCode());
                                    arrayListVillage1.get(j).setPanchyatcode(prefManager.getPrefPanchyatCode());
                                    arrayListVillage1.get(j).setVocode(prefManager.getPREF_VOCode());
                                    arrayListVillage1.get(j).setFinaltypes("jpeg");
                                    arrayListVillage1.get(j).setFinalnames(System.currentTimeMillis() + "");
                                    arrayListVillage1.get(j).setFinalsizes((long) fileSizeInBytes);
                                    arrayListVillage1.get(j).setImgebytes(encodedBase64);
                                    arrayListVillage1.get(j).save();
                                }
                            } else {
                                ImageSaveModel imageSaveModel = new ImageSaveModel();
                                imageSaveModel.setAaganwaricode(prefManager.getPrefAaganwariCode());
                                imageSaveModel.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                imageSaveModel.setVocode(prefManager.getPREF_VOCode());
                                imageSaveModel.setFinaltypes("jpeg");
                                imageSaveModel.setFinalnames(System.currentTimeMillis() + "");
                                imageSaveModel.setFinalsizes((long) fileSizeInBytes);
                                imageSaveModel.setImgebytes(encodedBase64);
                                imageSaveModel.save();

                            }
                        }
                    }*/
                    Constant.finalbytes.add(encodedBase64);
                    Constant.finalnames.add(System.currentTimeMillis() + ".jpg");
                    Constant.finaltypes.add("jpg");
                    Constant.finalsizes.add((long) fileSizeInBytes);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Error e) {
                    //Toaster.toastLong("RAM is running out of memory, Please clear your RAM first.");
                }
            }
        } /*else if (requestCode == SELECT_FILE) {
            onSelectFromGalleryResult(data);
        }*/

    }

    String encodedBase64;

    /*private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        //userImage.setImageBitmap(bm);

        File imgFile = new File(selectedImagePath);
        if (imgFile.exists()) {
            FileInputStream fileInputStreamReader = null;
            try {
                fileInputStreamReader = new FileInputStream(imgFile);
                int fileSizeInBytes = fileInputStreamReader.available();
                byte[] bytes = new byte[(int) imgFile.length()];
                fileInputStreamReader.read(bytes);
                if (fileSizeInBytes < 5000000) {
                    encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                    Constant.finalbytes.add(encodedBase64);
                    Constant.finalnames.add(selectedImagePath + "");
                    Constant.finaltypes.add("jpeg");
                    Constant.finalsizes.add((long) fileSizeInBytes);
                } else {
                    encodedBase64 = null;
                    Toast.makeText(getActivity(), "Your pic size more than 5 mb", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Error e) {

            }
        }


    }*/

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private Bitmap decodeFile(File f, int size) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = size;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constant.finalbytes.clear();
        Constant.finalnames.clear();
        Constant.finalsizes.clear();
        Constant.finaltypes.clear();
        Constant.editFlag = false;
        MainActivity.newCall = false;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

}

