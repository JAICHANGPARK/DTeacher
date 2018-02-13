package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity0;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

public class SignUpCheckActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String userID;

    @BindView(R.id.signupButton)
    FButton signupButton;
    @BindView(R.id.loginButton)
    FButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_check);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signupButton)
    public void onSignUpButtonClicked(){
        startActivity(new Intent(getBaseContext(), SignUpActivity0.class));
        finish();
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked(){
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
        finish();
    }

}
