package com.mediumsitompul.sensortemperatureiot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mediumsitompul.sensortemperatureiot.apihelper.BaseApiService;
import com.mediumsitompul.sensortemperatureiot.apihelper.UtilsApi;

import java.lang.reflect.Array;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    BottomSheetBehavior sheetBehavior;
    CardView submitBtn;
    ProgressDialog progressDialog;

    Spinner spinner_witel;
    Spinner spinner_datel;
    Spinner spinner_sto;
    Spinner spinner_flagging;
    String spinnerValue;
    String witeldatel_code;
    String witelParam;
    String datelParam;
    String stoParam;
    String flaggingParam;

    SensorApi sensorApi;
    BaseApiService mApiService;
    String[] itemData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Data Sensor");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);

        mApiService = UtilsApi.getAPIService();
        spinner_witel = findViewById(R.id.data_witel);
        spinner_datel = findViewById(R.id.data_kandatel);
//        spinner_sto = findViewById(R.id.data_sto);
//        spinner_flagging = findViewById(R.id.data_flagging);

        linearLayout = findViewById(R.id.ll_1);
        sheetBehavior = BottomSheetBehavior.from(linearLayout);
        submitBtn = findViewById(R.id.card_btn_submit);


        sheetBehavior.setDraggable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        sensorApi = new SensorApi(this,this,progressDialog,mApiService,spinner_witel,spinner_datel,spinner_sto,spinner_flagging);
        sensorApi.getSensorDataWitel(SensorConstants.TABEL);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,MainActivityTable.class);
                intent.putExtra("WITEL_PARAM",witelParam);
                intent.putExtra("DATEL_PARAM",datelParam);
                intent.putExtra("WITELDATEL_CODE",spinnerValue);
//                intent.putExtra("STO_PARAM",stoParam);
//                intent.putExtra("FLAGGING_PARAM",flaggingParam);
//                intent.putExtra("DATA_TEMP_PARAM",sensorApi.datasensor_list.get(0));
//                intent.putExtra("CUTOFF_ALARM_PARAM",sensorApi.datasensor_list.get(1));
//                intent.putExtra("DATE_PARAM",sensorApi.datasensor_list.get(2));
//                intent.putExtra("LOC_POINT",sensorApi.datasensor_list.get(3));
//                intent.putExtra("ADDRESS",sensorApi.datasensor_list.get(4));
//                intent.putExtra("LAT",sensorApi.datasensor_list.get(5));
//                intent.putExtra("LONG",sensorApi.datasensor_list.get(6));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        spinner_witel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                witelParam = selectedItem;
                spinnerValue = sensorApi.datasensor_map_witel.get(selectedItem);
                if (spinnerValue != null) {
                    sensorApi.getSensorDataDatel(spinnerValue, SensorConstants.TABEL);
                    Log.e("spinner", "spinner_test > " + spinnerValue.toString());
                }else {
                    sensorApi.getSensorDataDatel(spinnerValue, SensorConstants.TABEL);
                }

//                Toast.makeText(getApplicationContext(), spinnerValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_datel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                datelParam = selectedItem;
                spinnerValue = sensorApi.datasensor_map_datel.get(selectedItem);
                sensorApi.getSensorDataSto(spinnerValue,SensorConstants.TABEL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        spinner_sto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedItem = (String) adapterView.getItemAtPosition(i);
//                stoParam = selectedItem;
//                sensorApi.getSensorDataFlagging(selectedItem,SensorConstants.TABEL);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        spinner_flagging.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedItem = (String) adapterView.getItemAtPosition(i);
//                flaggingParam = selectedItem;
//                sensorApi.getSensorDataAll(flaggingParam);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}