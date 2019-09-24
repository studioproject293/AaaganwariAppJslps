package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

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
import com.jslps.aaganbariapp.adapter.SyncWithServerRecyclerviewAdapter;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.DataSaveModel1;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.services.BenifisheryDataUpload;
import com.jslps.aaganbariapp.services.ImageUploadToServer;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SyncWithServerFragment extends BaseFragment implements OnFragmentListItemSelectListener {

    private View rootView;
    RecyclerView recyclerViewBenifishery;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSends;
    Button uploaddata;
    TableLayout tableLayout;

    public SyncWithServerFragment() {
        // Required empty public constructor
    }

    ArrayList<BenifisheryDataModelDbSend> newArrr = new ArrayList<>();

    public static SyncWithServerFragment newInstance() {
        return new SyncWithServerFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.sync_with_server)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.FRAGMENT_SYNC_WITH_SERVER);
        benifisheryDataModelDbSends = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class).list();
        ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                .where(Condition.prop("isuploadtoserver").eq("false")).list();
        ArrayList<DataSaveModel1> dataSaveModel1ArrayList = new ArrayList<DataSaveModel1>();
        ArrayList<DataSaveModel1> adaptersendList = new ArrayList<DataSaveModel1>();
        ArrayList<String> ympStringString = new ArrayList<>();
        for (int i = 0; i < benifisheryDataModelDbSendss.size(); i++) {
            BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSendss.get(i);
            String ymp = benifisheryDataModelDbSend.getPanchyatcode() + benifisheryDataModelDbSend.getYear() + benifisheryDataModelDbSend.getMonth();

            if (!ympStringString.contains(ymp)) {
                ympStringString.add(ymp);
                DataSaveModel1 dataSaveModel1 = new DataSaveModel1();
                dataSaveModel1.setMonth(benifisheryDataModelDbSend.getMonth());
                dataSaveModel1.setYear(benifisheryDataModelDbSend.getYear());
                dataSaveModel1.setPancayatcode(benifisheryDataModelDbSend.getPanchyatcode());
                dataSaveModel1.setPanchayatname(benifisheryDataModelDbSend.getPanchyatname());
                dataSaveModel1ArrayList.add(dataSaveModel1);

            }
        }

        if (dataSaveModel1ArrayList != null && dataSaveModel1ArrayList.size() > 0) {
            for (int i = 0; i < dataSaveModel1ArrayList.size(); i++) {
                DataSaveModel1 dataSaveModel1 = dataSaveModel1ArrayList.get(i);
                String pCode = dataSaveModel1.getPancayatcode();
                String year = dataSaveModel1.getYear();
                String month = dataSaveModel1.getMonth();
                String pName = dataSaveModel1.getPanchayatname();
                ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendArrayList = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                        .where(Condition.prop("panchyatcode").eq(pCode))
                        .where(Condition.prop("year").eq(year))
                        .where(Condition.prop("month").eq(month))
                        .where(Condition.prop("isuploadtoserver").eq("false"))
                        .list();
                ArrayList<String> aaganwariListString = new ArrayList<>();
                int count = 0;
                for (int j = 0; j < benifisheryDataModelDbSendArrayList.size(); j++) {
                    String aCode = benifisheryDataModelDbSendArrayList.get(j).getAaganwaricode();
                    if (!aaganwariListString.contains(aCode)) {
                        aaganwariListString.add(aCode);
                        count++;
                    }
                }
                DataSaveModel1 dataSaveModel11 = new DataSaveModel1();
                dataSaveModel11.setPanchayatname(pName);
                dataSaveModel11.setPancayatcode(pCode);
                dataSaveModel11.setYear(year);
                dataSaveModel11.setMonth(month);
                dataSaveModel11.setAaganwaricount(count + "");
                adaptersendList.add(dataSaveModel11);

            }
        }
        System.out.println("dfhdsghfdsghfs" + new Gson().toJson(adaptersendList));
        updateList(adaptersendList);
    }

    private void updateList(ArrayList<DataSaveModel1> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            recyclerViewBenifishery.setVisibility(View.VISIBLE);
            tableLayout.setVisibility(View.VISIBLE);
            uploaddata.setVisibility(View.VISIBLE);
            SyncWithServerRecyclerviewAdapter panchyatRecyclerviewAdapter = new SyncWithServerRecyclerviewAdapter(getActivity(), arrayList);
            panchyatRecyclerviewAdapter.setListner(this);
            recyclerViewBenifishery.setAdapter(panchyatRecyclerviewAdapter);
        } else {
            recyclerViewBenifishery.setVisibility(View.GONE);
            tableLayout.setVisibility(View.GONE);
            uploaddata.setVisibility(View.GONE);
            Snackbar.with(getActivity(), null)
                    .type(Type.ERROR)
                    .message(getString(R.string.no_record_sync))
                    .duration(Duration.SHORT)
                    .fillParent(true)
                    .textAlign(Align.CENTER)
                    .show();

        }

    }

    ArrayList<BenifisheryDataModelDbSend> dataModelDbSendArrayListData = new ArrayList<>();
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendArrayListSendToServer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.syncwithserver, container, false);
        recyclerViewBenifishery = rootView.findViewById(R.id.syncwithserverrecyclerview);
        uploaddata = rootView.findViewById(R.id.uploadData);
        tableLayout = rootView.findViewById(R.id.tableLayout);
        uploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DialogUtil.isConnectionAvailable(getActivity())) {
                    System.out.println("nw Gson" + new Gson().toJson(saveModel1ArrayList));

                    if (saveModel1ArrayList != null && saveModel1ArrayList.size() > 0) {
                        for (int i = 0; i < saveModel1ArrayList.size(); i++) {

                            benifisheryDataModelDbSendArrayListSendToServer = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                    .where(Condition.prop("panchyatcode").eq(saveModel1ArrayList.get(i).getPancayatcode()))
                                    .where(Condition.prop("year").eq(saveModel1ArrayList.get(i).getYear()))
                                    .where(Condition.prop("month").eq(saveModel1ArrayList.get(i).getMonth()))
                                    .where(Condition.prop("isuploadtoserver").eq("false"))
                                    .list();
                            for (int l = 0; l < benifisheryDataModelDbSendArrayListSendToServer.size(); l++) {
                                BenifisheryDataModelDbSend benifisheryDataModelDbSend = new BenifisheryDataModelDbSend();
                                benifisheryDataModelDbSend.setPanchyatname(benifisheryDataModelDbSendArrayListSendToServer.get(l).getPanchyatname());
                                benifisheryDataModelDbSend.setAaganwariname(benifisheryDataModelDbSendArrayListSendToServer.get(l).getAaganwariname());
                                benifisheryDataModelDbSend.setVoname(benifisheryDataModelDbSendArrayListSendToServer.get(l).getVoname());
                                newArrr.add(benifisheryDataModelDbSend);
                            }
                            for (int j = 0; j < benifisheryDataModelDbSendArrayListSendToServer.size(); j++) {
                                benifisheryDataModelDbSendArrayListSendToServer.get(j).setVoname(null);
                                benifisheryDataModelDbSendArrayListSendToServer.get(j).setAaganwariname(null);
                                benifisheryDataModelDbSendArrayListSendToServer.get(j).setPanchyatname(null);
                                benifisheryDataModelDbSendArrayListSendToServer.get(j).setId(null);
                                benifisheryDataModelDbSendArrayListSendToServer.get(j).setCreatedon(null);
                                benifisheryDataModelDbSendArrayListSendToServer.get(j).setIsuploadtoserver(null);
                            }
                            dataModelDbSendArrayListData.addAll(benifisheryDataModelDbSendArrayListSendToServer);

                        }
                        DialogUtil.displayProgress(getActivity());
                        System.out.println("data send" + new Gson().toJson(dataModelDbSendArrayListData));
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
                        BenifisheryDataUpload apiServices = retrofit.create(BenifisheryDataUpload.class);
                        String data = "{" + "\"AganwadiData\"" + " :" + new Gson().toJson(dataModelDbSendArrayListData) + " }";
                        System.out.println("jdfjhjds" + data);
                        Call<String> benifisherydataUpload = apiServices.benificeryDataUpload(data);
                        benifisherydataUpload.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                DialogUtil.stopProgressDisplay();
                                System.out.println("Response  data" + response.body());
                                String fullResponse = response.body();
                                String XmlString = fullResponse.substring(fullResponse.indexOf("\">") + 2);
                                String result = XmlString.replaceAll("</string>", "");
                                JSONObject jsonObj = null;
                                try {
                                    jsonObj = new JSONObject(result.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    JSONArray categoryObject = jsonObj.getJSONArray("Table");
                                    JSONObject jsonObject = categoryObject.getJSONObject(0);
                                    String Result = jsonObject.getString("RetValue");
                                    if (Result.equalsIgnoreCase("1")) {
                                        uploadImageService();
                                    }else if (Result.equalsIgnoreCase("0")){
                                        Snackbar.with(getActivity(), null)
                                                .type(Type.ERROR)
                                                .message("Data already exist")
                                                .duration(Duration.SHORT)
                                                .fillParent(true)
                                                .textAlign(Align.CENTER)
                                                .show();
                                    } else {
                                        Snackbar.with(getActivity(), null)
                                                .type(Type.ERROR)
                                                .message("Please try again")
                                                .duration(Duration.SHORT)
                                                .fillParent(true)
                                                .textAlign(Align.CENTER)
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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
                } else {
                    Snackbar.with(getActivity(), null)
                            .type(Type.ERROR)
                            .message(getString(R.string.no_internet_connection))
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.CENTER)
                            .show();
                }

            }
        });
        recyclerViewBenifishery.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        return rootView;
    }

    private void uploadImageService() {
        if (DialogUtil.isConnectionAvailable(getActivity())) {
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
            ImageUploadToServer apiServices = retrofit.create(ImageUploadToServer.class);
            ArrayList<ImageSaveModel> imageSaveModelArrayList = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class).list();
            System.out.println("bsdfhkjsdhfvUREFH" + new Gson().toJson(imageSaveModelArrayList));
            final ArrayList<ImageSaveModel> dataModelDbSendArrayList = new ArrayList<>();
            if (saveModel1ArrayList != null && saveModel1ArrayList.size() > 0) {
                for (int i = 0; i < saveModel1ArrayList.size(); i++) {
                    ArrayList<ImageSaveModel> benifisheryDataModelDbSendArrayListImage = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class)
                            .where(Condition.prop("panchyatcode").eq(saveModel1ArrayList.get(i).getPancayatcode()))
                            .where(Condition.prop("year").eq(saveModel1ArrayList.get(i).getYear()))
                            .where(Condition.prop("month").eq(saveModel1ArrayList.get(i).getMonth()))
                            .where(Condition.prop("isuploadtoserver").eq("false"))
                            .list();
                    for (int j = 0; j < benifisheryDataModelDbSendArrayListImage.size(); j++) {
                        benifisheryDataModelDbSendArrayListImage.get(j).setId(null);
                        benifisheryDataModelDbSendArrayListImage.get(j).setIsuploadtoserver(null);
                    }
                    dataModelDbSendArrayList.addAll(benifisheryDataModelDbSendArrayListImage);

                }
            }
            System.out.println("data sendimge" + new Gson().toJson(dataModelDbSendArrayList));

            String data = "{" + "\"AganwadiImages\"" + " :" + new Gson().toJson(dataModelDbSendArrayList) + " }";

            Call<String> imageDatUpload = apiServices.imageUpload(data);
            imageDatUpload.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    DialogUtil.stopProgressDisplay();
                    System.out.println("Response  data" + response.body());
                    String fullResponse = response.body();
                    String XmlString = fullResponse.substring(fullResponse.indexOf("\">") + 2);
                    String result = XmlString.replaceAll("</string>", "");
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray categoryObject = jsonObj.getJSONArray("Table");
                        JSONObject jsonObject = categoryObject.getJSONObject(0);
                        String Result = jsonObject.getString("RetValue");
                        if (Result.equalsIgnoreCase("1")) {
                            for (int i = 0; i < dataModelDbSendArrayList.size(); i++) {
                                dataModelDbSendArrayList.get(i).setIsuploadtoserver("true");
                                dataModelDbSendArrayList.get(i).save();
                            }
                            for (int j = 0; j < dataModelDbSendArrayListData.size(); j++) {
                                dataModelDbSendArrayListData.get(j).setIsuploadtoserver("true");
                                dataModelDbSendArrayListData.get(j).setAaganwariname(newArrr.get(j).getAaganwariname());
                                dataModelDbSendArrayListData.get(j).setPanchyatname(newArrr.get(j).getPanchyatname());
                                dataModelDbSendArrayListData.get(j).setVoname(newArrr.get(j).getVoname());
                                dataModelDbSendArrayListData.get(j).save();
                            }
                            Snackbar.with(getActivity(), null)
                                    .type(Type.SUCCESS)
                                    .message(getString(R.string.save_message))
                                    .duration(Duration.SHORT)
                                    .fillParent(true)
                                    .textAlign(Align.CENTER)
                                    .show();
                            onResume();
                        }else if (Result.equalsIgnoreCase("0")){
                            for (int i = 0; i < dataModelDbSendArrayList.size(); i++) {
                                dataModelDbSendArrayList.get(i).setIsuploadtoserver("false");
                                dataModelDbSendArrayList.get(i).save();
                                System.out.println("benifishery data fail" + new Gson().toJson(benifisheryDataModelDbSends));


                            }
                            for (int j = 0; j < dataModelDbSendArrayListData.size(); j++) {
                                dataModelDbSendArrayListData.get(j).setIsuploadtoserver("false");
                                dataModelDbSendArrayListData.get(j).setAaganwariname(newArrr.get(j).getAaganwariname());
                                dataModelDbSendArrayListData.get(j).setPanchyatname(newArrr.get(j).getPanchyatname());
                                dataModelDbSendArrayListData.get(j).setVoname(newArrr.get(j).getVoname());
                                dataModelDbSendArrayListData.get(j).save();
                            }
                            Snackbar.with(getActivity(), null)
                                    .type(Type.ERROR)
                                    .message("Data already exist")
                                    .duration(Duration.SHORT)
                                    .fillParent(true)
                                    .textAlign(Align.CENTER)
                                    .show();
                        } else {
                            for (int i = 0; i < dataModelDbSendArrayList.size(); i++) {
                                dataModelDbSendArrayList.get(i).setIsuploadtoserver("false");
                                dataModelDbSendArrayList.get(i).save();
                                System.out.println("benifishery data fail" + new Gson().toJson(benifisheryDataModelDbSends));


                            }
                            for (int j = 0; j < dataModelDbSendArrayListData.size(); j++) {
                                dataModelDbSendArrayListData.get(j).setIsuploadtoserver("false");
                                dataModelDbSendArrayListData.get(j).setAaganwariname(newArrr.get(j).getAaganwariname());
                                dataModelDbSendArrayListData.get(j).setPanchyatname(newArrr.get(j).getPanchyatname());
                                dataModelDbSendArrayListData.get(j).setVoname(newArrr.get(j).getVoname());
                                dataModelDbSendArrayListData.get(j).save();
                            }
                            Snackbar.with(getActivity(), null)
                                    .type(Type.ERROR)
                                    .message("Please try again")
                                    .duration(Duration.SHORT)
                                    .fillParent(true)
                                    .textAlign(Align.CENTER)
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
        }else {
            Snackbar.with(getActivity(), null)
                    .type(Type.ERROR)
                    .message(getString(R.string.no_internet_connection))
                    .duration(Duration.SHORT)
                    .fillParent(true)
                    .textAlign(Align.CENTER)
                    .show();
        }
    }

    ArrayList<DataSaveModel1> saveModel1ArrayList = new ArrayList<>();

    @Override
    public void onListItemSelected(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {
    }

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {
        saveModel1ArrayList = (ArrayList<DataSaveModel1>) data;
    }

}

