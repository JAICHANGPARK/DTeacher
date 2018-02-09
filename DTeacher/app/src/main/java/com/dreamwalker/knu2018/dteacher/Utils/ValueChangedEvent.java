package com.dreamwalker.knu2018.dteacher.Utils;

/**
 * Created by KNU2017 on 2018-02-08.
 * 이벤트 버스를 사용하여 프레그먼트간 데이터를 전달해보기 위함.
 */

public class ValueChangedEvent {
    public String newText;
    public String newText2;

    public ValueChangedEvent(String newText, String newText2) {
        this.newText = newText;
        this.newText2 = newText2;
    }
}
