package com.dreamwalker.knu2018.dteacher.DBHelper

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * Created by KNU2017 on 2018-02-08.
 */

class FitnessDBHelper(private val context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        val sb = StringBuffer()
        sb.append(" CREATE TABLE FITNESS ( ")
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" TYPE TEXT, ")
        sb.append(" STRENGTH TEXT, ")
        sb.append(" FITNESSTIME TEXT, ")
        sb.append(" KCAL TEXT, ")
        sb.append(" DATE TEXT, ")
        sb.append(" TIME TEXT ) ")
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString())
        Toast.makeText(context, "DB Table 생성완료", Toast.LENGTH_SHORT).show()

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "drop table FITNESS;" // 테이블 드랍
        db.execSQL(sql)
        onCreate(db) // 다시 테이블 생성
    }

    fun insertFitnessData(type: String, strength: String, fitTime: String,
                          kcal: String, date: String, time: String) {

        val db = writableDatabase
        val sb = StringBuffer()
        sb.append(" INSERT INTO FITNESS ( ")
        sb.append(" TYPE, STRENGTH, FITNESSTIME, KCAL, DATE, TIME ) ")
        sb.append(" VALUES ( ?, ?, ? , ? ,? , ?) ")
        db.execSQL(sb.toString(), arrayOf<Any>(type, strength, fitTime, kcal, date, time))
        Toast.makeText(context, "운동기록 완료!", Toast.LENGTH_SHORT).show()
    }

    fun selectAllData(): String {
        val sb = StringBuffer()
        val dateStringBuiler = StringBuilder()
        sb.append("SELECT _ID, TYPE, STRENGTH, FITNESSTIME, KCAL, DATE, TIME FROM FITNESS")
        // 읽기 전용 DB 객체를 만든다.
        val db = readableDatabase
        val cursor = db.rawQuery(sb.toString(), null)

        while (cursor.moveToNext()) {
            dateStringBuiler.append("id:")
            dateStringBuiler.append(cursor.getString(0))
            dateStringBuiler.append("type:")
            dateStringBuiler.append(cursor.getString(1))
            dateStringBuiler.append("STRENGTH:")
            dateStringBuiler.append(cursor.getString(2))
            dateStringBuiler.append("FITNESSTIME:")
            dateStringBuiler.append(cursor.getString(3))
            dateStringBuiler.append("KCAL:")
            dateStringBuiler.append(cursor.getString(4))
            dateStringBuiler.append("DATE:")
            dateStringBuiler.append(cursor.getString(5))
            dateStringBuiler.append("DATE:")
            dateStringBuiler.append(cursor.getString(6))
            dateStringBuiler.append("\n\n")
        }
        Toast.makeText(context, "데이터 가져왔어요", Toast.LENGTH_SHORT).show()
        return dateStringBuiler.toString()
    }
}
