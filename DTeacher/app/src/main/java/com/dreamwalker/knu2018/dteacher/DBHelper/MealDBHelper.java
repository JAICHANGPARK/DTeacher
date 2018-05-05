package com.dreamwalker.knu2018.dteacher.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Model.Global;

import java.util.ArrayList;

/**
 * Created by KNU2017 on 2018-05-05.
 */

public class MealDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "MealDBHelper";
    private Context context;

    public MealDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE IF NOT EXISTS MEAL ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TYPE TEXT, ");
        sb.append(" GOKRYU TEXT, ");
        sb.append(" BEEF TEXT, ");
        sb.append(" VEGETABLE TEXT, ");
        sb.append(" FAT TEXT, ");
        sb.append(" MILK TEXT, ");
        sb.append(" FRUIT TEXT, ");
        sb.append(" TOTALEXCHANGE TEXT, ");
        sb.append(" SATISFACTION TEXT, ");
        sb.append(" KCAL TEXT, ");
        sb.append(" STARTTIME TEXT, ");
        sb.append(" ENDTIME TEXT, ");
        sb.append(" MEALTIME TEXT, ");
        sb.append(" DATE TEXT, ");
        sb.append(" TIME TEXT ) ");
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());
        Log.e(TAG, "onCreate: MEAL DB Table 생성완료");
        Toast.makeText(context, "MEAL DB Table 생성완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table IF EXISTS MEAL;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insertMealData(String type, String gokryu, String beef, String vegetable, String fat,
                               String milk, String fruit, String totalExchange, String satisfaction,
                               String kcal, String startTime, String endTime, String mealTime,
                               String date, String time) {

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO MEAL ( ");
        sb.append(" TYPE, " +
                "GOKRYU, " +
                "BEEF, " +
                "VEGETABLE, " +
                "FAT, " +
                "MILK, " +
                "FRUIT," +
                "TOTALEXCHANGE, " +
                "SATISFACTION, " +
                "KCAL, " +
                "STARTTIME, " +
                "ENDTIME, " +
                "MEALTIME," +
                "DATE, TIME ) ");
        sb.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
        db.execSQL(sb.toString(), new Object[]{type, gokryu, beef, vegetable, fat, milk, fruit,
                totalExchange, satisfaction, kcal, startTime, endTime, mealTime, date, time});
        Toast.makeText(context, "식사 기록 완료!", Toast.LENGTH_SHORT).show();
    }

    public String selectAllData() {
        StringBuffer sb = new StringBuffer();
        StringBuilder dateStringBuiler = new StringBuilder();
        sb.append("SELECT _ID, ");
        sb.append("TYPE, " +
                "GOKRYU, " +
                "BEEF, " +
                "VEGETABLE, " +
                "FAT, " +
                "MILK, " +
                "FRUIT," +
                "TOTALEXCHANGE, " +
                "SATISFACTION, " +
                "KCAL, " +
                "STARTTIME, " +
                "ENDTIME, " +
                "MEALTIME," +
                "DATE, TIME FROM MEAL");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while (cursor.moveToNext()) {
            dateStringBuiler.append("id:");
            dateStringBuiler.append(cursor.getString(0));
            dateStringBuiler.append("type:");
            dateStringBuiler.append(cursor.getString(1));
            dateStringBuiler.append("곡류:");
            dateStringBuiler.append(cursor.getString(2));
            dateStringBuiler.append("어육류:");
            dateStringBuiler.append(cursor.getString(3));
            dateStringBuiler.append("채소:");
            dateStringBuiler.append(cursor.getString(4));
            dateStringBuiler.append("지방:");
            dateStringBuiler.append(cursor.getString(5));
            dateStringBuiler.append("우유:");
            dateStringBuiler.append(cursor.getString(6));
            dateStringBuiler.append("과일:");
            dateStringBuiler.append(cursor.getString(7));
            dateStringBuiler.append("총교환단위:");
            dateStringBuiler.append(cursor.getString(8));

            dateStringBuiler.append("만족도:");
            dateStringBuiler.append(cursor.getString(9));
            dateStringBuiler.append("총칼로리:");
            dateStringBuiler.append(cursor.getString(10));
            dateStringBuiler.append("시작시간:");
            dateStringBuiler.append(cursor.getString(11));
            dateStringBuiler.append("종료시간:");
            dateStringBuiler.append(cursor.getString(12));
            dateStringBuiler.append("식사시간:");
            dateStringBuiler.append(cursor.getString(13));
            dateStringBuiler.append("\n\n");
        }
        Log.e(TAG, "selectAllData: 다이어리 식사데이터 가져왔어요 ");
        //Toast.makeText(context, "데이터 가져왔어요", Toast.LENGTH_SHORT).show();
        return dateStringBuiler.toString();
    }

    public ArrayList<Global> readHomeDate(String date) {
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

        while (cursor.moveToNext()) {
            valueType = cursor.getString(0);
            value = cursor.getString(1);
            timeValue = cursor.getString(2);
            fitnessList.add(new Global("1", valueType, value, timeValue));
        }
        return fitnessList;
    }
}
