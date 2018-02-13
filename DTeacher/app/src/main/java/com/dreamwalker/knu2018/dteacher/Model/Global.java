package com.dreamwalker.knu2018.dteacher.Model;

/**
 * Created by KNU2017 on 2018-02-11.
 */

public class Global {
    String head;
    String type;
    String value;
    String time;

    public Global(String head, String type, String value, String time) {
        this.head = head;
        this.type = type;
        this.value = value;
        this.time = time;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
