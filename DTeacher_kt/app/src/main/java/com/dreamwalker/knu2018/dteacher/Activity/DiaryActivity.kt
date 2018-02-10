package com.dreamwalker.knu2018.dteacher.Activity

import android.content.Intent
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.dreamwalker.knu2018.dteacher.Adapter.DiarySectionsPagerAdapter
import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDangFragment
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFitnessFragment
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFoodFragment
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.UIViews.Fab
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener

import butterknife.BindView
import butterknife.ButterKnife

/***
 * 제작 : 박제창
 * 2018.02.05
 */

class DiaryActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * The [ViewPager] that will host the section contents.
     */
    private var materialSheetFab: MaterialSheetFab<*>? = null
    private var statusBarColor: Int = 0

    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null
    @BindView(R.id.container)
    internal var mViewPager: ViewPager? = null
    @BindView(R.id.tabs)
    internal var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        setupViewPager(mViewPager)
        setupFab()
        tabLayout!!.setupWithViewPager(mViewPager)
        mViewPager!!.offscreenPageLimit = 4

        // TODO: 2018-02-05 HomeActivity에서 전달되는 페이지 번호를 이곳에서 받아 처리한다.
        PAGE_NUM = intent.getIntExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, -1)
        mViewPager!!.currentItem = PAGE_NUM

        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = DiarySectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(DiaryDangFragment(), "혈당")
        adapter.addFragment(DiaryFitnessFragment(), "운동")
        adapter.addFragment(DiaryFoodFragment(), "식사")
        adapter.addFragment(DiaryDrugFragment(), "투약")
        viewPager!!.adapter = adapter
    }

    private fun setupFab() {

        val fab = findViewById<View>(R.id.fab) as Fab
        val sheetView = findViewById<View>(R.id.fab_sheet)
        val overlay = findViewById<View>(R.id.overlay)
        val sheetColor = resources.getColor(R.color.background_card)
        val fabColor = resources.getColor(R.color.theme_accent)

        // Create material sheet FAB
        materialSheetFab = MaterialSheetFab(fab, sheetView, overlay, sheetColor, fabColor)

        // Set material sheet event listener
        materialSheetFab!!.setEventListener(object : MaterialSheetFabEventListener() {
            override fun onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor()
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(resources.getColor(R.color.PrimaryDark3))
            }

            override fun onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor)
            }
        })

        // Set material sheet item click listeners
        findViewById<View>(R.id.fab_sheet_item_food).setOnClickListener(this) // 식사
        findViewById<View>(R.id.fab_sheet_item_recording).setOnClickListener(this)  //운동
        findViewById<View>(R.id.fab_sheet_item_reminder).setOnClickListener(this) //투약
        findViewById<View>(R.id.fab_sheet_item_photo).setOnClickListener(this) //혈당
        findViewById<View>(R.id.fab_sheet_item_note).setOnClickListener(this) //종합
    }

    override fun onClick(v: View) {
        when (v.id) {
        // TODO: 2018-02-08 식사 입력 클릭 이벤트 처리
            R.id.fab_sheet_item_food -> Toast.makeText(this, "습식 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show()
        // TODO: 2018-02-08 운동 입력 클릭 이벤트 처리
            R.id.fab_sheet_item_recording -> {
                startActivity(Intent(this@DiaryActivity, WriteFitnessActivity::class.java))
                Toast.makeText(this, "운동 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show()
            }
        // TODO: 2018-02-08 투약 입력 클릭 이벤트 처리
            R.id.fab_sheet_item_reminder -> {
                startActivity(Intent(this@DiaryActivity, WriteDrugActivity::class.java))
                Toast.makeText(this, "투약 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show()
            }
        // TODO: 2018-02-08 혈당 입력 클릭 이벤트 처리
            R.id.fab_sheet_item_photo -> {
                startActivity(Intent(this@DiaryActivity, WriteBSActivity::class.java))
                Toast.makeText(this, "혈당 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show()
            }
        // TODO: 2018-02-08 종합 입력 클릭 이벤트 처리
            R.id.fab_sheet_item_note -> Toast.makeText(this, "종합입력 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show()
        }
        //Toast.makeText(this, R.string.sheet_item_pressed + ", " +   v.getId(), Toast.LENGTH_SHORT).show();
        materialSheetFab!!.hideSheet()
    }

    private fun getStatusBarColor(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor
        } else 0
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        private val TAG = "DiaryActivity"
        private var PAGE_NUM = 0
    }
}
