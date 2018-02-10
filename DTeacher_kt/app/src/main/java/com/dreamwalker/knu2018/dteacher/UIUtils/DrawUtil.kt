package com.dreamwalker.knu2018.dteacher.UIUtils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowManager

import java.io.ByteArrayOutputStream
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * 绘制工具类
 *
 * @author luopeihuan
 */
object DrawUtil {
    var sDensity = 1.0f
    var sDensityDpi: Int = 0
    var sWidthPixels: Int = 0
    var sHeightPixels: Int = 0
    var sFontDensity: Float = 0.toFloat()
    var sTouchSlop = 15 // 点击的最大识别距离，超过即认为是移动

    var sStatusHeight: Int = 0 // 平板中底边的状态栏高度
    private var sClass: Class<*>? = null
    private var sMethodForWidth: Method? = null
    private var sMethodForHeight: Method? = null
    var sTopStatusHeight: Int = 0

    var sNavBarLocation: Int = 0
    private var sRealWidthPixels: Int = 0
    private var sRealHeightPixels: Int = 0
    private var sNavBarWidth: Int = 0 // 虚拟键宽度
    private var sNavBarHeight: Int = 0 // 虚拟键高度
    val NAVBAR_LOCATION_RIGHT = 1
    val NAVBAR_LOCATION_BOTTOM = 2

    // 在某些机子上存在不同的density值，所以增加两个虚拟值
    var sVirtualDensity = -1f
    var sVirtualDensityDpi = -1f

    var sStatusBar: Int = 0

    val isPad: Boolean
        get() {
            if (sDensity >= 1.5 || sDensity <= 0) {
                return false
            }
            if (sWidthPixels < sHeightPixels) {
                if (sWidthPixels > 480 && sHeightPixels > 800) {
                    return true
                }
            } else {
                if (sWidthPixels > 800 && sHeightPixels > 480) {
                    return true
                }
            }
            return false
        }

    val realWidth: Int
        get() = if (Machine.s_IS_SDK_ABOVE_KITKAT) {
            sRealWidthPixels
        } else sWidthPixels

    val realHeight: Int
        get() = if (Machine.s_IS_SDK_ABOVE_KITKAT) {
            sRealHeightPixels
        } else sHeightPixels


    /**
     * 虚拟键在下面时
     *
     * @return
     */
    val navBarHeight: Int
        get() = if (Machine.s_IS_SDK_ABOVE_KITKAT) {
            sNavBarHeight
        } else 0

    /**
     * 横屏，虚拟键在右边时
     *
     * @return
     */
    val navBarWidth: Int
        get() = if (Machine.s_IS_SDK_ABOVE_KITKAT) {
            sNavBarWidth
        } else 0

    val navBarLocation: Int
        get() = if (sRealWidthPixels > sWidthPixels) {
            NAVBAR_LOCATION_RIGHT
        } else NAVBAR_LOCATION_BOTTOM


    /**
     * dip/dp转像素
     *
     *
     * dip或 dp大小
     *
     * @return 像素值
     */
    fun dip2px(dipVlue: Float): Int {
        return (dipVlue * sDensity + 0.5f).toInt()
    }

    /**
     * 像素转dip/dp
     *
     * @param pxValue 像素大小
     * @return dip值
     */
    fun px2dip(pxValue: Float): Int {
        val scale = sDensity
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp 转 px
     *
     * @param spValue sp大小
     * @return 像素值
     */
    fun sp2px(spValue: Float): Int {
        val scale = sDensity
        return (scale * spValue).toInt()
    }

    /**
     * px转sp
     *
     * @param pxValue 像素大小
     * @return sp值
     */
    fun px2sp(pxValue: Float): Int {
        val scale = sDensity
        return (pxValue / scale).toInt()
    }

    fun resetDensity(context: Context?) {
        if (context != null && null != context.resources) {
            val metrics = context.resources.displayMetrics
            sDensity = metrics.density
            sFontDensity = metrics.scaledDensity
            sWidthPixels = metrics.widthPixels
            sHeightPixels = metrics.heightPixels
            sDensityDpi = metrics.densityDpi
            if (Machine.isTablet(context)) {
                sStatusHeight = getTabletScreenHeight(context) - sHeightPixels
            }
            try {
                val configuration = ViewConfiguration.get(context)
                if (null != configuration) {
                    sTouchSlop = configuration.scaledTouchSlop
                }
                getStatusBarHeight(context)
            } catch (e: Error) {
                Log.i("DrawUtil", "resetDensity has error" + e.message)
            }

            sStatusBar = getStatusBarHeight(context)

        }
        resetNavBarHeight(context)
    }

    private fun resetNavBarHeight(context: Context?) {
        if (context != null) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            try {
                if (sClass == null) {
                    sClass = Class.forName("android.view.Display")
                }
                val realSize = Point()
                val method = sClass!!.getMethod("getRealSize", Point::class.java)
                method.invoke(display, realSize)
                sRealWidthPixels = realSize.x
                sRealHeightPixels = realSize.y
                sNavBarWidth = realSize.x - sWidthPixels
                sNavBarHeight = realSize.y - sHeightPixels
            } catch (e: Exception) {
                sRealWidthPixels = sWidthPixels
                sRealHeightPixels = sHeightPixels
                sNavBarHeight = 0
            }

        }
        sNavBarLocation = navBarLocation
    }

    fun getTabletScreenWidth(context: Context?): Int {
        var width = 0
        if (context != null) {
            try {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                if (sClass == null) {
                    sClass = Class.forName("android.view.Display")
                }
                if (sMethodForWidth == null) {
                    sMethodForWidth = sClass!!.getMethod("getRealWidth")
                }
                width = sMethodForWidth!!.invoke(display) as Int
            } catch (e: Exception) {
            }

        }

        // Rect rect= new Rect();
        // ((Activity)
        // context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // int statusbarHeight = height - rect.bottom;
        if (width == 0) {
            width = sWidthPixels
        }

        return width
    }

    fun getTabletScreenHeight(context: Context?): Int {
        var height = 0
        if (context != null) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            try {
                if (sClass == null) {
                    sClass = Class.forName("android.view.Display")
                }
                if (sMethodForHeight == null) {
                    sMethodForHeight = sClass!!.getMethod("getRealHeight")
                }
                height = sMethodForHeight!!.invoke(display) as Int
            } catch (e: Exception) {
            }

        }

        // Rect rect= new Rect();
        // ((Activity)
        // context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // int statusbarHeight = height - rect.bottom;
        if (height == 0) {
            height = sHeightPixels
        }

        return height
    }

    fun getStatusBarHeight(context: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var top = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            top = context.resources.getDimensionPixelSize(x)
            sTopStatusHeight = top
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return top
    }

    /**
     * 底部状态栏高度
     */
    fun getNavigationBarHeight(context: Context): Int {
        if (Build.VERSION.SDK_INT >= 19) {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        } else {
            return 0
        }
    }

    /**
     * 裁剪成圆形图片
     *
     * @param bitmap 原图
     */
    fun getRoundedCornerBitmap(bitmap: Bitmap?): Bitmap? {
        if (bitmap != null) {
            val moutBitmap = Bitmap.createBitmap(bitmap.width,
                    bitmap.height, Bitmap.Config.ARGB_8888)
            val mcanvas = Canvas(moutBitmap)
            val mcolor = -0xbdbdbe
            val mpaint = Paint()
            val mrect = Rect(0, 0, bitmap.width,
                    bitmap.height)
            val mrectF = RectF(mrect)
            val mroundPX = (bitmap.width / 2).toFloat()
            mpaint.isAntiAlias = true
            mcanvas.drawARGB(0, 0, 0, 0)
            mpaint.color = mcolor
            mcanvas.drawRoundRect(mrectF, mroundPX, mroundPX, mpaint)
            mpaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            mcanvas.drawBitmap(bitmap, mrect, mrect, mpaint)
            return moutBitmap
        } else {
            return null
        }

    }

    fun Bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    //使用Bitmap加Matrix来缩放
    fun resizeImage(bitmap: Bitmap, w: Int, h: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scaleWidth = w.toFloat() / width
        val scaleHeight = h.toFloat() / height

        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        return Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true)
    }

    fun setTranslucentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            //                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            //            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    fun setNoTranslucentStatusBar(window: Window) {
        Log.i("zou", "DrawUtil setNoTranslucentStatusBar")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i("zou", "DrawUtil setNoTranslucentStatusBar1111")
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.BLACK
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("zou", "DrawUtil setNoTranslucentStatusBar2222")
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        }
    }

    /**
     * 跑步中使用截图
     * @param view
     * @return
     */
    fun convertViewToBitmap(view: View): Bitmap {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        val bitmap = view.drawingCache
        view.isDrawingCacheEnabled = false
        return bitmap
    }
}
