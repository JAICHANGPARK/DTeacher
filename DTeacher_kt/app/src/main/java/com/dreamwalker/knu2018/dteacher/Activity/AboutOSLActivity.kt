package com.dreamwalker.knu2018.dteacher.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.dreamwalker.knu2018.dteacher.Adapter.OSLAdapter
import com.dreamwalker.knu2018.dteacher.R
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

class AboutOSLActivity : AppCompatActivity() {

    @BindView(R.id.oslRecyclerView)
    internal var recyclerView: RecyclerView? = null

    internal var layoutManager: FlexboxLayoutManager
    internal var adapter: OSLAdapter
    internal var licenseList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Open Source Licenses"
        setContentView(R.layout.activity_about_osl)
        ButterKnife.bind(this)

        addLicenseList()
        adapter = OSLAdapter(licenseList, this)
        recyclerView!!.setHasFixedSize(true)

        layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        recyclerView!!.layoutManager = layoutManager

        // TODO: 2018-02-05 리사이클러뷰에 구분자(선) 넣기위한 코드
        val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)
        // TODO: 2018-02-05 recycler view 어댑터 세팅
        recyclerView!!.adapter = adapter
    }

    private fun addLicenseList() {
        licenseList = ArrayList()
        licenseList.add(resources.getString(R.string.license_butterknife))
        licenseList.add(resources.getString(R.string.license_spot_dialog))
        licenseList.add(resources.getString(R.string.license_flat_button))
        licenseList.add(resources.getString(R.string.license_step_view))
        licenseList.add(resources.getString(R.string.license_stick_switch))
    }
}
