package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity0;
import com.dreamwalker.knu2018.dteacher.Utils.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.refactor.lib.colordialog.PromptDialog;
import info.hoang8f.widget.FButton;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.nextButton)
    TextView skipText;
    @BindView(R.id.signUpText)
    TextView signUpText;
    @BindView(R.id.loginButton)
    FButton signInButton;

    @BindView(R.id.userName)
    EditText userIDEdt;

    @BindView(R.id.password)
    EditText userPasswordEdt;
    
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
                // TODO: 2018-02-02 스킵을 누르면 바로 홈으로 갈지 인트로 화면으로 갈지 결정해야.
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                finish();
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018-01-30 로그인 처리 하기

                String userID = userIDEdt.getText().toString(); // EditText에서 값을 받아옴.
                String userPassword = userPasswordEdt.getText().toString();

                if (userID.equals("") || userPassword.equals("")) {
                    new PromptDialog(LoginActivity.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("등록하신 아이디와 비밀번호를 입력해주세요.")
                            .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }

                Log.e(TAG, "userID: " + userID);
                Log.e(TAG, "userPassword: " + userPassword);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            Log.e(TAG, "jsonResult: " + jsonResponse);
                            Log.e(TAG, "success: " + success);

                            if (success) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                dialog = builder.setMessage("로그인에 성공했습니다.")
//                                        .setPositiveButton("확인", null)
//                                        .create();
//                                dialog.show();

                                new PromptDialog(LoginActivity.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                        .setAnimationEnable(true)
                                        .setTitleText("성공")
                                        .setContentText("로그인에 성공했습니다")
                                        .setPositiveListener("시작하기", new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();

                                Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                dialog = builder.setMessage("로그인에 실패했습니다. 계정을 확인하세요")
//                                        .setNegativeButton("확인", null)
//                                        .create();
//                                dialog.show();
                                new PromptDialog(LoginActivity.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                        .setAnimationEnable(true)
                                        .setTitleText("실패")
                                        .setContentText("로그인에 실패했습니다. 계정을 확인하세요")
                                        .setPositiveListener("확인", new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

//                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
//                startActivity(intent);
//                finish();
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