package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyncWearableActivity extends AppCompatActivity {

    // TODO: 2018-02-14 장비등록 버튼
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.buttonRealTime)
    Button buttonRealTime;
    @BindView(R.id.buttonSync)
    Button buttonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_wearable);
        setTitle("웨어러블 장비");
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonRegister)
    public void onDeviceRegisterButtonClicked() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }

    @OnClick(R.id.buttonRealTime)
    public void onRealTimeButtonClicked(){

    }

    @OnClick(R.id.buttonSync)
    public void onSyncButtonClicked(){

    }

}
