package com.jslps.aaganbariapp;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final int HOME_FRAGMENT = 101;
    public static final int setTitle = 1111;
    public static final int PANCHYAT_FRAGNMENT = 102;
    public static final int ENTRY_FORM_FRAGNMENT = 103;
    public static final int VO_LIST_FRAGNMENT = 104;
    public static final int AAGANWARI_LIST_FRAGNMENT = 105;
    public static final int FRAGMENT_FEEDBACK = 106;
    public static final int FRAGMENT_REPORTS = 107;
    public static final int FRAGMENT_ENTRY_EDIT = 108;
    public static final int FRAGMENT_SYNC_WITH_SERVER = 109;
    public static final int UPDATE_FRAGMENT = 999;
    public static int maxAttachment = 0;
    public static boolean editFlag=false;
    public static List<String> finalbytes = new ArrayList<String>();
    public static List<String> finalnames = new ArrayList<String>();
    public static List<Long> finalsizes = new ArrayList<Long>();
    public static List<String> finaltypes = new ArrayList<String>();
    public static List<Bitmap> finalBitmap = new ArrayList<Bitmap>();
    public static final String API_BASE_URL = "http://swalekha.in/webServiceModalPopup.asmx/";
}