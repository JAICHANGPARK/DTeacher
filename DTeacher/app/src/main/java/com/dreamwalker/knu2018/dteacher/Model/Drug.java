package com.dreamwalker.knu2018.dteacher.Model;

/**
 * Created by KNU2017 on 2018-02-09.
 */

public class Drug {

    String valueUnit;
    String drugName;

    public Drug(String valueUnit, String drugName) {
        this.valueUnit = valueUnit;
        this.drugName = drugName;
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

