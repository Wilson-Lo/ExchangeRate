package com.wilson.android.exchangerate;

public class RateItem {

    private float rate = 0;
    private String countryCode = "";

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public float getRate() {
        return this.rate;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

}
