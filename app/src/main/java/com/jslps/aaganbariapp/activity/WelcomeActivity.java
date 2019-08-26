package com.jslps.aaganbariapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.DialogUtil;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.adapter.MyCustomPagerAdapter;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.LoginDataModel;
import com.jslps.aaganbariapp.model.LoginModelDb;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.jslps.aaganbariapp.services.ApiServices;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WelcomeActivity extends AppCompatActivity {
    int images[] = {R.mipmap.image3, R.mipmap.image2, R.mipmap.image1};

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    int currentPage = 0;
    Timer timer;
    CheckBox checkBox, checkboxRember;
    SharedPreferences preferences;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private TextView logIn, versionNo;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PrefManager.getInstance().init(this);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(1500000) // 1.5 Mb
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // Not necessary in common
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

        viewPager = findViewById(R.id.view_pager);
        logIn = findViewById(R.id.logIn);
        versionNo = findViewById(R.id.versionNo);
        versionNo.setText("Version No: 1.1");
        // making notification bar transparent
        changeStatusBarColor();
        MyCustomPagerAdapter myCustomPagerAdapter = new MyCustomPagerAdapter(WelcomeActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        // myViewPagerAdapter = new MyViewPagerAdapter();
        // viewPager.setAdapter(myViewPagerAdapter);
        // viewPager.setOffscreenPageLimit(layouts.length);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int NUM_PAGES = images.length;
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });


    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        // Include dialog.xml file
        dialog.setContentView(R.layout.layout_dialog);
        ImageView closeButton = dialog.findViewById(R.id.closeButton);
        final EditText editTextUserName = dialog.findViewById(R.id.etusername);
        final EditText editTextPassword = dialog.findViewById(R.id.etpass);
        Button sigiin = dialog.findViewById(R.id.sigiin);
        checkBox = dialog.findViewById(R.id.checkbox);
        checkboxRember = dialog.findViewById(R.id.checkboxRember);
        preferences = getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);

        String value = preferences.getString("userName", "");
        String value1 = preferences.getString("Password", "");
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value1)) {
            editTextUserName.setText(value);
            editTextUserName.setEnabled(true);
            editTextPassword.setText(value1);
        } else {
            editTextUserName.setText("");
            editTextUserName.setEnabled(true);
            editTextPassword.setText("");
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());


            }
        });
        sigiin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUserName.getText().toString().trim().isEmpty()) {
                    editTextUserName.setError("Please enter user name");
                    editTextUserName.requestFocus();
                    showError(editTextUserName);
                } else if (editTextPassword.getText().toString().trim().isEmpty()) {
                    editTextPassword.setError("Please enter user name");
                    editTextPassword.requestFocus();
                    showError(editTextPassword);
                } else {
                    ArrayList<LoginModelDb> arrayListVillage1 = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class)
                            .where(Condition.prop("username").eq(editTextUserName.getText().toString()),
                                    Condition.prop("password").eq(editTextPassword.getText().toString())).list();
                    System.out.println("LogInDbsdfsdfs" + new Gson().toJson(arrayListVillage1));
                    if (arrayListVillage1 != null && arrayListVillage1.size() > 0) {

                        if (arrayListVillage1.get(0).getUsername().equals(editTextUserName.getText().toString()) ||
                                arrayListVillage1.get(0).getUsername().equals(editTextPassword.getText().toString())) {
                            if (checkboxRember.isChecked()) {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("userName", editTextUserName.getText().toString());
                                editor.putString("Password", editTextPassword.getText().toString());
                                editor.apply();
                            }
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            DialogUtil.displayProgress(WelcomeActivity.this);
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
                            Call<String> changePhotoResponseModelCall = apiServices.getTabletMasterDownLoad("Login", editTextUserName.getText().toString(), editTextPassword.getText().toString());
                            changePhotoResponseModelCall.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Gson gson = new Gson();
                                    Log.v("Response prof :", "hgfgfrhgs" + response.body());
                                    if (checkboxRember.isChecked()) {
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("userName", editTextUserName.getText().toString());
                                        editor.putString("Password", editTextPassword.getText().toString());
                                        editor.apply();
                                    }
                                    String fullResponse = response.body();
                                    String XmlString = fullResponse.substring(fullResponse.indexOf("\">") + 2);
                                    String result = XmlString.replaceAll("</string>", "");

                                    System.out.print("fhrjfghf" + result);
                                    LoginDataModel mStudentObject1 = gson.fromJson(result, LoginDataModel.class);
                                    System.out.println("vvh" + gson.toJson(mStudentObject1));
                                    LoginModelDb.deleteAll(LoginModelDb.class);
                                    for (int i = 0; i < mStudentObject1.getMaster().size(); i++) {
                                        LoginModelDb stateModel1 = new LoginModelDb(mStudentObject1.getMaster().get(i).getBlockCode(),
                                                mStudentObject1.getMaster().get(i).getContactNo(), mStudentObject1.getMaster().get(i).getCreatedBy(),
                                                mStudentObject1.getMaster().get(i).getCreatedOn(), mStudentObject1.getMaster().get(i).getDesignation(),
                                                mStudentObject1.getMaster().get(i).getBlockCode(), mStudentObject1.getMaster().get(i).getLoginID(),
                                                mStudentObject1.getMaster().get(i).getName(), mStudentObject1.getMaster().get(i).getPanchayatCode(), mStudentObject1.getMaster().get(i).getPassword(),
                                                mStudentObject1.getMaster().get(i).getRoleId(), mStudentObject1.getMaster().get(i).getUserName(), mStudentObject1.getMaster().get(i).getVillageCode());
                                        stateModel1.save();
                                    }
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
                                        VOListDataModelDb panchyatDataModelDb = new VOListDataModelDb(mStudentObject1.getTable5().get(i).getAct(),
                                                mStudentObject1.getTable5().get(i).getBank_Acc(), mStudentObject1.getTable5().get(i).getBank_AccNo(),
                                                mStudentObject1.getTable5().get(i).getBank_branch(), mStudentObject1.getTable5().get(i).getBank_Name(),
                                                mStudentObject1.getTable5().get(i).getBank_opDate(), mStudentObject1.getTable5().get(i).getBlockcode(),
                                                mStudentObject1.getTable5().get(i).getCluster2_Villagecode1(), mStudentObject1.getTable5().get(i).getCluster2_Villagecode2(),
                                                mStudentObject1.getTable5().get(i).getCluster2_Villagecode3(), mStudentObject1.getTable5().get(i).getCluster2_Villagecode4(),
                                                mStudentObject1.getTable5().get(i).getCluster2_Villagecode5(), mStudentObject1.getTable5().get(i).getClustercode(),
                                                mStudentObject1.getTable5().get(i).getClustercode2(), mStudentObject1.getTable5().get(i).getCompSaving(),
                                                mStudentObject1.getTable5().get(i).getContactName(), mStudentObject1.getTable5().get(i).getContactNumber(),
                                                mStudentObject1.getTable5().get(i).getDistrictcode(), mStudentObject1.getTable5().get(i).getExiting_VOCode(),
                                                mStudentObject1.getTable5().get(i).getFedRegisteredIn(),
                                                mStudentObject1.getTable5().get(i).getFedType_New_Old(), mStudentObject1.getTable5().get(i).getFed_RegistrationDate(),
                                                mStudentObject1.getTable5().get(i).getIFSCCode(), mStudentObject1.getTable5().get(i).getInterloaningRate(),
                                                mStudentObject1.getTable5().get(i).getMeetingdate(), mStudentObject1.getTable5().get(i).getMeetingFrequency(),
                                                mStudentObject1.getTable5().get(i).getMtgFreq(), mStudentObject1.getTable5().get(i).getMtgFreqValue(),
                                                mStudentObject1.getTable5().get(i).getMtg_Day2(), mStudentObject1.getTable5().get(i).getOrgLevel(),
                                                mStudentObject1.getTable5().get(i).getRegistration(), mStudentObject1.getTable5().get(i).getRegistrationNo(),
                                                mStudentObject1.getTable5().get(i).getSHPI_Code(), mStudentObject1.getTable5().get(i).getStatecode(),
                                                mStudentObject1.getTable5().get(i).getTotalShginvillage(), mStudentObject1.getTable5().get(i).getTotalshginVo(),
                                                mStudentObject1.getTable5().get(i).getVillagecode(), mStudentObject1.getTable5().get(i).getVillageCode2(),
                                                mStudentObject1.getTable5().get(i).getVillageCode3(), mStudentObject1.getTable5().get(i).getVillagecode4(),
                                                mStudentObject1.getTable5().get(i).getVillagecode5(), mStudentObject1.getTable5().get(i).getVOaddress(),
                                                mStudentObject1.getTable5().get(i).getVOCode(), mStudentObject1.getTable5().get(i).getVOFormationagency(),
                                                mStudentObject1.getTable5().get(i).getVOFormationdate(), mStudentObject1.getTable5().get(i).getVOHonorariumToVOA(),
                                                mStudentObject1.getTable5().get(i).getVOName(), mStudentObject1.getTable5().get(i).getVOofficeType(),
                                                mStudentObject1.getTable5().get(i).getVORegisterMaintenedBy());
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

                                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    DialogUtil.stopProgressDisplay();
                                    Snackbar.with(WelcomeActivity.this, null)
                                            .type(Type.ERROR)
                                            .message(t.toString())
                                            .duration(Duration.SHORT)
                                            .fillParent(true)
                                            .textAlign(Align.LEFT)
                                            .show();
                                }
                            });
                        }
                    } else {
                        ArrayList<LoginModelDb> loginModelDbs = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
                        if (loginModelDbs != null && loginModelDbs.size() > 0) {
                            boolean isFound=false;
                            for (int i = 0; i < loginModelDbs.size(); i++) {
                                if (loginModelDbs.get(i).getUsername().equalsIgnoreCase(editTextUserName.getText().toString())){
                                    isFound=true;
                                }
                            }
                            if (!isFound){
                                Toast.makeText(WelcomeActivity.this, "Already One user Map In this device", Toast.LENGTH_SHORT).show();
                            }else {
                                DialogUtil.displayProgress(WelcomeActivity.this);
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
                                Call<String> changePhotoResponseModelCall = apiServices.getTabletMasterDownLoad("Login", editTextUserName.getText().toString(), editTextPassword.getText().toString());
                                changePhotoResponseModelCall.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Gson gson = new Gson();
                                        Log.v("Response prof :", "hgfgfrhgs" + response.body());
                                        if (checkboxRember.isChecked()) {
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putString("userName", editTextUserName.getText().toString());
                                            editor.putString("Password", editTextPassword.getText().toString());
                                            editor.apply();
                                        }
                                        String fullResponse = response.body();
                                        String XmlString = fullResponse.substring(fullResponse.indexOf("\">") + 2);
                                        String result = XmlString.replaceAll("</string>", "");

                                        System.out.print("fhrjfghf" + result);
                                        LoginDataModel mStudentObject1 = gson.fromJson(result, LoginDataModel.class);
                                        System.out.println("vvh" + gson.toJson(mStudentObject1));
                                        LoginModelDb.deleteAll(LoginModelDb.class);
                                        for (int i = 0; i < mStudentObject1.getMaster().size(); i++) {
                                            LoginModelDb stateModel1 = new LoginModelDb(mStudentObject1.getMaster().get(i).getBlockCode(),
                                                    mStudentObject1.getMaster().get(i).getContactNo(), mStudentObject1.getMaster().get(i).getCreatedBy(),
                                                    mStudentObject1.getMaster().get(i).getCreatedOn(), mStudentObject1.getMaster().get(i).getDesignation(),
                                                    mStudentObject1.getMaster().get(i).getBlockCode(), mStudentObject1.getMaster().get(i).getLoginID(),
                                                    mStudentObject1.getMaster().get(i).getName(), mStudentObject1.getMaster().get(i).getPanchayatCode(), mStudentObject1.getMaster().get(i).getPassword(),
                                                    mStudentObject1.getMaster().get(i).getRoleId(), mStudentObject1.getMaster().get(i).getUserName(), mStudentObject1.getMaster().get(i).getVillageCode());
                                            stateModel1.save();
                                        }
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
                                            VOListDataModelDb panchyatDataModelDb = new VOListDataModelDb(mStudentObject1.getTable5().get(i).getAct(),
                                                    mStudentObject1.getTable5().get(i).getBank_Acc(), mStudentObject1.getTable5().get(i).getBank_AccNo(),
                                                    mStudentObject1.getTable5().get(i).getBank_branch(), mStudentObject1.getTable5().get(i).getBank_Name(),
                                                    mStudentObject1.getTable5().get(i).getBank_opDate(), mStudentObject1.getTable5().get(i).getBlockcode(),
                                                    mStudentObject1.getTable5().get(i).getCluster2_Villagecode1(), mStudentObject1.getTable5().get(i).getCluster2_Villagecode2(),
                                                    mStudentObject1.getTable5().get(i).getCluster2_Villagecode3(), mStudentObject1.getTable5().get(i).getCluster2_Villagecode4(),
                                                    mStudentObject1.getTable5().get(i).getCluster2_Villagecode5(), mStudentObject1.getTable5().get(i).getClustercode(),
                                                    mStudentObject1.getTable5().get(i).getClustercode2(), mStudentObject1.getTable5().get(i).getCompSaving(),
                                                    mStudentObject1.getTable5().get(i).getContactName(), mStudentObject1.getTable5().get(i).getContactNumber(),
                                                    mStudentObject1.getTable5().get(i).getDistrictcode(), mStudentObject1.getTable5().get(i).getExiting_VOCode(),
                                                    mStudentObject1.getTable5().get(i).getFedRegisteredIn(), mStudentObject1.getTable5().get(i).getFedType_New_Old(),
                                                    mStudentObject1.getTable5().get(i).getFed_RegistrationDate(), mStudentObject1.getTable5().get(i).getIFSCCode(),
                                                    mStudentObject1.getTable5().get(i).getInterloaningRate(), mStudentObject1.getTable5().get(i).getMeetingdate(),
                                                    mStudentObject1.getTable5().get(i).getMeetingFrequency(), mStudentObject1.getTable5().get(i).getMtgFreq(),
                                                    mStudentObject1.getTable5().get(i).getMtgFreqValue(), mStudentObject1.getTable5().get(i).getMtg_Day2(),
                                                    mStudentObject1.getTable5().get(i).getOrgLevel(), mStudentObject1.getTable5().get(i).getRegistration(),
                                                    mStudentObject1.getTable5().get(i).getRegistrationNo(), mStudentObject1.getTable5().get(i).getSHPI_Code(),
                                                    mStudentObject1.getTable5().get(i).getStatecode(), mStudentObject1.getTable5().get(i).getTotalShginvillage(),
                                                    mStudentObject1.getTable5().get(i).getTotalshginVo(), mStudentObject1.getTable5().get(i).getVillagecode(),
                                                    mStudentObject1.getTable5().get(i).getVillageCode2(), mStudentObject1.getTable5().get(i).getVillageCode3(),
                                                    mStudentObject1.getTable5().get(i).getVillagecode4(), mStudentObject1.getTable5().get(i).getVillagecode5(),
                                                    mStudentObject1.getTable5().get(i).getVOaddress(), mStudentObject1.getTable5().get(i).getVOCode(),
                                                    mStudentObject1.getTable5().get(i).getVOFormationagency(), mStudentObject1.getTable5().get(i).getVOFormationdate(),
                                                    mStudentObject1.getTable5().get(i).getVOHonorariumToVOA(), mStudentObject1.getTable5().get(i).getVOName(),
                                                    mStudentObject1.getTable5().get(i).getVOofficeType(), mStudentObject1.getTable5().get(i).getVORegisterMaintenedBy());
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
                                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        DialogUtil.stopProgressDisplay();
                                        Snackbar.with(WelcomeActivity.this, null)
                                                .type(Type.ERROR)
                                                .message(t.toString())
                                                .duration(Duration.SHORT)
                                                .fillParent(true)
                                                .textAlign(Align.LEFT)
                                                .show();
                                    }
                                });
                            }
                        }else {
                            DialogUtil.displayProgress(WelcomeActivity.this);
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
                            Call<String> changePhotoResponseModelCall = apiServices.getTabletMasterDownLoad("Login", editTextUserName.getText().toString(), editTextPassword.getText().toString());
                            changePhotoResponseModelCall.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Gson gson = new Gson();
                                    Log.v("Response prof :", "hgfgfrhgs" + response.body());
                                    if (checkboxRember.isChecked()) {
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("userName", editTextUserName.getText().toString());
                                        editor.putString("Password", editTextPassword.getText().toString());
                                        editor.apply();
                                    }
                                    String fullResponse = response.body();
                                    String XmlString = fullResponse.substring(fullResponse.indexOf("\">") + 2);
                                    String result = XmlString.replaceAll("</string>", "");

                                    System.out.print("fhrjfghf" + result);
                                    LoginDataModel mStudentObject1 = gson.fromJson(result, LoginDataModel.class);
                                    System.out.println("vvh" + gson.toJson(mStudentObject1));
                                    LoginModelDb.deleteAll(LoginModelDb.class);
                                    for (int i = 0; i < mStudentObject1.getMaster().size(); i++) {
                                        LoginModelDb stateModel1 = new LoginModelDb(mStudentObject1.getMaster().get(i).getBlockCode(),
                                                mStudentObject1.getMaster().get(i).getContactNo(), mStudentObject1.getMaster().get(i).getCreatedBy(),
                                                mStudentObject1.getMaster().get(i).getCreatedOn(), mStudentObject1.getMaster().get(i).getDesignation(),
                                                mStudentObject1.getMaster().get(i).getBlockCode(), mStudentObject1.getMaster().get(i).getLoginID(),
                                                mStudentObject1.getMaster().get(i).getName(), mStudentObject1.getMaster().get(i).getPanchayatCode(), mStudentObject1.getMaster().get(i).getPassword(),
                                                mStudentObject1.getMaster().get(i).getRoleId(), mStudentObject1.getMaster().get(i).getUserName(), mStudentObject1.getMaster().get(i).getVillageCode());
                                        stateModel1.save();
                                    }
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
                                        VOListDataModelDb panchyatDataModelDb = new VOListDataModelDb(mStudentObject1.getTable5().get(i).getAct(),
                                                mStudentObject1.getTable5().get(i).getBank_Acc(), mStudentObject1.getTable5().get(i).getBank_AccNo(),
                                                mStudentObject1.getTable5().get(i).getBank_branch(), mStudentObject1.getTable5().get(i).getBank_Name(),
                                                mStudentObject1.getTable5().get(i).getBank_opDate(), mStudentObject1.getTable5().get(i).getBlockcode(),
                                                mStudentObject1.getTable5().get(i).getCluster2_Villagecode1(), mStudentObject1.getTable5().get(i).getCluster2_Villagecode2(),
                                                mStudentObject1.getTable5().get(i).getCluster2_Villagecode3(), mStudentObject1.getTable5().get(i).getCluster2_Villagecode4(),
                                                mStudentObject1.getTable5().get(i).getCluster2_Villagecode5(), mStudentObject1.getTable5().get(i).getClustercode(),
                                                mStudentObject1.getTable5().get(i).getClustercode2(), mStudentObject1.getTable5().get(i).getCompSaving(),
                                                mStudentObject1.getTable5().get(i).getContactName(), mStudentObject1.getTable5().get(i).getContactNumber(),
                                                mStudentObject1.getTable5().get(i).getDistrictcode(), mStudentObject1.getTable5().get(i).getExiting_VOCode(),
                                                mStudentObject1.getTable5().get(i).getFedRegisteredIn(), mStudentObject1.getTable5().get(i).getFedType_New_Old(),
                                                mStudentObject1.getTable5().get(i).getFed_RegistrationDate(), mStudentObject1.getTable5().get(i).getIFSCCode(),
                                                mStudentObject1.getTable5().get(i).getInterloaningRate(), mStudentObject1.getTable5().get(i).getMeetingdate(),
                                                mStudentObject1.getTable5().get(i).getMeetingFrequency(), mStudentObject1.getTable5().get(i).getMtgFreq(),
                                                mStudentObject1.getTable5().get(i).getMtgFreqValue(), mStudentObject1.getTable5().get(i).getMtg_Day2(),
                                                mStudentObject1.getTable5().get(i).getOrgLevel(), mStudentObject1.getTable5().get(i).getRegistration(),
                                                mStudentObject1.getTable5().get(i).getRegistrationNo(), mStudentObject1.getTable5().get(i).getSHPI_Code(),
                                                mStudentObject1.getTable5().get(i).getStatecode(), mStudentObject1.getTable5().get(i).getTotalShginvillage(),
                                                mStudentObject1.getTable5().get(i).getTotalshginVo(), mStudentObject1.getTable5().get(i).getVillagecode(),
                                                mStudentObject1.getTable5().get(i).getVillageCode2(), mStudentObject1.getTable5().get(i).getVillageCode3(),
                                                mStudentObject1.getTable5().get(i).getVillagecode4(), mStudentObject1.getTable5().get(i).getVillagecode5(),
                                                mStudentObject1.getTable5().get(i).getVOaddress(), mStudentObject1.getTable5().get(i).getVOCode(),
                                                mStudentObject1.getTable5().get(i).getVOFormationagency(), mStudentObject1.getTable5().get(i).getVOFormationdate(),
                                                mStudentObject1.getTable5().get(i).getVOHonorariumToVOA(), mStudentObject1.getTable5().get(i).getVOName(),
                                                mStudentObject1.getTable5().get(i).getVOofficeType(), mStudentObject1.getTable5().get(i).getVORegisterMaintenedBy());
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
                                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    DialogUtil.stopProgressDisplay();
                                    Snackbar.with(WelcomeActivity.this, null)
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
                }
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = WelcomeActivity.this.getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = WelcomeActivity.this.getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
    }
}
