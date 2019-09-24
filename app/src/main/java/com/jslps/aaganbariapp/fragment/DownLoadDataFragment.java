package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.util.Log;
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

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.DialogUtil;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.ReprtsListRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.DownloadModelSend;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.ReportDisplayFormModel;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.jslps.aaganbariapp.services.ApiServices;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DownLoadDataFragment extends BaseFragment implements OnFragmentListItemSelectListener {
    Spinner sppinerMonth, sppinerYear, sppinerVillage, sppinerPanchyat;
    private View rootView;
    private RecyclerView recyclerViewVoMember;
    ArrayList<PanchyatDataModelDb> panchyatDataModelDbArrayList;
    ArrayList<VOListDataModelDb> voListDataModelDbArrayList;
    Button getDataFromServer;
    String panchyatCode, vocode, monthSelected, yearSelected;
    ArrayList<VOListDataModelDb> voListDataModelDbArrayListSpiner = new ArrayList<>();

    public DownLoadDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.download_data)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.FRAGMENT_DATA_DOWNLOAD);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.datadownload_home, container, false);
        setId();
        panchyatDataModelDbArrayList = (ArrayList<PanchyatDataModelDb>) Select.from(PanchyatDataModelDb.class).list();
        //voListDataModelDbArrayList = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).list();

        ArrayAdapter<PanchyatDataModelDb> dataAdapter = new ArrayAdapter<PanchyatDataModelDb>(getActivity(), android.R.layout.simple_spinner_item, panchyatDataModelDbArrayList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sppinerPanchyat.setAdapter(dataAdapter);

        ArrayList<String> arrayListMonth = new ArrayList<String>();
        arrayListMonth.add("All");
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
        arrayListYear.add("All");
        arrayListYear.add("2019");
        arrayListYear.add("2020");

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        System.out.println("" + year + "fvhfdd" + month);

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListMonth);

        // Drop down layout style - list view with radio button
        dataAdapterMonth.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        sppinerMonth.setAdapter(dataAdapterMonth);
        ArrayAdapter<String> dataAdapterYear = new ArrayAdapter<String>(getActivity(), R.layout.dialog_spinner_item, arrayListYear);

        // Drop down layout style - list view with radio button
        dataAdapterYear.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);

        // attaching data adapter to spinner
        sppinerYear.setAdapter(dataAdapterYear);
        if (year == 2019) {
            sppinerYear.setSelection(1);
        } else if (year == 2019) sppinerYear.setSelection(2);


        sppinerMonth.setSelection(month + 1);
        sppinerPanchyat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                PanchyatDataModelDb panchyatDataModelDb = (PanchyatDataModelDb) adapterView.getItemAtPosition(i);
                panchyatCode = panchyatDataModelDb.getClustercode();

                voListDataModelDbArrayListSpiner = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).
                        where(Condition.prop("panchayatcode").eq(panchyatCode)).list();
                voListDataModelDbArrayListSpiner.add(0, new VOListDataModelDb("All"));
                ArrayAdapter<VOListDataModelDb> dataAdapterPanchayat = new ArrayAdapter<VOListDataModelDb>(getActivity(), android.R.layout.simple_list_item_1, voListDataModelDbArrayListSpiner);

                // Drop down layout style - list view with radio button
                dataAdapterPanchayat.setDropDownViewResource(android.R.layout.simple_list_item_1);

                // attaching data adapter to spinner
                sppinerVillage.setAdapter(dataAdapterPanchayat);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sppinerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    monthSelected = "";
                else
                    monthSelected = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sppinerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    yearSelected = "";
                } else if (i == 1) {
                    yearSelected = "2019";
                } else yearSelected = "2020";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sppinerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    vocode = "";
                } else {
                    VOListDataModelDb panchyatDataModelDb = (VOListDataModelDb) adapterView.getItemAtPosition(i);
                    vocode = panchyatDataModelDb.getVocode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getDataFromServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.displayProgress(getActivity());
                Gson gson = new GsonBuilder().setLenient().create();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //comment in live build and uncomment in uat
                builder.interceptors().add(interceptor);

                builder.connectTimeout(120, TimeUnit.SECONDS);
                builder.readTimeout(120, TimeUnit.SECONDS);
                OkHttpClient client = builder.build();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).client(client).build();
                ApiServices apiServices = retrofit.create(ApiServices.class);
                Call<String> changePhotoResponseModelCall = apiServices.getTabletMasterDownLoad("downloadANGANWADIData", panchyatCode, vocode, monthSelected, yearSelected);
                changePhotoResponseModelCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        DialogUtil.stopProgressDisplay();
                        Gson gson = new Gson();
                        Log.v("Response prof :", "hgfgfrhgs" + response.body());
                        String fullResponse = response.body();
                        String XmlString = fullResponse.substring(fullResponse.indexOf("\">") + 2);
                        String result = XmlString.replaceAll("</string>", "");
                        System.out.print("fhrjfghf" + result);
                        DownloadModelSend mStudentObject1 = gson.fromJson(result, DownloadModelSend.class);
                        System.out.println("vvh" + gson.toJson(mStudentObject1));
                        boolean snackbarerror = false;
                        boolean snackbarSucess = false;
                        for (int i = 0; i < mStudentObject1.getMaster().size(); i++) {
                            ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendArrayList = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class).
                                    where(Condition.prop("panchyatcode").eq(mStudentObject1.getMaster().get(i).getPanchyatcode()),
                                            Condition.prop("aaganwaricode").eq(mStudentObject1.getMaster().get(i).getAaganwaricode()),
                                            Condition.prop("month").eq(mStudentObject1.getMaster().get(i).getMonth()),
                                            Condition.prop("year").eq(mStudentObject1.getMaster().get(i).getYear()),
                                            Condition.prop("vocode").eq(mStudentObject1.getMaster().get(i).getVocode()),
                                            Condition.prop("guid").eq(mStudentObject1.getMaster().get(i).getGuid())).list();
                            if (benifisheryDataModelDbSendArrayList != null && benifisheryDataModelDbSendArrayList.size() > 3) {
                                snackbarerror = true;
                            } else {
                                ArrayList<PanchyatDataModelDb> panchyatDataModelDbs = (ArrayList<PanchyatDataModelDb>) Select.from(PanchyatDataModelDb.class).
                                        where(Condition.prop("clustercode").eq(mStudentObject1.getMaster().get(i).getPanchyatcode())).list();
                                ArrayList<VOListDataModelDb> voListDataModelDbArrayList1 = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class).
                                        where(Condition.prop("vocode").eq(mStudentObject1.getMaster().get(i).getVocode())).list();
                                ArrayList<AanganWariModelDb> aanganWariModelDbArrayList = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class).
                                        where(Condition.prop("awid").eq(mStudentObject1.getMaster().get(i).getAaganwaricode())).list();
                                System.out.println("fbvhjsfdvbhsd" + mStudentObject1.getMaster().get(i).getAaganwaricode());
                                BenifisheryDataModelDbSend benifisheryDataModelDbSend = new BenifisheryDataModelDbSend(mStudentObject1.getMaster().get(i).getBenfid(),
                                        mStudentObject1.getMaster().get(i).getBenfname(), mStudentObject1.getMaster().get(i).getCreatedby(), mStudentObject1.getMaster().get(i).getCreatedon(),
                                        mStudentObject1.getMaster().get(i).getNoofmeal(), mStudentObject1.getMaster().get(i).getUnitrateofmeal(), mStudentObject1.getMaster().get(i).getNoofbenf(),
                                        mStudentObject1.getMaster().get(i).getAmount(), mStudentObject1.getMaster().get(i).getMonth(), mStudentObject1.getMaster().get(i).getYear(),
                                        mStudentObject1.getMaster().get(i).getRemarks(), mStudentObject1.getMaster().get(i).getGuid(), mStudentObject1.getMaster().get(i).getPanchyatcode(),
                                        mStudentObject1.getMaster().get(i).getVocode(), mStudentObject1.getMaster().get(i).getAaganwaricode(), panchyatDataModelDbs.get(0).getClustername(),
                                        voListDataModelDbArrayList1.get(0).getVoshg(),
                                        aanganWariModelDbArrayList.get(0).getAnganwadiname(), "true");
                                benifisheryDataModelDbSend.save();
                                snackbarSucess = true;
                                snackbarerror=false;
                            }
                        }
                        if (snackbarerror) {
                            if (!snackbarSucess) {
                                Snackbar.with(getActivity(), null)
                                        .type(Type.ERROR)
                                        .message("You have already data")
                                        .duration(Duration.SHORT)
                                        .fillParent(true)
                                        .textAlign(Align.CENTER)
                                        .show();
                            }

                        }
                        if (snackbarSucess) {
                            Snackbar.with(getActivity(), null)
                                    .type(Type.SUCCESS)
                                    .message("Data Download Successfully")
                                    .duration(Duration.SHORT)
                                    .fillParent(true)
                                    .textAlign(Align.CENTER)
                                    .show();
                        }
                        for (int j = 0; j < mStudentObject1.getTable1().size(); j++) {
                            ArrayList<ImageSaveModel> imageSaveModelArrayList = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class).
                                    where(Condition.prop("panchyatcode").eq(mStudentObject1.getTable1().get(j).getPanchyatcode()),
                                            Condition.prop("awccode").eq(mStudentObject1.getTable1().get(j).getAwccode()),
                                            Condition.prop("month").eq(mStudentObject1.getTable1().get(j).getMonth()),
                                            Condition.prop("year").eq(mStudentObject1.getTable1().get(j).getYear()),
                                            Condition.prop("vocode").eq(mStudentObject1.getTable1().get(j).getVocode()),
                                            Condition.prop("guid").eq(mStudentObject1.getTable1().get(j).getGuid())).list();
                            if (imageSaveModelArrayList != null && imageSaveModelArrayList.size() > 1) {

                            } else {

                                ImageSaveModel benifisheryDataModelDbSend = new ImageSaveModel(mStudentObject1.getTable1().get(j).getImagebyte(),
                                        mStudentObject1.getTable1().get(j).getImagename(), null, null,
                                        mStudentObject1.getTable1().get(j).getGuid(), mStudentObject1.getTable1().get(j).getPanchyatcode(),
                                        mStudentObject1.getTable1().get(j).getVocode(), mStudentObject1.getTable1().get(j).getAwccode(),
                                        mStudentObject1.getTable1().get(j).getMonth(), mStudentObject1.getTable1().get(j).getYear(), "true"
                                        ,mStudentObject1.getTable1().get(j).getCreatedby());
                                benifisheryDataModelDbSend.save();
                            }
                        }
                        /*ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) BenifisheryDataModelDbSend.listAll(BenifisheryDataModelDbSend.class);
                        ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
                        ArrayList<ReportDisplayFormModel> arrayListReportDisplayFormModel = new ArrayList<>();
                        ArrayList<String> arrayListString = new ArrayList<>();
                        for (int i = 0; i < benifisheryDataModelDbSends.size(); i++) {
                            BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
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
                        }*/
                        // updateAdapter(arrayListReportDisplayFormModel);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        DialogUtil.stopProgressDisplay();
                        Snackbar.with(getActivity(), null)
                                .type(Type.ERROR)
                                .message(t.toString())
                                .duration(Duration.SHORT)
                                .fillParent(true)
                                .textAlign(Align.CENTER)
                                .show();
                    }
                });
            }
        });
        return rootView;
    }

    private void updateAdapter(ArrayList<ReportDisplayFormModel> arrayListReportDisplayFormModel) {
        if (arrayListReportDisplayFormModel != null && arrayListReportDisplayFormModel.size() > 0) {
            recyclerViewVoMember.setVisibility(View.VISIBLE);
            ReprtsListRecyclerviewAdapter panchyatRecyclerviewAdapter = new ReprtsListRecyclerviewAdapter(getActivity(), arrayListReportDisplayFormModel);
            panchyatRecyclerviewAdapter.setListner(this);
            recyclerViewVoMember.setAdapter(panchyatRecyclerviewAdapter);
        } else {
            recyclerViewVoMember.setVisibility(View.GONE);
            Snackbar.with(getActivity(), null)
                    .type(Type.ERROR)
                    .message(getString(R.string.no_record))
                    .duration(Duration.SHORT)
                    .fillParent(true)
                    .textAlign(Align.CENTER)
                    .show();
        }
    }

    private void setId() {
        sppinerMonth = rootView.findViewById(R.id.sppinerMonth);
        sppinerYear = rootView.findViewById(R.id.sppinerYear);
        sppinerVillage = rootView.findViewById(R.id.sppinerVo);
        sppinerPanchyat = rootView.findViewById(R.id.sppinerPanchyat);
        recyclerViewVoMember = rootView.findViewById(R.id.recylerviewVoMember);
        getDataFromServer = rootView.findViewById(R.id.getData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewVoMember.setLayoutManager(mLayoutManager);
    }


    @Override
    public void onListItemSelected(int itemId, Object data) {
        Constant.editFlag = false;
        ReportDisplayFormModel benifisheryDataModelDbSend1 = (ReportDisplayFormModel) data;
        mListener.onFragmentInteraction(Constant.FRAGMENT_ENTRY_EDIT, benifisheryDataModelDbSend1);
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {

    }
}

