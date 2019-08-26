package com.jslps.aaganbariapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;


public class PrefManager {

    private SharedPreferences pref;
    private Context _context;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;
    private static PrefManager manager;
    private static final String PREF_NAME = "AAGANWARI";
    private static final String PREF_PANCHYAT_CODE = "panchyatCode";
    private static final String PREF_AAGANWARI_CODE = "aaganwariCode";
    private static final String PREF_VOCode = "VOCode";
    private static final String PREF_PANCHYAT_NAME = "panchyatName";
    private static final String PREF_AAGANWARI_NAME = "aaganwariName";
    private static final String PREF_VONAME = "VOName";
    private static final String PREF_LANGAUGE_SELECTION = "langaugeSelection";
    public void init(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static PrefManager getInstance() {
        if (manager == null) manager = new PrefManager();
        return manager;
    }

    public String getPREF_VONAME() {
        return pref.getString(PREF_VONAME, null);
    }

    public void setPREF_VONAME(String voCode) {
        editor.putString(PREF_VONAME, voCode);
        editor.commit();
    }
    public String getPrefPanchyatNAME() {
        return pref.getString(PREF_PANCHYAT_NAME, null);
    }

    public void setPrefPanchyatNAME(String imageLink) {
        editor.putString(PREF_PANCHYAT_NAME, imageLink);
        editor.commit();
    }

    public String getPrefAaganwariNAME() {
        return pref.getString(PREF_AAGANWARI_NAME, null);
    }

    public void setPrefAaganwariNAME(String userApartmnet) {
        editor.putString(PREF_AAGANWARI_NAME, userApartmnet);
        editor.commit();
    }
    public String getPREF_VOCode() {
        return pref.getString(PREF_VOCode, null);
    }

    public void setPREF_VOCode(String voCode) {
        editor.putString(PREF_VOCode, voCode);
        editor.commit();
    }
    public String getPrefPanchyatCode() {
        return pref.getString(PREF_PANCHYAT_CODE, null);
    }

    public void setPrefPanchyatCode(String imageLink) {
        editor.putString(PREF_PANCHYAT_CODE, imageLink);
        editor.commit();
    }

    public String getPrefAaganwariCode() {
        return pref.getString(PREF_AAGANWARI_CODE, null);
    }

    public void setPrefAaganwariCode(String userApartmnet) {
        editor.putString(PREF_AAGANWARI_CODE, userApartmnet);
        editor.commit();
    }
    public String getPrefLangaugeSelection() {
        return pref.getString(PREF_LANGAUGE_SELECTION, null);
    }

    public void setPrefLangaugeSelection(String userApartmnet) {
        editor.putString(PREF_LANGAUGE_SELECTION, userApartmnet);
        editor.commit();
    }
    public int getSize() {
        int size;
        Map<String, ?> entries = pref.getAll();
        if (entries != null) {
            size = entries.size();
        } else size = 0;

        return size;
    }


}