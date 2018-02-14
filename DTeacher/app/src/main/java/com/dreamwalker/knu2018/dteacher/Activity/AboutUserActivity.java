package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUserActivity extends AppCompatActivity {

    @BindView(R.id.buttonMyInfo)
    Button buttonMyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonMyInfo)
    public void onMyInfoButtonClicked(){

        startActivity(new Intent(AboutUserActivity.this, AboutUserInfoActivity.class));

    }
}
