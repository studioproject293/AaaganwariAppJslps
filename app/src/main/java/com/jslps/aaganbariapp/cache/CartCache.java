package com.jslps.aaganbariapp.cache;

import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;

import java.util.ArrayList;


public class CartCache {
    public static CartCache cartCache;
    ArrayList<BenifisheryDataModelDbSend> gcmMessageList = new ArrayList<>();

    public static CartCache getInstance() {
        if (cartCache == null)
            cartCache = new CartCache();
        return cartCache;
    }

    public ArrayList<BenifisheryDataModelDbSend> getGcmMessageList() {
        return gcmMessageList;
    }

    public void setGcmMessageList(ArrayList<BenifisheryDataModelDbSend> gcmMessageList) {
        this.gcmMessageList = gcmMessageList;
    }
    public void clearBenifisheryData() {
        if (gcmMessageList != null && gcmMessageList.size() > 0)
            gcmMessageList.clear();
    }
}
