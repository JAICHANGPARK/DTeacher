package com.dreamwalker.knu2018.dteacher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;
import info.hoang8f.widget.FButton;

public class SignUpActivity0 extends AppCompatActivity {


    @BindView(R.id.step_view)
    StepView setpview;

    @BindView(R.id.vaildateButton)
    FButton vaildateButton;
    @BindView(R.id.registerButton)
    FButton registerButton;

    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.email)
    EditText email;

    private AlertDialog dialog;

    ColorDialog colorDialog;
    private boolean validate = false; //아이디 중복검사 변수
    String userID, userPassword, userEmail;

    ArrayList<String> userInfo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sign Up");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_up0);

        ButterKnife.bind(this);

        colorDialog = new ColorDialog(this);

        userInfo = new ArrayList<>();

        context = getApplicationContext();

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

        setpview.go(0, false);

        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        lottieAnimationView.setAnimation("loading_animation.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userID = userName.getText().toString();
                userPassword = password.getText().toString();
                userEmail = email.getText().toString();

                if (userID.equals("") || userPassword.equals("") || userEmail.equals("")) {

                    new PromptDialog(SignUpActivity0.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("필수 정보는 빈 칸 없이 입력해주세요.")
                            .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity0.this);
//                    dialog = builder.setMessage("필수 정보는 빈 칸 없이 입력해주세요.").setNegativeButton("확인", null)
//                            .create();
//                    dialog.show();
                    return;
                }

                if (userPassword.length() < 8) {
                    new PromptDialog(SignUpActivity0.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("보안을 높이기 위해 8자리 이상의 비밀번호로 구성해주세요")
                            .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity0.this);
//                    dialog = builder.setMessage("보안을 높이기 위해 8자리 이상의 비밀번호로 구성해주세요").setNegativeButton("확인", null)
//                            .create();
//                    dialog.show();
                    return;
                }
                if (!checkEmail(userEmail)) {

                    new PromptDialog(SignUpActivity0.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("정확한 이메일을 입력해주세요")
                            .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();

//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity0.this);
//                    dialog = builder.setMessage("정확한 이메일을 입력해주세요").setNegativeButton("확인", null)
//                            .create();
//                    dialog.show();
                    return;
                }

//                new PromptDialog(SignUpActivity0.this)
//                        .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
//                        .setAnimationEnable(true)
//                        .setTitleText("알림")
//                        .setContentText("당신에게 정보를 줄게요 ")
//                        .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
//                            @Override
//                            public void onClick(PromptDialog dialog) {
//                                dialog.dismiss();
//                            }
//                        }).show();


//                colorDialog.setTitle("알림");
//                colorDialog.setContentText("이건 테스트에요");
//                colorDialog.setPositiveListener("아", new ColorDialog.OnPositiveListener() {
//                    @Override
//                    public void onClick(ColorDialog dialog) {
//                        Toast.makeText(SignUpActivity0.this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                        .setNegativeListener("아니요", new ColorDialog.OnNegativeListener() {
//                            @Override
//                            public void onClick(ColorDialog dialog) {
//                                Toast.makeText(SignUpActivity0.this, dialog.getNegativeText().toString(), Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        }).show();

                new PanterDialog(SignUpActivity0.this)
                        .setHeaderBackground(R.drawable.pattern_bg_blue)
                        .setTitle("알림")
                        .setPositive("좋아요", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userInfo.add(userID);
                                userInfo.add(userPassword);
                                userInfo.add(userEmail);
                                Intent intent = new Intent(getApplicationContext(), SignUpActivity1.class);
                                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0, userInfo);
                                startActivity(intent);
                                finish();
                            }
                        }) // You can pass also View.OnClickListener as second param
                        .setNegative("괜찮아요.이정도면 충분해요", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userInfo.add(userID);
                                userInfo.add(userPassword);
                                userInfo.add(userEmail);
                                Intent intent = new Intent(getApplicationContext(), SignUpDoneActivity.class);
                                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0, userInfo);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setMessage("조금더 많은 정보를 입력해주시면 당뇨 관리 및 데이터 제공에 도움이 되요. 좀 더 입력하시겠어요?")
                        .isCancelable(false)
                        .show();

                // TODO: 2018-02-01 입력 받은데이터를 다음 액티비티로 전달한다.
//                Intent intent = new Intent(getApplicationContext(), SignUpActivity1.class);
//                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0, userInfo);
//                startActivity(intent);
//                finish();
            }
        });
    }

    /**
     * 이메일 포맷 체크
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;
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
                .withAnimation(Animation.POP)
                .setMessage("보나 나은 서비스 이용을 위해서 회원가입이 필요한데 그래도 그만두시겠어요?")
                .isCancelable(false)
                .show();
    }
}
