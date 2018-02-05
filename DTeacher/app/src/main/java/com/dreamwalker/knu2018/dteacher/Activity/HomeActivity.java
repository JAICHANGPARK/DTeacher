package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private FloatingNavigationView mFloatingNavigationView;
    private MultiChoicesCircleButton multiChoicesCircleButton;
    private HorizontalCalendar horizontalCalendar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("당선생");
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Paper.init(this);

        // TODO: 2018-02-04 뷰 초기화
        initCalendarView();
        initBottomFloating();
        initBottomNavigation();
        initNavFloating();

        // We load a drawable and create a location to show a tap target here
        // We need the display to get the width and height at this point in time
        final Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
        int deviceWidth = disp.widthPixels;
        int deviceHeight = disp.heightPixels;

        Log.e(TAG, "DisplayMetrics: " + deviceWidth + ", " + deviceHeight);

        // Load our little droid guy
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_loyalty_black_24dp);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        final Rect bottomFabTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);
        bottomFabTarget.offset((deviceWidth / 2) - 50, deviceHeight - 200);

        final TapTargetSequence sequence = new TapTargetSequence(this)
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
                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Log.e(TAG, "onSequenceFinish: ");

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.e(TAG, "onSequenceStep: ");
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        Log.e(TAG, "onSequenceCanceled: ");

                        final AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

        sequence.start();


//        TapTargetView.showFor(this,                 // `this` is an Activity
//                TapTarget.forView(findViewById(R.id.floating_navigation_view), "This is a target",
//                        "We have the best targets, believe me")
//                        .icon(getDrawable(R.drawable.ic_menu_black_24dp))
////                        // All options below are optional
////                        .outerCircleColor(R.color.red)      // Specify a color for the outer circle
////                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
////                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
////                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
////                        .titleTextColor(R.color.white)      // Specify the color of the title text
////                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
////                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
////                        .textColor(R.color.blue)            // Specify a color for both the title and description text
////                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
////                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
////                        .drawShadow(true)                   // Whether to draw a drop shadow or not
////                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
////                        .tintTarget(true)                   // Whether to tint the target view's color
////                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
////                        .targetRadius(60),                  // Specify the target radius (in dp)
////                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
////                    @Override
////                    public void onTargetClick(TapTargetView view) {
////                        super.onTargetClick(view);      // This call is optional
////                        //doSomething();
////                    }
//        );

    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

    private void initBottomNavigation() {

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        // Create items
        AHBottomNavigationItem bitem1 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp);
        AHBottomNavigationItem bitem2 = new AHBottomNavigationItem("Analysis", R.drawable.ic_assessment_black_24dp);
        AHBottomNavigationItem bitem3 = new AHBottomNavigationItem("Account", R.drawable.ic_person_black_24dp);

        // Add items
        bottomNavigation.addItem(bitem1);
        bottomNavigation.addItem(bitem2);
        bottomNavigation.addItem(bitem3);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                Intent intent;
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(HomeActivity.this, DiaryActivity.class);
                        intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 0);
                        startActivity(intent);
                        break;
                    case 2:
                        break;

                }
                Log.e(TAG, "bottomNavigation : onTabSelected: " + position + ", " + wasSelected);
                return true;
            }
        });

        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
                Log.e(TAG, "bottomNavigation : onPositionChange: " + y);

            }
        });

    }

    private void initBottomFloating() {

        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("혈당기록", getResources().getDrawable(R.drawable.ic_loyalty_black_24dp), 20);
        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("운동기록", getResources().getDrawable(R.drawable.ic_directions_run_black_24dp), 65);
        MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("습식기록", getResources().getDrawable(R.drawable.ic_local_dining_black_24dp), 110);
        MultiChoicesCircleButton.Item item4 = new MultiChoicesCircleButton.Item("투약기록", getResources().getDrawable(R.drawable.ic_local_drink_black_24dp), 155);

        // TODO: 2018-02-04 생성한 아이템을 이곳에  buttonItems리스트에 넣는다.
        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        buttonItems.add(item1);
        buttonItems.add(item2);
        buttonItems.add(item3);
        buttonItems.add(item4);

        multiChoicesCircleButton = (MultiChoicesCircleButton) findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);

        // TODO: 2018-02-04 하단 플로팅 리스너
        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                // Do something
                switch (index) {
                    case 0:
                        startActivity(new Intent(HomeActivity.this, DangActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this, WorkoutActivity.class));
                        break;

                }
                Log.e(TAG, "onSelected: " + index + ", " + item.getText());
                //Toast.makeText(HomeActivity.this, "onSelected" , Toast.LENGTH_SHORT).show();
            }
        });

        multiChoicesCircleButton.setOnHoverItemListener(new MultiChoicesCircleButton.OnHoverItemListener() {
            @Override
            public void onHovered(MultiChoicesCircleButton.Item item, int index) {
                // Do something
                Log.e(TAG, "onHovered: " + index + ", " + item.getText());
                //Toast.makeText(HomeActivity.this, "onHovered" + index + item.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initCalendarView() {

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .defaultSelectedDate(Calendar.getInstance())
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
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // TODO: 2018-02-04 데이터가 처리됬을때 데이터 값을받아 하단에 표기할 처리 메소드가 들어가면됨.
                Log.e(TAG, "onDateSelected: " + "Date : " + date + ", position:  " + position);

            }

            // TODO: 2018-02-04 Optionss
            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });
    }

    private void initNavFloating() {

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        // mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
        mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Accent3)));
        //mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_A200)));

        // TODO: 2018-02-04  mFloatingNavigationView 상단 네비게이션 드로우 리스너 
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_diabetes:
                        mFloatingNavigationView.close();
                        intent = new Intent(HomeActivity.this, DiaryActivity.class);
                        intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 0);
                        startActivity(intent);
                        break;
                    case R.id.nav_workout:
                        mFloatingNavigationView.close();
                        intent = new Intent(HomeActivity.this, DiaryActivity.class);
                        intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 1);
                        startActivity(intent);
                        break;
                    case R.id.nav_food:
                        mFloatingNavigationView.close();
                        intent = new Intent(HomeActivity.this, DiaryActivity.class);
                        intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 2);
                        startActivity(intent);
                        break;
                    case R.id.nav_drug:
                        mFloatingNavigationView.close();
                        intent = new Intent(HomeActivity.this, DiaryActivity.class);
                        intent.putExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, 3);
                        startActivity(intent);
                        break;

                    case R.id.nav_setting:
                        mFloatingNavigationView.close();
                        intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
                //Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!" + item.getItemId(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

}
