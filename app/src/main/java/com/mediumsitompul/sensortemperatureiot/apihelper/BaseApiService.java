package com.mediumsitompul.sensortemperatureiot.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    // Fungsi ini untuk memanggil API http://192.168.43.247/android/_login.php
    @FormUrlEncoded
    @POST("_login.php")
    public Call<ResponseBody> loginRequest(@Field("userid") String userid,
                                           @Field("password") String password,
                                           @Field("flagging") String flagging,
                                           @Field("apps_name") String apps_name);
    @GET("witel.php")
    public Call<ResponseBody> getSensorDataWitel(@Query("pilih_tabel") String tabel);

    @GET("datel.php")
    public Call<ResponseBody> getSensorDataDatel(@Query("pilih_witel") String witel,@Query("pilih_tabel") String tabel);

    @GET("sto.php")
    public Call<ResponseBody> getSensorDataSto(@Query("pilih_datel") String datel,@Query("pilih_tabel") String tabel);

    @GET("flagging.php")
    public Call<ResponseBody> getSensorDataFlagging(@Query("pilih_sto") String sto,@Query("pilih_tabel") String tabel);

    @GET("datasensortables.php")
    public Call<ResponseBody> getSensorDataTables(@Query("witeldatel_code") String witeldatel_code,@Query("tabel") String tabel);

    @GET("datasensor.php")
    public Call<ResponseBody> getSensorDataAll(@Query("flagging") String flagging);

}
