package com.dreamwalker.knu2018.dteacher.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
    private static final int CONTEXT_NUM = 5;
    private static final int PAGE_ZERO = 0;
    private static final int PAGE_ONE = 1;
    private static final int PAGE_TWO = 2;
    private static final int PAGE_THREE = 3;
    private static final int PAGE_FOUR = 4;


    @BindView(R.id.textSave)
    TextView textSave;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    EventBus bus = EventBus.getDefault();

    int pageNum;

    String dataArray[] = new String[CONTEXT_NUM];
    String timeArray[] = new String[CONTEXT_NUM];
    String typeArray[] = new String[CONTEXT_NUM];
    String valueArray[] = new String[CONTEXT_NUM];

    String extraArray[] = new String[CONTEXT_NUM];
    String extraArray2[] = new String[CONTEXT_NUM];
    String extraArray3[] = new String[CONTEXT_NUM];

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

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0 ; i < CONTEXT_NUM; i ++){
                    Log.e(TAG, "onClick: " + dataArray[i] + "- " + timeArray[i] + "- " + typeArray[i] + "- " + valueArray[i] + "- " + extraArray[i] + "- "
                    + extraArray2[i] + "- " + extraArray3[i] + "\n");
                }
            }
        });


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

        pageNum = event.pageNumber;
//        String date = event.date;
//        String time = event.time;
//        String type = event.type;
//        String value = event.value;
//        String extValue1 = event.extraValue1;
//        String extValue2 = event.extraValue2;

        switch (pageNum) {
            case 0:
                dataArray[PAGE_ZERO] = event.date;
                timeArray[PAGE_ZERO] = event.time;
                typeArray[PAGE_ZERO] = event.type;
                valueArray[PAGE_ZERO] = event.value;
                extraArray[PAGE_ZERO] = event.extraValue1;
                extraArray2[PAGE_ZERO] = event.extraValue2;

                Log.e(TAG, "onEvent: PAGE_ZERO " +
                        dataArray[PAGE_ZERO] + "- " +
                        timeArray[PAGE_ZERO] + "- " +
                        typeArray[PAGE_ZERO] + "- " +
                        valueArray[PAGE_ZERO] + "- " +
                        extraArray[PAGE_ZERO] + "- " +
                        extraArray2[PAGE_ZERO] + "- " +
                        pageNum);
                return;
                //break;
            case 1:
                dataArray[PAGE_ONE] = event.date;
                timeArray[PAGE_ONE] = event.time;
                typeArray[PAGE_ONE] = event.type;
                valueArray[PAGE_ONE] = event.value;
                extraArray[PAGE_ONE] = event.extraValue1;
                extraArray2[PAGE_ONE] = event.extraValue2;

                Log.e(TAG, "onEvent:  PAGE_ONE - " +
                        dataArray[PAGE_ONE] + "- " +
                        timeArray[PAGE_ONE] + "- " +
                        typeArray[PAGE_ONE] + "- " +
                        valueArray[PAGE_ONE] + "- " +
                        extraArray[PAGE_ONE] + "- " +
                        extraArray2[PAGE_ONE] + "- " +
                        pageNum);
                return;
            //break;
            case 2:
                dataArray[PAGE_TWO] = event.date;
                timeArray[PAGE_TWO] = event.time;
                typeArray[PAGE_TWO] = event.type;
                valueArray[PAGE_TWO] = event.value;
                extraArray[PAGE_TWO] = event.extraValue1;
                extraArray2[PAGE_TWO] = event.extraValue2;
                extraArray3[PAGE_TWO] = event.extraValue3;

                Log.e(TAG, "onEvent:  PAGE_TWO - " +
                        dataArray[PAGE_TWO] + "- " +
                        timeArray[PAGE_TWO] + "- " +
                        typeArray[PAGE_TWO] + "- " +
                        valueArray[PAGE_TWO] + "- " +
                        extraArray[PAGE_TWO] + "- " +
                        extraArray2[PAGE_TWO] + "- " +
                        pageNum);
                return;
            //break;
            case 3:
                dataArray[PAGE_THREE] = event.date;
                timeArray[PAGE_THREE] = event.time;
                typeArray[PAGE_THREE] = event.type;
                valueArray[PAGE_THREE] = event.value;
                extraArray[PAGE_THREE] = event.extraValue1;
                extraArray2[PAGE_THREE] = event.extraValue2;

                Log.e(TAG, "onEvent: PAGE_THREE " +
                        dataArray[PAGE_THREE] + "- " +
                        timeArray[PAGE_THREE] + "- " +
                        typeArray[PAGE_THREE] + "- " +
                        valueArray[PAGE_THREE] + "- " +
                        extraArray[PAGE_THREE] + "- " +
                        extraArray2[PAGE_THREE] + "- " +
                        pageNum);
                //break;
                return;

            case 4:
                dataArray[PAGE_FOUR] = event.date;
                timeArray[PAGE_FOUR] = event.time;
                typeArray[PAGE_FOUR] = event.type;
                valueArray[PAGE_FOUR] = event.value;
                extraArray[PAGE_FOUR] = event.extraValue1;
                extraArray2[PAGE_FOUR] = event.extraValue2;
                Log.e(TAG, "onEvent: PAGE_FOUR" +
                        dataArray[PAGE_FOUR] + "- " +
                        timeArray[PAGE_FOUR] + "- " +
                        typeArray[PAGE_FOUR] + "- " +
                        valueArray[PAGE_FOUR] + "- " +
                        extraArray[PAGE_FOUR] + "- " +
                        extraArray2[PAGE_FOUR] + "- " +
                        pageNum);
                break;
            default:
                break;
        }


        //Log.e(TAG, "onEvent: " + date + time + type + value + extValue1 + extValue2 + pageNum);

//        values = event.valueUnit;
//        valueString = String.valueOf(values);

    }
}
