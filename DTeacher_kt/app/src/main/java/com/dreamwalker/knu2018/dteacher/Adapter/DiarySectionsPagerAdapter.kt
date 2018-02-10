package com.dreamwalker.knu2018.dteacher.Adapter

/**
 * Created by 2E313JCP on 2017-11-16.
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.github.kimkevin.cachepot.CachePot
import com.github.paolorotolo.appintro.PagerAdapter

import java.util.ArrayList

class DiarySectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        // return super.getItemPosition(object);
        return android.support.v4.view.PagerAdapter.POSITION_NONE
    }
}
