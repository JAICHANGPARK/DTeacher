package com.dreamwalker.knu2018.dteacher.Model;

/**
 * Created by KNU2017 on 2018-02-09.
 */

public class Drug {

    String valueUnit;
    String drugName;
    String date;
    String time;

    public Drug(String valueUnit, String drugName) {
        this.valueUnit = valueUnit;
        this.drugName = drugName;
    }

    public Drug(String drugName, String valueUnit, String date, String time) {
        this.drugName = drugName;
        this.valueUnit = valueUnit;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValueUnit() {
        return valueUnit;
    }

    public void setValueUnit(String valueUnit) {
        this.valueUnit = valueUnit;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
}

