package com.dreamwalker.knu2018.dteacher.Model;

/**
 * Created by KNU2017 on 2018-02-11.
 */

public class BloodSugar {

    String bsType;
    String bsValue;
    String bsTime;

    public BloodSugar(String bsType, String bsValue, String bsTime) {
        this.bsType = bsType;
        this.bsValue = bsValue;
        this.bsTime = bsTime;
    }

    public String getBsType() {
        return bsType;
    }

    public void setBsType(String bsType) {
        this.bsType = bsType;
    }

    public String getBsValue() {
        return bsValue;
    }

    public void setBsValue(String bsValue) {
        this.bsValue = bsValue;
    }

    public String getBsTime() {
        return bsTime;
    }

    public void setBsTime(String bsTime) {
        this.bsTime = bsTime;
    }
}
