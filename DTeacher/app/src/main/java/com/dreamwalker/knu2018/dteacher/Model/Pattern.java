package com.dreamwalker.knu2018.dteacher.Model;

/**
 * Created by KNU2017 on 2018-01-26.
 */

public class Pattern {

    String id;
    String type;
    String value;
    String year;
    String month;
    String day;
    String hour;
    String minute;
    String second;

    public Pattern(String id, String type, String value, String year, String month, String day, String hour, String minute, String second) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Pattern(String type, String value, String year, String month, String day, String hour, String minute, String second) {
        this.type = type;
        this.value = value;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
