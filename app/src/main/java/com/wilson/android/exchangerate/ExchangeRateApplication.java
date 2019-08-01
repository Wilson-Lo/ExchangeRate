package com.wilson.android.exchangerate;

import android.app.Application;
import android.content.Context;

/**
 * This class to slove "static way can not get Context issue"
 */
public class ExchangeRateApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ExchangeRateApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ExchangeRateApplication.context;
    }
}
