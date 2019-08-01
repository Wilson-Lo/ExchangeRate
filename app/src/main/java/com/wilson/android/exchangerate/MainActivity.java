package com.wilson.android.exchangerate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";
    private static RecyclerView recyclerView;
    private static Spinner spinner;
    public static final int UPDATE_UI = 0;
    private static RecyclerViewAdapter recyclerViewAdapter;
    private static ArrayList<RateItem> rateHashMap;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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

        //recycle view
        recyclerViewAdapter = new RecyclerViewAdapter(this, rateHashMap);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (isTablet(this))
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);

        //SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> countryCodeList = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.countryCode,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(countryCodeList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new SearchExchangeRate(parent.getSelectedItem().toString()).getExchangeRate();
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

    /**
     * @param context : context
     * @return true is tablet, false is smart phone
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        new SearchExchangeRate(spinner.getSelectedItem().toString()).getExchangeRate();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
