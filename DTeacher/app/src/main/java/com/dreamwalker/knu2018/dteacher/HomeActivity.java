package com.dreamwalker.knu2018.dteacher;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private FloatingNavigationView mFloatingNavigationView;
    private MultiChoicesCircleButton multiChoicesCircleButton;
    private HorizontalCalendar horizontalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("당선생");
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Paper.init(this);

        // TODO: 2018-02-04 뷰 초기화
        initCalendarView();
        initBottomFloating();
        initBottomNavigation();
        initNavFloatong();

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
    
    private void initNavFloatong(){

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        // mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
        mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.PrimaryDark3)));

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
                Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
                mFloatingNavigationView.close();
                return true;
            }
        });
        
    }

}
