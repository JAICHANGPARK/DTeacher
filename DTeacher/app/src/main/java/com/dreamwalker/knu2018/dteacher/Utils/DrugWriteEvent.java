package com.dreamwalker.knu2018.dteacher.Utils;

/**
 * Created by KNU2017 on 2018-02-09.
 */

public class DrugWriteEvent {
    public  int valueUnit;
    public String drugName;
    public int position;

    public DrugWriteEvent(int valueUnit, String drugName, int position) {
        this.valueUnit = valueUnit;
        this.drugName = drugName;
        this.position = position;
    }
}
