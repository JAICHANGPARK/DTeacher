package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";

    @BindView(R.id.buttonDeveloper)
    Button buttonDeveloper;
    @BindView(R.id.buttonVersion)
    Button buttonVersion;
    @BindView(R.id.buttonOpenSourceLicense)
    Button buttonOpenSourceLicense;
    @BindView(R.id.buttonUserPrivacy)
    Button buttonUserPrivacy;
    @BindView(R.id.buttonWearable)
    Button buttonWearable;
    @BindView(R.id.buttonDiabetesDevice)
    Button buttonDiabetesDevice;
    @BindView(R.id.buttonMachine)
    Button buttonMachine;
    @BindView(R.id.buttonContact)
    Button buttonContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SETTING");
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonDeveloper)
    public void onButtonDeveloperClicked(View v) {
        startActivity(new Intent(SettingActivity.this, AboutDeveloperActivity.class));
        Toast.makeText(this, "buttonDeveloper clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonVersion)
    public void onButtonVersionClicked(View v) {
        startActivity(new Intent(SettingActivity.this, AboutVersionInfoActivity.class));
        Toast.makeText(this, "Version clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonOpenSourceLicense)
    public void onButtonOSLClicked(View v) {
        startActivity(new Intent(SettingActivity.this, AboutOSLActivity.class));
        Toast.makeText(this, "OSL clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonUserPrivacy)
    public void onButtonUserPrivacyClicked(View v) {
        startActivity(new Intent(SettingActivity.this, AboutUserActivity.class));
        Toast.makeText(this, "buttonUserPrivacy clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonWearable)
    public void onButtonWearableClicked(View v) {
        startActivity(new Intent(SettingActivity.this, SyncWearableActivity.class));
        Toast.makeText(this, "onButtonWearableClicked clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonDiabetesDevice)
    public void onButtonDiabetesDeviceClicked(View v) {
        startActivity(new Intent(SettingActivity.this, SyncBSMActivity.class));
        Toast.makeText(this, "buttonDiabetesDevice clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonMachine)
    public void onButtonMachineClicked(View v) {
        startActivity(new Intent(SettingActivity.this, SyncFEActivity.class));
        Toast.makeText(this, "buttonMachine clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonContact)
    public void onButtonContactClicked(View v) {
        startActivity(new Intent(SettingActivity.this, AboutContactActivity.class));
        Toast.makeText(this, "buttonContact clicked", Toast.LENGTH_SHORT).show();
    }

}
