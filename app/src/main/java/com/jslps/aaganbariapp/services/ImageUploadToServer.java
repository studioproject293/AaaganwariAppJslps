package com.jslps.aaganbariapp.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageUploadToServer {
    @POST("PutImage_AganwadiimageInfo")
    @FormUrlEncoded
    Call<String> imageUpload(@Field("sData") String sData);
}
