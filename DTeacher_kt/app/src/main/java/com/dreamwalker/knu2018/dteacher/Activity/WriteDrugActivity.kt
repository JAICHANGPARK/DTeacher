package com.dreamwalker.knu2018.dteacher.Activity

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.Fragment.WriteDrugFragment
import com.dreamwalker.knu2018.dteacher.Model.Drug
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent
import com.dreamwalker.knu2018.dteacher.Utils.ValueChangedEvent

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.w3c.dom.Text

import java.util.ArrayList
import java.util.Locale

import butterknife.BindView
import butterknife.ButterKnife

class WriteDrugActivity : AppCompatActivity() {

    private var rrapid: ArrayList<String>? = null
    private var rapid: ArrayList<String>? = null
    private var neutral: ArrayList<String>? = null
    private var longtime: ArrayList<String>? = null
    private var mixed: ArrayList<String>? = null

    internal var mSectionsPagerAdapter: SectionsPagerAdapter
    internal var mViewPager: ViewPager
    internal var bus = EventBus.getDefault()

    @BindView(R.id.doneTextView)
    internal var doneTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "약물 선택"
        setContentView(R.layout.activity_write_drug)
        ButterKnife.bind(this)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        mViewPager.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        rrapid = ArrayList()
        rapid = ArrayList()
        longtime = ArrayList()
        neutral = ArrayList()
        mixed = ArrayList()


        for (i in 0..4) {
            rrapid!!.add(i, "unknown")
            rapid!!.add(i, "unknown")
            longtime!!.add(i, "unknown")
            neutral!!.add(i, "unknown")
        }
        for (i in 0..9) {
            mixed!!.add(i, "unknown")
        }
        bus.register(this)

        doneTextView!!.setOnClickListener {
            val intent = Intent(this@WriteDrugActivity, WriteDrugUnitActivity::class.java)
            val stringBuilder = StringBuilder()
            // TODO: 2018-02-03 내가 짜도 참 괜찮은 코드
            for (i in rrapid!!.indices) {
                if (!rrapid!![i].contains("unknown")) {
                    stringBuilder.append(rrapid!![i])
                    stringBuilder.append(",")
                }
            }
            for (i in rapid!!.indices) {
                if (!rapid!![i].contains("unknown")) {
                    stringBuilder.append(rapid!![i])
                    stringBuilder.append(",")
                }
            }
            for (i in neutral!!.indices) {
                if (!neutral!![i].contains("unknown")) {
                    stringBuilder.append(neutral!![i])
                    stringBuilder.append(",")
                }
            }
            for (i in longtime!!.indices) {
                if (!longtime!![i].contains("unknown")) {
                    stringBuilder.append(longtime!![i])
                    stringBuilder.append(",")
                }
            }
            for (i in mixed!!.indices) {
                if (!mixed!![i].contains("unknown")) {
                    stringBuilder.append(mixed!![i])
                    stringBuilder.append(",")
                }
            }
            Log.e(TAG, "onClick: result" + stringBuilder.toString())
            intent.putExtra("WRITE_DRUG_TYPE", stringBuilder.toString())
            startActivity(intent)
        }
    }

    @Subscribe
    fun onEvent(event: DrugDataEvent) {
        val index = event.position
        val pageNum = event.pageNumber

        if (pageNum == 1) rrapid = event.drugRrapidList
        if (pageNum == 2) rapid = event.drugRapidList
        if (pageNum == 3) neutral = event.drugNeutralList
        if (pageNum == 4) longtime = event.drugLongtimeList
        if (pageNum == 5) mixed = event.drugMixedList

        for (i in rrapid!!.indices) {
            Log.e(TAG, "onEvent: rrapid : index " + i + " , " + "value : " + rrapid!![i])
        }
        for (i in rapid!!.indices) {
            Log.e(TAG, "onEvent: rapid : index " + i + " , " + "value : " + rapid!![i])
        }
        for (i in neutral!!.indices) {
            Log.e(TAG, "onEvent: neutral : index " + i + " , " + "value : " + neutral!![i])
        }
        for (i in longtime!!.indices) {
            Log.e(TAG, "onEvent: DRUG_TYPE : index " + i + " , " + "value : " + longtime!![i])
        }
    }

    override fun onStop() {
        bus.unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        bus.unregister(this)
        super.onDestroy()
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        internal var PAGE_NUMBER_SIZE = 5

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 ->
                    // TODO: 2018-02-09 초속효성
                    return WriteDrugFragment.newInstance("1")
                1 ->
                    // TODO: 2018-02-09 속효성
                    return WriteDrugFragment.newInstance("2")
                2 ->
                    // TODO: 2018-02-09 중간형
                    return WriteDrugFragment.newInstance("3")
                3 ->
                    // TODO: 2018-02-09 지속형
                    return WriteDrugFragment.newInstance("4")
                4 ->
                    // TODO: 2018-02-09 혼합형
                    return WriteDrugFragment.newInstance("5")
            }
            return null
        }

        override fun getCount(): Int {
            return PAGE_NUMBER_SIZE
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val l = Locale.getDefault()
            when (position) {
                0 -> return getString(R.string.rapid).toUpperCase(l)
                1 -> return getString(R.string.rapid2).toUpperCase(l)
                2 -> return getString(R.string.netural).toUpperCase(l)
                3 -> return getString(R.string.longtime).toUpperCase(l)
                4 -> return getString(R.string.mixed).toUpperCase(l)
            }
            return null
        }
    }

    companion object {

        private val TAG = "WriteDrugActivity"
    }
}
