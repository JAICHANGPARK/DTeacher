package com.dreamwalker.knu2018.dteacher.UIViews


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.dreamwalker.knu2018.dteacher.R
import java.util.Random

/**
 * Created by katsuyagoto on 2015/04/17.
 */
class KenBurnsView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(mContext, attrs, defStyle) {

    private var mLoadType = LoadType.String

    private val mHandler: Handler?

    private var mImageViews: Array<ImageView>? = null

    private var mRootLayout: FrameLayout? = null

    private val mRandom = Random()

    private var mSwapMs = 5500

    private var mFadeInOutMs = 500

    private var mMaxScaleFactor = 1.5f

    private var mMinScaleFactor = 1.0f

    private var mPosition = 0

    private var mPreviousPosition = 0

    private var mActiveImageIndex = -1

    private var mStrings: List<String>? = null

    private var mResourceIDs: List<Int>? = null

    private var mMixingList: List<Any>? = null

    private var mLoopViewPager: LoopViewPager? = null

    private var mScaleType: ImageView.ScaleType? = null

    private val mSwapImageRunnable = object : Runnable {
        override fun run() {
            autoSwapImage()
            mHandler!!.postDelayed(this, (mSwapMs - mFadeInOutMs * 2).toLong())
        }
    }

    private val mForceSwapImageRunnable = Runnable {
        forceSwapImage()
        mHandler!!.postDelayed(mSwapImageRunnable, (mSwapMs - mFadeInOutMs * 2).toLong())
    }

    private val sizeByLoadType: Int
        get() {
            if (sCachedSizeForLoadType > 0) {
                return sCachedSizeForLoadType
            }
            when (mLoadType) {
                KenBurnsView.LoadType.String -> sCachedSizeForLoadType = mStrings!!.size
                KenBurnsView.LoadType.ResourceID -> sCachedSizeForLoadType = mResourceIDs!!.size
                KenBurnsView.LoadType.MIXING -> sCachedSizeForLoadType = mMixingList!!.size
            }
            return sCachedSizeForLoadType
        }

    private enum class LoadType {
        String, // A file path, or a uri or url (default)
        ResourceID, // The id of the resource containing the image
        MIXING       // String & Resource
    }

    init {
        mHandler = Handler()
    }

    fun setScaleType(mScaleType: ImageView.ScaleType) {
        this.mScaleType = mScaleType
    }

    fun setPager(mPager: LoopViewPager) {
        this.mLoopViewPager = mPager
    }

    fun forceSelected(position: Int) {
        mPreviousPosition = mPosition
        if (mHandler != null) {
            stopKenBurnsAnimation()
            startForceKenBurnsAnimation()
        }
        mPosition = position
    }


    private fun forceSwapImage() {

        if (mImageViews!!.size <= 0) {
            return
        }

        if (mActiveImageIndex == -1) {
            mActiveImageIndex = FIRST_IMAGE_VIEW_INDEX
            animate(mImageViews!![mActiveImageIndex])
            return
        }

        val inactiveIndex = mActiveImageIndex

        if (mPreviousPosition >= mPosition) {
            mActiveImageIndex = swapDirection(mActiveImageIndex, true)
        } else {
            mActiveImageIndex = swapDirection(mActiveImageIndex, false)
        }

        if (mPreviousPosition == 0 && mPosition == sizeByLoadType - 1) {
            mActiveImageIndex = swapDirection(mActiveImageIndex, true)
        }

        if (mPreviousPosition == sizeByLoadType - 1 && mPosition == 0) {
            mActiveImageIndex = swapDirection(mActiveImageIndex, false)
        }

        if (mActiveImageIndex >= mImageViews!!.size) {
            mActiveImageIndex = FIRST_IMAGE_VIEW_INDEX
        }

        val activeImageView = mImageViews!![mActiveImageIndex]
        loadImages(mPosition, mActiveImageIndex)
        activeImageView.alpha = 0.0f

        val inactiveImageView = mImageViews!![inactiveIndex]

        animate(activeImageView)

        val animatorSet = AnimatorSet()
        animatorSet.duration = mFadeInOutMs.toLong()
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(inactiveImageView, PROPERTY_ALPHA, 1.0f, 0.0f),
                ObjectAnimator.ofFloat(activeImageView, PROPERTY_ALPHA, 0.0f, 1.0f)
        )
        animatorSet.start()
    }

    private fun swapDirection(activeIndex: Int, isPrevious: Boolean): Int {
        if (activeIndex == FIRST_IMAGE_VIEW_INDEX) {
            return if (isPrevious) {
                THIRD_IMAGE_VIEW_INDEX
            } else {
                SECOND_IMAGE_VIEW_INDEX
            }
        } else if (activeIndex == SECOND_IMAGE_VIEW_INDEX) {
            return if (isPrevious) {
                FIRST_IMAGE_VIEW_INDEX
            } else {
                THIRD_IMAGE_VIEW_INDEX
            }

        } else if (activeIndex == THIRD_IMAGE_VIEW_INDEX) {
            return if (isPrevious) {
                SECOND_IMAGE_VIEW_INDEX
            } else {
                FIRST_IMAGE_VIEW_INDEX
            }
        }
        return FIRST_IMAGE_VIEW_INDEX
    }


    private fun autoSwapImage() {

        if (mImageViews!!.size <= 0) {
            return
        }

        if (mActiveImageIndex == -1) {
            mActiveImageIndex = FIRST_IMAGE_VIEW_INDEX
            animate(mImageViews!![mActiveImageIndex])
            return
        }

        val inactiveIndex = mActiveImageIndex
        mActiveImageIndex = 1 + mActiveImageIndex

        if (mActiveImageIndex >= mImageViews!!.size) {
            mActiveImageIndex = FIRST_IMAGE_VIEW_INDEX
        }

        if (mLoopViewPager != null) {
            mPosition++

            if (mPosition >= sizeByLoadType) {
                mPosition = 0
            }

            mLoopViewPager!!.setCurrentItemAfterCancelListener(mPosition, false)
        }

        val activeImageView = mImageViews!![mActiveImageIndex]
        loadImages(mPosition, mActiveImageIndex)
        activeImageView.alpha = 0.0f

        val inactiveImageView = mImageViews!![inactiveIndex]

        animate(activeImageView)

        val animatorSet = AnimatorSet()
        animatorSet.duration = mFadeInOutMs.toLong()
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(inactiveImageView, PROPERTY_ALPHA, 1.0f, 0.0f),
                ObjectAnimator.ofFloat(activeImageView, PROPERTY_ALPHA, 0.0f, 1.0f)
        )
        animatorSet.start()
    }

    private fun start(view: View, duration: Long, fromScale: Float, toScale: Float, fromTranslationX: Float,
                      fromTranslationY: Float, toTranslationX: Float, toTranslationY: Float) {
        view.scaleX = fromScale
        view.scaleY = fromScale
        view.translationX = fromTranslationX
        view.translationY = fromTranslationY
        val propertyAnimator = view.animate().translationX(toTranslationX).translationY(toTranslationY).scaleX(toScale).scaleY(toScale).setDuration(duration)
        propertyAnimator.start()
    }

    private fun pickScale(): Float {
        return this.mMinScaleFactor + this.mRandom.nextFloat() * (this.mMaxScaleFactor - this.mMinScaleFactor)
    }

    private fun pickTranslation(value: Int, ratio: Float): Float {
        return value.toFloat() * (ratio - 1.0f) * (this.mRandom.nextFloat() - 0.5f)
    }

    fun animate(view: ImageView) {
        val fromScale = pickScale()
        val toScale = pickScale()
        val fromTranslationX = pickTranslation(view.width, fromScale)
        val fromTranslationY = pickTranslation(view.height, fromScale)
        val toTranslationX = pickTranslation(view.width, toScale)
        val toTranslationY = pickTranslation(view.height, toScale)
        start(view, this.mSwapMs.toLong(), fromScale, toScale, fromTranslationX, fromTranslationY, toTranslationX,
                toTranslationY)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startKenBurnsAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopKenBurnsAnimation()
    }

    private fun stopKenBurnsAnimation() {
        mHandler!!.removeCallbacks(mSwapImageRunnable)
        mHandler.removeCallbacks(mForceSwapImageRunnable)
    }

    private fun startKenBurnsAnimation() {
        mHandler!!.post(mSwapImageRunnable)
    }

    private fun startForceKenBurnsAnimation() {
        mHandler!!.post(mForceSwapImageRunnable)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val view = View.inflate(context, R.layout.ken_burns_view, this)
        mRootLayout = view.findViewById<View>(R.id.ken_burns_root) as FrameLayout
    }

    fun loadStrings(strings: List<String>) {
        mLoadType = LoadType.String
        sCachedSizeForLoadType = 0
        mStrings = strings
        if (mRootLayout != null) {
            initImageViews(mRootLayout)
        }
    }

    fun loadResourceIDs(resourceIDs: List<Int>) {
        mLoadType = LoadType.ResourceID
        sCachedSizeForLoadType = 0
        mResourceIDs = resourceIDs
        if (mRootLayout != null) {
            initImageViews(mRootLayout)
        }
    }

    fun loadMixing(mixingList: List<Any>) {
        mLoadType = LoadType.MIXING
        sCachedSizeForLoadType = 0
        mMixingList = mixingList
        if (mRootLayout != null) {
            initImageViews(mRootLayout)
        }
    }

    private fun initImageViews(root: FrameLayout) {

        mImageViews = arrayOfNulls(NUM_OF_IMAGE_VIEWS)

        for (i in NUM_OF_IMAGE_VIEWS - 1 downTo 0) {
            mImageViews[i] = ImageView(mContext)

            if (i != 0) {
                mImageViews!![i].alpha = 0.0f
            }

            if (mScaleType != null) {
                mImageViews!![i].scaleType = mScaleType
            }

            mImageViews!![i].layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)

            root.addView(mImageViews!![i])
        }

        loadImages(0, FIRST_IMAGE_VIEW_INDEX)
    }

    private fun loadImages(position: Int, activeIndex: Int) {
        loadImage(position, activeIndex)

        var prePosition = position - 1
        var nextPosition = position + 1

        if (prePosition < 0) {
            prePosition = sizeByLoadType - 1
        }

        if (nextPosition > sizeByLoadType - 1) {
            nextPosition = 0
        }

        when (activeIndex) {
            FIRST_IMAGE_VIEW_INDEX -> {
                if (position != prePosition) {
                    loadImage(prePosition, THIRD_IMAGE_VIEW_INDEX)
                }
                if (position != nextPosition) {
                    loadImage(nextPosition, SECOND_IMAGE_VIEW_INDEX)
                }
            }
            SECOND_IMAGE_VIEW_INDEX -> {
                if (position != prePosition) {
                    loadImage(prePosition, FIRST_IMAGE_VIEW_INDEX)
                }
                if (position != nextPosition) {
                    loadImage(nextPosition, THIRD_IMAGE_VIEW_INDEX)
                }
            }
            THIRD_IMAGE_VIEW_INDEX -> {
                if (position != prePosition) {
                    loadImage(prePosition, SECOND_IMAGE_VIEW_INDEX)
                }
                if (position != nextPosition) {
                    loadImage(nextPosition, FIRST_IMAGE_VIEW_INDEX)
                }
            }
        }
    }

    private fun loadImage(position: Int, imageIndex: Int) {
        when (mLoadType) {
            KenBurnsView.LoadType.String -> Glide.with(mContext).load(mStrings!![position]).into(mImageViews!![imageIndex])
            KenBurnsView.LoadType.ResourceID -> Glide.with(mContext).load(mResourceIDs!![position]).into(mImageViews!![imageIndex])
            KenBurnsView.LoadType.MIXING -> {
                val `object` = mMixingList!![position]
                if (`object`.javaClass == String::class.java) {
                    val string = `object` as String
                    Glide.with(mContext).load(string).into(mImageViews!![imageIndex])
                } else if (`object`.javaClass == Int::class.java) {
                    val integer = `object` as Int
                    Glide.with(mContext).load(integer).into(mImageViews!![imageIndex])
                }
            }
        }
    }

    fun setSwapMs(mSwapMs: Int) {
        this.mSwapMs = mSwapMs
    }

    fun setFadeInOutMs(mFadeInOutMs: Int) {
        this.mFadeInOutMs = mFadeInOutMs
    }

    fun setMaxScaleFactor(mMaxScaleFactor: Float) {
        this.mMaxScaleFactor = mMaxScaleFactor
    }

    fun setMinScaleFactor(mMinScaleFactor: Float) {
        this.mMinScaleFactor = mMinScaleFactor
    }

    companion object {

        private val NUM_OF_IMAGE_VIEWS = 3

        private val FIRST_IMAGE_VIEW_INDEX = 0
        private val SECOND_IMAGE_VIEW_INDEX = 1
        private val THIRD_IMAGE_VIEW_INDEX = 2

        private val PROPERTY_ALPHA = "alpha"

        private var sCachedSizeForLoadType: Int = 0
    }
}