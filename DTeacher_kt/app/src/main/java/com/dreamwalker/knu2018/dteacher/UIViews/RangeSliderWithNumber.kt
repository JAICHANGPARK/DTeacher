package com.dreamwalker.knu2018.dteacher.UIViews

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.UIUtils.ResUtils

import java.util.HashSet

/**
 * Slider following Material Design with two movable targets
 * that allow user to select a range of integers.
 */
class RangeSliderWithNumber : View {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lineStartX: Int = 0
    private var lineEndX: Int = 0
    private var lineLength: Int = 0
    private var minPosition = 0
    private val minLimit = -1//最小值选择限定
    private val maxLimit = -1//最大值选择限定
    private var maxPosition = 0
    private var middleY = 0
    //List of event IDs touching targets
    private val isTouchingMinTarget = HashSet<Int>()
    private val isTouchingMaxTarget = HashSet<Int>()
    private var min = 0
    private var max = DEFAULT_MAX
    private var range: Int = 0
    private var convertFactor: Float = 0.toFloat()
    var rangeSliderListener: RangeSliderListener? = null
    private var targetColor: Int = 0
    private var insideRangeColor: Int = 0
    private var outsideRangeColor: Int = 0
    private var numberTextColor: Int = 0
    private var numberTextSize: Float = 0.toFloat()
    private var numberMarginBottom: Float = 0.toFloat()
    private var colorControlNormal: Int = 0
    private var colorControlHighlight: Int = 0
    private var insideRangeLineStrokeWidth: Float = 0.toFloat()
    private var outsideRangeLineStrokeWidth: Float = 0.toFloat()
    internal var lastTouchedMin: Boolean = false
    private var isTouchingMin: Boolean = false

    private var startingMin = -1
    private var startingMax = -1
    internal var isFirstInit = true
    private var isTouching = false
    private var isShowBubble: Boolean = false
    private var isLineRound: Boolean = false
    private var bubbleBitmap: Bitmap? = null
    private val minTextRect = Rect()
    private val maxTextRect = Rect()
    private val rulerTextRect = Rect()
    private val circleBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_choose_ring)
    private var circleBitmapFocus = BitmapFactory.decodeResource(resources, R.mipmap.ic_choose_ring_focus)

    private var isShowRuler: Boolean = false
    private var rulerTextColor = ResUtils.getInstance(context).getColor(R.color.color_gray_66)
    private var rulerColor: Int = 0
    private var rulerTextSize: Float = 0.toFloat()
    private var rulerInterval: Int = 0
    private var rulerMarginTop: Float = 0.toFloat()
    private var rulerAndTextMargin: Float = 0.toFloat()
    private val rulerNormalHeight = dp2Px(4f)

    private val minTextLength: Int
        get() {
            getTextBounds(min.toString(), minTextRect)
            return minTextRect.width()
        }

    private val maxTextLength: Int
        get() {
            getTextBounds(max.toString(), maxTextRect)
            return maxTextRect.width()
        }

    var selectedMin: Int
        get() = Math.round((minPosition - lineStartX) * convertFactor + min)
        private set(selectedMin) {
            minPosition = Math.round((selectedMin - min) / convertFactor + lineStartX)
            callMinChangedCallbacks()
        }

    var selectedMax: Int
        get() = Math.round((maxPosition - lineStartX) * convertFactor + min)
        private set(selectedMax) {
            maxPosition = Math.round((selectedMax - min) / convertFactor + lineStartX)
            callMaxChangedCallbacks()
        }

    interface RangeSliderListener {
        fun onMaxChanged(newValue: Int)

        fun onMinChanged(newValue: Int)
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        getDefaultColors()
        getDefaultMeasurements()

        if (attrs != null) {
            //get attributes passed in XML
            val styledAttrs = context.obtainStyledAttributes(attrs,
                    R.styleable.RangeSliderWithNumber, 0, 0)
            targetColor = styledAttrs.getColor(R.styleable.RangeSliderWithNumber_rsn_insideRangeLineColor,
                    colorControlNormal)
            insideRangeColor = styledAttrs.getColor(R.styleable.RangeSliderWithNumber_rsn_insideRangeLineColor,
                    colorControlNormal)
            outsideRangeColor = styledAttrs.getColor(R.styleable.RangeSliderWithNumber_rsn_outsideRangeLineColor,
                    colorControlHighlight)
            numberTextColor = styledAttrs.getColor(R.styleable.RangeSliderWithNumber_rsn_numberTextColor,
                    colorControlHighlight)
            numberTextSize = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_numberTextSize, sp2Px(12).toFloat())
            numberMarginBottom = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_numberMarginBottom, dp2Px(5f))
            min = styledAttrs.getInt(R.styleable.RangeSliderWithNumber_rsn_min, min)
            max = styledAttrs.getInt(R.styleable.RangeSliderWithNumber_rsn_max, max)

            insideRangeLineStrokeWidth = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_insideRangeLineStrokeWidth, DEFAULT_INSIDE_RANGE_STROKE_WIDTH.toFloat())
            outsideRangeLineStrokeWidth = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_outsideRangeLineStrokeWidth, DEFAULT_OUTSIDE_RANGE_STROKE_WIDTH.toFloat())

            isShowBubble = styledAttrs.getBoolean(R.styleable.RangeSliderWithNumber_rsn_isShowBubble, false)
            bubbleBitmap = BitmapFactory.decodeResource(resources, styledAttrs.getResourceId(R.styleable.RangeSliderWithNumber_rsn_bubbleResource, R.mipmap.bg_choose_green))

            circleBitmapFocus = BitmapFactory.decodeResource(resources, styledAttrs.getResourceId(R.styleable.RangeSliderWithNumber_rsn_circleFocusBitmap, R.mipmap.ic_choose_ring_focus))

            isLineRound = styledAttrs.getBoolean(R.styleable.RangeSliderWithNumber_rsn_isLineRound, true)

            isShowRuler = styledAttrs.getBoolean(R.styleable.RangeSliderWithNumber_rsn_isShowRuler, false)
            rulerTextColor = styledAttrs.getColor(R.styleable.RangeSliderWithNumber_rsn_rulerTextColor, rulerTextColor)
            rulerColor = styledAttrs.getColor(R.styleable.RangeSliderWithNumber_rsn_rulerColor, colorControlNormal)
            rulerTextSize = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_rulerTextSize, sp2Px(12).toFloat())
            rulerInterval = styledAttrs.getInt(R.styleable.RangeSliderWithNumber_rsn_rulerInterval, 20)
            rulerMarginTop = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_rulerMarginTop, dp2Px(4f))
            rulerAndTextMargin = styledAttrs.getDimension(R.styleable.RangeSliderWithNumber_rsn_rulerAndTextMargin, dp2Px(4f))

            styledAttrs.recycle()
        }

        range = max - min

    }

    /**
     * Get default colors from theme.  Compatible with 5.0+ themes and AppCompat themes.
     * Will attempt to get 5.0 colors, if not avail fallback to AppCompat, and if not avail use
     * black and gray.
     * These will be used if colors are not set in xml.
     */
    private fun getDefaultColors() {
        val typedValue = TypedValue()

        val materialStyledAttrs = context.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.colorControlNormal, android.R.attr.colorControlHighlight))

        val appcompatMaterialStyledAttrs = context.obtainStyledAttributes(typedValue.data, intArrayOf(android.support.v7.appcompat.R.attr.colorControlNormal, android.support.v7.appcompat.R.attr.colorControlHighlight))
        colorControlNormal = ResUtils.getInstance(context).getColor(R.color.colorPrimary)
        colorControlHighlight = ResUtils.getInstance(context).getColor(R.color.colorPrimaryDark)

        targetColor = colorControlNormal
        insideRangeColor = colorControlHighlight

        materialStyledAttrs.recycle()
        appcompatMaterialStyledAttrs.recycle()
    }

    /**
     * Get default measurements to use for radius and stroke width.
     * These are used if measurements are not set in xml.
     */
    private fun getDefaultMeasurements() {
        insideRangeLineStrokeWidth = Math.round(dp2Px(DEFAULT_INSIDE_RANGE_STROKE_WIDTH.toFloat())).toFloat()
        outsideRangeLineStrokeWidth = Math.round(dp2Px(DEFAULT_OUTSIDE_RANGE_STROKE_WIDTH.toFloat())).toFloat()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var desiredHeight: Int

        getTextBounds(min.toString(), minTextRect)
        getTextBounds(max.toString(), maxTextRect)

        if (isShowBubble) {
            desiredHeight = (circleBitmap.height + numberMarginBottom).toInt() + bubbleBitmap!!.height
        } else {

            desiredHeight = (circleBitmap.height.toFloat() + numberMarginBottom + minTextRect.height().toFloat()).toInt()
        }

        val rulerHeight = (rulerMarginTop + rulerNormalHeight * 3 + rulerAndTextMargin + rulerTextRect.height().toFloat()).toInt()
        if (isShowRuler) {
            getRulerTextBounds(min.toString(), rulerTextRect)
            desiredHeight += rulerHeight
        }

        var width = widthSize
        var height = desiredHeight

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            width = Math.min(widthSize, widthSize)
        } else {
            width = widthSize
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            height = desiredHeight
        }

        val marginStartEnd = if (isShowBubble) bubbleBitmap!!.width else Math.max(circleBitmap.width, maxTextRect.width())

        lineLength = width - marginStartEnd
        middleY = if (isShowRuler) height - rulerHeight - circleBitmap.height / 2 else height - circleBitmap.height / 2
        lineStartX = marginStartEnd / 2
        lineEndX = lineLength + marginStartEnd / 2

        calculateConvertFactor()

        if (isFirstInit) {
            selectedMin = if (startingMin != -1) startingMin else min
            selectedMax = if (startingMax != -1) startingMax else max
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        drawEntireRangeLine(canvas)
        drawSelectedRangeLine(canvas)
        drawSelectedNumber(canvas)
        drawRuler(canvas)
        drawSelectedTargets(canvas)
    }

    private fun drawEntireRangeLine(canvas: Canvas) {
        paint.color = outsideRangeColor
        paint.strokeWidth = outsideRangeLineStrokeWidth
        canvas.drawLine(lineStartX.toFloat(), middleY.toFloat(), lineEndX.toFloat(), middleY.toFloat(), paint)

        if (isLineRound) {
            canvas.drawCircle(lineStartX.toFloat(), middleY.toFloat(), outsideRangeLineStrokeWidth / 2, paint)
            canvas.drawCircle(lineEndX.toFloat(), middleY.toFloat(), outsideRangeLineStrokeWidth / 2, paint)
        }
    }

    private fun drawSelectedRangeLine(canvas: Canvas) {
        paint.strokeWidth = insideRangeLineStrokeWidth
        paint.color = insideRangeColor
        canvas.drawLine(minPosition.toFloat(), middleY.toFloat(), maxPosition.toFloat(), middleY.toFloat(), paint)
    }

    private fun drawSelectedNumber(canvas: Canvas) {

        val min = selectedMin.toString()
        val max = selectedMax.toString()

        getTextBounds(min, minTextRect)
        getTextBounds(max, maxTextRect)


        var minXBubble = (minPosition - bubbleBitmap!!.width / 2).toFloat()
        var maxXBubble = (maxPosition - bubbleBitmap!!.width / 2).toFloat()
        val maxTextLengthBubble = bubbleBitmap!!.width + 5
        if (isTouching && isTouchingMaxTarget.size > 0) {
            if (Math.abs(maxXBubble - minXBubble) <= maxTextLengthBubble) {
                maxXBubble = minXBubble + maxTextLengthBubble

                if (maxXBubble > lineEndX - maxTextLengthBubble / 2) {
                    maxXBubble = (lineEndX - maxTextLengthBubble / 2).toFloat()
                }
            }
        }

        if (isTouching && isTouchingMinTarget.size > 0) {
            if (Math.abs(maxXBubble - minXBubble) <= maxTextLengthBubble) {
                minXBubble = maxXBubble - maxTextLengthBubble

                if (minXBubble < lineStartX) {
                    minXBubble = lineStartX.toFloat()
                }
            }
        }

        if (Math.abs(maxXBubble - minXBubble) <= maxTextLengthBubble) {

            if (!isTouchingMin) {
                maxXBubble = minXBubble + maxTextLengthBubble
                if (maxXBubble > lineEndX - maxTextLengthBubble / 2) {
                    maxXBubble = (lineEndX - maxTextLengthBubble / 2).toFloat()
                    minXBubble = maxXBubble - maxTextLengthBubble
                }
            } else {
                minXBubble = maxXBubble - maxTextLengthBubble
                if (minXBubble < lineStartX + maxTextLengthBubble / 2) {
                    minXBubble = (lineStartX + maxTextLengthBubble / 2).toFloat()
                    maxXBubble = minXBubble + maxTextLengthBubble
                }
            }
        }


        val yText: Float
        //bubble
        if (isShowBubble) {

            val top = middleY.toFloat() - (circleBitmap.height / 2).toFloat() - bubbleBitmap!!.height.toFloat() - numberMarginBottom
            yText = top + (bubbleBitmap!!.height / 2).toFloat() + (minTextRect.height() / 2).toFloat() - 6

            canvas.drawBitmap(bubbleBitmap!!, maxXBubble, top, paint)
            canvas.drawBitmap(bubbleBitmap!!, minXBubble, top, paint)

        } else {
            yText = middleY.toFloat() - (circleBitmap.height / 2).toFloat() - numberMarginBottom
        }

        //text

        var minX = (minPosition - minTextRect.width() / 2).toFloat()
        var maxX = (maxPosition - maxTextRect.width() / 2 - 6).toFloat()
        val maxTextLength = if (isShowBubble) bubbleBitmap!!.width else maxTextLength + 5

        if (isTouching && isTouchingMaxTarget.size > 0) {
            if (Math.abs(maxX - minX) <= maxTextLength) {
                maxX = minX + maxTextLength

                if (maxX > lineEndX - maxTextLength / 2) {
                    maxX = (lineEndX - maxTextLength / 2).toFloat()
                }
            }
        }

        if (isTouching && isTouchingMinTarget.size > 0) {
            if (Math.abs(maxX - minX) <= maxTextLength) {
                minX = maxX - maxTextLength

                if (minX < lineStartX) {
                    minX = lineStartX.toFloat()
                }
            }
        }

        if (Math.abs(maxX - minX) <= maxTextLength) {
            if (!isTouchingMin) {
                maxX = minX + maxTextLength
                if (maxX > lineEndX - maxTextLength / 2) {
                    maxX = (lineEndX - maxTextLength / 2).toFloat()
                    minX = maxX - maxTextLength
                }
            } else {
                minX = maxX - maxTextLength
                if (minX < lineStartX + maxTextLength / 2) {
                    minX = (lineStartX + maxTextLength / 2).toFloat()
                    maxX = minX + maxTextLength
                }
            }
        }


        paint.textSize = numberTextSize

        paint.color = numberTextColor
        canvas.drawText(min, minX, yText, paint)

        paint.color = numberTextColor
        canvas.drawText(max, maxX, yText, paint)


    }

    private fun drawRuler(canvas: Canvas) {
        if (isShowRuler) {
            var startX = lineStartX.toFloat()
            var stopX = 0f
            var stopY = 0f
            var startY = 0f
            val totalLength = lineLength.toFloat()
            val divider = rulerInterval / 10
            val scaleLength = lineLength.toFloat() / ((max - min) / (rulerInterval / 10)).toFloat() / divider.toFloat()

            var isMinHasText = false
            var isMaxHasText = false

            for (i in min..max) {
                if (i % rulerInterval == 0) {
                    //draw big scale
                    stopX = startX
                    startY = middleY.toFloat() + (circleBitmap.height / 2).toFloat() + rulerMarginTop
                    stopY = startY + rulerNormalHeight * 3


                    paint.color = rulerTextColor
                    paint.textSize = rulerTextSize
                    getRulerTextBounds(i.toString(), rulerTextRect)
                    canvas.drawText(i.toString(), startX - rulerTextRect.width() / 2, stopY + rulerTextRect.height().toFloat() + rulerAndTextMargin, paint)

                    if (i == min) {
                        isMinHasText = true
                    }
                    if (i == max) {
                        isMaxHasText = true
                    }
                    paint.strokeWidth = 1.5f


                    paint.color = rulerColor
                    canvas.drawLine(startX, startY, startX, stopY, paint)

                } else if (i % (rulerInterval / 2) == 0) {
                    //draw middle scale
                    startY = middleY.toFloat() + (circleBitmap.height / 2).toFloat() + rulerMarginTop
                    stopY = startY + rulerNormalHeight * 2
                    paint.strokeWidth = 1.0f

                    paint.color = rulerColor
                    canvas.drawLine(startX, startY, startX, stopY, paint)


                } else {
                    //draw small scale
                    startY = middleY.toFloat() + (circleBitmap.height / 2).toFloat() + rulerMarginTop
                    stopY = startY + rulerNormalHeight
                    paint.strokeWidth = 0.8f

                    if (i % (rulerInterval / 10) == 0) {
                        paint.color = rulerColor
                        canvas.drawLine(startX, startY, startX, stopY, paint)
                    }

                }

                if (i == max && !isMaxHasText || i == min && !isMinHasText) {
                    paint.color = rulerTextColor
                    paint.textSize = rulerTextSize
                    getRulerTextBounds(i.toString(), rulerTextRect)
                    canvas.drawText(i.toString(), startX - rulerTextRect.width() / 2, startY + rulerNormalHeight * 3 + rulerTextRect.height().toFloat() + rulerAndTextMargin, paint)

                }

                startX += scaleLength


            }
        }

    }

    private fun drawSelectedTargets(canvas: Canvas) {
        paint.color = targetColor
        canvas.drawCircle(minPosition.toFloat(), middleY.toFloat(), 20f, paint)
        canvas.drawCircle(maxPosition.toFloat(), middleY.toFloat(), 20f, paint)

        if (isTouching) {
            if (lastTouchedMin) {
                canvas.drawBitmap(circleBitmapFocus, (minPosition - circleBitmapFocus.width / 2).toFloat(), (middleY - circleBitmapFocus.width / 2).toFloat(), paint)
                canvas.drawBitmap(circleBitmap, (maxPosition - circleBitmap.width / 2).toFloat(), (middleY - circleBitmap.width / 2).toFloat(), paint)
            } else {
                canvas.drawBitmap(circleBitmap, (minPosition - circleBitmap.width / 2).toFloat(), (middleY - circleBitmap.width / 2).toFloat(), paint)
                canvas.drawBitmap(circleBitmapFocus, (maxPosition - circleBitmapFocus.width / 2).toFloat(), (middleY - circleBitmapFocus.width / 2).toFloat(), paint)
            }
        } else {
            canvas.drawBitmap(circleBitmap, (maxPosition - circleBitmap.width / 2).toFloat(), (middleY - circleBitmap.width / 2).toFloat(), paint)
            canvas.drawBitmap(circleBitmap, (minPosition - circleBitmap.width / 2).toFloat(), (middleY - circleBitmap.width / 2).toFloat(), paint)
        }
    }

    private fun getTextBounds(text: String, rect: Rect) {
        paint.textSize = numberTextSize
        paint.getTextBounds(text, 0, text.length, rect)
    }

    private fun getRulerTextBounds(text: String, rect: Rect) {
        paint.textSize = rulerTextSize
        paint.getTextBounds(text, 0, text.length, rect)
    }

    //user has touched outside the target, lets jump to that position
    private fun jumpToPosition(index: Int, event: MotionEvent) {
        if (event.getX(index) > maxPosition && event.getX(index) <= lineEndX) {
            maxPosition = event.getX(index).toInt()
            invalidate()
            callMaxChangedCallbacks()
        } else if (event.getX(index) < minPosition && event.getX(index) >= lineStartX) {
            minPosition = event.getX(index).toInt()
            invalidate()
            callMinChangedCallbacks()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled)
            return false

        isFirstInit = false

        val actionIndex = event.actionIndex
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

                isTouching = true

                if (lastTouchedMin) {
                    if (!checkTouchingMinTarget(actionIndex, event) && !checkTouchingMaxTarget(actionIndex, event)) {
                        jumpToPosition(actionIndex, event)
                    }
                } else if (!checkTouchingMaxTarget(actionIndex, event) && !checkTouchingMinTarget(actionIndex, event)) {
                    jumpToPosition(actionIndex, event)
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {

                isTouching = false

                isTouchingMinTarget.remove(event.getPointerId(actionIndex))
                isTouchingMaxTarget.remove(event.getPointerId(actionIndex))

                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {

                isTouching = true

                for (i in 0 until event.pointerCount) {
                    if (isTouchingMinTarget.contains(event.getPointerId(i))) {
                        var touchX = event.getX(i).toInt()
                        touchX = clamp(touchX, lineStartX, lineEndX)
                        if (touchX >= maxPosition) {
                            maxPosition = touchX
                            callMaxChangedCallbacks()
                        }
                        minPosition = touchX
                        callMinChangedCallbacks()
                        isTouchingMin = true
                    }
                    if (isTouchingMaxTarget.contains(event.getPointerId(i))) {
                        var touchX = event.getX(i).toInt()
                        touchX = clamp(touchX, lineStartX, lineEndX)
                        if (touchX <= minPosition) {
                            minPosition = touchX
                            callMinChangedCallbacks()
                        }
                        maxPosition = touchX
                        callMaxChangedCallbacks()
                        isTouchingMin = false
                    }
                }
                invalidate()
            }

            MotionEvent.ACTION_POINTER_DOWN -> {

                isTouching = true

                for (i in 0 until event.pointerCount) {
                    if (lastTouchedMin) {
                        if (!checkTouchingMinTarget(i, event) && !checkTouchingMaxTarget(i, event)) {
                            jumpToPosition(i, event)
                        }
                    } else if (!checkTouchingMaxTarget(i, event) && !checkTouchingMinTarget(i, event)) {
                        jumpToPosition(i, event)
                    }
                }
            }

            MotionEvent.ACTION_CANCEL -> {

                isTouching = false

                isTouchingMinTarget.clear()
                isTouchingMaxTarget.clear()

                invalidate()
            }

            else -> {
            }
        }

        return true
    }

    /**
     * Checks if given index is touching the min target.  If touching start animation.
     */
    private fun checkTouchingMinTarget(index: Int, event: MotionEvent): Boolean {
        if (isTouchingMinTarget(index, event)) {
            lastTouchedMin = true
            isTouchingMinTarget.add(event.getPointerId(index))
            return true
        }
        return false
    }

    /**
     * Checks if given index is touching the max target.  If touching starts animation.
     */
    private fun checkTouchingMaxTarget(index: Int, event: MotionEvent): Boolean {
        if (isTouchingMaxTarget(index, event)) {
            lastTouchedMin = false
            isTouchingMaxTarget.add(event.getPointerId(index))
            return true
        }
        return false
    }

    private fun callMinChangedCallbacks() {
        if (rangeSliderListener != null) {
            rangeSliderListener!!.onMinChanged(selectedMin)
        }
    }

    private fun callMaxChangedCallbacks() {
        if (rangeSliderListener != null) {
            rangeSliderListener!!.onMaxChanged(selectedMax)
        }
    }

    private fun isTouchingMinTarget(pointerIndex: Int, event: MotionEvent): Boolean {
        return (event.getX(pointerIndex) > minPosition - DEFAULT_TOUCH_TARGET_SIZE
                && event.getX(pointerIndex) < minPosition + DEFAULT_TOUCH_TARGET_SIZE
                && event.getY(pointerIndex) > middleY - DEFAULT_TOUCH_TARGET_SIZE
                && event.getY(pointerIndex) < middleY + DEFAULT_TOUCH_TARGET_SIZE)
    }

    private fun isTouchingMaxTarget(pointerIndex: Int, event: MotionEvent): Boolean {
        return (event.getX(pointerIndex) > maxPosition - DEFAULT_TOUCH_TARGET_SIZE
                && event.getX(pointerIndex) < maxPosition + DEFAULT_TOUCH_TARGET_SIZE
                && event.getY(pointerIndex) > middleY - DEFAULT_TOUCH_TARGET_SIZE
                && event.getY(pointerIndex) < middleY + DEFAULT_TOUCH_TARGET_SIZE)
    }

    private fun calculateConvertFactor() {
        convertFactor = range.toFloat() / lineLength
    }

    fun setStartingMinMax(startingMin: Int, startingMax: Int) {
        this.startingMin = startingMin
        this.startingMax = startingMax
        selectedMin = startingMin
        selectedMax = startingMax
        invalidate()
    }

    fun getMin(): Int {
        return min
    }

    fun setMin(min: Int) {
        this.min = min
        range = max - min
    }

    fun getMax(): Int {
        return max
    }

    fun setMax(max: Int) {
        this.max = max
        range = max - min
    }

    /**
     * Resets selected values to MIN and MAX.
     */
    fun reset() {
        minPosition = lineStartX
        maxPosition = lineEndX
        if (rangeSliderListener != null) {
            rangeSliderListener!!.onMinChanged(selectedMin)
            rangeSliderListener!!.onMaxChanged(selectedMax)
        }
        invalidate()
    }


    /**
     * Keeps Number value inside min/max bounds by returning min or max if outside of
     * bounds.  Otherwise will return the value without altering.
     */
    private fun <T : Number> clamp(value: T, min: T, max: T): T {
        if (value.toDouble() > max.toDouble()) {
            return max
        } else if (value.toDouble() < min.toDouble()) {
            return min
        }
        return value
    }

    private fun getColor(res: Int): Int {
        return ContextCompat.getColor(context, res)
    }

    fun sp2Px(spValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue.toFloat(), context.resources.displayMetrics).toInt()

    }

    companion object {

        //Padding that is always added to both sides of slider, in addition to layout_margin
        private val DEFAULT_TOUCH_TARGET_SIZE = Math.round(dp2Px(40f))
        private val DEFAULT_UNPRESSED_RADIUS = 15
        private val DEFAULT_PRESSED_RADIUS = 40
        private val DEFAULT_INSIDE_RANGE_STROKE_WIDTH = dp2Px(5f).toInt()
        private val DEFAULT_OUTSIDE_RANGE_STROKE_WIDTH = dp2Px(5f).toInt()
        private val DEFAULT_MAX = 100

        private fun dp2Px(dp: Float): Float {
            val metrics = Resources.getSystem().displayMetrics
            return dp * (metrics.densityDpi / 160f)
        }
    }
}