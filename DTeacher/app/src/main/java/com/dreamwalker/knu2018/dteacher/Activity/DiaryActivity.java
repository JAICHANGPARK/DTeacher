package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Adapter.DiarySectionsPagerAdapter;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDangFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFitnessFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFoodFragment;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.UIViews.Fab;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 제작 : 박제창
 * 2018.02.05
 */

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "DiaryActivity";
    private static int PAGE_NUM = 0;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupViewPager(mViewPager);
        setupFab();
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);

        // TODO: 2018-02-05 HomeActivity에서 전달되는 페이지 번호를 이곳에서 받아 처리한다.
        PAGE_NUM = getIntent().getIntExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, -1);
        mViewPager.setCurrentItem(PAGE_NUM);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        DiarySectionsPagerAdapter adapter = new DiarySectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DiaryDangFragment(), "혈당");
        adapter.addFragment(new DiaryFitnessFragment(), "운동");
        adapter.addFragment(new DiaryFoodFragment(), "식사");
        adapter.addFragment(new DiaryDrugFragment(), "투약");
        viewPager.setAdapter(adapter);
    }
    private void setupFab() {

        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.theme_accent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.PrimaryDark3));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners
        findViewById(R.id.fab_sheet_item_food).setOnClickListener(this); // 식사
        findViewById(R.id.fab_sheet_item_recording).setOnClickListener(this);  //운동
        findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this); //투약
        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this); //혈당
        findViewById(R.id.fab_sheet_item_note).setOnClickListener(this); //종합
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // TODO: 2018-02-08 식사 입력 클릭 이벤트 처리
            case R.id.fab_sheet_item_food:
                startActivity(new Intent(DiaryActivity.this, WriteMealActivity.class));
                Toast.makeText(this, "습식 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show();
                break;
            // TODO: 2018-02-08 운동 입력 클릭 이벤트 처리
            case R.id.fab_sheet_item_recording:
                startActivity(new Intent(DiaryActivity.this, WriteFitnessActivity.class));
                Toast.makeText(this, "운동 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show();
                break;
            // TODO: 2018-02-08 투약 입력 클릭 이벤트 처리
            case R.id.fab_sheet_item_reminder:
                startActivity(new Intent(DiaryActivity.this, WriteDrugActivity.class));
                Toast.makeText(this, "투약 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show();
                break;
            // TODO: 2018-02-08 혈당 입력 클릭 이벤트 처리
            case R.id.fab_sheet_item_photo:
                startActivity(new Intent(DiaryActivity.this, WriteBSActivity.class));
                Toast.makeText(this, "혈당 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show();
                break;
            // TODO: 2018-02-08 종합 입력 클릭 이벤트 처리
            case R.id.fab_sheet_item_note:
                startActivity(new Intent(DiaryActivity.this, TotalWriteActivity.class));
                Toast.makeText(this, "종합입력 버튼 눌렀어요 ", Toast.LENGTH_SHORT).show();
                break;
        }
        //Toast.makeText(this, R.string.sheet_item_pressed + ", " +   v.getId(), Toast.LENGTH_SHORT).show();
        materialSheetFab.hideSheet();
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
