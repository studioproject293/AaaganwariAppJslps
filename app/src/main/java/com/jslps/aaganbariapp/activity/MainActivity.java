package com.jslps.aaganbariapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.fragment.AaganWariListFragment;
import com.jslps.aaganbariapp.fragment.EntryFormFragment;
import com.jslps.aaganbariapp.fragment.EntryFormFragmentEdit;
import com.jslps.aaganbariapp.fragment.FeedbackFragment;
import com.jslps.aaganbariapp.fragment.HomeFragment;
import com.jslps.aaganbariapp.fragment.PanchyatFragment;
import com.jslps.aaganbariapp.fragment.ReportsListFragment;
import com.jslps.aaganbariapp.fragment.VoListFragment;
import com.jslps.aaganbariapp.listener.OnFragmentInteractionListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orm.SugarDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static java.io.File.createTempFile;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private FragmentManager mFragmentManager;
    private String mFragmentTag;
    private int mCurrentFragment;
    Toolbar toolbar_home;
    TextView textheader;
    Button savenewMember;
    RadioGroup radioGroup;
    RadioButton button_bank_connection, button_brand_connection;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoader imageLoader = ImageLoader.getInstance();
        toolbar_home = findViewById(R.id.toolbar_home);
        radioGroup = findViewById(R.id.radioGroup);
        textheader = findViewById(R.id.toolbar_title);
        savenewMember = findViewById(R.id.savenewMember);
        button_bank_connection = findViewById(R.id.button_bank_connection);
        button_brand_connection = findViewById(R.id.button_brand_connection);
        onFragmentInteraction(Constant.HOME_FRAGMENT, null);
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        savenewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            button_bank_connection.setBackgroundResource(R.drawable.radio_button_style);
            button_brand_connection.setBackgroundResource(R.drawable.radio_background);
            button_bank_connection.setTextColor(getResources().getColor(R.color.buttoncolor));
            button_brand_connection.setTextColor(getResources().getColor(R.color.color_white));

        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.button_bank_connection) {
                    button_bank_connection.setBackgroundResource(R.drawable.radio_button_style);
                    button_brand_connection.setBackgroundResource(R.drawable.radio_background);
                    button_bank_connection.setTextColor(getResources().getColor(R.color.buttoncolor));
                    button_brand_connection.setTextColor(getResources().getColor(R.color.color_white));

                } else {
                    button_bank_connection.setBackgroundResource(R.drawable.radio_background);
                    button_brand_connection.setBackgroundResource(R.drawable.radio_button_style);
                    button_bank_connection.setTextColor(getResources().getColor(R.color.color_white));
                    button_brand_connection.setTextColor(getResources().getColor(R.color.buttoncolor));


                }
            }
        });

    }


    @Override
    public void onFragmentInteraction(int fragmentId, Object data) {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = fragmentId;
        mFragmentTag = String.valueOf(fragmentId);
        switch (fragmentId) {
            case Constant.HOME_FRAGMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new HomeFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.PANCHYAT_FRAGNMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new PanchyatFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.VO_LIST_FRAGNMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new VoListFragment().newInstance((PanchyatDataModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.AAGANWARI_LIST_FRAGNMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new AaganWariListFragment().newInstance((VOListDataModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.ENTRY_FORM_FRAGNMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new EntryFormFragment().newInstance((AanganWariModelDb) data), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_FEEDBACK:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new FeedbackFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_REPORTS:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new ReportsListFragment(), mFragmentTag).commitAllowingStateLoss();
                break;
            case Constant.FRAGMENT_ENTRY_EDIT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new EntryFormFragmentEdit().newInstance((BenifisheryDataModelDbSend) data), mFragmentTag).commitAllowingStateLoss();
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
                    radioGroup.setVisibility(View.GONE);
                } else {
                    savenewMember.setVisibility(View.GONE);
                    radioGroup.setVisibility(View.VISIBLE);
                }
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

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static Boolean copyFile(File sourceFile, File destFile)
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
    }

    private void copyDatabase() throws IOException {

        File actualFile = new File(new SugarDb(MainActivity.this).getDB().getPath());
        File cuurentfile = new File(actualFile.toString());
        Log.e("actualPath", "vsfkvsk" + actualFile.toString());


        File newFile = createTempFile("sugarFiles", ".db", Environment.getExternalStorageDirectory());

        Log.e("newPath", "gbgkjfdk" + newFile.toString());

        boolean yes = copyFile(cuurentfile, newFile);

        if (yes) {
            Log.e("result", "" + true);
        }

    }

}
