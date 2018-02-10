package com.dreamwalker.knu2018.dteacher.UIViews

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View

import com.dreamwalker.knu2018.dteacher.R


/**
 * @author Alost
 */
class SlantedTextView : View {
    private var mPaint: Paint? = null
    private var mTextPaint: TextPaint? = null
    private var mSlantedLength = 40f
    private var mTextSize = 16f
    private var mSlantedBackgroundColor = Color.TRANSPARENT
    private var mTextColor = Color.WHITE
    private var mSlantedText: String? = ""
    private var mMode = MODE_LEFT

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.SlantedTextView)

        mTextSize = array.getDimension(R.styleable.SlantedTextView_slantedTextSize, mTextSize)
        mTextColor = array.getColor(R.styleable.SlantedTextView_slantedTextColor, mTextColor)
        mSlantedLength = array.getDimension(R.styleable.SlantedTextView_slantedLength, mSlantedLength)
        mSlantedBackgroundColor = array.getColor(R.styleable.SlantedTextView_slantedBackgroundColor, mSlantedBackgroundColor)

        if (array.hasValue(R.styleable.SlantedTextView_slantedText)) {
            mSlantedText = array.getString(R.styleable.SlantedTextView_slantedText)
        }

        if (array.hasValue(R.styleable.SlantedTextView_slantedMode)) {
            mMode = array.getInt(R.styleable.SlantedTextView_slantedMode, 0)
        }
        array.recycle()

        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        mPaint!!.isAntiAlias = true
        mPaint!!.color = mSlantedBackgroundColor

        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.textSize = mTextSize
        mTextPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
        mTextPaint!!.color = mTextColor
    }


    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawText(canvas)
    }


    private fun drawBackground(canvas: Canvas) {
        var path = Path()
        val w = width
        val h = height

        if (w != h) throw IllegalStateException("SlantedTextView's width must equal to height")

        when (mMode) {
            MODE_LEFT -> path = getModeLeftPath(path, w, h)
            MODE_RIGHT -> path = getModeRightPath(path, w, h)
            MODE_LEFT_BOTTOM -> path = getModeLeftBottomPath(path, w, h)
            MODE_RIGHT_BOTTOM -> path = getModeRightBottomPath(path, w, h)
            MODE_LEFT_TRIANGLE -> path = getModeLeftTrianglePath(path, w, h)
            MODE_RIGHT_TRIANGLE -> path = getModeRightTrianglePath(path, w, h)
            MODE_LEFT_BOTTOM_TRIANGLE -> path = getModeLeftBottomTrianglePath(path, w, h)
            MODE_RIGHT_BOTTOM_TRIANGLE -> path = getModeRightBottomTrianglePath(path, w, h)
        }
        path.close()
        canvas.drawPath(path, mPaint!!)
        canvas.save()
    }

    private fun getModeLeftPath(path: Path, w: Int, h: Int): Path {
        path.moveTo(w.toFloat(), 0f)
        path.lineTo(0f, h.toFloat())
        path.lineTo(0f, h - mSlantedLength)
        path.lineTo(w - mSlantedLength, 0f)
        return path
    }

    private fun getModeRightPath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(w.toFloat(), h - mSlantedLength)
        path.lineTo(mSlantedLength, 0f)
        return path
    }

    private fun getModeLeftBottomPath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(w - mSlantedLength, h.toFloat())
        path.lineTo(0f, mSlantedLength)
        return path
    }

    private fun getModeRightBottomPath(path: Path, w: Int, h: Int): Path {
        path.moveTo(0f, h.toFloat())
        path.lineTo(mSlantedLength, h.toFloat())
        path.lineTo(w.toFloat(), mSlantedLength)
        path.lineTo(w.toFloat(), 0f)
        return path
    }

    private fun getModeLeftTrianglePath(path: Path, w: Int, h: Int): Path {
        path.lineTo(0f, h.toFloat())
        path.lineTo(w.toFloat(), 0f)
        return path
    }

    private fun getModeRightTrianglePath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), 0f)
        path.lineTo(w.toFloat(), h.toFloat())
        return path
    }

    private fun getModeLeftBottomTrianglePath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(0f, h.toFloat())
        return path
    }

    private fun getModeRightBottomTrianglePath(path: Path, w: Int, h: Int): Path {
        path.moveTo(0f, h.toFloat())
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(w.toFloat(), 0f)
        return path
    }

    private fun drawText(canvas: Canvas) {
        val w = (canvas.width - mSlantedLength / 2).toInt()
        val h = (canvas.height - mSlantedLength / 2).toInt()
        val xy = calculateXY(canvas, w, h)
        val toX = xy[0]
        val toY = xy[1]
        val centerX = xy[2]
        val centerY = xy[3]
        val angle = xy[4]

        canvas.rotate(angle, centerX, centerY)

        canvas.drawText(mSlantedText!!, toX, toY, mTextPaint!!)
    }

    private fun calculateXY(canvas: Canvas, w: Int, h: Int): FloatArray {
        val xy = FloatArray(5)
        var rect: Rect? = null
        var rectF: RectF? = null
        val offset = (mSlantedLength / 2).toInt()
        when (mMode) {
            MODE_LEFT_TRIANGLE, MODE_LEFT -> {
                rect = Rect(0, 0, w, h)
                rectF = RectF(rect)
                rectF.right = mTextPaint!!.measureText(mSlantedText, 0, mSlantedText!!.length)
                rectF.bottom = mTextPaint!!.descent() - mTextPaint!!.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - mTextPaint!!.ascent()
                xy[2] = (w / 2).toFloat()
                xy[3] = (h / 2).toFloat()
                xy[4] = (-ROTATE_ANGLE).toFloat()
            }
            MODE_RIGHT_TRIANGLE, MODE_RIGHT -> {
                rect = Rect(offset, 0, w + offset, h)
                rectF = RectF(rect)
                rectF.right = mTextPaint!!.measureText(mSlantedText, 0, mSlantedText!!.length)
                rectF.bottom = mTextPaint!!.descent() - mTextPaint!!.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - mTextPaint!!.ascent()
                xy[2] = (w / 2 + offset).toFloat()
                xy[3] = (h / 2).toFloat()
                xy[4] = ROTATE_ANGLE.toFloat()
            }
            MODE_LEFT_BOTTOM_TRIANGLE, MODE_LEFT_BOTTOM -> {
                rect = Rect(0, offset, w, h + offset)
                rectF = RectF(rect)
                rectF.right = mTextPaint!!.measureText(mSlantedText, 0, mSlantedText!!.length)
                rectF.bottom = mTextPaint!!.descent() - mTextPaint!!.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f

                xy[0] = rectF.left
                xy[1] = rectF.top - mTextPaint!!.ascent()
                xy[2] = (w / 2).toFloat()
                xy[3] = (h / 2 + offset).toFloat()
                xy[4] = ROTATE_ANGLE.toFloat()
            }
            MODE_RIGHT_BOTTOM_TRIANGLE, MODE_RIGHT_BOTTOM -> {
                rect = Rect(offset, offset, w + offset, h + offset)
                rectF = RectF(rect)
                rectF.right = mTextPaint!!.measureText(mSlantedText, 0, mSlantedText!!.length)
                rectF.bottom = mTextPaint!!.descent() - mTextPaint!!.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - mTextPaint!!.ascent()
                xy[2] = (w / 2 + offset).toFloat()
                xy[3] = (h / 2 + offset).toFloat()
                xy[4] = (-ROTATE_ANGLE).toFloat()
            }
        }
        return xy
    }

    fun setText(str: String): SlantedTextView {
        mSlantedText = str
        postInvalidate()
        return this
    }

    fun setText(res: Int): SlantedTextView {
        val str = resources.getString(res)
        if (!TextUtils.isEmpty(str)) {
            setText(str)
        }
        return this
    }

    fun getText(): String? {
        return mSlantedText
    }

    fun setSlantedBackgroundColor(color: Int): SlantedTextView {
        mSlantedBackgroundColor = color
        mPaint!!.color = mSlantedBackgroundColor
        postInvalidate()
        return this
    }

    fun setTextColor(color: Int): SlantedTextView {
        mTextColor = color
        mTextPaint!!.color = mTextColor
        postInvalidate()
        return this
    }

    /**
     * @param mode :
     * SlantedTextView.MODE_LEFT : top left
     * SlantedTextView.MODE_RIGHT :top right
     * @return this
     */
    fun setMode(mode: Int): SlantedTextView {
        if (mMode > MODE_RIGHT_BOTTOM_TRIANGLE || mMode < 0)
            throw IllegalArgumentException(mode.toString() + "is illegal argument ,please use right value")
        this.mMode = mode
        postInvalidate()
        return this
    }

    fun getMode(): Int {
        return mMode
    }

    fun setTextSize(size: Int): SlantedTextView {
        this.mTextSize = size.toFloat()
        mPaint!!.textSize = mTextSize
        postInvalidate()
        return this
    }

    /**
     * set slanted space length
     *
     * @param length
     * @return this
     */
    fun setSlantedLength(length: Int): SlantedTextView {
        mSlantedLength = length.toFloat()
        postInvalidate()
        return this
    }

    companion object {

        val MODE_LEFT = 0
        val MODE_RIGHT = 1
        val MODE_LEFT_BOTTOM = 2
        val MODE_RIGHT_BOTTOM = 3
        val MODE_LEFT_TRIANGLE = 4
        val MODE_RIGHT_TRIANGLE = 5
        val MODE_LEFT_BOTTOM_TRIANGLE = 6
        val MODE_RIGHT_BOTTOM_TRIANGLE = 7

        val ROTATE_ANGLE = 45
    }

}
