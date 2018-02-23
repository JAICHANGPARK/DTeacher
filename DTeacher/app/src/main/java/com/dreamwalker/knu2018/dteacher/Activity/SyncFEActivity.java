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

public class SyncFEActivity extends AppCompatActivity {

    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.buttonSync)
    Button buttonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_fe);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonRegister)
    public void onDeviceRegisterButtonClicked() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }
}
