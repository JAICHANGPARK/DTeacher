package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamwalker.knu2018.dteacher.Adapter.DiarySectionsPagerAdapter;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDangFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFitnessFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFoodFragment;
import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 제작 : 박제창
 * 2018.02.05
 */

public class DiaryActivity extends AppCompatActivity {
    private static final String TAG = "DiaryActivity";
    private static int PAGE_NUM = 0;
    /**
     * The {@link ViewPager} that will host the section contents.
     */

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
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);

        // TODO: 2018-02-05 HomeActivity에서 전달되는 페이지 번호를 이곳에서 받아 처리한다.
        PAGE_NUM = getIntent().getIntExtra(IntentConst.DIARY_PAGE_FRAGMENT_NUM, -1);
        mViewPager.setCurrentItem(PAGE_NUM);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}
