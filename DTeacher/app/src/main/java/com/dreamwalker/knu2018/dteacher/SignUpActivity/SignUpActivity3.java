package com.dreamwalker.knu2018.dteacher.SignUpActivity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;
import com.eminayar.panter.PanterDialog;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.refactor.lib.colordialog.PromptDialog;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import info.hoang8f.widget.FButton;

/**
 * 당뇨 유형 선택 뷰
 */
public class SignUpActivity3 extends AppCompatActivity {

    @BindView(R.id.step_view)
    StepView setpview;
    @BindView(R.id.nextButton)
    FButton nextButton;

    @BindView(R.id.radioRealButtonGroup)
    RadioRealButtonGroup diabetesTypeGroup;

    //List<String> stepLabel;

    PanterDialog panterDialog;

    String userDiabetesType;
    ArrayList<String> userSignUpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        setTitle("Sign Up");

        ButterKnife.bind(this);

        userSignUpInfo = new ArrayList<>();
        userSignUpInfo = getIntent().getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_2);

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

        diabetesTypeGroup.setOnPositionChangedListener(new RadioRealButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(RadioRealButton button, int currentPosition, int lastPosition) {

                userDiabetesType = String.valueOf(currentPosition);

                switch (currentPosition) {
                    case 0:
                        Snackbar.make(getWindow().getDecorView().getRootView(), "제 1형 당뇨병" + currentPosition, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        //Toast.makeText(SignUpActivity3.this, "제 1형 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Snackbar.make(getWindow().getDecorView().getRootView(), "제 2형 당뇨병" + currentPosition, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        break;
                    case 2:
                        Snackbar.make(getWindow().getDecorView().getRootView(), "임신성 당뇨병" + currentPosition, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        //Toast.makeText(SignUpActivity3.this, "임신성 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Snackbar.make(getWindow().getDecorView().getRootView(), "기타" + currentPosition, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        //Toast.makeText(SignUpActivity3.this, "기타 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                }

                //Toast.makeText(SignUpActivity3.this, "Clicked! Position: " + currentPosition, Toast.LENGTH_SHORT).show();
            }
        });

        diabetesTypeGroup.setOnLongClickedButtonListener(new RadioRealButtonGroup.OnLongClickedButtonListener() {

            @Override
            public boolean onLongClickedButton(RadioRealButton button, int position) {
                switch (position) {
                    case 0:
                        //Toast.makeText(SignUpActivity3.this, "제 1형 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        new PanterDialog(getBaseContext())
//                                .setHeaderBackground(R.drawable.pattern_bg_orange)
//                                .setTitle("Sample Title")
//                                .setPositive("I GOT IT")// You can pass also View.OnClickListener as second param
//                                .setNegative("DISMISS")
//                                .setMessage("제 2형 당뇨병에대한 설명 ")
//                                .isCancelable(false)
//                                .show();

                        //Toast.makeText(SignUpActivity3.this, "제 2형 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:

                        //Toast.makeText(SignUpActivity3.this, "임신성 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(SignUpActivity3.this, "기타 당뇨병", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userSignUpInfo.equals("")){
                    new PromptDialog(SignUpActivity3.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("당신의 당뇨 유형을 선택해주세요 ")
                            .setPositiveListener("알겠어", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                userSignUpInfo.add(userDiabetesType);
                Intent intent = new Intent(getApplicationContext(), SignUpActivity4.class);
                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_3,userSignUpInfo);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {

        if (panterDialog != null) {
            panterDialog.dismiss();
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
