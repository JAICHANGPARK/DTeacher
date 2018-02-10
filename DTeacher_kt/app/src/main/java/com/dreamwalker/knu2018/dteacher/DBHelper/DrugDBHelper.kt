package com.dreamwalker.knu2018.dteacher.DBHelper

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * Created by KNU2017 on 2018-02-10.
 */

class DrugDBHelper(private val context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        val sb = StringBuffer()
        sb.append(" CREATE TABLE DRUG ( ")
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" DRUGNAME TEXT, ")
        sb.append(" UNIT TEXT, ")
        sb.append(" DATE TEXT, ")
        sb.append(" TIME TEXT ) ")
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString())
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "drop table DRUG;" // 테이블 드랍
        db.execSQL(sql)
        onCreate(db) // 다시 테이블 생성
    }

    fun insertDrugData(drugName: String, unit: String, date: String, time: String) {

        val db = writableDatabase
        val sb = StringBuffer()

        sb.append(" INSERT INTO DRUG ( ")
        sb.append(" DRUGNAME, UNIT, DATE, TIME ) ")
        sb.append(" VALUES ( ?, ?, ? , ? ) ")
        db.execSQL(sb.toString(), arrayOf<Any>(drugName, unit, date, time))

    }

    fun selectAllData(): String {
        val sb = StringBuffer()
        val dateStringBuiler = StringBuilder()

        sb.append("SELECT _ID, DRUGNAME, UNIT, DATE, TIME FROM DRUG")
        // 읽기 전용 DB 객체를 만든다.
        val db = readableDatabase
        val cursor = db.rawQuery(sb.toString(), null)

        while (cursor.moveToNext()) {
            dateStringBuiler.append("id:")
            dateStringBuiler.append(cursor.getString(0))
            dateStringBuiler.append("DRUGNAME:")
            dateStringBuiler.append(cursor.getString(1))
            dateStringBuiler.append("UNIT:")
            dateStringBuiler.append(cursor.getString(2))
            dateStringBuiler.append("date:")
            dateStringBuiler.append(cursor.getString(3))
            dateStringBuiler.append("time:")
            dateStringBuiler.append(cursor.getString(4))
            dateStringBuiler.append("\n")
        }
        return dateStringBuiler.toString()
    }
}
