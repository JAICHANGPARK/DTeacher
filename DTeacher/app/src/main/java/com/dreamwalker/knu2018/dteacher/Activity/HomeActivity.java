package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.dreamwalker.knu2018.dteacher.Adapter.HomeTimeLineAdapter;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.DBHelper.HomeDBHelper;
import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.Model.Global;
import com.dreamwalker.knu2018.dteacher.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private HomeDBHelper homeDBHelper;
    private SQLiteDatabase db;

    SharedPreferences sharedPreferences;
    String userID;
    String userPassword;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //    @BindView(R.id.dbText)
//    TextView dbTextView;
    @BindView(R.id.animation_view)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.animationLayout)
    LinearLayout animationLayout;

    @BindView(R.id.homeRecyclerView)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    HomeTimeLineAdapter adapter;
    ArrayList<Global> bloodSugarArrayList;

    HashMap<String, String> tempMap;
    String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("당선생");
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Paper.init(this);

        sharedPreferences = getSharedPreferences("userinfo",MODE_PRIVATE);
        userID = sharedPreferences.getString("userID","");
        userPassword = sharedPreferences.getString("userPassword","");
        Log.e(TAG, "onCreate: " + userID  + "," + userPassword );
        homeDBHelper = new HomeDBHelper(this, "bs.db", null, 1);
        bloodSugarArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tempMap = new HashMap<>();

        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        int tempMonth = now.get(Calendar.MONTH);
        tempMonth = tempMonth + 1;
        String month = String.valueOf(tempMonth);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        today = year + "-" + month + "-" + day;

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
                                .setTitle("알림")
                                .setMessage("그만보시겠어요?")
                                .setPositiveButton("응", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "알림", "그만보시겠어요?" + lastTarget.id())
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
// TODO: 2018-02-11 텍스트뷰로 가져온 방법
//        String result = homeDBHelper.selectDateAllData(today);
//        if (result.equals("null")) {
//            animationLayout.setVisibility(View.VISIBLE);
//            lottieAnimationView.playAnimation();
//            dbTextView.setVisibility(View.GONE);
//        } else {
//            animationLayout.setVisibility(View.GONE);
//            lottieAnimationView.cancelAnimation();
//            dbTextView.setVisibility(View.VISIBLE);
//            dbTextView.setText(result);
//        }
        bloodSugarArrayList = homeDBHelper.allReadData(today);

        for (int i = 0; i < bloodSugarArrayList.size(); i++) {

            Log.e(TAG, "모든 테이블의 데이터를 가져온값  : " + bloodSugarArrayList.get(i).getHead() + ","
                    + bloodSugarArrayList.get(i).getType() + "," + bloodSugarArrayList.get(i).getValue());
        }

//        ArrayList<String> list = new ArrayList<>();
//        if (!bloodSugarArrayList.isEmpty()) {
//            for (int i = 0; i < bloodSugarArrayList.size(); i++) {
//
//                String times = bloodSugarArrayList.get(i).getTime();
//                String[] timeArray = times.split(":");
//                String time = timeArray[0]+timeArray[1];
//                list.add(time);
////                tempMap.put("index", String.valueOf(i));
////                tempMap.put("value", time);
//            }
//            Collections.sort(list);
//        }
//        for (int k = 0; k < list.size(); k++){
//            Log.e(TAG, "copy list: " + list.get(k));
//        }
        // TODO: 2018-02-11 이제 리사이클러뷰 적용
        //bloodSugarArrayList = homeDBHelper.selectAll(today);
        adapter = new HomeTimeLineAdapter(this, bloodSugarArrayList);
        recyclerView.setAdapter(adapter);

        if (bloodSugarArrayList.get(0).getHead().equals("null")) {
            animationLayout.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
            recyclerView.setVisibility(View.GONE);
            //dbTextView.setVisibility(View.GONE);
        } else {
            animationLayout.setVisibility(View.GONE);
            lottieAnimationView.cancelAnimation();
            recyclerView.setVisibility(View.VISIBLE);
            //adapter.notifyDataSetChanged();
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

                        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                        userID = sharedPreferences.getString("userID","");

                        if (userID.equals("GUEST")) {
                            startActivity(new Intent(HomeActivity.this, SignUpCheckActivity.class));
                        }else {
                            startActivity(new Intent(HomeActivity.this, AboutUserActivity.class));
                        }

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
                        // TODO: 2018-02-07 1차
                        //startActivity(new Intent(HomeActivity.this, DangActivity.class));
                        // TODO: 2018-02-07 2차
                        startActivity(new Intent(HomeActivity.this, WriteBSActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this, WriteFitnessActivity.class));
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this, "아직 미구현 , 곧 추가할게요", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(HomeActivity.this, WriteDrugActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this, WriteDrugActivity.class));
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
                .build();
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

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // TODO: 2018-02-04 데이터가 처리됬을때 데이터 값을받아 하단에 표기할 처리 메소드가 들어가면됨.

                String year = String.valueOf(date.get(Calendar.YEAR));
                int tempMonth = date.get(Calendar.MONTH) + 1;
                String month = String.valueOf(tempMonth);
                String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
                String selectDate = year + "-" + month + "-" + day;

              /*  // TODO: 2018-02-08 혈당 데이터베이스에서 가져온다.
                String result = homeDBHelper.selectDateAllData(selectDate);
                Log.e(TAG, "onDateSelected: result " + result);

                if (result.equals("null")) {
                    animationLayout.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    dbTextView.setVisibility(View.GONE);
                } else {
                    animationLayout.setVisibility(View.GONE);
                    lottieAnimationView.cancelAnimation();
                    dbTextView.setVisibility(View.VISIBLE);
                    dbTextView.setText("");
                    dbTextView.setText(result);
                }*/

                // TODO: 2018-02-11 이제 리사이클러뷰 적용
                bloodSugarArrayList.clear();
                bloodSugarArrayList = homeDBHelper.allReadData(selectDate);
                // TODO: 2018-02-11 혼합된 데이터를 나눠 시간 내림차순으로 정렬해야 한다.

                //bloodSugarArrayList = homeDBHelper.selectAll(selectDate);
                adapter = new HomeTimeLineAdapter(getApplicationContext(), bloodSugarArrayList);
                recyclerView.setAdapter(adapter);

                for (int i = 0; i < bloodSugarArrayList.size(); i++) {
                    Log.e(TAG, "데이터 선택 리턴 값 : " + bloodSugarArrayList.get(i).getType() + ","
                            + bloodSugarArrayList.get(i).getValue() + "," + bloodSugarArrayList.get(i).getTime());
                }
                //adapter.notifyDataSetChanged();
                if (bloodSugarArrayList.get(0).getHead().equals("null")) {
                    animationLayout.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    recyclerView.setVisibility(View.GONE);
                    //dbTextView.setVisibility(View.GONE);
                } else {
                    animationLayout.setVisibility(View.GONE);
                    lottieAnimationView.cancelAnimation();
                    recyclerView.setVisibility(View.VISIBLE);
                }

                //recyclerView.setAdapter(adapter);
                //recyclerView.getAdapter().notifyDataSetChanged();

//                if (result.equals("null")) {
//                    dbTextView.setText("데이터가 없네요 ");
//                } else {
//
//                    dbTextView.setText(result);
//                }

                Log.e(TAG, "onDateSelected: " + "Date : " + year + month + day + ", position:  " + month);

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

    @Override
    protected void onResume() {
        // TODO: 2018-02-08 여기서 Adapter Notify하면 되겠네
        Log.e(TAG, "onResume: 화면 돌아왔어요 ");

        // TODO: 2018-02-11 이제 리사이클러뷰 적용
        bloodSugarArrayList.clear();
        bloodSugarArrayList = homeDBHelper.allReadData(today);
        // TODO: 2018-02-11 혼합된 데이터를 나눠 시간 내림차순으로 정렬해야 한다.

        //bloodSugarArrayList = homeDBHelper.selectAll(selectDate);
        adapter = new HomeTimeLineAdapter(getApplicationContext(), bloodSugarArrayList);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < bloodSugarArrayList.size(); i++) {
            Log.e(TAG, "데이터 선택 리턴 값 : " + bloodSugarArrayList.get(i).getType() + ","
                    + bloodSugarArrayList.get(i).getValue() + "," + bloodSugarArrayList.get(i).getTime());
        }
        //adapter.notifyDataSetChanged();
        if (bloodSugarArrayList.get(0).getHead().equals("null")) {
            animationLayout.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
            recyclerView.setVisibility(View.GONE);
            //dbTextView.setVisibility(View.GONE);
        } else {
            animationLayout.setVisibility(View.GONE);
            lottieAnimationView.cancelAnimation();
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void readDatabaseData() {

    }
}
