package com.dreamwalker.knu2018.dteacher.Activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.dreamwalker.knu2018.dteacher.R

import butterknife.BindView
import butterknife.ButterKnife
import info.hoang8f.widget.FButton

class AboutVersionInfoActivity : AppCompatActivity() {

    @BindView(R.id.versionImageView)
    internal var versionImageView: ImageView? = null
    @BindView(R.id.versionText)
    internal var versionText: TextView? = null
    @BindView(R.id.versionButton)
    internal var versionButton: FButton? = null

    internal var versionNumber: String
    internal var i: PackageInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_version_info)
        ButterKnife.bind(this)
        Glide.with(this).load(R.drawable.app_icon_hr).into(versionImageView!!)

        try {
            i = packageManager.getPackageInfo(packageName, 0)
            versionNumber = i!!.versionName
            versionText!!.text = versionNumber
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

}
