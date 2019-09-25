package com.jslps.aaganbariapp.cache;

import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.ImageSaveModel;

import java.util.ArrayList;


public class CartCache {
    public static CartCache cartCache;
    ArrayList<BenifisheryDataModelDbSend> gcmMessageList = new ArrayList<>();
    ArrayList<ImageSaveModel>imageSaveModels=new ArrayList<>();
    public static CartCache getInstance() {
        if (cartCache == null)
            cartCache = new CartCache();
        return cartCache;
    }

    public ArrayList<ImageSaveModel> getImageSaveModels() {
        return imageSaveModels;
    }

    public void setImageSaveModels(ArrayList<ImageSaveModel> imageSaveModels) {
        this.imageSaveModels = imageSaveModels;
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
