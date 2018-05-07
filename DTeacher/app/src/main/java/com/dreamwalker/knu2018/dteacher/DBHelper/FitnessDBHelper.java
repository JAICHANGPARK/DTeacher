package com.dreamwalker.knu2018.dteacher.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.Model.Global;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by KNU2017 on 2018-02-08.
 */

public class FitnessDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "FitnessDBHelper";
    private Context context;

    public FitnessDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE IF NOT EXISTS FITNESS ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TYPE TEXT, ");
        sb.append(" STRENGTH TEXT, ");
        sb.append(" FITNESSTIME TEXT, ");
        sb.append(" KCAL TEXT, ");
        sb.append(" DATE TEXT, ");
        sb.append(" TIME TEXT ) ");
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());
        Toast.makeText(context, "DB Table 생성완료", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table IF EXISTS FITNESS;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insertFitnessData(String type, String strength, String fitTime,
                                  String kcal, String date, String time) {

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO FITNESS ( ");
        sb.append(" TYPE, STRENGTH, FITNESSTIME, KCAL, DATE, TIME ) ");
        sb.append(" VALUES ( ?, ?, ? , ? ,? , ?) ");
        db.execSQL(sb.toString(), new Object[]{type, strength, fitTime, kcal, date, time});
        Toast.makeText(context, "운동기록 완료!", Toast.LENGTH_SHORT).show();
    }

    public String selectAllData(){
        StringBuffer sb = new StringBuffer();
        StringBuilder dateStringBuiler = new StringBuilder();
        sb.append("SELECT _ID, TYPE, STRENGTH, FITNESSTIME, KCAL, DATE, TIME FROM FITNESS");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(),null);

        while (cursor.moveToNext()){
            dateStringBuiler.append("id:");
            dateStringBuiler.append(cursor.getString(0));
            dateStringBuiler.append("type:");
            dateStringBuiler.append(cursor.getString(1));
            dateStringBuiler.append("STRENGTH:");
            dateStringBuiler.append(cursor.getString(2));
            dateStringBuiler.append("FITNESSTIME:");
            dateStringBuiler.append(cursor.getString(3));
            dateStringBuiler.append("KCAL:");
            dateStringBuiler.append(cursor.getString(4));
            dateStringBuiler.append("DATE:");
            dateStringBuiler.append(cursor.getString(5));
            dateStringBuiler.append("DATE:");
            dateStringBuiler.append(cursor.getString(6));
            dateStringBuiler.append("\n\n");
        }
        Toast.makeText(context, "데이터 가져왔어요", Toast.LENGTH_SHORT).show();
        return dateStringBuiler.toString();
    }

    public ArrayList<Global> readHomeDate(String date){
        ArrayList<Global> fitnessList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String valueType;
        String value;
        String timeValue;

        // TODO: 2018-02-11 운동 값 가져오기
        sb.append(" SELECT TYPE, KCAL, TIME FROM FITNESS");
        sb.append(" WHERE");
        sb.append(" DATE='" + date + "'");
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while (cursor.moveToNext()){
            valueType = cursor.getString(0);
            value = cursor.getString(1);
            timeValue = cursor.getString(2);
            fitnessList.add(new Global("1",valueType, value,timeValue));
        }
        return fitnessList;
    }
}
