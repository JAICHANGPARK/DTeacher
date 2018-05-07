package com.dreamwalker.knu2018.dteacher.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Model.Drug;
import com.dreamwalker.knu2018.dteacher.Model.Global;

import java.util.ArrayList;

/**
 * Created by KNU2017 on 2018-02-10.
 */

public class DrugDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String TAG = "DrugDBHelper";

    public DrugDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE IF NOT EXISTS DRUG ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" DRUGNAME TEXT, ");
        sb.append(" UNIT TEXT, ");
        sb.append(" DATE TEXT, ");
        sb.append(" TIME TEXT ) ");
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table IF EXISTS DRUG;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insertDrugData(String drugName, String unit, String date, String time) {

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO DRUG ( ");
        sb.append(" DRUGNAME, UNIT, DATE, TIME ) ");
        sb.append(" VALUES ( ?, ?, ? , ? ) ");
        db.execSQL(sb.toString(), new Object[]{drugName, unit, date, time});

    }

    public String selectAllData() {
        StringBuffer sb = new StringBuffer();
        StringBuilder dateStringBuiler = new StringBuilder();

        sb.append("SELECT _ID, DRUGNAME, UNIT, DATE, TIME FROM DRUG");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while (cursor.moveToNext()) {
            dateStringBuiler.append("id:");
            dateStringBuiler.append(cursor.getString(0));
            dateStringBuiler.append("DRUGNAME:");
            dateStringBuiler.append(cursor.getString(1));
            dateStringBuiler.append("UNIT:");
            dateStringBuiler.append(cursor.getString(2));
            dateStringBuiler.append("date:");
            dateStringBuiler.append(cursor.getString(3));
            dateStringBuiler.append("time:");
            dateStringBuiler.append(cursor.getString(4));
            dateStringBuiler.append("\n");
        }
        return dateStringBuiler.toString();
    }

    public ArrayList<Global> readHomeDate(String date) {
        ArrayList<Global> drugList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String valueType;
        String value;
        String timeValue;

        Cursor cursor = null;
        // TODO: 2018-02-11 혈당 값 가져오기
        sb.append(" SELECT DRUGNAME, UNIT, TIME FROM DRUG");
        sb.append(" WHERE");
        sb.append(" DATE='" + date + "'");

        if (db.rawQuery(sb.toString(), null) != null) {
            cursor = db.rawQuery(sb.toString(), null);
        } else {
            onCreate(db);
        }

        while (cursor.moveToNext()) {
            valueType = cursor.getString(0);
            value = cursor.getString(1);
            timeValue = cursor.getString(2);
            drugList.add(new Global("3", valueType, value, timeValue));
        }
        return drugList;
    }


    /**
     * @author : JAICHANGPARK
     * 다이터리에 사용될 메소드 .
     * 오름차순으로 가져옴.
     *
     * @return
     */

    public ArrayList<Drug> selectDiaryAll(){

        ArrayList<Drug> drugList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        String valueType;
        String value;
        String dateValue;
        String timeValue;

        sb.append(" SELECT DRUGNAME, UNIT, DATE, TIME FROM DRUG");
        sb.append(" ORDER BY ");
        sb.append(" DATE ASC");

        Cursor cursor = db.rawQuery(sb.toString(), null);

        while (cursor.moveToNext()) {
            valueType = cursor.getString(0);
            value = cursor.getString(1);
            dateValue = cursor.getString(2);
            timeValue = cursor.getString(3);
            drugList.add(new Drug(valueType, value, dateValue, timeValue));
        }

        return drugList;
    }

    public ArrayList<Drug> selectDiaryDESC(){

        ArrayList<Drug> drugList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        String valueType;
        String value;
        String dateValue;
        String timeValue;

        sb.append(" SELECT DRUGNAME, UNIT, DATE, TIME FROM DRUG");
        sb.append(" ORDER BY ");
        sb.append(" DATE DESC,TIME DESC");

        Cursor cursor = db.rawQuery(sb.toString(), null);

        while (cursor.moveToNext()) {
            valueType = cursor.getString(0);
            value = cursor.getString(1);
            dateValue = cursor.getString(2);
            timeValue = cursor.getString(3);
            drugList.add(new Drug(valueType, value, dateValue, timeValue));
        }

        return drugList;
    }
}
