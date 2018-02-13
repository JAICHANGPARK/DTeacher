package com.dreamwalker.knu2018.dteacher.Utils;

import java.util.ArrayList;

/**
 * Created by KNU2017 on 2018-02-08.
 * 이벤트 버스를 사용하여 프레그먼트간 데이터를 전달해보기 위함.
 */

public class DrugDataEvent {
    public ArrayList<String> drugRrapidList;
    public ArrayList<String> drugRapidList;
    public ArrayList<String> drugNeutralList;
    public ArrayList<String> drugLongtimeList;
    public ArrayList<String> drugMixedList;
    public int position;
    public int pageNumber;

    public DrugDataEvent(ArrayList<String> drugRrapidList, ArrayList<String> drugRapidList,
                         ArrayList<String> drugNeutralList, ArrayList<String> drugLongtimeList,
                         ArrayList<String> drugMixedList, int position , int pageNumber) {
        this.drugRrapidList = drugRrapidList;
        this.drugRapidList = drugRapidList;
        this.drugNeutralList = drugNeutralList;
        this.drugLongtimeList = drugLongtimeList;
        this.drugMixedList = drugMixedList;

        this.position = position;
        this.pageNumber = pageNumber;
    }

}
