package com.mediumsitompul.sensortemperatureiot;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.mediumsitompul.sensortemperatureiot.apihelper.BaseApiService;
import com.mediumsitompul.sensortemperatureiot.apihelper.UtilsApi;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class MainActivity extends AppCompatActivity {
    CardView refreshBtn;
    ConstraintLayout constraintLayout;
    SkeletonScreen skeletonScreen;
    ShimmerLayout shimmerLayout;
    LinearLayout linearLayout;
    BottomSheetBehavior sheetBehavior;

    String witelParam;
    String datelParam;
    String stoParam;
    String flaggingParam;
    String dataTemp;
    String cutoffAlarm;
    String dateTime;
    String locPoint;
    String address;
    String lat;
    String lng;

    TextView txtWitel;
    TextView txtDatel;
    TextView txtSto;
    TextView txtFlagging;
    TextView txtLocPoint;
    TextView txtAddress;
//    TextView txtLatLong;
    TextView txtTempCutoffAlarm;
    TextView txtDateTime;
    TextView txtCurrentTemp;
    Chip chipLatLong;
    ScrollView scrollView;

    SensorApi sensorApi;
    BaseApiService mApiService;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshBtn = findViewById(R.id.card_btn_refresh);
        constraintLayout = findViewById(R.id.cl_1);
        linearLayout = findViewById(R.id.ll_2);
        shimmerLayout = findViewById(R.id.shimmerLayout);
        sheetBehavior = BottomSheetBehavior.from(linearLayout);

        txtWitel = findViewById(R.id.data_witel);
        txtDatel = findViewById(R.id.data_kandatel);
        txtSto = findViewById(R.id.data_sto);
        txtFlagging = findViewById(R.id.data_flagging);
        txtLocPoint = findViewById(R.id.data_loc_point);
        txtAddress = findViewById(R.id.data_addrs);
//        txtLatLong = findViewById(R.id.data_latlong);
        txtTempCutoffAlarm = findViewById(R.id.data_cutoff);
        txtDateTime = findViewById(R.id.data_datetime);
        txtCurrentTemp = findViewById(R.id.data_current_temp);
        chipLatLong = findViewById(R.id.data_latlong_chip);
        scrollView = findViewById(R.id.theScroll);

        sheetBehavior.setDraggable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        witelParam = getIntent().getStringExtra("WITEL_PARAM");
        datelParam = getIntent().getStringExtra("DATEL_PARAM");
        stoParam = getIntent().getStringExtra("STO_PARAM");
        flaggingParam = getIntent().getStringExtra("FLAGGING_PARAM");
//        dataTemp = getIntent().getStringExtra("DATA_TEMP_PARAM");
//        cutoffAlarm = getIntent().getStringExtra("CUTOFF_ALARM_PARAM");
//        dateTime = getIntent().getStringExtra("DATE_PARAM");
//        locPoint = getIntent().getStringExtra("LOC_POINT");
//        address = getIntent().getStringExtra("ADDRESS");
//        lat = getIntent().getStringExtra("LAT");
//        lng = getIntent().getStringExtra("LONG");
//        Toast.makeText(getApplicationContext(), locPoint, Toast.LENGTH_SHORT).show();

        txtWitel.setText(witelParam);
        txtDatel.setText(datelParam);
//        txtSto.setText(stoParam);
        txtFlagging.setText(flaggingParam);
//        txtLocPoint.setText(locPoint);
//        txtAddress.setText(address);
////        txtLatLong.setText(latlong);
//        chipLatLong.setText(lat+" , "+lng);
//        txtTempCutoffAlarm.setText(cutoffAlarm+"C");
//        txtDateTime.setText(dateTime);
//        txtCurrentTemp.setText(dataTemp+"C");

        mApiService = UtilsApi.getAPIService();

        sensorApi = new SensorApi(this,shimmerLayout,mApiService);
        getData();

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
//                constraintLayout.setVisibility(View.INVISIBLE);
//                shimmerLayout.setVisibility(View.VISIBLE);
//                shimmerLayout.startShimmerAnimation();
//                sensorApi.getSensorDataAll(flaggingParam,txtLocPoint,
//                        txtAddress,
//                        txtTempCutoffAlarm,
//                        txtDateTime,
//                        txtCurrentTemp,
//                        chipLatLong);
//                skeletonScreen = Skeleton.bind(constraintLayout).load(R.layout.item_placeholder_layout).show();
            }
        });

        chipLatLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps(sensorApi.datasensor_list.get(5),sensorApi.datasensor_list.get(6));
            }
        });

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                sheetBehavior.setDraggable(false);
            }
        });

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                sheetBehavior.setDraggable(true);
                return false;
            }
        });

    }

    public void goToMaps(String lat,String lng){
        if (sensorApi.datasensor_list != null) {
            if (!lat.equals("") && !lng.equals("") && !lat.equals("No Data") && !lng.equals("No Data")) {
                Uri uri = Uri.parse("google.navigation:q=" + lat + "," + lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "No Data LatLong", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Data LatLong Is Null", Toast.LENGTH_SHORT).show();
        }
    }

    public void getData(){
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
        sensorApi.getSensorDataAll(flaggingParam,txtSto,txtLocPoint,
                txtAddress,
                txtTempCutoffAlarm,
                txtDateTime,
                txtCurrentTemp,
                chipLatLong);
    }


}