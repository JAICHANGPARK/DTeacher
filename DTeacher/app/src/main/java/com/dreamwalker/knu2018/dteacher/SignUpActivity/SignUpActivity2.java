package com.dreamwalker.knu2018.dteacher.SignUpActivity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.UIViews.ScaleRulerView;
import com.eminayar.panter.PanterDialog;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

/**
 *
 *  전 엑티비티에서 받아오는 데이터
 *  1. 사용자 아이디  : index 0
 *  2. 사용자 비밀번호 : index 1
 *  3. 사용자 이메일 : index 2
 *  4. 사용자 이름 : index 3
 *  5. 성별 ( default : male) : index 4
 *  6. 전화번호 : index 5
 *  7. 생년월일 : index 6
 *  8. 나이 : index 7
 *
 *  처리되는 데이터
 *  1. 사용자 신장(키) (default : 170)
 *  2. 사용자 몸무게   (default : 60)
 *
 */

public class SignUpActivity2 extends AppCompatActivity {
    private static final String TAG = "SignUpActivity2";

    @BindView(R.id.step_view)
    StepView setpview;
    @BindView(R.id.nextButton)
    FButton nextButton;

    @BindView(R.id.scaleWheelView_height)
    ScaleRulerView mHeightWheelView;
    @BindView(R.id.tv_user_height_value)
    TextView mHeightValue;

    @BindView(R.id.scaleWheelView_weight)
    ScaleRulerView mWeightWheelView;
    @BindView(R.id.tv_user_weight_value)
    TextView mWeightValue;

    private float mHeight = 170;
    private float mMaxHeight = 220;
    private float mMinHeight = 100;

    private float mWeight = 60.0f;
    private float mMaxWeight = 200;
    private float mMinWeight = 25;

    ArrayList<String> userSignUpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        setTitle("Sign Up");

        ButterKnife.bind(this);

        userSignUpInfo = new ArrayList<>();
        userSignUpInfo = getIntent().getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_1);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < userSignUpInfo.size(); i++) {
            stringBuilder.append(userSignUpInfo.get(i));
            stringBuilder.append(",");
            Log.e(TAG, "onCreate: " + userSignUpInfo.get(i));
        }

        //Toast.makeText(this, "전달받은 데이터" + stringBuilder, Toast.LENGTH_SHORT).show();

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

        setpview.go(1, false);

        ruler_init();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringHeight = String.valueOf(mHeight);
                String stringWeight = String.valueOf(mWeight);
                userSignUpInfo.add(stringHeight);
                userSignUpInfo.add(stringWeight);

                //Toast.makeText(SignUpActivity2.this, "选择身高： " + mHeight + " 体重： " + mWeight, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), SignUpActivity3.class);
                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_2, userSignUpInfo);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ruler_init() {

        mHeightValue.setText((int) mHeight + "");
        mWeightValue.setText(mWeight + "");

        mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
        mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mHeightValue.setText((int) value + "");
                mHeight = value;
            }
        });

        mWeightWheelView.initViewParam(mWeight, mMaxWeight, mMinWeight);
        mWeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mWeightValue.setText(value + "");
                mWeight = value;
            }
        });
    }

    @Override
    protected void onPause() {

        for (int i = 3; i < userSignUpInfo.size(); i++) {
            userSignUpInfo.remove(i);
        }

        super.onPause();

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
