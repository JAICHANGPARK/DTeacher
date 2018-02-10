package com.dreamwalker.knu2018.dteacher.Activity

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.database.sqlite.SQLiteDatabase
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.airbnb.lottie.LottieAnimationView
import com.andremion.floatingnavigationview.FloatingNavigationView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.DBHelper.HomeDBHelper
import com.dreamwalker.knu2018.dteacher.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton

import java.util.ArrayList
import java.util.Calendar

import butterknife.BindView
import butterknife.ButterKnife
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.paperdb.Paper

class HomeActivity : AppCompatActivity() {

    private var mFloatingNavigationView: FloatingNavigationView? = null
    private var multiChoicesCircleButton: MultiChoicesCircleButton? = null
    private var horizontalCalendar: HorizontalCalendar? = null
    private var homeDBHelper: HomeDBHelper? = null
    private val db: SQLiteDatabase? = null

    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null

    @BindView(R.id.dbText)
    internal var dbTextView: TextView? = null
    @BindView(R.id.animation_view)
    internal var lottieAnimationView: LottieAnimationView? = null
    @BindView(R.id.animationLayout)
    internal var animationLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTitle("당선생");
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        Paper.init(this)

        homeDBHelper = HomeDBHelper(this, "bs.db", null, 1)

        val now = Calendar.getInstance()
        val year = now.get(Calendar.YEAR).toString()
        var tempMonth = now.get(Calendar.MONTH)
        tempMonth = tempMonth + 1
        val month = tempMonth.toString()
        val day = now.get(Calendar.DAY_OF_MONTH).toString()
        val hour = now.get(Calendar.HOUR).toString()
        val minutes = now.get(Calendar.MINUTE).toString()
        val today = "$year-$month-$day"

        // TODO: 2018-02-04 뷰 초기화
        initCalendarView()
        initBottomFloating()
        initBottomNavigation()
        initNavFloating()

        // We load a drawable and create a location to show a tap target here
        // We need the display to get the width and height at this point in time
        val display = windowManager.defaultDisplay
        val disp = applicationContext.resources.displayMetrics
        val deviceWidth = disp.widthPixels
        val deviceHeight = disp.heightPixels

        Log.e(TAG, "DisplayMetrics: $deviceWidth, $deviceHeight")

        // Load our little droid guy
        val droid = ContextCompat.getDrawable(this, R.drawable.ic_loyalty_black_24dp)
        // Tell our droid buddy where we want him to appear
        val droidTarget = Rect(0, 0, droid!!.intrinsicWidth * 2, droid.intrinsicHeight * 2)
        val bottomFabTarget = Rect(0, 0, droid.intrinsicWidth * 2, droid.intrinsicHeight * 2)
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.width / 2, display.height / 2)
        bottomFabTarget.offset(deviceWidth / 2 - 50, deviceHeight - 200)

        val sequence = TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.floating_navigation_view), "전체 메뉴", "이곳에서 모든 메뉴선택이 가능합니다.")
                                .icon(getDrawable(R.drawable.ic_menu_black_24dp)),
                        TapTarget.forBounds(bottomFabTarget, "빨리 기록하기", "여기를 터치하여 다이어리에 빠르게 기록해보세요")
                                .cancelable(false)
                                .icon(getDrawable(R.drawable.ic_add_black_24dp))
                                .id(4),
                        TapTarget.forBounds(droidTarget, "타임 라인", "이곳에 기록한 내용들이 요약되어 표기됩니다.")
                                .cancelable(false)
                                .icon(droid)
                                .id(5)
                ).listener(object : TapTargetSequence.Listener {
            override fun onSequenceFinish() {
                Log.e(TAG, "onSequenceFinish: ")
            }

            override fun onSequenceStep(lastTarget: TapTarget, targetClicked: Boolean) {
                Log.e(TAG, "onSequenceStep: ")
            }

            override fun onSequenceCanceled(lastTarget: TapTarget) {
                Log.e(TAG, "onSequenceCanceled: ")

                val dialog = AlertDialog.Builder(this@HomeActivity)
                        .setTitle("Uh oh")
                        .setMessage("You canceled the sequence")
                        .setPositiveButton("Oops", null).show()
                TapTargetView.showFor(dialog,
                        TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                .cancelable(false)
                                .tintTarget(false), object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                        dialog.dismiss()
                    }
                })
            }
        })

        sequence.start()

        val result = homeDBHelper!!.selectDateAllData(today)
        if (result == "null") {
            animationLayout!!.visibility = View.VISIBLE
            lottieAnimationView!!.playAnimation()
            dbTextView!!.visibility = View.GONE
        } else {
            animationLayout!!.visibility = View.GONE
            lottieAnimationView!!.cancelAnimation()
            dbTextView!!.visibility = View.VISIBLE
            dbTextView!!.text = result
        }


        /*        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.floating_navigation_view), "This is a target",
                        "We have the best targets, believe me")
                        .icon(getDrawable(R.drawable.ic_menu_black_24dp))
//                        // All options below are optional
//                        .outerCircleColor(R.color.red)      // Specify a color for the outer circle
//                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
//                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
//                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
//                        .titleTextColor(R.color.white)      // Specify the color of the title text
//                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
//                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
//                        .textColor(R.color.blue)            // Specify a color for both the title and description text
//                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
//                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
//                        .drawShadow(true)                   // Whether to draw a drop shadow or not
//                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
//                        .tintTarget(true)                   // Whether to tint the target view's color
//                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
//                        .targetRadius(60),                  // Specify the target radius (in dp)
//                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
//                    @Override
//                    public void onTargetClick(TapTargetView view) {
//                        super.onTargetClick(view);      // This call is optional
//                        //doSomething();
//                    }
        );*/

    }

    override fun onBackPressed() {
        if (mFloatingNavigationView!!.isOpened) {
            mFloatingNavigationView!!.close()
        } else {
            super.onBackPressed()
        }
    }

    private fun initBottomNavigation() {

        val bottomNavigation = findViewById<View>(R.id.bottom_navigation) as AHBottomNavigation
        // Create items
        val bitem1 = AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp)
        val bitem2 = AHBottomNavigationItem("Analysis", R.drawable.ic_assessment_black_24dp)
        val bitem3 = AHBottomNavigationItem("Account", R.drawable.ic_person_black_24dp)

        // Add items
        bottomNavigation.addItem(bitem1)
        bottomNavigation.addItem(bitem2)
        bottomNavigation.addItem(bitem3)

        // Set listeners
        bottomNavigation.setOnTabSelectedListener { position, wasSelected ->
            // Do something cool here...
            val intent: Intent
            when (position) {
                0 -> {
                }
                1 -> {
                    intent = Intent(this@HomeActivity, DiaryActivity::class.java)
                    intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 0)
                    startActivity(intent)
                }
                2 -> {
                }
            }
            Log.e(TAG, "bottomNavigation : onTabSelected: $position, $wasSelected")
            true
        }

        bottomNavigation.setOnNavigationPositionListener { y ->
            // Manage the new y position
            Log.e(TAG, "bottomNavigation : onPositionChange: " + y)
        }

    }

    private fun initBottomFloating() {

        val item1 = MultiChoicesCircleButton.Item("혈당기록", resources.getDrawable(R.drawable.ic_loyalty_black_24dp), 20)
        val item2 = MultiChoicesCircleButton.Item("운동기록", resources.getDrawable(R.drawable.ic_directions_run_black_24dp), 65)
        val item3 = MultiChoicesCircleButton.Item("습식기록", resources.getDrawable(R.drawable.ic_local_dining_black_24dp), 110)
        val item4 = MultiChoicesCircleButton.Item("투약기록", resources.getDrawable(R.drawable.ic_local_drink_black_24dp), 155)

        // TODO: 2018-02-04 생성한 아이템을 이곳에  buttonItems리스트에 넣는다.
        val buttonItems = ArrayList<MultiChoicesCircleButton.Item>()
        buttonItems.add(item1)
        buttonItems.add(item2)
        buttonItems.add(item3)
        buttonItems.add(item4)

        multiChoicesCircleButton = findViewById<View>(R.id.multiChoicesCircleButton) as MultiChoicesCircleButton
        multiChoicesCircleButton!!.setButtonItems(buttonItems)

        // TODO: 2018-02-04 하단 플로팅 리스너
        multiChoicesCircleButton!!.onSelectedItemListener = MultiChoicesCircleButton.OnSelectedItemListener { item, index ->
            // Do something
            when (index) {
                0 ->
                    // TODO: 2018-02-07 1차
                    //startActivity(new Intent(HomeActivity.this, DangActivity.class));
                    // TODO: 2018-02-07 2차
                    startActivity(Intent(this@HomeActivity, WriteBSActivity::class.java))
                1 -> startActivity(Intent(this@HomeActivity, WriteFitnessActivity::class.java))
                2 -> Toast.makeText(this@HomeActivity, "아직 미구현 , 곧 추가할게요", Toast.LENGTH_SHORT).show()
                3 -> startActivity(Intent(this@HomeActivity, WriteDrugActivity::class.java))
            }//startActivity(new Intent(HomeActivity.this, WriteDrugActivity.class));
            Log.e(TAG, "onSelected: " + index + ", " + item.text)
            //Toast.makeText(HomeActivity.this, "onSelected" , Toast.LENGTH_SHORT).show();
        }

        multiChoicesCircleButton!!.onHoverItemListener = MultiChoicesCircleButton.OnHoverItemListener { item, index ->
            // Do something
            Log.e(TAG, "onHovered: " + index + ", " + item.text)
            //Toast.makeText(HomeActivity.this, "onHovered" + index + item.getText(), Toast.LENGTH_SHORT).show();
        }

    }

    private fun initCalendarView() {

        /* starts before 1 month from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        /* ends after 1 month from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        horizontalCalendar = HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .defaultSelectedDate(Calendar.getInstance())
                .build()
        //                .addEvents(new CalendarEventsPredicate() {
        //
        //                    Random rnd = new Random();
        //
        //                    @Override
        //                    public List<CalendarEvent> events(Calendar date) {
        //                        List<CalendarEvent> events = new ArrayList<>();
        //                        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        //
        //                        if (dayOfWeek == 4){
        //                            events.add(new CalendarEvent(getResources().getColor(R.color.Accent3),"event"));
        //                        }
        //                       // int count = rnd.nextInt(6); //  nextInt(6) 이렇게 하면 0~6 까지의 랜덤한 숫자가 출려됨
        //                        //events.add(new CalendarEvent(getResources().getColor(R.color.Accent3),"event"));
        ////                        for (int i = 0; i <= count; i++) {
        ////                            //events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
        ////
        ////                        }
        //
        //                        return events;
        //                    }
        //                })

        horizontalCalendar!!.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                // TODO: 2018-02-04 데이터가 처리됬을때 데이터 값을받아 하단에 표기할 처리 메소드가 들어가면됨.

                val year = date.get(Calendar.YEAR).toString()
                val tempMonth = date.get(Calendar.MONTH) + 1
                val month = tempMonth.toString()
                val day = date.get(Calendar.DAY_OF_MONTH).toString()
                val selectDate = "$year-$month-$day"

                // TODO: 2018-02-08 혈당 데이터베이스에서 가져온다.
                val result = homeDBHelper!!.selectDateAllData(selectDate)
                Log.e(TAG, "onDateSelected: result " + result)

                if (result == "null") {
                    animationLayout!!.visibility = View.VISIBLE
                    lottieAnimationView!!.playAnimation()
                    dbTextView!!.visibility = View.GONE
                } else {
                    animationLayout!!.visibility = View.GONE
                    lottieAnimationView!!.cancelAnimation()
                    dbTextView!!.visibility = View.VISIBLE
                    dbTextView!!.text = ""
                    dbTextView!!.text = result
                }

                //                if (result.equals("null")) {
                //                    dbTextView.setText("데이터가 없네요 ");
                //                } else {
                //
                //                    dbTextView.setText(result);
                //                }
                Log.e(TAG, "onDateSelected: Date : $year$month$day, position:  $month")
            }

            // TODO: 2018-02-04 Optionss
            override fun onCalendarScroll(calendarView: HorizontalCalendarView?,
                                          dx: Int, dy: Int) {

            }

            override fun onDateLongClicked(date: Calendar?, position: Int): Boolean {
                return true
            }
        }
    }

    private fun initNavFloating() {

        mFloatingNavigationView = findViewById<View>(R.id.floating_navigation_view) as FloatingNavigationView
        // mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
        mFloatingNavigationView!!.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Accent3))
        //mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_A200)));

        // TODO: 2018-02-04  mFloatingNavigationView 상단 네비게이션 드로우 리스너
        mFloatingNavigationView!!.setOnClickListener { mFloatingNavigationView!!.open() }
        mFloatingNavigationView!!.setNavigationItemSelectedListener { item ->
            val intent: Intent
            when (item.itemId) {
                R.id.nav_diabetes -> {
                    mFloatingNavigationView!!.close()
                    intent = Intent(this@HomeActivity, DiaryActivity::class.java)
                    intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 0)
                    startActivity(intent)
                }
                R.id.nav_workout -> {
                    mFloatingNavigationView!!.close()
                    intent = Intent(this@HomeActivity, DiaryActivity::class.java)
                    intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 1)
                    startActivity(intent)
                }
                R.id.nav_food -> {
                    mFloatingNavigationView!!.close()
                    intent = Intent(this@HomeActivity, DiaryActivity::class.java)
                    intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 2)
                    startActivity(intent)
                }
                R.id.nav_drug -> {
                    mFloatingNavigationView!!.close()
                    intent = Intent(this@HomeActivity, DiaryActivity::class.java)
                    intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 3)
                    startActivity(intent)
                }

                R.id.nav_setting -> {
                    mFloatingNavigationView!!.close()
                    intent = Intent(this@HomeActivity, SettingActivity::class.java)
                    startActivity(intent)
                }
            }
            //Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!" + item.getItemId(), Snackbar.LENGTH_SHORT).show();
            true
        }
    }

    override fun onResume() {
        // TODO: 2018-02-08 여기서 Adapter Notify하면 되겠네
        super.onResume()
    }

    companion object {

        private val TAG = "HomeActivity"
    }
}
