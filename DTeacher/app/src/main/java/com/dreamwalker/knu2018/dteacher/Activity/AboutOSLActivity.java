package com.dreamwalker.knu2018.dteacher.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dreamwalker.knu2018.dteacher.Adapter.OSLAdapter;
import com.dreamwalker.knu2018.dteacher.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutOSLActivity extends AppCompatActivity {

    @BindView(R.id.oslRecyclerView)
    RecyclerView recyclerView;

    FlexboxLayoutManager layoutManager;
    OSLAdapter adapter;
    List<String> licenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Open Source Licenses");
        setContentView(R.layout.activity_about_osl);
        ButterKnife.bind(this);

        addLicenseList();
        adapter = new OSLAdapter(licenseList,this);
        recyclerView.setHasFixedSize(true);

        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);

        // TODO: 2018-02-05 리사이클러뷰에 구분자(선) 넣기위한 코드
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        // TODO: 2018-02-05 recycler view 어댑터 세팅
        recyclerView.setAdapter(adapter);
    }
    private void addLicenseList(){
        licenseList = new ArrayList<>();
        licenseList.add(getResources().getString(R.string.license_butterknife));
        licenseList.add(getResources().getString(R.string.license_spot_dialog));
        licenseList.add(getResources().getString(R.string.license_flat_button));
        licenseList.add(getResources().getString(R.string.license_step_view));
        licenseList.add(getResources().getString(R.string.license_stick_switch));
    }
}
