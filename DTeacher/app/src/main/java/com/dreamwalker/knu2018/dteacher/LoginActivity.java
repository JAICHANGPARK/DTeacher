package com.dreamwalker.knu2018.dteacher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.nextButton)
    TextView skipText;
    @BindView(R.id.signUpText)
    TextView signUpText;
    @BindView(R.id.loginButton)
    FButton signInButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view2);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("bus.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
    
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018-01-30 로그인 처리 하기
            }
        });
        
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity0.class);
                startActivity(intent);
            }
        });
        
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}