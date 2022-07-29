package com.mediumsitompul.sensortemperatureiot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mediumsitompul.sensortemperatureiot.apihelper.BaseApiService;
import com.mediumsitompul.sensortemperatureiot.apihelper.UtilsApi;

import java.util.HashMap;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class MainActivityTable extends AppCompatActivity {
    RecyclerView recyclerView;
    ShimmerLayout shimmerLayout;
    SensorApi sensorApi;
    BaseApiService mApiService;
    BottomSheetBehavior sheetBehavior;
    LinearLayout linearLayout;
    String witel_param;
    String datel_param;
    String sto_param;
    String flagging_param;
    String witelDatel_code;
    HashMap<String,String> datatables;
    CardView card_refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearLayout = findViewById(R.id.ll_3);
        sheetBehavior = BottomSheetBehavior.from(linearLayout);
        shimmerLayout = findViewById(R.id.shimmerLayout);

        sheetBehavior.setDraggable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        datatables = new HashMap<>();

        card_refreshBtn = findViewById(R.id.card_btn_refresh);

        mApiService = UtilsApi.getAPIService();
        sensorApi = new SensorApi(this,recyclerView,shimmerLayout,mApiService);

        witel_param = getIntent().getStringExtra("WITEL_PARAM");
        datel_param = getIntent().getStringExtra("DATEL_PARAM");
        witelDatel_code = getIntent().getStringExtra("WITELDATEL_CODE");

//        sto_param = getIntent().getStringExtra("STO_PARAM");

        datatables.put("WITEL_PARAM",witel_param);
        datatables.put("DATEL_PARAM",datel_param);
//        datatables.put("STO_PARAM",sto_param);

        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
        sensorApi.getSensorDataTables(witelDatel_code,SensorConstants.TABEL,datatables);

        card_refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shimmerLayout.setVisibility(View.VISIBLE);
                shimmerLayout.startShimmerAnimation();
                sensorApi.getSensorDataTables(witelDatel_code,SensorConstants.TABEL,datatables);
//                Toast.makeText(getApplicationContext(), witelDatel_code, Toast.LENGTH_SHORT).show();
            }
        });

    }
}