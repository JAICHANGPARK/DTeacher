package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Fragment.WriteDrugFragment;
import com.dreamwalker.knu2018.dteacher.Model.Drug;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent;
import com.dreamwalker.knu2018.dteacher.Utils.ValueChangedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WriteDrugActivity extends AppCompatActivity {

    private static final String TAG = "WriteDrugActivity";

    private ArrayList<String> rrapid, rapid, neutral, longtime, mixed;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    EventBus bus = EventBus.getDefault();

    @BindView(R.id.doneTextView)
    TextView doneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("약물 선택");
        setContentView(R.layout.activity_write_drug);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        rrapid = new ArrayList<>();
        rapid = new ArrayList<>();
        longtime = new ArrayList<>();
        neutral = new ArrayList<>();
        mixed = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
            rrapid.add(i, "unknown");
            rapid.add(i, "unknown");
            longtime.add(i, "unknown");
            neutral.add(i, "unknown");
        }
        for (int i = 0; i < 10; i++) {
            mixed.add(i, "unknown");
        }
        bus.register(this);

        doneTextView.setOnClickListener(v -> {
            Intent intent = new Intent(WriteDrugActivity.this, WriteDrugUnitActivity.class);
            StringBuilder stringBuilder = new StringBuilder();
            // TODO: 2018-02-03 내가 짜도 참 괜찮은 코드
            for (int i = 0; i < rrapid.size(); i++) {
                if (!rrapid.get(i).contains("unknown")) {
                    stringBuilder.append(rrapid.get(i));
                    stringBuilder.append(",");
                }
            }
            for (int i = 0; i < rapid.size(); i++) {
                if (!rapid.get(i).contains("unknown")) {
                    stringBuilder.append(rapid.get(i));
                    stringBuilder.append(",");
                }
            }
            for (int i = 0; i < neutral.size(); i++) {
                if (!neutral.get(i).contains("unknown")) {
                    stringBuilder.append(neutral.get(i));
                    stringBuilder.append(",");
                }
            }
            for (int i = 0; i < longtime.size(); i++) {
                if (!longtime.get(i).contains("unknown")) {
                    stringBuilder.append(longtime.get(i));
                    stringBuilder.append(",");
                }
            }
            for (int i = 0; i < mixed.size(); i++) {
                if (!mixed.get(i).contains("unknown")) {
                    stringBuilder.append(mixed.get(i));
                    stringBuilder.append(",");
                }
            }
            Log.e(TAG, "onClick: result" + stringBuilder.toString() );
            intent.putExtra("WRITE_DRUG_TYPE", stringBuilder.toString());
            startActivity(intent);
            finish();
        });
    }

    @Subscribe
    public void onEvent(DrugDataEvent event) {
        int index = event.position;
        int pageNum = event.pageNumber;

        if (pageNum == 1) rrapid = event.drugRrapidList;
        if (pageNum == 2) rapid = event.drugRapidList;
        if (pageNum == 3) neutral = event.drugNeutralList;
        if (pageNum == 4) longtime = event.drugLongtimeList;
        if (pageNum == 5) mixed = event.drugMixedList;

        for (int i = 0; i < rrapid.size(); i++) {
            Log.e(TAG, "onEvent: rrapid : index " + i + " , " + "value : " + rrapid.get(i));
        }
        for (int i = 0; i < rapid.size(); i++) {
            Log.e(TAG, "onEvent: rapid : index " + i + " , " + "value : " + rapid.get(i));
        }
        for (int i = 0; i < neutral.size(); i++) {
            Log.e(TAG, "onEvent: neutral : index " + i + " , " + "value : " + neutral.get(i));
        }
        for (int i = 0; i < longtime.size(); i++) {
            Log.e(TAG, "onEvent: DRUG_TYPE : index " + i + " , " + "value : " + longtime.get(i));
        }
    }

    @Override
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int PAGE_NUMBER_SIZE = 5;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    // TODO: 2018-02-09 초속효성 
                    return WriteDrugFragment.newInstance("1");
                case 1:
                    // TODO: 2018-02-09 속효성
                    return WriteDrugFragment.newInstance("2");
                case 2:
                    // TODO: 2018-02-09 중간형 
                    return WriteDrugFragment.newInstance("3");
                case 3:
                    // TODO: 2018-02-09 지속형 
                    return WriteDrugFragment.newInstance("4");
                case 4:
                    // TODO: 2018-02-09 혼합형 
                    return WriteDrugFragment.newInstance("5");
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_NUMBER_SIZE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.rapid).toUpperCase(l);
                case 1:
                    return getString(R.string.rapid2).toUpperCase(l);
                case 2:
                    return getString(R.string.netural).toUpperCase(l);
                case 3:
                    return getString(R.string.longtime).toUpperCase(l);
                case 4:
                    return getString(R.string.mixed).toUpperCase(l);
            }
            return null;
        }
    }
}
