package com.inventec.android.exchangerate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SearchExchangeRate {

    private final String TAG = "SearchExchangeRate";
    private String url_str = "";
    public static final String  HANDLER_UPDATE_UI_DATA_KEY = "RateData";

    /**
     * set search base
     *
     * @param countryCode :initial  exchange rate base
     */
    SearchExchangeRate(String countryCode) {
        this.url_str = "https://api.exchangerate-api.com/v4/latest/" + countryCode;
    }

    /**
     * set search base
     *
     * @param countryCode : exchange rate base
     */
    public void setBase(String countryCode) {
        this.url_str = "https://api.exchangerate-api.com/v4/latest/" + countryCode;
    }

    //get exchange rate, use asyncTask and update UI
    public void getExchangeRate() {
        Log.d(TAG, "getExchangeRate");
        new GetExchangeRateTask().execute(this.url_str);
    }

    //this class use to get exchange rate
    class GetExchangeRateTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                URL url_str = new URL(url[0]);
                HttpURLConnection request = (HttpURLConnection) url_str.openConnection();
                request.connect();
                // Convert to JSON
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject rootJsonObject = root.getAsJsonObject();
                JsonObject ratesJsonObject = rootJsonObject.getAsJsonObject("rates");

                ArrayList<RateItem> rateHashMap = new ArrayList<>();
                Set<Map.Entry<String, JsonElement>> entries = ratesJsonObject.entrySet();//will return members of your object

                for (Map.Entry<String, JsonElement> entry : entries) {
                    RateItem rateItem = new RateItem();
                    rateItem.setCountryCode(entry.getKey());
                    rateItem.setRate(Float.parseFloat(entry.getValue().toString()));
                    rateHashMap.add(rateItem);
                    Log.d(TAG, "key = " + entry.getKey() + " value = " + entry.getValue());
                }

                Message message = Message.obtain();
                message.what = MainActivity.UPDATE_UI;
                Bundle extras = new Bundle();
                extras.putSerializable(HANDLER_UPDATE_UI_DATA_KEY, rateHashMap);
                message.setData(extras);
                MainActivity.mHandler.sendMessage(message);
            } catch (MalformedURLException e) {
                Log.d(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, "IOException");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
