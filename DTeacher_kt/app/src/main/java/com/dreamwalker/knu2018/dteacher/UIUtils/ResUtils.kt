package com.dreamwalker.knu2018.dteacher.UIUtils


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

class ResUtils {

    fun getColor(res: Int): Int {
        return ContextCompat.getColor(mContext!!, res)
    }

    fun getDrawable(res: Int): Drawable? {
        return ContextCompat.getDrawable(mContext!!, res)
    }

    companion object {
        var instance: ResUtils? = null
        private var mContext: Context? = null

        fun getInstance(context: Context): ResUtils {
            if (instance == null) {
                synchronized(ResUtils::class.java) {
                    if (instance == null) {
                        instance = ResUtils()
                        mContext = context.applicationContext
                    }
                }
            }
            return instance
        }
    }
}
