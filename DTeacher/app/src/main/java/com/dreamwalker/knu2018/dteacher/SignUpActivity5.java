package com.dreamwalker.knu2018.dteacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.UIViews.RangeSliderWithNumber;
import com.dreamwalker.knu2018.dteacher.UIViews.SeekBarWithNumber;
import com.eminayar.panter.PanterDialog;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

/**
 * 당뇨 목표 구간 선택 뷰
 */

public class SignUpActivity5 extends AppCompatActivity {

    @BindView(R.id.step_view)
    StepView setpview;
    @BindView(R.id.nextButton)
    FButton nextButton;

    @BindView(R.id.rsn_bubble)
    RangeSliderWithNumber aimRangeNumber;
    //당 위험 수치 뷰
    @BindView(R.id.sbn)
    SeekBarWithNumber dangerSeekBar;

    ArrayList<String> userSignUpInfo;
    String userMax, userMin, userDanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up5);

        setTitle("Sign Up");

        ButterKnife.bind(this);

        userSignUpInfo = new ArrayList<>();
        userSignUpInfo = getIntent().getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_4);

        userMax = "200"; //default
        userMin = "50"; // default
        userDanger = "50"; // default

        setpview.getState()
                .selectedTextColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.fbutton_color_sun_flower))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("필수 정보");
                    add("기본 정보");
                    add("당뇨 정보");
                    add("투약 정보");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                // other state methods are equal to the corresponding xml attributes
                .commit();

        setpview.go(2, false);

        // TODO: 2018-01-31 목표 당뇨수치 값 변화 리스너
        aimRangeNumber.setRangeSliderListener(new RangeSliderWithNumber.RangeSliderListener() {
            @Override
            public void onMaxChanged(int newValue) {
                userMax = String.valueOf(newValue);
            }

            @Override
            public void onMinChanged(int newValue) {
                userMin = String.valueOf(newValue);
            }
        });
        // TODO: 2018-01-31 위험 당수치 변화 리스너
        dangerSeekBar.setDefaultSelected(50);
        dangerSeekBar.setRangeSliderListener(new SeekBarWithNumber.NumberChangeListener() {
            @Override
            public void onNumberChange(int newValue) {
                userDanger = String.valueOf(newValue);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUpInfo.add(userMax);
                userSignUpInfo.add(userMin);
                userSignUpInfo.add(userDanger);
                Intent intent = new Intent(getApplicationContext(), SignUpActivity6.class);
                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_5, userSignUpInfo);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        new PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_orange)
                .setTitle("알림")
                .setPositive("계속할래요") // You can pass also View.OnClickListener as second param
                .setNegative("그만둘래요", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setMessage("보나 나은 서비스 이용을 위해서 회원가입이 필요한데 그래도 그만두시겠어요?")
                .isCancelable(false)
                .show();
    }
}
