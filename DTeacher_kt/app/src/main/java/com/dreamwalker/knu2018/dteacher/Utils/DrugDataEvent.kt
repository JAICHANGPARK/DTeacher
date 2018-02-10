package com.dreamwalker.knu2018.dteacher.Utils

import java.util.ArrayList

/**
 * Created by KNU2017 on 2018-02-08.
 * 이벤트 버스를 사용하여 프레그먼트간 데이터를 전달해보기 위함.
 */

class DrugDataEvent(var drugRrapidList: ArrayList<String>, var drugRapidList: ArrayList<String>,
                    var drugNeutralList: ArrayList<String>, var drugLongtimeList: ArrayList<String>,
                    var drugMixedList: ArrayList<String>, var position: Int, var pageNumber: Int)
