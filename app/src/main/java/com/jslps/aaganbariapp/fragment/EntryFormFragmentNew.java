package com.jslps.aaganbariapp.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.jslps.aaganbariapp.adapter.BenifisheryRowRecyclerviewAdapterNew;
import com.jslps.aaganbariapp.adapter.CreateAppointmentAttachmentAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbNew;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSendNew;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.model.LoginModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EntryFormFragmentNew extends BaseFragment implements OnFragmentListItemSelectListener {

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
    ArrayList<BenifisheryDataModelDbNew> benifisheryDataModelDbArrayList;
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
    Uri fileuri;
    public static TextView totalNoofbef, totalRice, totalArharDal, totalPenauts, totalChana,
            totalJaggery, totalPotatao;

    public EntryFormFragmentNew() {
        // Required empty public constructor
    }

    public static EntryFormFragmentNew newInstance(AanganWariModelDb aanganWariModelDb) {
        aanganWariModelDbrec = aanganWariModelDb;
        return new EntryFormFragmentNew();
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



        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, "Anganwadi  Name : " + aanganWariModelDbrec.getAnganwadiname()));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.ENTRY_FORM_FRAGNMENT);
        if (MainActivity.newCall) {
            monthSpiner.setSelection(month);
            if (year == 2019) {
                yearSppiner.setSelection(0);
            } else yearSppiner.setSelection(1);
            MainActivity.newCall = false;
            benifisheryDataModelDbArrayList = new ArrayList<>();
            ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                    .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                            Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                            Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode()),
                            Condition.prop("month").eq(month + 1),
                            Condition.prop("year").eq(year)).list();
            System.out.println("hfgsdsvffdhvghfd" + new Gson().toJson(benifisheryDataModelDbSendss));
            if (benifisheryDataModelDbSendss != null && benifisheryDataModelDbSendss.size() > 0) {
                for (int i = 0; i < benifisheryDataModelDbSendss.size(); i++) {
                    BenifisheryDataModelDbNew benifisheryDataModelDb = new BenifisheryDataModelDbNew();
                    benifisheryDataModelDb.setArhardal(benifisheryDataModelDbSendss.get(i).getArhardal());
                    benifisheryDataModelDb.setNoofbenf(benifisheryDataModelDbSendss.get(i).getNoofbenf());
                    benifisheryDataModelDb.setPenauts(benifisheryDataModelDbSendss.get(i).getPeanuts());
                    benifisheryDataModelDb.setPotato(benifisheryDataModelDbSendss.get(i).getPotato());
                    benifisheryDataModelDb.setRice(benifisheryDataModelDbSendss.get(i).getRice());
                    benifisheryDataModelDb.setJaggery(benifisheryDataModelDbSendss.get(i).getJaggery());
                    benifisheryDataModelDb.setChana(benifisheryDataModelDbSendss.get(i).getChana());
                    benifisheryDataModelDb.setBenfid(benifisheryDataModelDbSendss.get(i).getBenfid());
                    benifisheryDataModelDb.setBenfname(benifisheryDataModelDbSendss.get(i).getBenfname());
                    benifisheryDataModelDb.setCreatedby(benifisheryDataModelDbSendss.get(i).getCreatedby());
                    benifisheryDataModelDb.setCreatedon(benifisheryDataModelDbSendss.get(i).getCreatedon());
                    benifisheryDataModelDbArrayList.add(benifisheryDataModelDb);
                }

            } else {
                benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDbNew>) BenifisheryDataModelDb.listAll(BenifisheryDataModelDbNew.class);

            }
            updateList(benifisheryDataModelDbArrayList);
        }else {
            if (TextUtils.isEmpty(prefManager.getPREF_MonthName()))
               monthSpiner.setSelection(month);
            else  monthSpiner.setSelection(Integer.parseInt(prefManager.getPREF_MonthName()));
            if (TextUtils.isEmpty(prefManager.getPREF_YearName())) {
                if (year == 2019) {
                    yearSppiner.setSelection(0);
                } else yearSppiner.setSelection(1);
            }else  yearSppiner.setSelection(Integer.parseInt(prefManager.getPREF_YearName()));
        }
//        benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDbNew>) BenifisheryDataModelDbNew.listAll(BenifisheryDataModelDbNew.class);


        if (Constant.finalbytes.size() != 0) {
            Constant.editFlag = true;
            imageLayout.setVisibility(View.VISIBLE);
            CreateAppointmentAttachmentAdapter createAppointmentAttachmentAdapter = new CreateAppointmentAttachmentAdapter(getContext(), Constant.finalbytes,
                    Constant.finalnames, Constant.finalsizes, Constant.finaltypes);
            attachmentRecycler.setAdapter(createAppointmentAttachmentAdapter);
        } else {
            imageLayout.setVisibility(View.GONE);
        }


    }

    Double totalAll = 0.0;

    private void updateList(ArrayList<BenifisheryDataModelDbNew> benifisheryDataModelDbArrayList) {

        BenifisheryRowRecyclerviewAdapterNew benifisheryRowRecyclerviewAdapter = new BenifisheryRowRecyclerviewAdapterNew(getActivity(), benifisheryDataModelDbArrayList);
        benifisheryRowRecyclerviewAdapter.setListner(this);
        recyclerViewBenifishery.setAdapter(benifisheryRowRecyclerviewAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.layout_input_form_new, container, false);
        rootView = inflater.inflate(R.layout.layout_input_form_new, container, false);
        prefManager = PrefManager.getInstance();

        monthSpiner = rootView.findViewById(R.id.sppinermonth);
        totalLyout = (LinearLayout) rootView.findViewById(R.id.totalLyout);
        tableLayout = (LinearLayout) rootView.findViewById(R.id.tableLayout);
        imageLayout = (LinearLayout) rootView.findViewById(R.id.image_layout);
        editTextRemarks = (EditText) rootView.findViewById(R.id.editTextRemarks);
        switchtrue = (Switch) rootView.findViewById(R.id.switchtrue);
        saveData = (Button) rootView.findViewById(R.id.saveData);
        totalNoofbef = (TextView) rootView.findViewById(R.id.totalNoofbef);
        totalRice = (TextView) rootView.findViewById(R.id.totalRice);
        totalArharDal = (TextView) rootView.findViewById(R.id.totalArharDal);
        totalPenauts = (TextView) rootView.findViewById(R.id.totalPenauts);
        totalPotatao = (TextView) rootView.findViewById(R.id.totalPotatao);
        totalChana = (TextView) rootView.findViewById(R.id.totalChana);
        totalJaggery = (TextView) rootView.findViewById(R.id.totalJaggery);
//        textViewtotalAll = (TextView) rootView.findViewById(R.id.totalAll);
        attachmentRecycler = (RecyclerView) rootView.findViewById(R.id.attachmentRecycler);
        recyclerViewBenifishery = (RecyclerView) rootView.findViewById(R.id.recyclerviewBenifishry);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        yearSppiner = rootView.findViewById(R.id.sppinerYear);
        uploadImage = rootView.findViewById(R.id.uploadImage);
        recyclerViewBenifishery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
       /* final ArrayList<UnitRateModelDb> unitRateModelDbArrayList = (ArrayList<UnitRateModelDb>) Select.from(UnitRateModelDb.class).list();
        System.out.println("dsvnhdsgfhf" + new Gson().toJson(unitRateModelDbArrayList));*/


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
                prefManager.setPREF_YearName(i+"");
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
                prefManager.setPREF_MonthName(i+"");
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
                /*if (checkBoxPotato.isChecked() || checkBoxRice.isChecked() || checkBoxDal.isChecked() || checkBoxJaggery.isChecked() || checkBoxChickpea.isChecked()
                        || checkBoxPenauts.isChecked()) {*/
//                if (Integer.parseInt(yearSelect) <= year) {
//                    if (monthSeleted - 1 <= month) {
                        if (switchtrue.isChecked()) {
                            ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
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
                                BenifisheryDataModelDbSendNew benifisheryDataModelDbSend = new BenifisheryDataModelDbSendNew();
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


                            ArrayList<BenifisheryDataModelDbSendNew> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
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
                                if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                                    ArrayList<BenifisheryDataModelDbSendNew> arrayList = new ArrayList<>();
                                    for (int j = 0; j < Constant.finalbytes.size(); j++) {
                                        String id = UUID.randomUUID().toString();
                                        ImageSaveModel imageSaveModel1 = new ImageSaveModel();
                                        imageSaveModel1.setAwccode(prefManager.getPrefAaganwariCode());
                                        imageSaveModel1.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                        imageSaveModel1.setVocode(prefManager.getPREF_VOCode());
                                        imageSaveModel1.setIsuploadtoserver("false");
                                        imageSaveModel1.setImagename(Constant.finalnames.get(j));
                                        imageSaveModel1.setImagebyte(Constant.finalbytes.get(j));
                                        imageSaveModel1.setMonth(monthSeleted + "");
                                        imageSaveModel1.setGuid(id);
                                        ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                        imageSaveModel1.setCreatedby(loginModelDbs.get(0).getUsername());
                                        imageSaveModel1.setYear(yearSelect);
                                        imageSaveModel1.save();

                                    }

                                    String id1 = UUID.randomUUID().toString();
                                    for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {

                                        BenifisheryDataModelDbSendNew benifisheryDataModelDbSend = new BenifisheryDataModelDbSendNew();
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
                                        benifisheryDataModelDbSend.setArhardal(benifisheryDataModelDbArrayList.get(i).getArhardal());
                                        benifisheryDataModelDbSend.setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                        benifisheryDataModelDbSend.setPeanuts(benifisheryDataModelDbArrayList.get(i).getPenauts());
                                        benifisheryDataModelDbSend.setPotato(benifisheryDataModelDbArrayList.get(i).getPotato());
                                        benifisheryDataModelDbSend.setRice(benifisheryDataModelDbArrayList.get(i).getRice());
                                        benifisheryDataModelDbSend.setJaggery(benifisheryDataModelDbArrayList.get(i).getJaggery());
                                        benifisheryDataModelDbSend.setChana(benifisheryDataModelDbArrayList.get(i).getChana());
                                        benifisheryDataModelDbSend.setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                        ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                        benifisheryDataModelDbSend.setCreatedby(loginModelDbs.get(0).getUsername());
                                        benifisheryDataModelDbSend.setCreatedon(getDate(System.currentTimeMillis()));
//                                            benifisheryDataModelDbSend.setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
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
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.select_image_validation), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }

                   /* } else {
                        Toast.makeText(getActivity(), "Please Select current month", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please Select current Year", Toast.LENGTH_SHORT).show();

                }*/
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
                /*}else {
                    Snackbar.with(getActivity(), null)
                            .type(Type.ERROR)
                            .message("Please Select any product list")
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.CENTER)
                            .show();
                }*/
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
                        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);*/
                        String fileName = System.currentTimeMillis() + ".jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        fileuri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
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
        benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDbNew>) data;
        Double totalRice = 0.0;
        Double totalPotato = 0.0;
        Double totalNoofBenf = 0.0;
        Double totalJaggery = 0.0;
        Double totalChana = 0.0;
        Double totalPeanauts = 0.0;
        Double totalArharDal = 0.0;
        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getChana()))
                totalChana += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getChana());
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getNoofbenf())) {
                totalNoofBenf += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                totalNoofbef.setText(totalNoofBenf.toString());
            }
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getPotato()))
                totalPotato += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getPotato());
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getPenauts()))
                totalPeanauts += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getPenauts());
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getArhardal()))
                totalArharDal += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getArhardal());
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getRice()))
                totalRice += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getRice());
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getJaggery()))
                totalJaggery += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getJaggery());
        }
        totalPotatao.setText(totalPotato.toString());

        this.totalArharDal.setText(totalArharDal.toString());
        this.totalRice.setText(totalRice.toString());
        totalPenauts.setText(totalPeanauts.toString());
        this.totalChana.setText(totalChana.toString());
        this.totalJaggery.setText(totalJaggery.toString());
        /*this.benifisheryDataModelDbArrayList.clear();
        this.benifisheryDataModelDbArrayList.addAll(benifisheryDataModelDbArrayList);*/


    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {
        switch (itemId) {
            case 1:
                uploadImage.setEnabled(false);
                uploadImage.setAlpha(0.5f);
                saveData.setEnabled(false);
                saveData.setAlpha(0.5f);
                Snackbar.with(getActivity(), null)
                        .type(Type.ERROR)
                        .message("No of beni. should be less than 199")
                        .duration(Duration.SHORT)
                        .fillParent(true)
                        .textAlign(Align.CENTER)
                        .show();
                break;
            default:
                uploadImage.setEnabled(true);
                uploadImage.setAlpha(1f);
                saveData.setEnabled(true);
                saveData.setAlpha(1f);
                break;
        }
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

            Bitmap bmp = decodeFile(getFile(), 1000);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
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

        } else if (requestCode == 1 && resultCode == -1) {


            Bitmap thumbnail = null;
            try {
                thumbnail = decodeUri(fileuri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String imgName = System.currentTimeMillis() + ".jpg";

            byte[] byteArray = bytes.toByteArray();
            encodedBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            System.out.print("");
            Constant.finalbytes.add(encodedBase64);
            Constant.finalnames.add(imgName);
            Constant.finaltypes.add("jpg");

           /* Bitmap photo = (Bitmap) data.getExtras().get("data");
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
            }*/


        } /*else if (requestCode == SELECT_FILE) {
            onSelectFromGalleryResult(data);
        }*/

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getActivity().getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 400;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;

            height_tmp /= 2;

            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                .openInputStream(selectedImage), null, o2);

        return bitmap;
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

