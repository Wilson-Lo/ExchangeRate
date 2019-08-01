package com.wilson.android.exchangerate;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static RecyclerView recyclerView;
    private Spinner spinner;
    public static final int UPDATE_UI = 0;
    private static RecyclerViewAdapter recyclerViewAdapter;
    private static ArrayList<RateItem> rateHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //setup UI
    private void setupUI() {
        rateHashMap = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(this, rateHashMap);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> countryCodeList = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.countryCode,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(countryCodeList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new SearchExchangeRate(parent.getSelectedItem().toString()).getExchangeRate();
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Update UI
    @SuppressLint("HandlerLeak")
    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    Log.d(TAG, "UPDATE_UI");

                    if (rateHashMap != null) {
                        Log.d(TAG, "UrateHashMap != null");
                        rateHashMap.clear();
                        rateHashMap.addAll((ArrayList<RateItem>) msg.getData().getSerializable(SearchExchangeRate.HANDLER_UPDATE_UI_DATA_KEY));
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;

            }
        }
    };

}
