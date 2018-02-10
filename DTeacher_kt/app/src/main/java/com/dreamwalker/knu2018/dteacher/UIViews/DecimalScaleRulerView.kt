package com.dreamwalker.knu2018.dteacher.UIViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller

import com.dreamwalker.knu2018.dteacher.UIUtils.DrawUtil
import com.dreamwalker.knu2018.dteacher.UIUtils.TextUtil


/**
 * Created by zoubo on 16/2/26.
 * 小数点横向滚动刻度尺
 */

class DecimalScaleRulerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mMinVelocity: Int = 0
    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mValue = 50f
    private var mMaxValue = 100f
    private var mMinValue = 0f
    private var mItemSpacing: Int = 0
    private var mPerSpanValue = 1
    private var mMaxLineHeight: Int = 0
    private var mMiddleLineHeight: Int = 0
    private var mMinLineHeight: Int = 0
    private var mLineWidth: Int = 0
    private var mTextMarginTop: Int = 0
    private var mTextHeight: Float = 0.toFloat()

    private var mTextPaint: Paint? = null // 绘制文本的画笔
    private var mLinePaint: Paint? = null

    private var mTotalLine: Int = 0
    private var mMaxOffset: Int = 0
    private var mOffset: Float = 0.toFloat() // 默认尺起始点在屏幕中心, offset是指尺起始点的偏移值
    private var mLastX: Int = 0
    private var mMove: Int = 0
    private var mListener: OnValueChangeListener? = null

    init {
        init(context)
    }

    protected fun init(context: Context) {
        mScroller = Scroller(context)
        mMinVelocity = ViewConfiguration.get(getContext()).scaledMinimumFlingVelocity
        mItemSpacing = DrawUtil.dip2px(14f)
        mLineWidth = DrawUtil.dip2px(1f)
        mMaxLineHeight = DrawUtil.dip2px(42f)
        mMiddleLineHeight = DrawUtil.dip2px(31f)
        mMinLineHeight = DrawUtil.dip2px(17f)
        mTextMarginTop = DrawUtil.dip2px(11f)

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint!!.textSize = DrawUtil.sp2px(16f).toFloat()
        mTextPaint!!.color = -0x7fddddde
        mTextHeight = TextUtil.getFontHeight(mTextPaint)

        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint!!.strokeWidth = mLineWidth.toFloat()
        mLinePaint!!.color = -0x7fddddde
    }

    fun setParam(itemSpacing: Int, maxLineHeight: Int, middleLineHeight: Int, minLineHeight: Int, textMarginTop: Int, textSize: Int) {
        mItemSpacing = itemSpacing
        mMaxLineHeight = maxLineHeight
        mMiddleLineHeight = middleLineHeight
        mMinLineHeight = minLineHeight
        mTextMarginTop = textMarginTop
        mTextPaint!!.textSize = textSize.toFloat()
    }

    fun initViewParam(defaultValue: Float, minValue: Float, maxValue: Float, spanValue: Int) {
        this.mValue = defaultValue
        this.mMaxValue = maxValue
        this.mMinValue = minValue
        this.mPerSpanValue = spanValue
        this.mTotalLine = (maxValue * 10 - minValue * 10).toInt() / spanValue + 1
        mMaxOffset = -(mTotalLine - 1) * mItemSpacing

        mOffset = (minValue - defaultValue) / spanValue * mItemSpacing.toFloat() * 10f
        invalidate()
        visibility = View.VISIBLE
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    fun setValueChangeListener(listener: OnValueChangeListener) {
        mListener = listener
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            mWidth = w
            mHeight = h
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var left: Float
        var height: Float
        var value: String
        var alpha: Int
        var scale: Float
        val srcPointX = mWidth / 2 // 默认表尺起始点在屏幕中心
        for (i in 0 until mTotalLine) {
            left = srcPointX.toFloat() + mOffset + (i * mItemSpacing).toFloat()

            if (left < 0 || left > mWidth) {
                continue
            }

            if (i % 10 == 0) {
                height = mMaxLineHeight.toFloat()
            } else if (i % 5 == 0) {
                height = mMiddleLineHeight.toFloat()
            } else {
                height = mMinLineHeight.toFloat()
            }
            scale = 1 - Math.abs(left - srcPointX) / srcPointX
            alpha = (255f * scale * scale).toInt()
            mLinePaint!!.alpha = alpha
            canvas.drawLine(left, 0f, left, height, mLinePaint!!)

            if (i % 10 == 0) { // 大指标,要标注文字
                value = (mMinValue + i * mPerSpanValue / 10).toInt().toString()
                mTextPaint!!.alpha = alpha
                canvas.drawText(value, left - mTextPaint!!.measureText(value) / 2,
                        height + mTextMarginTop.toFloat() + mTextHeight - DrawUtil.dip2px(3f), mTextPaint!!)
            }

        }
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
                mMove = mLastX - xPosition
                changeMoveAndValue()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                countMoveEnd()
                countVelocityTracker()
                return false
            }
        // break;
            else -> {
            }
        }

        mLastX = xPosition
        return true
    }

    private fun countVelocityTracker() {
        mVelocityTracker!!.computeCurrentVelocity(1000)
        val xVelocity = mVelocityTracker!!.xVelocity
        if (Math.abs(xVelocity) > mMinVelocity) {
            mScroller!!.fling(0, 0, xVelocity.toInt(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0)
        }
    }

    private fun countMoveEnd() {
        mOffset -= mMove.toFloat()
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset.toFloat()
        } else if (mOffset >= 0) {
            mOffset = 0f
        }

        mLastX = 0
        mMove = 0

        mValue = mMinValue + Math.round(Math.abs(mOffset) * 1.0f / mItemSpacing) * mPerSpanValue / 10.0f
        mOffset = (mMinValue - mValue) * 10.0f / mPerSpanValue * mItemSpacing // 矫正位置,保证不会停留在两个相邻刻度之间
        notifyValueChange()
        postInvalidate()
    }

    private fun changeMoveAndValue() {
        mOffset -= mMove.toFloat()
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset.toFloat()
            mMove = 0
            mScroller!!.forceFinished(true)
        } else if (mOffset >= 0) {
            mOffset = 0f
            mMove = 0
            mScroller!!.forceFinished(true)
        }
        mValue = mMinValue + Math.round(Math.abs(mOffset) * 1.0f / mItemSpacing) * mPerSpanValue / 10.0f
        notifyValueChange()
        postInvalidate()
    }

    private fun notifyValueChange() {
        if (null != mListener) {
            mListener!!.onValueChange(mValue)
        }
    }

    interface OnValueChangeListener {
        fun onValueChange(value: Float)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller!!.computeScrollOffset()) {
            if (mScroller!!.currX == mScroller!!.finalX) { // over
                countMoveEnd()
            } else {
                val xPosition = mScroller!!.currX
                mMove = mLastX - xPosition
                changeMoveAndValue()
                mLastX = xPosition
            }
        }
    }
}
