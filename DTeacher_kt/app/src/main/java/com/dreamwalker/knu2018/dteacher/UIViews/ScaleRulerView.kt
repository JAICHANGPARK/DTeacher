package com.dreamwalker.knu2018.dteacher.UIViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller

/**
 * Created by zoubo on 16/3/16.
 * 自定义横向滚动刻度尺
 *
 * @version 1.0
 */

class ScaleRulerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mDensity: Float = 0.toFloat()
    /**
     * 获取当前刻度值
     *
     * @return
     */
    var value = 50f
        private set
    private var mMaxValue = 100f
    private var mDefaultMinValue = 0f
    private val mModType = MOD_TYPE
    private val mLineDivider = ITEM_HEIGHT_DIVIDER
    private var mLastX: Int = 0
    private var mMove: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mMinVelocity: Int = 0
    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null

    private var mListener: OnValueChangeListener? = null

    private val mLinePaint = Paint()
    private val mSelectPaint = Paint()
    private val mSelectWidth = 8
    private val mNormalLineWidth = 4
    private val mSelectColor = "#F7577F"
    private val mNormalLineColor = "#E8E8E8"


    interface OnValueChangeListener {
        fun onValueChange(value: Float)
    }

    init {
        init(context)
    }

    protected fun init(context: Context) {
        mScroller = Scroller(context)
        mDensity = context.resources.displayMetrics.density

        mMinVelocity = ViewConfiguration.get(getContext()).scaledMinimumFlingVelocity
    }

    /**
     * @param defaultValue 初始值
     * @param maxValue     最大值
     */
    fun initViewParam(defaultValue: Float, maxValue: Float, defaultMinValue: Float) {
        value = defaultValue
        mMaxValue = maxValue
        mDefaultMinValue = defaultMinValue

        invalidate()

        mLastX = 0
        mMove = 0
        notifyValueChange()
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    fun setValueChangeListener(listener: OnValueChangeListener) {
        mListener = listener
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mWidth = width
        mHeight = height
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawScaleLine(canvas)
        drawMiddleLine(canvas)
    }


    /**
     * 从中间往两边开始画刻度线
     *
     * @param canvas
     */
    private fun drawScaleLine(canvas: Canvas) {
        canvas.save()

        mLinePaint.strokeWidth = mNormalLineWidth.toFloat()
        mLinePaint.color = Color.parseColor(mNormalLineColor)

        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = TEXT_SIZE * mDensity
        val textWidth = Layout.getDesiredWidth("0", textPaint)

        val width = mWidth
        var drawCount = 0
        var xPosition: Float

        var i = 0
        while (drawCount <= 4 * width) {

            xPosition = width / 2 - mMove + i.toFloat() * mLineDivider.toFloat() * mDensity
            if (xPosition + paddingRight < mWidth && value + i <= mMaxValue) {
                if ((value + i) % mModType == 0f) {
                    canvas.drawLine(xPosition, height.toFloat(), xPosition, height - mDensity * ITEM_MAX_HEIGHT, mLinePaint)

                    //                    if (mValue + i <= mMaxValue) {
                    //                        canvas.drawText(String.valueOf((mValue + i)), countLeftStart(mValue + i, xPosition, textWidth), getHeight() - textWidth, textPaint);
                    //                    }
                } else {
                    canvas.drawLine(xPosition, height.toFloat(), xPosition, height - mDensity * ITEM_MIN_HEIGHT, mLinePaint)
                }
            }

            xPosition = width / 2 - mMove - i.toFloat() * mLineDivider.toFloat() * mDensity
            if (xPosition > paddingLeft && value - i >= mDefaultMinValue) {
                if ((value - i) % mModType == 0f) {
                    canvas.drawLine(xPosition, height.toFloat(), xPosition, height - mDensity * ITEM_MAX_HEIGHT, mLinePaint)

                    //                    if (mValue - i >= 0) {
                    //                        canvas.drawText(String.valueOf((mValue - i)), countLeftStart(mValue - i, xPosition, textWidth), getHeight() - textWidth, textPaint);
                    //                    }
                } else {
                    canvas.drawLine(xPosition, height.toFloat(), xPosition, height - mDensity * ITEM_MIN_HEIGHT, mLinePaint)
                }
            }

            drawCount += (2f * mLineDivider.toFloat() * mDensity).toInt()
            i++
        }

        canvas.restore()
    }

    /**
     * 计算没有数字显示位置的辅助方法
     *
     * @param value
     * @param xPosition
     * @param textWidth
     * @return
     */
    private fun countLeftStart(value: Int, xPosition: Float, textWidth: Float): Float {
        var xp = 0f
        if (value < 20) {
            xp = xPosition - textWidth * 1 / 2
        } else {
            xp = xPosition - textWidth * 2 / 2
        }
        return xp
    }

    /**
     * 画中间的红色指示线、阴影等。指示线两端简单的用了两个矩形代替
     *
     * @param canvas
     */
    private fun drawMiddleLine(canvas: Canvas) {
        canvas.save()

        mSelectPaint.strokeWidth = mSelectWidth.toFloat()
        mSelectPaint.color = Color.parseColor(mSelectColor)
        canvas.drawLine((mWidth / 2).toFloat(), 0f, (mWidth / 2).toFloat(), mHeight.toFloat(), mSelectPaint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val xPosition = event.x.toInt()

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScroller!!.forceFinished(true)

                mLastX = xPosition
                mMove = 0
            }
            MotionEvent.ACTION_MOVE -> {
                mMove += mLastX - xPosition
                changeMoveAndValue()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                countMoveEnd()
                countVelocityTracker(event)
                return false
            }
        // break;
            else -> {
            }
        }

        mLastX = xPosition
        return true
    }

    private fun countVelocityTracker(event: MotionEvent) {
        mVelocityTracker!!.computeCurrentVelocity(1000)
        val xVelocity = mVelocityTracker!!.xVelocity
        if (Math.abs(xVelocity) > mMinVelocity) {
            mScroller!!.fling(0, 0, xVelocity.toInt(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0)
        }

    }

    private fun changeMoveAndValue() {
        val tValue = (mMove / (mLineDivider * mDensity)).toInt()
        if (Math.abs(tValue) > 0) {
            value += tValue.toFloat()
            mMove -= (tValue.toFloat() * mLineDivider.toFloat() * mDensity).toInt()
            if (value <= mDefaultMinValue || value > mMaxValue) {
                value = if (value <= mDefaultMinValue) mDefaultMinValue else mMaxValue
                mMove = 0
                mScroller!!.forceFinished(true)
            }

            notifyValueChange()
        }
        postInvalidate()
    }

    private fun countMoveEnd() {
        val roundMove = Math.round(mMove / (mLineDivider * mDensity))
        value = value + roundMove
        value = if (value <= 0) 0 else value
        value = if (value > mMaxValue) mMaxValue else value

        mLastX = 0
        mMove = 0

        notifyValueChange()
        postInvalidate()
    }

    private fun notifyValueChange() {
        if (null != mListener) {
            if (mModType == MOD_TYPE) {
                mListener!!.onValueChange(value)
            }
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller!!.computeScrollOffset()) {
            if (mScroller!!.currX == mScroller!!.finalX) { // over
                countMoveEnd()
            } else {
                val xPosition = mScroller!!.currX
                mMove += mLastX - xPosition
                changeMoveAndValue()
                mLastX = xPosition
            }
        }
    }

    companion object {
        val MOD_TYPE = 5  //刻度盘精度

        private val ITEM_HEIGHT_DIVIDER = 12

        private val ITEM_MAX_HEIGHT = 36  //最大刻度高度
        private val ITEM_MIN_HEIGHT = 25  //最小刻度高度
        private val TEXT_SIZE = 18
    }
}
