package com.dreamwalker.knu2018.dteacher

import android.app.Application
import android.content.Context

import com.dreamwalker.knu2018.dteacher.UIUtils.DrawUtil

/**
 * application
 */
class BaseApplication : Application() {
    init {
        sInstance = this
    }

    override fun onCreate() {
        super.onCreate()
        DrawUtil.resetDensity(this)
    }

    companion object {
        protected var sInstance: BaseApplication


        val appContext: Context
            get() = sInstance
    }
}
