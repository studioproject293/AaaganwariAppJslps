package com.jslps.aaganbariapp.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.DialogUtil;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.activity.WelcomeActivity;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.LoginDataModel;
import com.jslps.aaganbariapp.model.LoginModelDb;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.jslps.aaganbariapp.services.ApiServices;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeFragment extends BaseFragment  {

    private View rootView;
    LinearLayout layoutStart, logout,feedbackFragment,reports,sysncwithserverLayout,dataDownLoadFragment;
    FloatingActionButton refereshbutton;
    public HomeFragment() { }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.dashboard)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT,Constant.HOME_FRAGMENT);
        MainActivity.radioGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        layoutStart = rootView.findViewById(R.id.layoutStart);
        feedbackFragment = rootView.findViewById(R.id.feedbackFragment);
        refereshbutton = rootView.findViewById(R.id.refrreshicon);
        refereshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
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
                Call<String> changePhotoResponseModelCall = apiServices.getTabletMasterDownLoad("Login", loginModelDbs.get(0).getUsername(), loginModelDbs.get(0).getPassword(),"","");
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
                        LoginDataModel mStudentObject1 = gson.fromJson(result, LoginDataModel.class);
                        System.out.println("vvh" + gson.toJson(mStudentObject1));
                        PanchyatDataModelDb.deleteAll(PanchyatDataModelDb.class);
                        for (int i = 0; i < mStudentObject1.getTable6().size(); i++) {
                            PanchyatDataModelDb panchyatDataModelDb = new PanchyatDataModelDb(mStudentObject1.getTable6().get(i).getBlockCode(),
                                    mStudentObject1.getTable6().get(i).getClusterCode(), mStudentObject1.getTable6().get(i).getClusterName(),
                                    mStudentObject1.getTable6().get(i).getClusterName_H(), mStudentObject1.getTable6().get(i).getCreatedBy(), mStudentObject1.getTable6().get(i).getCreatedOn(),
                                    mStudentObject1.getTable6().get(i).getDistrictCode(), mStudentObject1.getTable6().get(i).getStateCode());
                            panchyatDataModelDb.save();
                        }
                        VOListDataModelDb.deleteAll(VOListDataModelDb.class);
                        for (int i = 0; i < mStudentObject1.getTable5().size(); i++) {
                            VOListDataModelDb panchyatDataModelDb = new VOListDataModelDb(mStudentObject1.getTable5().get(i).getBlock(),
                                    mStudentObject1.getTable5().get(i).getBlockCode(), mStudentObject1.getTable5().get(i).getCreatedBy(),
                                    mStudentObject1.getTable5().get(i).getCreatedOn(), mStudentObject1.getTable5().get(i).getDistrict(),
                                    mStudentObject1.getTable5().get(i).getDistrictCode(), mStudentObject1.getTable5().get(i).getFlag(),
                                    mStudentObject1.getTable5().get(i).getID(), mStudentObject1.getTable5().get(i).getPanchayat(),
                                    mStudentObject1.getTable5().get(i).getPanchayatCode(), mStudentObject1.getTable5().get(i).getPanchayat_VO(),
                                    mStudentObject1.getTable5().get(i).getSHGCode(), mStudentObject1.getTable5().get(i).getVillage(),
                                    mStudentObject1.getTable5().get(i).getVillageCode(), mStudentObject1.getTable5().get(i).getVOCode(),
                                    mStudentObject1.getTable5().get(i).getVO_SHG());
                            panchyatDataModelDb.save();
                        }

                        BenifisheryDataModelDb.deleteAll(BenifisheryDataModelDb.class);
                        for (int i = 0; i < mStudentObject1.getTable2().size(); i++) {
                            BenifisheryDataModelDb panchyatDataModelDb = new BenifisheryDataModelDb(mStudentObject1.getTable2().get(i).getBenfID(),
                                    mStudentObject1.getTable2().get(i).getBenfName(), mStudentObject1.getTable2().get(i).getCreatedBy(),
                                    mStudentObject1.getTable2().get(i).getCreatedOn(), mStudentObject1.getTable2().get(i).getNo_of_meal(),
                                    mStudentObject1.getTable2().get(i).getUnitRate_of_meal(), mStudentObject1.getTable2().get(i).getNo_of_Benf());
                            panchyatDataModelDb.save();
                        }
                        AanganWariModelDb.deleteAll(AanganWariModelDb.class);

                        for (int i = 0; i < mStudentObject1.getTable1().size(); i++) {
                            AanganWariModelDb panchyatDataModelDb = new AanganWariModelDb(mStudentObject1.getTable1().get(i).getAnganwadiCode(),
                                    mStudentObject1.getTable1().get(i).getAnganwadiName(), mStudentObject1.getTable1().get(i).getContactNo(),
                                    mStudentObject1.getTable1().get(i).getCreatedBy(), mStudentObject1.getTable1().get(i).getCreatedOn(),
                                    mStudentObject1.getTable1().get(i).getType(), mStudentObject1.getTable1().get(i).getVOCode(),
                                    mStudentObject1.getTable1().get(i).getAW_ID());
                            panchyatDataModelDb.save();
                            System.out.println("Aaganwari Dat" + new Gson().toJson(panchyatDataModelDb));
                        }

                        Snackbar.with(getActivity(), null)
                                .type(Type.SUCCESS)
                                .message("Data Download Successfully")
                                .duration(Duration.SHORT)
                                .fillParent(true)
                                .textAlign(Align.CENTER)
                                .show();
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
        sysncwithserverLayout = rootView.findViewById(R.id.sysncwithserverLayout);
        dataDownLoadFragment = rootView.findViewById(R.id.dataDownloadFragment);
        reports = rootView.findViewById(R.id.reports);
        logout = rootView.findViewById(R.id.logout);
        feedbackFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_FEEDBACK, null);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_REPORTS, null);
            }
        });
        layoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.PANCHYAT_FRAGNMENT, null);
            }
        });
        sysncwithserverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_SYNC_WITH_SERVER, null);
            }
        });
        dataDownLoadFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(Constant.FRAGMENT_DATA_DOWNLOAD, null);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userName", "");
                editor.putString("Password", "");
                editor.apply();
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return rootView;
    }


}

