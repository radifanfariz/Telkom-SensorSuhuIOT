package com.mediumsitompul.sensortemperatureiot;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mediumsitompul.sensortemperatureiot.adapter.MainTable_Adapter;
import com.mediumsitompul.sensortemperatureiot.adapter.MainTable_GetSet;
import com.mediumsitompul.sensortemperatureiot.apihelper.BaseApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import io.supercharge.shimmerlayout.ShimmerLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.mediumsitompul.sensortemperatureiot.adapter.DataSensor_GetSet;

public class SensorApi {
    Context context;
    Activity activity;
    ProgressDialog progressDialog;
    BaseApiService mApiService;
//    List<DataSensor_GetSet> datasensor_list;
    HashMap<String,String> datasensor_map_witel;
    HashMap<String,String> datasensor_map_datel;
    List<String> datasensor_list_sto;
    List<String> datasensor_list_flagging;
    List<String> datasensor_list;
    String[] datasensor_array;
    FirstActivity firstActivity;
    ShimmerLayout shimmerLayout;

    Spinner spinner_witel;
    ArrayAdapter<CharSequence> spinner_witel_adapter;

    Spinner spinner_datel;
    ArrayAdapter<CharSequence> spinner_datel_adapter;

    Spinner spinner_sto;
    ArrayAdapter<CharSequence> spinner_sto_adapter;

    Spinner spinner_flagging;
    ArrayAdapter<CharSequence> spinner_flagging_adapter;

    String spinnerValue;

    TextView txtSto;
    TextView txtLocPoint;
    TextView txtAddress;
    //    TextView txtLatLong;
    TextView txtTempCutoffAlarm;
    TextView txtDateTime;
    TextView txtCurrentTemp;
    TextView txtNo;
    TextView txtFlagging;
    TextView txtSuhu;
    Chip chipLatLong;
    List<MainTable_GetSet> list_maintabel;
    RecyclerView recyclerView;

    public SensorApi(Context context, Activity activity, ProgressDialog progressDialog, BaseApiService mApiService,Spinner spinner_witel,Spinner spinner_datel,Spinner spinner_sto,Spinner spinner_flagging){
        this.context = context;
        this.activity = activity;
        this.progressDialog = progressDialog;
        this.mApiService = mApiService;
        this.spinner_witel = spinner_witel;
        this.spinner_datel = spinner_datel;
        this.spinner_sto = spinner_sto;
        this.spinner_flagging = spinner_flagging;
    }

    public SensorApi(Context context, ShimmerLayout shimmerLayout,BaseApiService mApiService){
        this.context = context;
        this.mApiService = mApiService;
        this.shimmerLayout = shimmerLayout;
    }

    public SensorApi(Context context, RecyclerView recyclerView,ShimmerLayout shimmerLayout, BaseApiService mApiService){
        this.context = context;
        this.mApiService = mApiService;
        this.recyclerView = recyclerView;
        this.shimmerLayout = shimmerLayout;
    }
//    public SensorApi(Context context, ShimmerLayout shimmerLayout,BaseApiService mApiService,
//                     TextView txtLocPoint,
//                     TextView txtAddress,
//                     TextView txtTempCutoffAlarm,
//                     TextView txtDateTime,
//                     TextView txtCurrentTemp,
//                     Chip chipLatLong){
//        this.context = context;
//        this.mApiService = mApiService;
//        this.shimmerLayout = shimmerLayout;
//        this.txtLocPoint = txtLocPoint;
//        this.txtAddress = txtAddress;
//        this.txtTempCutoffAlarm = txtTempCutoffAlarm;
//        this.txtDateTime = txtDateTime;
//        this.txtCurrentTemp = txtCurrentTemp;
//        this.chipLatLong = chipLatLong;
//    }

    public String[] getArrayFromMap(HashMap<String,String> map){
//        String[] itemData;
        datasensor_array = new String[map.size()];
        datasensor_array = map.keySet().toArray(datasensor_array);
        return datasensor_array;
    }

    public String[] getArrayFromList(List<String> list){
//        String[] itemData;
        datasensor_array = new String[list.size()];
        datasensor_array = list.toArray(datasensor_array);
        return datasensor_array;
    }

    public void getSensorDataWitel(String tabel) {
//        progressDialog.show();
//        firstActivity = new FirstActivity();
        datasensor_map_witel = new HashMap<String, String>();
        mApiService.getSensorDataWitel(tabel)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
//                            progressDialog.dismiss();
                            try {
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String id_witel = jsonArray.getJSONObject(i).getString("id_witel");
                                    String witel = jsonArray.getJSONObject(i).getString("witel");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    datasensor_map_witel.put(witel,id_witel);
                                }
//                                firstActivity.setSpinner(firstActivity.getArrayFromList(datasensor_list));
//                                firstActivity.getArrayFromList(datasensor_list);
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<String> arraylist_temp = new ArrayList<String>(datasensor_map_witel.keySet());
                        arraylist_temp.add(0,"Pilih Witel");
                        spinner_witel_adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item,getArrayFromList(arraylist_temp));
                        spinner_witel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_witel.setAdapter(spinner_witel_adapter);
                        Log.e("LIST", "LIST_TEST > " + datasensor_map_witel.values());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        progressDialog.dismiss();
                    }
                });
    }

    public void getSensorDataDatel(String id_witel,String tabel) {
//        progressDialog.show();
//        firstActivity = new FirstActivity();
        datasensor_map_datel = new HashMap<String, String>();
        mApiService.getSensorDataDatel(id_witel,tabel)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        datasensor_map.clear();
                        if (response.isSuccessful()){
//                            progressDialog.dismiss();
                            try {
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String id_witel = jsonArray.getJSONObject(i).getString("id_witel");
                                    String id_datel = jsonArray.getJSONObject(i).getString("id_datel");
                                    String datel = jsonArray.getJSONObject(i).getString("datel");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    datasensor_map_datel.put(datel,id_witel+id_datel);
                                }
//                                firstActivity.setSpinner(firstActivity.getArrayFromList(datasensor_list));
//                                firstActivity.getArrayFromList(datasensor_list);
//                                Log.e("LIST", "LIST_TEST > " + getDatasensor_list().get(1).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            progressDialog.dismiss();
                            datasensor_map_datel.put("Pilih Datel","Pilih Datel");
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<String> arraylist_temp = new ArrayList<String>(datasensor_map_datel.keySet());
                        arraylist_temp.add(0,"Pilih Datel");
                        spinner_datel_adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item,getArrayFromList(arraylist_temp));
                        spinner_datel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_datel.setAdapter(spinner_datel_adapter);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        progressDialog.dismiss();
                    }
                });
    }

    public void getSensorDataSto(String id_datel,String tabel) {
//        progressDialog.show();
//        firstActivity = new FirstActivity();
        datasensor_list_sto = new ArrayList<String>();
        mApiService.getSensorDataSto(id_datel,tabel)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
//                            progressDialog.dismiss();
                            try {
                                datasensor_list_sto.add("Pilih STO");
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String sto = jsonArray.getJSONObject(i).getString("sto");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    datasensor_list_sto.add(sto);
                                }
//                                firstActivity.setSpinner(firstActivity.getArrayFromList(datasensor_list));
//                                firstActivity.getArrayFromList(datasensor_list);
//                                Log.e("LIST", "LIST_TEST > " + getDatasensor_list().get(1).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            progressDialog.dismiss();
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
//                        spinner_sto_adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item,getArrayFromList(datasensor_list_sto));
//                        spinner_sto_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spinner_sto.setAdapter(spinner_sto_adapter);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        progressDialog.dismiss();
                    }
                });
    }

    public void getSensorDataFlagging(String sto,String tabel) {
//        progressDialog.show();
//        firstActivity = new FirstActivity();
        datasensor_list_flagging = new ArrayList<String>();
        mApiService.getSensorDataFlagging(sto,tabel)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
//                            progressDialog.dismiss();
                            try {
                                datasensor_list_flagging.add("Pilih Flagging");
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String flagging = jsonArray.getJSONObject(i).getString("flagging");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    datasensor_list_flagging.add(flagging);
                                }
//                                firstActivity.setSpinner(firstActivity.getArrayFromList(datasensor_list));
//                                firstActivity.getArrayFromList(datasensor_list);
//                                Log.e("LIST", "LIST_TEST > " + getDatasensor_list().get(1).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
//                        spinner_flagging_adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item,getArrayFromList(datasensor_list_flagging));
//                        spinner_flagging_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spinner_flagging.setAdapter(spinner_flagging_adapter);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        progressDialog.dismiss();
                    }
                });
    }

    public void getSensorDataAll(String flagging) {
//        progressDialog.show();
//        firstActivity = new FirstActivity();
        datasensor_list = new ArrayList<String>();
        mApiService.getSensorDataAll(flagging)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            try {
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String data_temp = jsonArray.getJSONObject(i).getString("data_temp");
                                    String cutoff_alarm = jsonArray.getJSONObject(i).getString("cutoff_alarm");
                                    String date = jsonArray.getJSONObject(i).getString("date");
                                    String loc_point = jsonArray.getJSONObject(i).getString("loc_point");
                                    String address = jsonArray.getJSONObject(i).getString("address");
                                    String lat = jsonArray.getJSONObject(i).getString("lat");
                                    String lng = jsonArray.getJSONObject(i).getString("lng");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    datasensor_list.add(data_temp);
                                    datasensor_list.add(cutoff_alarm);
                                    datasensor_list.add(date);
                                    datasensor_list.add(loc_point);
                                    datasensor_list.add(address);
                                    datasensor_list.add(lat);
                                    datasensor_list.add(lng);
                                }
//                                firstActivity.setSpinner(firstActivity.getArrayFromList(datasensor_list));
//                                firstActivity.getArrayFromList(datasensor_list);
//                                spinner_flagging_adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item,getArrayFromList(datasensor_list_flagging));
//                                spinner_flagging_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spinner_flagging.setAdapter(spinner_flagging_adapter);
//                                Log.e("LIST", "LIST_TEST > " + getDatasensor_list().get(1).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        progressDialog.dismiss();
                    }
                });
    }

    public void getSensorDataAll(String flagging,TextView txtSto,TextView txtLocPoint,
                                 TextView txtAddress,
                                 TextView txtTempCutoffAlarm,
                                 TextView txtDateTime,
                                 TextView txtCurrentTemp,
                                 Chip chipLatLong) {
//        progressDialog.show();
//        firstActivity = new FirstActivity();
        datasensor_list = new ArrayList<String>();
        this.txtSto = txtSto;
        this.txtLocPoint = txtLocPoint;
        this.txtAddress = txtAddress;
        this.txtTempCutoffAlarm = txtTempCutoffAlarm;
        this.txtDateTime = txtDateTime;
        this.txtCurrentTemp = txtCurrentTemp;
        this.chipLatLong = chipLatLong;
        mApiService.getSensorDataAll(flagging)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String sto = null;
                        String data_temp = null;
                        String cutoff_alarm = null;
                        String date = null;
                        String loc_point = null;
                        String address = null;
                        String lat = null;
                        String lng = null;
                        if (response.isSuccessful()){
                            try {
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    sto = jsonArray.getJSONObject(i).getString("sto");
                                    data_temp = jsonArray.getJSONObject(i).getString("data_temp");
                                    cutoff_alarm = jsonArray.getJSONObject(i).getString("cutoff_alarm");
                                    date = jsonArray.getJSONObject(i).getString("date");
                                    loc_point = jsonArray.getJSONObject(i).getString("loc_point");
                                    address = jsonArray.getJSONObject(i).getString("address");
                                    lat = jsonArray.getJSONObject(i).getString("lat");
                                    lng = jsonArray.getJSONObject(i).getString("lng");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    datasensor_list.add(sto);
                                    datasensor_list.add(data_temp);
                                    datasensor_list.add(cutoff_alarm);
                                    datasensor_list.add(date);
                                    datasensor_list.add(loc_point);
                                    datasensor_list.add(address);
                                    datasensor_list.add(lat);
                                    datasensor_list.add(lng);
                                }
                                txtSto.setText(sto);
                                txtLocPoint.setText(loc_point);
                                txtAddress.setText(address);
                                chipLatLong.setText(lat+" , "+lng);
                                txtTempCutoffAlarm.setText(cutoff_alarm+"C");
                                txtDateTime.setText(date);
                                txtCurrentTemp.setText(data_temp+"C");
                                try {
                                    Thread.sleep(500);
                                    shimmerLayout.stopShimmerAnimation();
                                    shimmerLayout.setVisibility(View.GONE);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
//                                Log.e("LIST", "LIST_TEST > " + getDatasensor_list().get(1).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                datasensor_list.add("No Data");
                                try {
                                    Thread.sleep(500);
                                    shimmerLayout.stopShimmerAnimation();
                                    shimmerLayout.setVisibility(View.GONE);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                try {
                                    Thread.sleep(500);
                                    shimmerLayout.stopShimmerAnimation();
                                    shimmerLayout.setVisibility(View.GONE);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            try {
                                Thread.sleep(500);
                                shimmerLayout.stopShimmerAnimation();
                                shimmerLayout.setVisibility(View.GONE);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        shimmerLayout.stopShimmerAnimation();
                        shimmerLayout.setVisibility(View.GONE);
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

    public void getSensorDataTables(String witeldatel_code, String tabel, HashMap<String,String> datatables) {
        list_maintabel = new ArrayList<>();
        mApiService.getSensorDataTables(witeldatel_code,tabel)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONArray jsonArray = new JSONArray(response.body().string());
//                                Toast.makeText(context, "Berhasil Dimuat", Toast.LENGTH_SHORT).show();
                                list_maintabel.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String no = jsonArray.getJSONObject(i).getString("no");
                                    String date = jsonArray.getJSONObject(i).getString("date");
                                    String flagging = jsonArray.getJSONObject(i).getString("flagging");
                                    String suhu = jsonArray.getJSONObject(i).getString("data_temperatur");
//                                    Log.e("LIST", "LIST_TEST > " + witel.toString());
//                                    datasensor_list.add(new DataSensor_GetSet(id_witel,witel,"","","",""));
                                    list_maintabel.add(new MainTable_GetSet(no,date,flagging,suhu,datatables));
                                    MainTable_Adapter mainTable_adapter = new MainTable_Adapter(context,list_maintabel);
                                    recyclerView.setAdapter(mainTable_adapter);
                                }
                                try {
                                    Thread.sleep(500);
                                    shimmerLayout.stopShimmerAnimation();
                                    shimmerLayout.setVisibility(View.GONE);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
//                                Log.e("LIST", "LIST_TEST > " + getDatasensor_list().get(1).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                try {
                                    Thread.sleep(500);
                                    shimmerLayout.stopShimmerAnimation();
                                    shimmerLayout.setVisibility(View.GONE);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                try {
                                    Thread.sleep(500);
                                    shimmerLayout.stopShimmerAnimation();
                                    shimmerLayout.setVisibility(View.GONE);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
//                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            try {
                                Thread.sleep(500);
                                shimmerLayout.stopShimmerAnimation();
                                shimmerLayout.setVisibility(View.GONE);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            Toast.makeText(context, "Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        shimmerLayout.stopShimmerAnimation();
                        shimmerLayout.setVisibility(View.GONE);
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

}

