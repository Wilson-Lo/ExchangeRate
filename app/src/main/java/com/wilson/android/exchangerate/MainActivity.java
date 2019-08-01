package com.wilson.android.exchangerate;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static RecyclerView recyclerView;
    public static final int UPDATE_UI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    @Override
    protected void onStart() {
        SearchExchangeRate a = new SearchExchangeRate("TWD");
        a.setBase("USD");
        a.getExchangeRate();

        super.onStart();
    }

    private void setupUI(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
    }

    /**
     * Update UI
     */
    @SuppressLint("HandlerLeak")
    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    Log.d(TAG,"UPDATE_UI");
                    ArrayList<RateItem> rateHashMap = (ArrayList<RateItem>) msg.getData().getSerializable(SearchExchangeRate.HANDLER_UPDATE_UI_DATA_KEY);
                    if(rateHashMap != null){
                        Log.d(TAG,"UrateHashMap != null");
                        recyclerView.setAdapter(new RecyclerViewAdapter(ExchangeRateApplication.getAppContext() , rateHashMap));
                    }
                    break;

            }
        }
    };

}
