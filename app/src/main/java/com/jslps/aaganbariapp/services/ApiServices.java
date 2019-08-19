package com.jslps.aaganbariapp.services;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("TabletDownloadDataANGANWADIMIS")
    Call<String> getTabletMasterDownLoad(@Query("flag") String flag, @Query("whr1") String whr1,@Query("whr2")String whr2);



}