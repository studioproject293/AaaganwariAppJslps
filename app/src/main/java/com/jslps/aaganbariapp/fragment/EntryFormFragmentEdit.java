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
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.GalleryActivity;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.adapter.AttachmentImgeAdapter;
import com.jslps.aaganbariapp.adapter.BenifisheryRowEditRecyclerviewAdapter;
import com.jslps.aaganbariapp.cache.CartCache;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.model.LoginModelDb;
import com.jslps.aaganbariapp.model.ReportDisplayFormModel;
import com.jslps.aaganbariapp.model.UnitRateModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class EntryFormFragmentEdit extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    private EditText editTextRemarks;
    Spinner monthSpiner, yearSppiner;
    ArrayList<String> arrayListMonth;
    ArrayList<String> arrayListYear;
    Button uploadImage;
    public static RecyclerView attachmentRecycler;
    public static LinearLayout imageLayout;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int REQUEST_PERMISSIONS_CAMERA = 101;
    RecyclerView recyclerViewBenifishery;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList;
    ArrayList<ImageSaveModel> arrayListVillage1;
    public static TextView textViewtotalAll, productList;
    Button saveData;
    static ReportDisplayFormModel BenifisheryDataModelDbSendRec;
    PrefManager prefManager;
    LinearLayout totalLyout, tableLayout, butonLayout;
    CheckBox checkBoxRice, checkBoxDal, checkBoxPenauts, checkBoxJaggery, checkBoxPotato,
            checkBoxChickpea;

    public EntryFormFragmentEdit() {
        // Required empty public constructor
    }

    public static EntryFormFragmentEdit newInstance(ReportDisplayFormModel aanganWariModelDb) {
        BenifisheryDataModelDbSendRec = aanganWariModelDb;
        return new EntryFormFragmentEdit();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.entryform_edit)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.FRAGMENT_ENTRY_EDIT);
        if (MainActivity.newCall1) {
            benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                    .where(Condition.prop("panchyatcode").eq(BenifisheryDataModelDbSendRec.getPancayatcode()),
                            Condition.prop("vocode").eq(BenifisheryDataModelDbSendRec.getVocode()),
                            Condition.prop("aaganwaricode").eq(BenifisheryDataModelDbSendRec.getAaganwaricode())
                            , Condition.prop("month").eq(BenifisheryDataModelDbSendRec.getMonth()),
                            Condition.prop("year").eq(BenifisheryDataModelDbSendRec.getYear())).list();
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getCheckboxrice())) {
                if (benifisheryDataModelDbArrayList.get(0).getCheckboxrice().equals("checkBoxRice")) {
                    checkBoxRice.setChecked(true);
                } else checkBoxChickpea.setChecked(false);
            } else {
                checkBoxChickpea.setChecked(false);
            }
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getCheckboxdal())) {
                if (benifisheryDataModelDbArrayList.get(0).getCheckboxdal().equals("checkBoxDal")) {
                    checkBoxDal.setChecked(true);
                } else checkBoxDal.setChecked(false);
            } else {
                checkBoxDal.setChecked(false);
            }
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getCheckboxjaggery())) {
                if (benifisheryDataModelDbArrayList.get(0).getCheckboxjaggery().equals("checkBoxJaggery")) {
                    checkBoxJaggery.setChecked(true);
                } else checkBoxJaggery.setChecked(false);
            } else {
                checkBoxJaggery.setChecked(false);
            }
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getCheckboxpenauts())) {
                if (benifisheryDataModelDbArrayList.get(0).getCheckboxpenauts().equals("checkBoxPenauts")) {
                    checkBoxPenauts.setChecked(true);
                } else checkBoxPenauts.setChecked(false);
            }else {
                checkBoxPenauts.setChecked(false);
            }
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getCheckboxpotato())) {
                if (benifisheryDataModelDbArrayList.get(0).getCheckboxpotato().equals("checkBoxPotato")) {
                    checkBoxPotato.setChecked(true);
                } else checkBoxPotato.setChecked(false);
            }else {
                checkBoxPotato.setChecked(false);
            }
            if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getChexkboxchickpea())) {
                if (benifisheryDataModelDbArrayList.get(0).getChexkboxchickpea().equals("checkBoxChickpea")) {
                    checkBoxChickpea.setChecked(true);
                } else checkBoxChickpea.setChecked(false);
            }else {
                checkBoxChickpea.setChecked(false);
            }
        }
        editTextRemarks.setText(benifisheryDataModelDbArrayList.get(0).getRemarks());

        if (Constant.editFlag) {
            editTextRemarks.setVisibility(View.VISIBLE);
            editTextRemarks.setEnabled(true);
        } else {
            if (benifisheryDataModelDbArrayList != null) {
                if (TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(0).getRemarks()))
                    editTextRemarks.setVisibility(View.GONE);
                else {
                    editTextRemarks.setVisibility(View.VISIBLE);
                    editTextRemarks.setEnabled(false);
                }
            } else editTextRemarks.setVisibility(View.GONE);
        }
        System.out.println("djaHUWEQYE8WHQDUSYADAUWIGSDFAUI" + new Gson().toJson((ArrayList<ImageSaveModel>) ImageSaveModel.listAll(ImageSaveModel.class)));
        arrayListVillage1 = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class)
                .where(Condition.prop("panchyatcode").eq(BenifisheryDataModelDbSendRec.getPancayatcode()),
                        Condition.prop("vocode").eq(BenifisheryDataModelDbSendRec.getVocode()),
                        Condition.prop("awccode").eq(BenifisheryDataModelDbSendRec.getAaganwaricode()),
                        Condition.prop("month").eq(BenifisheryDataModelDbSendRec.getMonth()),
                        Condition.prop("year").eq(BenifisheryDataModelDbSendRec.getYear())).list();
        System.out.println("dsbfhjhxjkf" + new Gson().toJson(arrayListVillage1));
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
        if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
            updateList(benifisheryDataModelDbArrayList);
            if (benifisheryDataModelDbArrayList.size() == 1)
                uploadImage.setVisibility(View.GONE);
            else uploadImage.setVisibility(View.VISIBLE);
        } else {
            uploadImage.setVisibility(View.GONE);
        }

        if (arrayListVillage1 != null && arrayListVillage1.size() > 0) {
            Constant.maxAttachment = 1;

            if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {

                for (int k = 0; k < Constant.finalbytes.size(); k++) {
                    String id = UUID.randomUUID().toString();
                    ImageSaveModel imageSaveModel = new ImageSaveModel();
                    imageSaveModel.setAwccode(BenifisheryDataModelDbSendRec.getAaganwaricode());
                    imageSaveModel.setPanchyatcode(BenifisheryDataModelDbSendRec.getPancayatcode());
                    imageSaveModel.setVocode(BenifisheryDataModelDbSendRec.getVocode());
                    imageSaveModel.setImagename(Constant.finalnames.get(k));
                    imageSaveModel.setImagebyte(Constant.finalbytes.get(k));
                    imageSaveModel.setFinalsizes(Constant.finalsizes.get(k));
                    imageSaveModel.setFinaltypes(Constant.finaltypes.get(k));
                    imageSaveModel.setGuid(id);
                    ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                    imageSaveModel.setCreatedby(loginModelDbs.get(0).getCreatedby());
                    arrayListVillage1.add(1, imageSaveModel);

                }
                Constant.finalbytes.clear();
                Constant.finalnames.clear();
                Constant.finaltypes.clear();
                Constant.finalsizes.clear();


            }
            imageLayout.setVisibility(View.VISIBLE);
            AttachmentImgeAdapter createAppointmentAttachmentAdapter = new AttachmentImgeAdapter(getContext()
                    , arrayListVillage1);
            createAppointmentAttachmentAdapter.setListner(this);
            attachmentRecycler.setAdapter(createAppointmentAttachmentAdapter);


        } else {
            Constant.maxAttachment = 2;
            if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                if (CartCache.getInstance().getImageSaveModels() != null && CartCache.getInstance().getImageSaveModels().size() > 0) {
                    Constant.finalbytes.add(CartCache.getInstance().getImageSaveModels().get(0).getImagebyte());
                    Constant.finaltypes.add(CartCache.getInstance().getImageSaveModels().get(0).getFinaltypes());
                    Constant.finalsizes.add(CartCache.getInstance().getImageSaveModels().get(0).getFinalsizes());
                    Constant.finalnames.add(CartCache.getInstance().getImageSaveModels().get(0).getImagename());
                }
                for (int k = 0; k < Constant.finalbytes.size(); k++) {
                    String id = UUID.randomUUID().toString();
                    ImageSaveModel imageSaveModel = new ImageSaveModel();
                    imageSaveModel.setAwccode(BenifisheryDataModelDbSendRec.getAaganwaricode());
                    imageSaveModel.setPanchyatcode(BenifisheryDataModelDbSendRec.getPancayatcode());
                    imageSaveModel.setVocode(BenifisheryDataModelDbSendRec.getVocode());
                    imageSaveModel.setImagename(Constant.finalnames.get(k));
                    imageSaveModel.setImagebyte(Constant.finalbytes.get(k));
                    imageSaveModel.setFinalsizes(Constant.finalsizes.get(k));
                    imageSaveModel.setFinaltypes(Constant.finaltypes.get(k));
                    imageSaveModel.setGuid(id);
                    ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                    imageSaveModel.setCreatedby(loginModelDbs.get(0).getCreatedby());
                    arrayListVillage1.add(imageSaveModel);
                    CartCache.getInstance().setImageSaveModels(arrayListVillage1);
                }
                Constant.finalbytes.clear();
                Constant.finalnames.clear();
                Constant.finaltypes.clear();
                Constant.finalsizes.clear();
            }
            imageLayout.setVisibility(View.VISIBLE);
            AttachmentImgeAdapter createAppointmentAttachmentAdapter = new AttachmentImgeAdapter(getContext()
                    , arrayListVillage1);

            attachmentRecycler.setAdapter(createAppointmentAttachmentAdapter);
            createAppointmentAttachmentAdapter.notifyDataSetChanged();
        }
    }

    Double totalAll = 0.0;

    private void updateList(ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList) {
        tableLayout.setVisibility(View.VISIBLE);
        totalLyout.setVisibility(View.VISIBLE);
        if (benifisheryDataModelDbArrayList.get(0).getUnitrateofmeal() != null && benifisheryDataModelDbArrayList.get(0).getNoofmeal() != null) {
            BenifisheryRowEditRecyclerviewAdapter benifisheryRowRecyclerviewAdapter = new BenifisheryRowEditRecyclerviewAdapter(getActivity(), benifisheryDataModelDbArrayList);
            benifisheryRowRecyclerviewAdapter.setListner(this);
            recyclerViewBenifishery.setAdapter(benifisheryRowRecyclerviewAdapter);
            totalAll = 0.0;
            for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                        Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
            }
            textViewtotalAll.setText(totalAll.toString());
        } else {
            tableLayout.setVisibility(View.GONE);
            totalLyout.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.layout_input_form_edit, container, false);
        prefManager = PrefManager.getInstance();
        setId();

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
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        System.out.println("" + year + "fvhfdd" + month);
        recyclerViewBenifishery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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
        if (BenifisheryDataModelDbSendRec.getYear().equalsIgnoreCase("2019"))
            yearSppiner.setSelection(0);
        else yearSppiner.setSelection(1);
        /*if (year == 2019) {
            yearSppiner.setSelection(0);
        } else yearSppiner.setSelection(1);*/
        checkBoxRice = (CheckBox) rootView.findViewById(R.id.rice);
        checkBoxDal = (CheckBox) rootView.findViewById(R.id.Dal);
        checkBoxPenauts = (CheckBox) rootView.findViewById(R.id.Peanuts);
        checkBoxPotato = (CheckBox) rootView.findViewById(R.id.Potato);
        checkBoxJaggery = (CheckBox) rootView.findViewById(R.id.Jaggery);
        checkBoxChickpea = (CheckBox) rootView.findViewById(R.id.Chickpea);
        productList = (TextView) rootView.findViewById(R.id.productList);
        monthSpiner.setSelection(Integer.parseInt(BenifisheryDataModelDbSendRec.getMonth()) - 1);

        if (Constant.editFlag) {
            yearSppiner.setEnabled(true);
            monthSpiner.setEnabled(true);
            butonLayout.setVisibility(View.VISIBLE);
            checkBoxRice.setVisibility(View.VISIBLE);
            checkBoxDal.setVisibility(View.VISIBLE);
            checkBoxPenauts.setVisibility(View.VISIBLE);
            checkBoxPotato.setVisibility(View.VISIBLE);
            checkBoxJaggery.setVisibility(View.VISIBLE);
            checkBoxChickpea.setVisibility(View.VISIBLE);
            productList.setVisibility(View.VISIBLE);

        } else {
            yearSppiner.setEnabled(false);
            monthSpiner.setEnabled(false);
            butonLayout.setVisibility(View.GONE);
            checkBoxRice.setVisibility(View.GONE);
            checkBoxDal.setVisibility(View.GONE);
            checkBoxPenauts.setVisibility(View.GONE);
            checkBoxPotato.setVisibility(View.GONE);
            checkBoxJaggery.setVisibility(View.GONE);
            checkBoxChickpea.setVisibility(View.GONE);
            productList.setVisibility(View.GONE);
        }
        final ArrayList<UnitRateModelDb> unitRateModelDbArrayList = (ArrayList<UnitRateModelDb>) Select.from(UnitRateModelDb.class).list();
        System.out.println("dsvnhdsgfhf" + new Gson().toJson(unitRateModelDbArrayList));
        checkBoxChickpea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB2()));
                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB3()));
                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB4()));

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
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

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
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


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
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


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
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));


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
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(4).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(4).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(4).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(4).getB4() + unitRateModelDbArrayList.get(5).getB4()));

                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                } else {
                    if (benifisheryDataModelDbArrayList != null && benifisheryDataModelDbArrayList.size() > 0) {
                        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
//                            benifisheryDataModelDbArrayList.get(i).setUnitrateofmeal(String.valueOf(0.15 + 2.38 + 2.96 + 1.13 + 0.0));

                            benifisheryDataModelDbArrayList.get(0).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB1() + unitRateModelDbArrayList.get(1).getB1() + unitRateModelDbArrayList.get(2).getB1()
                                    + unitRateModelDbArrayList.get(3).getB1() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(1).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB2() + unitRateModelDbArrayList.get(1).getB2() + unitRateModelDbArrayList.get(2).getB2()
                                    + unitRateModelDbArrayList.get(3).getB2() + unitRateModelDbArrayList.get(5).getB1()));

                            benifisheryDataModelDbArrayList.get(2).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB3() + unitRateModelDbArrayList.get(1).getB3() + unitRateModelDbArrayList.get(2).getB3()
                                    + unitRateModelDbArrayList.get(3).getB3() + unitRateModelDbArrayList.get(5).getB3()));

                            benifisheryDataModelDbArrayList.get(3).setUnitrateofmeal(String.valueOf(unitRateModelDbArrayList.get(0).getB4() + unitRateModelDbArrayList.get(1).getB4() + unitRateModelDbArrayList.get(2).getB4()
                                    + unitRateModelDbArrayList.get(3).getB4() + unitRateModelDbArrayList.get(5).getB4()));


                        }
                        updateList(benifisheryDataModelDbArrayList);
                    }
                }
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.newCall1 = false;
                if ((ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    if (arrayListVillage1 != null) {
                        if (arrayListVillage1.size() == 2) {
                            Toast.makeText(getActivity(), "You have already 2 photo", Toast.LENGTH_SHORT).show();
                        } else if (arrayListVillage1.size() == 1) {
                            Constant.maxAttachment = 1;
                            attchmemntPopup(getActivity());
                        } else {
                            Constant.maxAttachment = 2;
                            attchmemntPopup(getActivity());
                        }
                    } else {
                        Constant.maxAttachment = 2;
                        attchmemntPopup(getActivity());
                    }
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
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(yearSelect) <= year) {
                    if (monthSeleted - 1 <= month) {
                        ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                        Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                        Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode())
                                        , Condition.prop("month").eq(BenifisheryDataModelDbSendRec.getMonth()),
                                        Condition.prop("year").eq(BenifisheryDataModelDbSendRec.getYear())).list();
                        if (arrayListVillage1 != null && arrayListVillage1.size() > 0) {

                            for (int i = 0; i < arrayListVillage1.size(); i++) {
                                String id1 = UUID.randomUUID().toString();
                                ImageSaveModel imageSaveModel = new ImageSaveModel();
                                imageSaveModel.setAwccode(arrayListVillage1.get(i).getAwccode());
                                imageSaveModel.setPanchyatcode(arrayListVillage1.get(i).getPanchyatcode());
                                imageSaveModel.setVocode(arrayListVillage1.get(i).getVocode());
                                imageSaveModel.setIsuploadtoserver("false");
                                imageSaveModel.setImagename(arrayListVillage1.get(i).getImagename());
                                imageSaveModel.setImagebyte(arrayListVillage1.get(i).getImagebyte());
                                imageSaveModel.setMonth(monthSeleted + "");
                                imageSaveModel.setGuid(id1);
                                ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                imageSaveModel.setCreatedby(loginModelDbs.get(0).getUsername());
                                imageSaveModel.setYear(yearSelect);
                                arrayListVillage1.get(i).delete();
                                imageSaveModel.save();
                            }

                            for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                                String id = UUID.randomUUID().toString();
                                if (benifisheryDataModelDbSends != null && benifisheryDataModelDbSends.size() > 0) {
                                    benifisheryDataModelDbSends.get(i).setAaganwaricode(prefManager.getPrefAaganwariCode());
                                    benifisheryDataModelDbSends.get(i).setPanchyatcode(prefManager.getPrefPanchyatCode());
                                    benifisheryDataModelDbSends.get(i).setVocode(prefManager.getPREF_VOCode());
                                    benifisheryDataModelDbSends.get(i).setMonth(monthSeleted + "");
                                    benifisheryDataModelDbSends.get(i).setYear(yearSelect);
                                    benifisheryDataModelDbSends.get(i).setRemarks(editTextRemarks.getText().toString());
                                    benifisheryDataModelDbSends.get(i).setGuid(id);
                                    benifisheryDataModelDbSends.get(i).setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                    benifisheryDataModelDbSends.get(i).setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                    benifisheryDataModelDbSends.get(i).setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                    benifisheryDataModelDbSends.get(i).setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
                                    benifisheryDataModelDbSends.get(i).setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                    benifisheryDataModelDbSends.get(i).setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                    ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                                    benifisheryDataModelDbSends.get(i).setCreatedby(loginModelDbs.get(0).getUsername());
                                    benifisheryDataModelDbSends.get(i).setCreatedon(benifisheryDataModelDbArrayList.get(i).getCreatedon());
                                    benifisheryDataModelDbSends.get(i).setIsuploadtoserver("false");
                                    benifisheryDataModelDbSends.get(i).save();

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                            Toast.makeText(getActivity(), getString(R.string.update_data_toServer), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.select_image_validation), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Select current month", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please Select current Year", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return rootView;
    }

    private int monthSeleted;
    private String yearSelect = null;

    private void setId() {
        monthSpiner = rootView.findViewById(R.id.sppinermonth);
        imageLayout = (LinearLayout) rootView.findViewById(R.id.image_layout);
        editTextRemarks = (EditText) rootView.findViewById(R.id.editTextRemarks);
        totalLyout = (LinearLayout) rootView.findViewById(R.id.totalLyout);
        tableLayout = (LinearLayout) rootView.findViewById(R.id.tableLayout);
        saveData = (Button) rootView.findViewById(R.id.saveData);
        textViewtotalAll = (TextView) rootView.findViewById(R.id.totalAll);
        attachmentRecycler = (RecyclerView) rootView.findViewById(R.id.attachmentRecycler);
        recyclerViewBenifishery = (RecyclerView) rootView.findViewById(R.id.recyclerviewBenifishry);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        yearSppiner = rootView.findViewById(R.id.sppinerYear);
        uploadImage = rootView.findViewById(R.id.uploadImage);
        butonLayout = rootView.findViewById(R.id.butonLayout);
    }

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
                            //1st method
                   /* Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = getFile();
                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                    startActivityForResult(camera_intent, 0);*/
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                            //second Method
                           /* Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivityForResult(intent, 0);*/
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
                if ((ContextCompat.checkSelfPermission(getActivity(),
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
        /*ImageSaveModel imageSaveModel = (ImageSaveModel) data;
        arrayListVillage1.remove(imageSaveModel);*/

        //arrayListVillage1.get(itemId).delete();
       /* ImageSaveModel author = ImageSaveModel.findById(ImageSaveModel.class, imageSaveModel.getId());
        author.delete();*/
        // onResume();
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
                    Constant.finalbytes.clear();
                    Constant.finalnames.clear();
                    Constant.finalsizes.clear();
                    Constant.finaltypes.clear();
                    Constant.finalbytes.add(encodedBase64);
                    Constant.finalnames.add(System.currentTimeMillis() + ".jpg");
                    Constant.finaltypes.add("jpeg");
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
        MainActivity.newCall1 = false;
        CartCache.getInstance().setImageSaveModels(null);
    }

}

