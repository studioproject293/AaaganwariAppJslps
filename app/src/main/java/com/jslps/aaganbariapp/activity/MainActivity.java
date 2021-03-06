package com.jslps.aaganbariapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.fragment.AaganWariListFragment;
import com.jslps.aaganbariapp.fragment.DownLoadDataFragment;
import com.jslps.aaganbariapp.fragment.EntryFormFragment;
import com.jslps.aaganbariapp.fragment.EntryFormFragmentEdit;
import com.jslps.aaganbariapp.fragment.EntryFormFragmentEditNew;
import com.jslps.aaganbariapp.fragment.EntryFormFragmentNew;
import com.jslps.aaganbariapp.fragment.FeedbackFragment;
import com.jslps.aaganbariapp.fragment.HomeFragment;
import com.jslps.aaganbariapp.fragment.PanchyatFragment;
import com.jslps.aaganbariapp.fragment.ReportsDisplayFragment;
import com.jslps.aaganbariapp.fragment.ReportsDisplayFragment1;
import com.jslps.aaganbariapp.fragment.ReportsListFragment;
import com.jslps.aaganbariapp.fragment.SyncWithServerFragment;
import com.jslps.aaganbariapp.fragment.VoListFragment;
import com.jslps.aaganbariapp.listener.OnFragmentInteractionListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.ReportDisplayFormModel;
import com.jslps.aaganbariapp.model.VOListDataModelDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private FragmentManager mFragmentManager;
    private String mFragmentTag;
    private int mCurrentFragment;
    Toolbar toolbar_home;
    TextView textheader;
    Button savenewMember;
    public static RadioGroup radioGroup;
    PrefManager prefManager;
    RadioButton button_bank_connection, button_brand_connection;
    public static boolean newCall = false;
    public static boolean newCall1 = false;
    private static final int REQUEST_PERMISSIONS = 101;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dbname = "Aaaganbari.db";
        File dbpath = this.getDatabasePath(dbname);
        System.out.print("");
//
        if ((ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA))) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
           backupToSugar(dbpath);
//            backup(dbpath);
        }

        setContentView(R.layout.activity_main);
        prefManager = PrefManager.getInstance();
        toolbar_home = findViewById(R.id.toolbar_home);
        radioGroup = findViewById(R.id.radioGroup);
        textheader = findViewById(R.id.toolbar_title);
        savenewMember = findViewById(R.id.savenewMember);
        button_bank_connection = findViewById(R.id.english);
        button_brand_connection = findViewById(R.id.hindi);
        onFragmentInteraction(Constant.HOME_FRAGMENT, null);
        /*try {
            copyDatabase();
    } catch (IOException e) {
        e.printStackTrace();
    }*/

       /* savenewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            button_bank_connection.setBackgroundResource(R.drawable.radio_button_style);
            button_brand_connection.setBackgroundResource(R.drawable.radio_background);
            button_bank_connection.setTextColor(getResources().getColor(R.color.buttoncolor));
            button_brand_connection.setTextColor(getResources().getColor(R.color.color_white));
            Locale locale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            prefManager.setPrefLangaugeSelection("english");

        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.english) {
                    button_bank_connection.setBackgroundResource(R.drawable.radio_button_style);
                    button_brand_connection.setBackgroundResource(R.drawable.radio_background);
                    button_bank_connection.setTextColor(getResources().getColor(R.color.buttoncolor));
                    button_brand_connection.setTextColor(getResources().getColor(R.color.color_white));
                    Locale locale = new Locale("en");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = locale;
                    res.updateConfiguration(conf, dm);
                    prefManager.setPrefLangaugeSelection("english");
                    if (mCurrentFragment == Constant.HOME_FRAGMENT)
                        gotoHomePage();
                } else {
                    button_bank_connection.setBackgroundResource(R.drawable.radio_background);
                    button_brand_connection.setBackgroundResource(R.drawable.radio_button_style);
                    button_bank_connection.setTextColor(getResources().getColor(R.color.color_white));
                    button_brand_connection.setTextColor(getResources().getColor(R.color.buttoncolor));
                    Locale locale = new Locale("hi");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = locale;
                    res.updateConfiguration(conf, dm);
                    prefManager.setPrefLangaugeSelection("hindi");
                    if (mCurrentFragment == Constant.HOME_FRAGMENT)
                        gotoHomePage();
                }
            }
        });
        if (mCurrentFragment == Constant.HOME_FRAGMENT)
            radioGroup.setVisibility(View.VISIBLE);
        else radioGroup.setVisibility(View.GONE);

    }

    @Override
    public void onFragmentInteraction(int fragmentId, Object data) {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = fragmentId;
        mFragmentTag = String.valueOf(fragmentId);
        switch (fragmentId) {
            case Constant.HOME_FRAGMENT:
                radioGroup.setVisibility(View.VISIBLE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new HomeFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.PANCHYAT_FRAGNMENT:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new PanchyatFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.VO_LIST_FRAGNMENT:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new VoListFragment().newInstance((PanchyatDataModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.AAGANWARI_LIST_FRAGNMENT:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new AaganWariListFragment().newInstance((VOListDataModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.ENTRY_FORM_FRAGNMENT:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new EntryFormFragment().newInstance((AanganWariModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_FEEDBACK:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new FeedbackFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_REPORTS:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new ReportsListFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_ENTRY_EDIT:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new EntryFormFragmentEdit().newInstance((ReportDisplayFormModel) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_SYNC_WITH_SERVER:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new SyncWithServerFragment().newInstance(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_DATA_DOWNLOAD:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new DownLoadDataFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_ENTRYFORM_NEW:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new EntryFormFragmentNew().newInstance((AanganWariModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_ENTRY_EDIT_NEW:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new EntryFormFragmentEditNew().newInstance((ReportDisplayFormModel) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_REPORTS_DISPLAY:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new ReportsDisplayFragment().newInstance(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_REPORTS_DISPLAY1:
                radioGroup.setVisibility(View.GONE);
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new ReportsDisplayFragment1().newInstance(), mFragmentTag).commitAllowingStateLoss();
                break;

        }
    }

    @Override
    public void onFragmentUpdate(int type, Object data) {
        switch (type) {
            case Constant.setTitle:
                HeaderData headerData = (HeaderData) data;
                textheader.setVisibility(View.VISIBLE);
                textheader.setText(headerData.getText());
                if (headerData.isLogoRequired()) {
                    savenewMember.setVisibility(View.VISIBLE);
                } else {
                    savenewMember.setVisibility(View.GONE);
                }
                break;
            case Constant.UPDATE_FRAGMENT:
                this.mCurrentFragment = (int) data;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            closeApp();
        } else
            super.onBackPressed();

    }

    public void closeApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.exit_message));

        alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

   /* public  Boolean copyFile(File sourceFile, File destFile)
            throws IOException {
        //        if (!destFile.exists()) {
        destFile.createNewFile();

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null)
                source.close();
            if (destination != null)
                destination.close();
        }
        return true;
        //        }
        //        return false;
    }*/

    public void gotoHomePage() {
        try {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            onFragmentInteraction(Constant.HOME_FRAGMENT, false);
        } catch (Exception e) {
        }
    }

    /*  private void copyDatabase() throws IOException {

      File actualFile = new File(new SugarDb(MainActivity.this).getDB().getPath());
      File cuurentfile = new File(actualFile.toString());
      Log.e("actualPath", "vsfkvsk" + actualFile.toString());


      File newFile = createTempFile("sugarFiles", ".db", Environment.getExternalStorageDirectory());

      Log.e("newPath", "gbgkjfdk" + newFile.toString());

      boolean yes = copyFile(cuurentfile, newFile);

      if (yes) {
          Log.e("result", "" + true);
      }

  }*/
    public void backup(File dbpath) {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File outputFile = new File(sdcard, "THR");

            if (!outputFile.exists())
                outputFile.createNewFile();

            File data = Environment.getDataDirectory();
            File inputFile = dbpath;
            InputStream input = new FileInputStream(inputFile);
            OutputStream output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Copying Failed");
        }
    }

    public void backupToSugar(File dbpath) {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File outputFile = new File(sdcard, "THR");

            if (!outputFile.exists())
                outputFile.createNewFile();

            File data = Environment.getDataDirectory();
            File inputFile = outputFile;
            InputStream input = new FileInputStream(outputFile);
            OutputStream output = new FileOutputStream(dbpath);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Copying Failed");
        }
    }
}
