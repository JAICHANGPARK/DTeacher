package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dreamwalker.knu2018.dteacher.BSMSync.SyncBSMDataActivity;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.WearableSync.SyncWearableDataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyncBSMActivity extends AppCompatActivity {

    // TODO: 2018-02-19 버튼 맴버
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.buttonSync)
    Button buttonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_dd);
        setTitle("혈당계");
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonRegister)
    public void onDeviceRegisterButtonClicked() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }

    @OnClick(R.id.buttonSync)
    public void onSyncButtonClicked(){
        startActivity(new Intent(SyncBSMActivity.this, SyncBSMDataActivity.class));
    }
}
