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

/**
 * Created by KNU2017 on 2018-02-07.
 */

public class BSDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "BSDBHelper";
    private Context context;

    public BSDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE IF NOT EXISTS BS ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TYPE TEXT, ");
        sb.append(" VALUE TEXT, ");
        sb.append(" DATE TEXT, ");
        sb.append(" TIME TEXT ) ");
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists BS;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insertBSData(String type, String value, String date, String time) {

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO BS ( ");
        sb.append(" TYPE, VALUE, DATE, TIME ) ");
        sb.append(" VALUES ( ?, ?, ? , ? ) ");
        db.execSQL(sb.toString(), new Object[]{type, value, date, time});
    }

    public String selectAllData() {
        StringBuffer sb = new StringBuffer();
        StringBuilder dateStringBuiler = new StringBuilder();

        sb.append("SELECT _ID, TYPE, VALUE, DATE, TIME FROM BS");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
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
        return dateStringBuiler.toString();
    }

    public ArrayList<BloodSugar> selectAll() {
        ArrayList<BloodSugar> bloodSugars = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT _ID, TYPE, VALUE, DATE, TIME FROM BS");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
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
        return bloodSugars;
    }

    public ArrayList<Global> readHomeDate(String date) {
        Log.e(TAG, "readHomeDate : 입력된 : 날짜 :  " + date);
        ArrayList<Global> bloodSugars = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String valueType;
        String value;
        String timeValue;

        // TODO: 2018-02-11 혈당 값 가져오기
        sb.append(" SELECT TYPE, VALUE, TIME FROM BS");
        sb.append(" WHERE");
        sb.append(" DATE='" + date + "'");

        Log.e(TAG, "BSDBHelper readHomeDate: " + " 쿼리 체크 ");
        Cursor cursor = db.rawQuery(sb.toString(), null);
        while (cursor.moveToNext()) {
            valueType = cursor.getString(0);
            value = cursor.getString(1);
            timeValue = cursor.getString(2);
            bloodSugars.add(new Global("0", valueType, value, timeValue));
        }

        //return bloodSugars;

        if (bloodSugars != null) {
            return bloodSugars;
        } else {
            bloodSugars.add(new Global("0", "unknown", "unknown", "unknown"));
            return bloodSugars;
        }
    }
}
