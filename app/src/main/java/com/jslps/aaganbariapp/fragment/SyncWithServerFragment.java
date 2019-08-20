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
        /*ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
        ArrayList<String> arrayListString = new ArrayList<>();
        for (int i = 0; i < benifisheryDataModelDbSends.size(); i++) {
            BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
            String aaganbarcode = benifisheryDataModelDbSend.getAaganwaricode();

            if (!arrayListString.contains(aaganbarcode)) {
                arrayListString.add(aaganbarcode);
                arrayList.add(benifisheryDataModelDbSend);
                *//*if (!benifisheryDataModelDbSends.get(i).getAaganwaricode().equals(benifisheryDataModelDbSends.get(j).getAaganwaricode())) {
                    BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(i);
                    benifisheryDataModelDbSend.setAaganwaricode(benifisheryDataModelDbSends.get(i).getAaganwaricode());
                    benifisheryDataModelDbSend.setPanchyatcode(benifisheryDataModelDbSends.get(i).getPanchyatcode());
                    benifisheryDataModelDbSend.setVocode(benifisheryDataModelDbSends.get(i).getVocode());
                    arrayList.add(benifisheryDataModelDbSend);
                }else {

                }*//*
            }
        }*/


        updateList(benifisheryDataModelDbSendss);
    }

    private void updateList(ArrayList<BenifisheryDataModelDbSend> arrayList) {
        if (arrayList!=null && arrayList.size()>0) {
            recyclerViewBenifishery.setVisibility(View.VISIBLE);
            tableLayout.setVisibility(View.VISIBLE);
            uploaddata.setVisibility(View.VISIBLE);
            SyncWithServerRecyclerviewAdapter panchyatRecyclerviewAdapter = new SyncWithServerRecyclerviewAdapter(getActivity(), arrayList);
            panchyatRecyclerviewAdapter.setListner(this);
            recyclerViewBenifishery.setAdapter(panchyatRecyclerviewAdapter);
        }else {
            recyclerViewBenifishery.setVisibility(View.GONE);
            tableLayout.setVisibility(View.GONE);
            uploaddata.setVisibility(View.GONE);


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
                });
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
        final ArrayList<ImageSaveModel> imageSaveModels = (ArrayList<ImageSaveModel>) Select.from(ImageSaveModel.class).list();
        for (int i = 0; i < imageSaveModels.size(); i++) {
            imageSaveModels.get(i).setId(null);
            imageSaveModels.get(i).setIsuploadtoserver(null);
        }
        String data = "{" + "\"AganwadiImagesInfo\"" + " :" + new Gson().toJson(imageSaveModels) + " }";

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
                        for (int j = 0; j < benifisheryDataModelDbSends.size(); j++) {
                            benifisheryDataModelDbSends.get(j).setIsuploadtoserver("true");
                            benifisheryDataModelDbSends.get(j).save();
                        }
                        for (int i = 0; i < imageSaveModels.size(); i++) {
                            imageSaveModels.get(i).setIsuploadtoserver("true");
                            imageSaveModels.get(i).save();
                            System.out.println("benifishery data sucess" + new Gson().toJson(benifisheryDataModelDbSends));


                        }
                        Snackbar.with(getActivity(), null)
                                .type(Type.SUCCESS)
                                .message(getString(R.string.save_message))
                                .duration(Duration.SHORT)
                                .fillParent(true)
                                .textAlign(Align.LEFT)
                                .show();
                    } else {
                        for (int i = 0; i < imageSaveModels.size(); i++) {
                            imageSaveModels.get(i).setIsuploadtoserver("false");
                            imageSaveModels.get(i).save();
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

    @Override
    public void onListItemSelected(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }
}

