package com.dreamwalker.knu2018.dteacher.UIUtils

import android.graphics.Paint
import android.text.TextPaint

/**
 * Created by chenjingmian
 */
object TextUtil {

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    fun getFontlength(paint: TextPaint, str: String): Float {
        return paint.measureText(str)
    }

    /**
     * @return 返回指定笔的文字高度
     */
    fun getFontHeight(paint: Paint): Float {
        val fm = paint.fontMetrics
        return fm.descent - fm.ascent
    }

    fun getNoString(no: Int): String {
        val result: String
        if (no > 19) {
            result = tranNoString(no / 10) + "十" + tranNoString(no % 10)
        } else if (no > 9) {
            result = "十" + tranNoString(no % 10)
        } else {
            result = tranNoString(no)
        }
        return result
    }

    fun tranNoString(no: Int): String {
        when (no) {
            1 -> return "一"
            2 -> return "二"
            3 -> return "三"
            4 -> return "四"
            5 -> return "五"
            6 -> return "六"
            7 -> return "七"
            8 -> return "八"
            9 -> return "九"
        }
        return ""
    }
}
