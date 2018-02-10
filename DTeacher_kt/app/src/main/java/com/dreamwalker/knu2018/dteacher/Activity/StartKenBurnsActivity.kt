package com.dreamwalker.knu2018.dteacher.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.UIUtils.KenBurnsImages
import com.dreamwalker.knu2018.dteacher.UIViews.KenBurnsView
import com.dreamwalker.knu2018.dteacher.UIViews.LoopViewPager

import java.util.Arrays

class StartKenBurnsActivity : AppCompatActivity() {
    private var nextButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 2018-02-02 status View를 없애기 위함.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // TODO: 2018-02-02 xml 붙이기
        setContentView(R.layout.activity_start_ken_burns)

        initializeKenBurnsView()
        nextButton = findViewById<View>(R.id.nextButton) as Button
        nextButton!!.setOnClickListener {
            startActivity(Intent(this@StartKenBurnsActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun initializeKenBurnsView() {
        // KenBurnsView
        val kenBurnsView = findViewById<View>(R.id.ken_burns_view) as KenBurnsView
        kenBurnsView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        kenBurnsView.setSwapMs(6000)
        kenBurnsView.setFadeInOutMs(750)

        // ResourceIDs
        val resourceIDs = Arrays.asList(*KenBurnsImages.IMAGES_RESOURCE)
        kenBurnsView.loadResourceIDs(resourceIDs)

        // LoopViewListener
        val listener = object : LoopViewPager.LoopViewPagerListener {
            override fun OnInstantiateItem(page: Int): View {
                val counterText = TextView(applicationContext)
                //counterText.setText(String.valueOf(page));
                counterText.text = ""
                return counterText
            }

            override fun onPageScroll(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                kenBurnsView.forceSelected(position)
            }

            override fun onPageScrollChanged(page: Int) {}
        }

        // LoopView
        val loopViewPager = LoopViewPager(this, resourceIDs.size, listener)

        val viewPagerFrame = findViewById<View>(R.id.view_pager_frame) as FrameLayout
        viewPagerFrame.addView(loopViewPager)

        kenBurnsView.setPager(loopViewPager)
    }

    companion object {
        private val TAG = "StartActivity"
    }
}
