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
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class EntryFormFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    private EditText editTextRemarks;
    Spinner monthSpiner, yearSppiner;
    ArrayList<String> arrayListMonth;
    ArrayList<String> arrayListYear;
    Button uploadImage;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    public static RecyclerView attachmentRecycler;
    public static LinearLayout imageLayout;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int REQUEST_PERMISSIONS_CAMERA = 101;
    RecyclerView recyclerViewBenifishery;
    ArrayList<BenifisheryDataModelDb> benifisheryDataModelDbArrayList;

    public static TextView textViewtotalAll;
    Button saveData;
    PrefManager prefManager;
    int SELECT_FILE = 4;
    static AanganWariModelDb aanganWariModelDbrec;
    Switch switchtrue;
    LinearLayout totalLyout, tableLayout;

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
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(true, aanganWariModelDbrec.getAnganwadiname()));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.ENTRY_FORM_FRAGNMENT);
        benifisheryDataModelDbArrayList = (ArrayList<BenifisheryDataModelDb>) BenifisheryDataModelDb.listAll(BenifisheryDataModelDb.class);

       /* if (arrayListVillage1 != null && arrayListVillage1.size() > 0) {
            Constant.finalbytes.clear();
            Constant.finalnames.clear();
            Constant.finalsizes.clear();
            Constant.finaltypes.clear();
            for (int i = 0; i < arrayListVillage1.size(); i++) {

                Constant.finalbytes.add(arrayListVillage1.get(i).getImgebytes());
                Constant.finalsizes.add(arrayListVillage1.get(i).getFinalsizes());
                Constant.finaltypes.add(arrayListVillage1.get(i).getFinaltypes());
                Constant.finalnames.add(arrayListVillage1.get(i).getFinalnames());
            }
        }*/
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
        recyclerViewBenifishery.setAdapter(benifisheryRowRecyclerviewAdapter);
        totalAll = 0.0;
        for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
            totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                    Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
        }
        textViewtotalAll.setText(totalAll.toString());
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
        textViewtotalAll = (TextView) rootView.findViewById(R.id.totalAll);
        attachmentRecycler = (RecyclerView) rootView.findViewById(R.id.attachmentRecycler);
        recyclerViewBenifishery = (RecyclerView) rootView.findViewById(R.id.recyclerviewBenifishry);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        yearSppiner = rootView.findViewById(R.id.sppinerYear);
        uploadImage = rootView.findViewById(R.id.uploadImage);
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
        int year = c.get(Calendar.YEAR);
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
        if (year == 2019) {
            yearSppiner.setSelection(0);
        } else yearSppiner.setSelection(1);

        monthSpiner.setSelection(month);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if (switchtrue.isChecked()) {
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
                    benifisheryDataModelDbSend.save();
                    Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                        ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                        Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                        Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode())).list();
                        if (benifisheryDataModelDbSendss != null && benifisheryDataModelDbSendss.size() > 0) {
                            for (int l = 0; l < benifisheryDataModelDbSendss.size(); l++) {
                                if (Integer.parseInt(benifisheryDataModelDbSendss.get(l).getMonth()) != monthSeleted) {
                                    if (yearSelect.equalsIgnoreCase("2019")) {
                                        if (monthSeleted >= month) {
                                            ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
                                            ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                                    .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                                            Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                                            Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode())).list();
                                            if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                                                for (int j = 0; j < Constant.finalbytes.size(); j++) {
                                                    ImageSaveModel imageSaveModel = new ImageSaveModel();
                                                    imageSaveModel.setAwccode(prefManager.getPrefAaganwariCode());
                                                    imageSaveModel.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                                    imageSaveModel.setVocode(prefManager.getPREF_VOCode());
                                                    imageSaveModel.setImagename(Constant.finalnames.get(j));
                                                    imageSaveModel.setImagebyte(Constant.finalbytes.get(j));
                                                    imageSaveModel.setMonth(monthSeleted+"");
                                                    imageSaveModel.setYear(yearSelect);
                                                    imageSaveModel.save();
                                                }
                                            }
                                            for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                                                String id = UUID.randomUUID().toString();
                                                if (benifisheryDataModelDbSends != null && benifisheryDataModelDbSends.size() > 0) {

                                                    benifisheryDataModelDbSends.get(i).setAaganwaricode(prefManager.getPrefAaganwariCode());
                                                    benifisheryDataModelDbSends.get(i).setPanchyatcode(prefManager.getPrefPanchyatCode());
                                                    benifisheryDataModelDbSends.get(i).setVocode(prefManager.getPREF_VOCode());
                                                    benifisheryDataModelDbSends.get(i).setAaganwariname(prefManager.getPrefAaganwariNAME());
                                                    benifisheryDataModelDbSends.get(i).setPanchyatname(prefManager.getPrefPanchyatNAME());
                                                    benifisheryDataModelDbSends.get(i).setVoname(prefManager.getPREF_VONAME());
                                                    benifisheryDataModelDbSends.get(i).setMonth(monthSeleted + "");
                                                    benifisheryDataModelDbSends.get(i).setYear(yearSelect);
                                                    benifisheryDataModelDbSends.get(i).setRemarks(editTextRemarks.getText().toString());
                                                    benifisheryDataModelDbSends.get(i).setGuid(id);
                                                    benifisheryDataModelDbSends.get(i).setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                                    benifisheryDataModelDbSends.get(i).setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                                    benifisheryDataModelDbSends.get(i).setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                                    benifisheryDataModelDbSends.get(i).setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                                    benifisheryDataModelDbSends.get(i).setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                                    benifisheryDataModelDbSends.get(i).setCreatedby(benifisheryDataModelDbArrayList.get(i).getCreatedby());
                                                    System.out.println("new Gson"+getDate(System.currentTimeMillis()));
                                                    benifisheryDataModelDbSends.get(i).setCreatedon(getDate(System.currentTimeMillis()));
                                                    benifisheryDataModelDbSends.get(i).setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
                                                    benifisheryDataModelDbSends.get(i).setIsuploadtoserver("false");
                                                    benifisheryDataModelDbSends.get(i).save();

                                                    Constant.finalbytes.clear();
                                                    Constant.finalnames.clear();
                                                    Constant.finalsizes.clear();
                                                    Constant.finaltypes.clear();
                                                    Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);

                                                } else {

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
                                                    System.out.println("new Gson"+getDate(System.currentTimeMillis()));
                                                    benifisheryDataModelDbSend.setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                                    benifisheryDataModelDbSend.setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                                    benifisheryDataModelDbSend.setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                                    benifisheryDataModelDbSend.setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                                    benifisheryDataModelDbSend.setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                                    benifisheryDataModelDbSend.setCreatedby(benifisheryDataModelDbArrayList.get(i).getCreatedby());
                                                    benifisheryDataModelDbSend.setCreatedon(getDate(System.currentTimeMillis()));
                                                    benifisheryDataModelDbSend.setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
                                                    benifisheryDataModelDbSends.get(i).setIsuploadtoserver("false");
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
                                            Toast.makeText(getActivity(), getString(R.string.month_validation), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.already_record), Toast.LENGTH_SHORT).show();
                                }
                            }


                        } else {
                            if (yearSelect.equalsIgnoreCase("2019")) {
                                if (monthSeleted >= month) {
                                    ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
                                    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                            .where(Condition.prop("panchyatcode").eq(prefManager.getPrefPanchyatCode()),
                                                    Condition.prop("vocode").eq(prefManager.getPREF_VOCode()),
                                                    Condition.prop("aaganwaricode").eq(prefManager.getPrefAaganwariCode())).list();
                                    if (Constant.finalbytes != null && Constant.finalbytes.size() > 0) {
                                        for (int j = 0; j < Constant.finalbytes.size(); j++) {
                                            ImageSaveModel imageSaveModel = new ImageSaveModel();
                                            imageSaveModel.setAwccode(prefManager.getPrefAaganwariCode());
                                            imageSaveModel.setPanchyatcode(prefManager.getPrefPanchyatCode());
                                            imageSaveModel.setVocode(prefManager.getPREF_VOCode());

                                            imageSaveModel.setImagename(Constant.finalnames.get(j));
                                            imageSaveModel.setImagebyte(Constant.finalbytes.get(j));
                                            imageSaveModel.setMonth(monthSeleted+"");
                                            imageSaveModel.setYear(yearSelect);
                                            imageSaveModel.save();

                                        }
                                    }
                                    for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                                        String id = UUID.randomUUID().toString();
                                        if (benifisheryDataModelDbSends != null && benifisheryDataModelDbSends.size() > 0) {

                                            benifisheryDataModelDbSends.get(i).setAaganwaricode(prefManager.getPrefAaganwariCode());
                                            benifisheryDataModelDbSends.get(i).setPanchyatcode(prefManager.getPrefPanchyatCode());
                                            benifisheryDataModelDbSends.get(i).setVocode(prefManager.getPREF_VOCode());
                                            benifisheryDataModelDbSends.get(i).setAaganwariname(prefManager.getPrefAaganwariNAME());
                                            benifisheryDataModelDbSends.get(i).setPanchyatname(prefManager.getPrefPanchyatNAME());
                                            benifisheryDataModelDbSends.get(i).setVoname(prefManager.getPREF_VONAME());


                                            benifisheryDataModelDbSends.get(i).setMonth(monthSeleted + "");
                                            benifisheryDataModelDbSends.get(i).setYear(yearSelect);
                                            benifisheryDataModelDbSends.get(i).setRemarks(editTextRemarks.getText().toString());
                                            benifisheryDataModelDbSends.get(i).setGuid(id);
                                            benifisheryDataModelDbSends.get(i).setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                            benifisheryDataModelDbSends.get(i).setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                            benifisheryDataModelDbSends.get(i).setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                            benifisheryDataModelDbSends.get(i).setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                            benifisheryDataModelDbSends.get(i).setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                            benifisheryDataModelDbSends.get(i).setCreatedby(benifisheryDataModelDbArrayList.get(i).getCreatedby());
                                            benifisheryDataModelDbSends.get(i).setCreatedon(getDate(System.currentTimeMillis()));
                                            benifisheryDataModelDbSends.get(i).setNoofmeal(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
                                            benifisheryDataModelDbSends.get(i).setIsuploadtoserver("false");
                                            benifisheryDataModelDbSends.get(i).save();

                                            Constant.finalbytes.clear();
                                            Constant.finalnames.clear();
                                            Constant.finalsizes.clear();
                                            Constant.finaltypes.clear();
                                            Toast.makeText(getActivity(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        } else {

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
                                            benifisheryDataModelDbSend.setBenfid(benifisheryDataModelDbArrayList.get(i).getBenfid());
                                            benifisheryDataModelDbSend.setAmount(benifisheryDataModelDbArrayList.get(i).getAmount());
                                            benifisheryDataModelDbSend.setNoofbenf(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                                            benifisheryDataModelDbSend.setUnitrateofmeal(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal());
                                            benifisheryDataModelDbSend.setBenfname(benifisheryDataModelDbArrayList.get(i).getBenfname());
                                            benifisheryDataModelDbSend.setCreatedby(benifisheryDataModelDbArrayList.get(i).getCreatedby());
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
                                    Toast.makeText(getActivity(), getString(R.string.month_validation), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.select_image_validation), Toast.LENGTH_SHORT).show();
                    }
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

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

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
            Constant.finalnames.add(System.currentTimeMillis() +  ".jpg");
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
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

}

