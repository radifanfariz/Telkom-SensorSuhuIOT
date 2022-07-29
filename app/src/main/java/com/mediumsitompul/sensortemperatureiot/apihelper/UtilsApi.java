package com.mediumsitompul.sensortemperatureiot.apihelper;

import com.mediumsitompul.sensortemperatureiot.SensorConstants;

public class UtilsApi {
    // 10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "http://192.168.43.247/login/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(SensorConstants.BASE_URL_API).create(BaseApiService.class);
    }
}
