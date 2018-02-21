package com.dreamwalker.knu2018.dteacher.Model;

/**
 * Created by KNU2017 on 2018-02-21.
 */

public class RealTime {
    String value;
    String datetime;

    public RealTime(String value, String datetime) {
        this.value = value;
        this.datetime = datetime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
