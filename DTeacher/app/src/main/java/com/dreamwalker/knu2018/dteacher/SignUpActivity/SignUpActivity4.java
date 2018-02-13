package com.dreamwalker.knu2018.dteacher.SignUpActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;
import com.eminayar.panter.PanterDialog;
import com.shawnlin.numberpicker.NumberPicker;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

/**
 * 당뇨 발병 년도 선택 뷰
 */
public class SignUpActivity4 extends AppCompatActivity {

    private static final String TAG = "SignUpActivity4";

    @BindView(R.id.step_view)
    StepView setpview;
    @BindView(R.id.nextButton)
    FButton nextButton;
    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    ArrayList<String> userSignUpInfo;
    String userOccurDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);
        ButterKnife.bind(this);

        userSignUpInfo = new ArrayList<>();
        userSignUpInfo = getIntent().getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_3);
        userOccurDate = "";
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


        // Set divider color
        numberPicker.setDividerColor(ContextCompat.getColor(this, R.color.colorPrimary));
        numberPicker.setDividerColorResource(R.color.colorPrimary);
        // Set formatter
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);
        // Set selected text color
        numberPicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        numberPicker.setSelectedTextColorResource(R.color.colorPrimary);
        // Set selected text size
        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);
        // Set text color
        numberPicker.setTextColor(ContextCompat.getColor(this, R.color.dark_grey));
        numberPicker.setTextColorResource(R.color.dark_grey);
        // Set text size
        numberPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);
        // Set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);
        //Set value
        numberPicker.setMaxValue(2040);
        numberPicker.setMinValue(1980);
        numberPicker.setValue(2018);
        // Set fading edge enabled
        numberPicker.setFadingEdgeEnabled(true);
        // Set scroller enabled
        numberPicker.setScrollerEnabled(true);
        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on current value");
            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                userOccurDate = String.valueOf(newVal);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userOccurDate.equals("")){
                    userOccurDate = "unknown";
                }

                userSignUpInfo.add(userOccurDate);
                Intent intent = new Intent(getApplicationContext(), SignUpActivity5.class);
                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_4, userSignUpInfo);
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
