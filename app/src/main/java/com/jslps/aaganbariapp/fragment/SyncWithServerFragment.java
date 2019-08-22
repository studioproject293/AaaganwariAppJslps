package com.jslps.aaganbariapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

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
        rootView = inflater.inflate(R.layout.syncwithserver, container, false);
        recyclerViewBenifishery = rootView.findViewById(R.id.syncwithserverrecyclerview);
        uploaddata = rootView.findViewById(R.id.uploadData);
        tableLayout = rootView.findViewById(R.id.tableLayout);
        uploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DialogUtil.displayProgress(getActivity());
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
                ArrayList<BenifisheryDataModelDbSend> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class).list();
                for (int i = 0; i < panchyatDataModelDbs.size(); i++) {
                    panchyatDataModelDbs.get(i).setVoname(null);
                    panchyatDataModelDbs.get(i).setAaganwariname(null);
                    panchyatDataModelDbs.get(i).setPanchyatname(null);
                    panchyatDataModelDbs.get(i).setId(null);
                    panchyatDataModelDbs.get(i).setCreatedon(null);
                }

                String data = "{" + "\"AganwadiImagesData\"" + " :" + new Gson().toJson(panchyatDataModelDbs) + " }";
                System.out.println("jdfjhjds" + data);
                Call<String> benifisherydataUpload = apiServices.benificeryDataUpload(data);
                benifisherydataUpload.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

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
                            } else {
                                Snackbar.with(getActivity(), null)
                                        .type(Type.ERROR)
                                        .message("Please try again")
                                        .duration(Duration.SHORT)
                                        .fillParent(true)
                                        .textAlign(Align.LEFT)
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
                                .textAlign(Align.LEFT)
                                .show();
                    }
                });*/



                System.out.println("nw Gson" + new Gson().toJson(saveModel1ArrayList));
                ArrayList<BenifisheryDataModelDbSend> dataModelDbSendArrayList = new ArrayList<>();
                if (saveModel1ArrayList != null && saveModel1ArrayList.size() > 0) {
                    for (int i = 0; i < saveModel1ArrayList.size(); i++) {
                        ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendArrayList = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                                .where(Condition.prop("panchyatcode").eq(saveModel1ArrayList.get(i).getPancayatcode()))
                                .where(Condition.prop("year").eq(saveModel1ArrayList.get(i).getYear()))
                                .where(Condition.prop("month").eq(saveModel1ArrayList.get(i).getMonth()))
                                .where(Condition.prop("isuploadtoserver").eq("false"))
                                .list();
                        for (int j = 0; j < benifisheryDataModelDbSendArrayList.size(); j++) {
                            benifisheryDataModelDbSendArrayList.get(j).setVoname(null);
                            benifisheryDataModelDbSendArrayList.get(j).setAaganwariname(null);
                            benifisheryDataModelDbSendArrayList.get(j).setPanchyatname(null);
                            benifisheryDataModelDbSendArrayList.get(j).setId(null);
                            benifisheryDataModelDbSendArrayList.get(j).setCreatedon(null);
                            benifisheryDataModelDbSendArrayList.get(j).setIsuploadtoserver(null);
                        }
                        dataModelDbSendArrayList.addAll(benifisheryDataModelDbSendArrayList);

                    }
                    System.out.println("data send" + new Gson().toJson(dataModelDbSendArrayList));
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
                    String data = "{" + "\"AganwadiImagesData\"" + " :" + new Gson().toJson(dataModelDbSendArrayList) + " }";
                    System.out.println("jdfjhjds" + data);
                    Call<String> benifisherydataUpload = apiServices.benificeryDataUpload(data);
                    benifisherydataUpload.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

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
                                } else {
                                    Snackbar.with(getActivity(), null)
                                            .type(Type.ERROR)
                                            .message("Please try again")
                                            .duration(Duration.SHORT)
                                            .fillParent(true)
                                            .textAlign(Align.LEFT)
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
                                    .textAlign(Align.LEFT)
                                    .show();
                        }
                    });
                }

            }
        });
        recyclerViewBenifishery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    private void uploadImageService() {
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
        ArrayList<ImageSaveModel>imageSaveModelArrayList= (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class).list();
        System.out.println("bsdfhkjsdhfvUREFH"+new Gson().toJson(imageSaveModelArrayList));
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
                    benifisheryDataModelDbSendArrayListImage.get(j).setIsuploadtoserver(null);
                }
                dataModelDbSendArrayList.addAll(benifisheryDataModelDbSendArrayListImage);

            }
        }
        System.out.println("data sendimge" + new Gson().toJson(dataModelDbSendArrayList));






       /* final ArrayList<ImageSaveModel> imageSaveModels = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class).list();
        for (int i = 0; i < imageSaveModels.size(); i++) {
            imageSaveModels.get(i).setId(null);
            imageSaveModels.get(i).setIsuploadtoserver(null);
        }*/
        String data = "{" + "\"AganwadiImagesInfo\"" + " :" + new Gson().toJson(dataModelDbSendArrayList) + " }";

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
                        for (int j = 0; j < dataModelDbSendArrayList.size(); j++) {
                            dataModelDbSendArrayList.get(j).setIsuploadtoserver("true");
                            dataModelDbSendArrayList.get(j).save();
                        }
                        for (int j = 0; j < benifisheryDataModelDbSends.size(); j++) {
                            benifisheryDataModelDbSends.get(j).setIsuploadtoserver("true");
                            benifisheryDataModelDbSends.get(j).save();
                        }
                        Snackbar.with(getActivity(), null)
                                .type(Type.SUCCESS)
                                .message(getString(R.string.save_message))
                                .duration(Duration.SHORT)
                                .fillParent(true)
                                .textAlign(Align.LEFT)
                                .show();
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
                    } else {
                        for (int i = 0; i < dataModelDbSendArrayList.size(); i++) {
                            dataModelDbSendArrayList.get(i).setIsuploadtoserver("false");
                            dataModelDbSendArrayList.get(i).save();
                            System.out.println("benifishery data fail" + new Gson().toJson(benifisheryDataModelDbSends));

                        }
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
                        .textAlign(Align.LEFT)
                        .show();
            }
        });
    }

    ArrayList<DataSaveModel1> saveModel1ArrayList = new ArrayList<>();

    @Override
    public void onListItemSelected(int itemId, Object data) {


    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    ArrayList<DataSaveModel1> arrayListSend = new ArrayList<>();

    @Override
    public void onListItemLongClickedSnd(int itemId, Object data, int position) {
        /*switch (itemId) {
            case 1:
                saveModel1ArrayList = (ArrayList<DataSaveModel1>) data;
                DataSaveModel1 dataSaveModel1 = new DataSaveModel1();
                dataSaveModel1.setPancayatcode(saveModel1ArrayList.get(position).getPancayatcode());
                dataSaveModel1.setMonth(saveModel1ArrayList.get(position).getMonth());
                dataSaveModel1.setYear(saveModel1ArrayList.get(position).getYear());
                arrayListSend.add(positio)
                break;
            case 0:
                break;
        }*/
        saveModel1ArrayList = (ArrayList<DataSaveModel1>) data;

       // Toast.makeText(getActivity(), data.toString(), Toast.LENGTH_SHORT).show();

    }

}

