package com.dreamwalker.knu2018.dteacher.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by KNU2017 on 2018-02-10.
 */

public class DrugDBHelper extends SQLiteOpenHelper {
    private Context context;

    public DrugDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE DRUG ( ");
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
        String sql = "drop table DRUG;"; // 테이블 드랍
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
}
