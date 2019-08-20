package com.jslps.aaganbariapp.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BenifisheryDataUpload {
    @POST("PutImage_Aganwadiimage")
    @FormUrlEncoded
    Call<String> benificeryDataUpload(@Field("sData") String sData);
}
