package com.dreamwalker.knu2018.dteacher.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dreamwalker.knu2018.dteacher.Adapter.DiarySectionsPagerAdapter;
import com.dreamwalker.knu2018.dteacher.Fragment.TWBloodFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.TWDrugFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.TWFitnessFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.TWMealFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.TWSleepFragment;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.TWDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TotalWriteActivity extends AppCompatActivity {
    private static final String TAG = "TotalWriteActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    EventBus bus = EventBus.getDefault();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_write);
        ButterKnife.bind(this);

        bus.register(this);

        setSupportActionBar(toolbar);
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(5);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    private void setupViewPager(ViewPager viewPager) {
        DiarySectionsPagerAdapter adapter = new DiarySectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TWBloodFragment(), "혈당");
        adapter.addFragment(new TWFitnessFragment(), "운동");
        adapter.addFragment(new TWMealFragment(), "식사");
        adapter.addFragment(new TWDrugFragment(), "투약");
        adapter.addFragment(new TWSleepFragment(), "수면");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_total_write, menu);
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


    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();

    }

    /**
     * EventBus 이벤트를 받아오는 부분.
     * Unit Value는 Adapter에서 받아 옴.
     *
     * @param event
     * @Author JAICHANGPARK
     */
    @Subscribe
    public void onEvent(TWDataEvent event) {
        String date = event.date;
        String time = event.time;
        String type = event.type;
        String value = event.value;
        String extValue1 = event.extraValue1;
        String extValue2 = event.extraValue2;
        int pageNum = event.pageNumber;


        Log.e(TAG, "onEvent: " + date + time + type + value + extValue1 + extValue2 + pageNum);

//        values = event.valueUnit;
//        valueString = String.valueOf(values);

    }
}
