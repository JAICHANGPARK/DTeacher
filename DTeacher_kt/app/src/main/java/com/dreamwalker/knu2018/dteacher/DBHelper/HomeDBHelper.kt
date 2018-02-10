package com.dreamwalker.knu2018.dteacher.DBHelper

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by KNU2017 on 2018-02-07.
 */

class HomeDBHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun selectDateAllData(date: String): String {
        val sb = StringBuffer()
        val dateStringBuiler = StringBuilder()
        val columns = arrayOf("TYPE", "VALUE", "DATE", "TIME")

        sb.append(" SELECT _ID, TYPE, VALUE, DATE, TIME FROM BS")
        sb.append(" WHERE")
        sb.append(" DATE='$date'")
        // 읽기 전용 DB 객체를 만든다.
        val db = writableDatabase
        //Cursor findEntry = db.query("BS", columns, "date=?", new String[]{date}, null, null, null);
        val cursor = db.rawQuery(sb.toString(), null)

        while (cursor.moveToNext()) {
            dateStringBuiler.append("id:")
            dateStringBuiler.append(cursor.getString(0))
            dateStringBuiler.append("type:")
            dateStringBuiler.append(cursor.getString(1))
            dateStringBuiler.append("value:")
            dateStringBuiler.append(cursor.getString(2))
            dateStringBuiler.append("date:")
            dateStringBuiler.append(cursor.getString(3))
            dateStringBuiler.append("time:")
            dateStringBuiler.append(cursor.getString(4))
            dateStringBuiler.append("\n")
        }
        Log.e(TAG, "selectDateAllData: " + dateStringBuiler)

        if (dateStringBuiler.toString() != "") {
            return dateStringBuiler.toString()
        } else {
            dateStringBuiler.append("null")
            return dateStringBuiler.toString()
        }
    }

    companion object {
        private val TAG = "HomeDBHelper"
    }
}
