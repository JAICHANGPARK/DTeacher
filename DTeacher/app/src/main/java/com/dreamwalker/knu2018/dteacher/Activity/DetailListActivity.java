package com.dreamwalker.knu2018.dteacher.Activity;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dreamwalker.knu2018.dteacher.Adapter.DetailListAdapter;
import com.dreamwalker.knu2018.dteacher.Model.RealTime;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class DetailListActivity extends AppCompatActivity {
    private static final String TAG = "DetailListActivity";
    @BindView(R.id.detail_recyclerview)
    RecyclerView recyclerView;

    DetailListAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<RealTime> realTimeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("데이터 확인");
        setContentView(R.layout.activity_detail_list);
        ButterKnife.bind(this);
        Paper.init(this);

        realTimeArrayList = new ArrayList<>();

        if (Paper.book("realtime").read("logging") != null){
            realTimeArrayList = Paper.book("realtime").read("logging");
        }else {
            Snackbar.make(getWindow().getDecorView().getRootView(),"저장된 데이터가 없어요", Snackbar.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            long startTime = System.currentTimeMillis();
            realTimeArrayList.forEach(s-> Log.e(TAG, "onCreate: " + s.getValue() + s.getDatetime() ));
            long endTime = System.currentTimeMillis();
            Log.e(TAG, "elapsedTime : " + (endTime - startTime));
        }

        adapter = new DetailListAdapter(this,realTimeArrayList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));

    }


}
