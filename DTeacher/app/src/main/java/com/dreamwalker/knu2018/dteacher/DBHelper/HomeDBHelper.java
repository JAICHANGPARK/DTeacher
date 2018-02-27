package com.dreamwalker.knu2018.dteacher.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.Model.Global;

import java.util.ArrayList;

/**
 * Created by KNU2017 on 2018-02-07.
 */

public class HomeDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "HomeDBHelper";
    Context mContext;

    BSDBHelper bsdbHelper;
    DrugDBHelper drugDBHelper;
    FitnessDBHelper fitnessDBHelper;

    ArrayList<Global> homeList;
    ArrayList<Global> drugList;
    ArrayList<Global> fitnessList;
    String readDate;

    public HomeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;

        Log.e(TAG, "HomeDBHelper:  생성자 호출 됭서요 "  );
        homeList = new ArrayList<>();
        drugList = new ArrayList<>();
        fitnessList = new ArrayList<>();

        bsdbHelper = new BSDBHelper(mContext, "bs.db", null, 1);
        drugDBHelper = new DrugDBHelper(mContext, "drug.db", null, 1);
        fitnessDBHelper = new FitnessDBHelper(mContext, "fitness.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "HomeDBHelper: onCreate 호출 됬어요 "  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String selectDateAllData(String date) {
        StringBuffer sb = new StringBuffer();
        StringBuilder dateStringBuiler = new StringBuilder();
        String[] columns = {"TYPE", "VALUE", "DATE", "TIME"};

        sb.append(" SELECT _ID, TYPE, VALUE, DATE, TIME FROM BS");
        sb.append(" WHERE");
        sb.append(" DATE='" + date + "'");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getWritableDatabase();
        //Cursor findEntry = db.query("BS", columns, "date=?", new String[]{date}, null, null, null);
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while (cursor.moveToNext()) {
            dateStringBuiler.append("id:");
            dateStringBuiler.append(cursor.getString(0));
            dateStringBuiler.append("type:");
            dateStringBuiler.append(cursor.getString(1));
            dateStringBuiler.append("value:");
            dateStringBuiler.append(cursor.getString(2));
            dateStringBuiler.append("date:");
            dateStringBuiler.append(cursor.getString(3));
            dateStringBuiler.append("time:");
            dateStringBuiler.append(cursor.getString(4));
            dateStringBuiler.append("\n");
        }
        Log.e(TAG, "selectDateAllData: " + dateStringBuiler);

        if (!dateStringBuiler.toString().equals("")) {
            return dateStringBuiler.toString();
        } else {
            dateStringBuiler.append("null");
            return dateStringBuiler.toString();
        }
    }

    public ArrayList<BloodSugar> selectAll(String date) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<BloodSugar> bloodSugars = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        String[] columns = {"TYPE", "VALUE", "DATE", "TIME"};
        sb.append(" SELECT _ID, TYPE, VALUE, DATE, TIME FROM BS");
        sb.append(" WHERE");
        sb.append(" DATE='" + date + "'");
        // 읽기 전용 DB 객체를 만든다.

        //Cursor findEntry = db.query("BS", columns, "date=?", new String[]{date}, null, null, null);
        Cursor cursor = db.rawQuery(sb.toString(), null);
        String valueType;
        String value;
        String timeValue;

        while (cursor.moveToNext()) {
            valueType = cursor.getString(1);
            value = cursor.getString(2);
            timeValue = cursor.getString(4);
            bloodSugars.add(new BloodSugar(valueType, value, timeValue));
        }

        for (int i = 0; i < bloodSugars.size(); i++) {
            Log.e(TAG, "selectDateAllData: " + bloodSugars.get(i).getBsType() + ","
                    + bloodSugars.get(i).getBsValue() + "," + bloodSugars.get(i).getBsTime());
        }

        if (bloodSugars.size() != 0) {
            return bloodSugars;
        } else {
            bloodSugars.add(new BloodSugar("null", "null", "null"));
            return bloodSugars;
        }
    }

    /**
     * 혈당 테이블 : BS
     * 투약 테이블 : DRUG
     * 운동 테이블 : FITNESS
     * 식사 테이블 : 아직 구현 x
     *
     * @param date
     * @return ArrayList<BloodSugar>
     * @author JAICHANGPARK
     */
    public ArrayList<Global> allReadData(String date) {
        SQLiteDatabase db = getWritableDatabase();
        if (date != null){
            Log.e(TAG, "allReadData: 데이터 들어왔네요   "  );
            readDate = date;
            homeList = bsdbHelper.readHomeDate(readDate);
            drugList = drugDBHelper.readHomeDate(readDate);
            if (!drugList.isEmpty()) homeList.addAll(drugList);
            fitnessList = fitnessDBHelper.readHomeDate(readDate);
            if (!fitnessList.isEmpty()) homeList.addAll(fitnessList);
        }else {
            Log.e(TAG, "allReadData: 널값이네요  "  );
        }


        if (homeList.size() != 0) {
            return homeList;
        } else {
            homeList.add(new Global("null", "null", "null", "null"));
            return homeList;
        }
    }
}
