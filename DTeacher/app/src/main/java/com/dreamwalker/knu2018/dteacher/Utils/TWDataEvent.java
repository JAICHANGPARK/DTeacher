package com.dreamwalker.knu2018.dteacher.Utils;

/**
 * Created by KNU2017 on 2018-02-09.
 */

public class TWDataEvent {
    public String date;
    public String time;
    public String type;
    public String value;
    public String extraValue1;
    public String extraValue2;
    public int pageNumber;

    public TWDataEvent(String date, String time, String type, String value, String extraValue1, String extraValue2, int pageNumber) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.value = value;
        this.extraValue1 = extraValue1;
        this.extraValue2 = extraValue2;
        this.pageNumber = pageNumber;
    }
}
