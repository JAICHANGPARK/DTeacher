package com.dreamwalker.knu2018.dteacher.UIViews


/**
 * Created by JAICHANGPARK on 10/16/17.
 */


import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup

/**
 * Created by katsuyagoto on 2015/04/17.
 */
class LoopViewPager(context: Context, private val mPages: Int, private val mListener: LoopViewPagerListener) : ViewPager(context) {

    private val mFirstPosition: Int

    private var mCurrentPage: Int = 0

    private var mAdapterPages: Int = 0

    private val mOnPageChangeListener: LoopOnPageChangeListener

    interface LoopViewPagerListener {

        fun OnInstantiateItem(page: Int): View

        fun onPageScrollChanged(page: Int)

        fun onPageScroll(position: Int, positionOffset: Float, positionOffsetPixels: Int)

        fun onPageSelected(position: Int)
    }

    init {

        if (mPages == 0) {
            return
        }

        if (mPages == 1) {
            mAdapterPages = 1
        } else {
            mAdapterPages = ALL_PAGE_COUNT
        }

        adapter = LoopPagerAdapter()

        val maxSets = ALL_PAGE_COUNT / mPages
        mFirstPosition = maxSets / 2 * mPages
        setCurrentItem(-1, false)
        mOnPageChangeListener = LoopOnPageChangeListener()
        addOnPageChangeListener(mOnPageChangeListener)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        val pos = if (item < 0) mFirstPosition else mFirstPosition + item
        super.setCurrentItem(pos, smoothScroll)
    }

    fun setCurrentItemAfterCancelListener(item: Int, smoothScroll: Boolean) {
        removeOnPageChangeListener(mOnPageChangeListener)

        val pos = if (item < 0) mFirstPosition else mFirstPosition + item
        super.setCurrentItem(pos, smoothScroll)

        addOnPageChangeListener(mOnPageChangeListener)
    }

    override fun setCurrentItem(item: Int) {
        val pos = if (item < 0) mFirstPosition else mFirstPosition + item
        super.setCurrentItem(pos)
    }

    fun setCurrentItemAfterCancelListener(item: Int) {
        removeOnPageChangeListener(mOnPageChangeListener)

        val pos = if (item < 0) mFirstPosition else mFirstPosition + item
        super.setCurrentItem(pos)

        addOnPageChangeListener(mOnPageChangeListener)
    }

    private inner class LoopOnPageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            mListener.onPageScroll(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            mCurrentPage = pos2page(position)
            mListener.onPageSelected(mCurrentPage)
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                mListener.onPageScrollChanged(mCurrentPage)
            }
        }
    }

    private fun pos2page(pos: Int): Int {
        return pos % mPages
    }

    private inner class LoopPagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val v = mListener.OnInstantiateItem(pos2page(position))
            container.addView(v)
            return v
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return mAdapterPages
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
    }

    companion object {

        private val ALL_PAGE_COUNT = 100000
    }
}
