package com.dreamwalker.knu2018.dteacher;

import android.app.Application;
import android.content.Context;

 import com.dreamwalker.knu2018.dteacher.UIUtils.DrawUtil;


/**
 * application
 */
public class BaseApplication extends Application {
    protected static BaseApplication sInstance;

    public BaseApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DrawUtil.resetDensity(this);
    }


    public static Context getAppContext() {
        return sInstance;
    }
}
