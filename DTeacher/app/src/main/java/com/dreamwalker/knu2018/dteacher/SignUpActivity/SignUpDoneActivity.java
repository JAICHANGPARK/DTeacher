package com.dreamwalker.knu2018.dteacher.SignUpActivity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.refactor.lib.colordialog.PromptDialog;

import static com.dreamwalker.knu2018.dteacher.Const.IntentConst.SIGNUP_EXTRA_DATA_0;

public class SignUpDoneActivity extends AppCompatActivity {

    private static final String TAG = "SignUpDoneActivity";
    PromptDialog promptDialog;

    String userID, userPassword, userEmail;
    String userName, userGender, userPhone, userBirth, userAge, userHeight, userWeight;
    String userType, userOccurDate, userMax, userMin, userDanger, userDrug;
    ArrayList<String> userRegiDataList;
    // TODO: 2018-02-13 초기 가입시 부가정보 추가 기입 유무 판단
    // 0이면 필수 정보만 입력 1이면 추가정보까지 모두 입력
    int registerType;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_done);

        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        promptDialog = new PromptDialog(SignUpDoneActivity.this);

        userRegiDataList = new ArrayList<>();
        registerType = getIntent().getIntExtra(IntentConst.SIGNUP_REGISTER_TYPE, -1);

        if (registerType == 0) {
            Log.e(TAG, "get 0 : ");
            userRegiDataList = getIntent().getStringArrayListExtra(SIGNUP_EXTRA_DATA_0);
            userID = userRegiDataList.get(0);
            userPassword = userRegiDataList.get(1);
            userEmail = userRegiDataList.get(2);

            for (int i = 0; i < userRegiDataList.size(); i++) {
                Log.e(TAG, "userRegiDataList: " + i + " : " + userRegiDataList.get(i));
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        Log.e(TAG, "jsonResult: " + jsonObject);
                        Log.e(TAG, "sucesse: " + success);

                        if (success) {

                            editor.putString("userID", userID);
                            editor.putString("userPassword", userPassword);
                            editor.putString("userEmail", userEmail);
                            editor.commit();

                            //new PromptDialog(SignUpDoneActivity.this)
                            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                    .setAnimationEnable(true)
                                    .setTitleText("성공")
                                    .setContentText("회원 등록에 성공했습니다.")
                                    .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                            finish(); // 회원가입 성공후 창 닫음.
                                        }
                                    }).show();
                        } else {

                            new PromptDialog(SignUpDoneActivity.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                                    .setAnimationEnable(true)
                                    .setTitleText("실패")
                                    .setContentText("회원 등록에 실패했습니다.")
                                    .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
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
            RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userEmail, "0", responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignUpDoneActivity.this);
            queue.add(registerRequest);

        }
        // TODO: 2018-02-13 모든 정보를 입력한 경우.
        else if (registerType == 1) {
            Log.e(TAG, "get 1 : ");
            userRegiDataList = getIntent().getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_6);
            userID = userRegiDataList.get(0);
            userPassword = userRegiDataList.get(1);
            userEmail = userRegiDataList.get(2);

            userName = userRegiDataList.get(3);
            userGender = userRegiDataList.get(4);
            userPhone = userRegiDataList.get(5);
            userBirth = userRegiDataList.get(6);
            userAge = userRegiDataList.get(7);
            userHeight = userRegiDataList.get(8);
            userWeight = userRegiDataList.get(9);

            userType = userRegiDataList.get(10);
            userOccurDate = userRegiDataList.get(11);
            userMax = userRegiDataList.get(12);
            userMin = userRegiDataList.get(13);
            userDanger = userRegiDataList.get(14);
            userDrug = userRegiDataList.get(15);

            for (int i = 0; i < userRegiDataList.size(); i++) {
                Log.e(TAG, "userRegiDataList: " + i + " : " + userRegiDataList.get(i));
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        Log.e(TAG, "jsonResult: " + jsonObject);
                        Log.e(TAG, "sucesse: " + success);

                        if (success) {
                            // TODO: 2018-02-13  SharedPreferences 성공하면 저장
                            editor.putString("userID", userID);
                            editor.putString("userPassword", userPassword);
                            editor.putString("userEmail", userEmail);
                            editor.putString("userName", userName);
                            editor.putString("userGender", userGender);
                            editor.putString("userPhone", userPhone);
                            editor.putString("userBirth", userBirth);
                            editor.putString("userAge", userAge);
                            editor.putString("userHeight", userHeight);
                            editor.putString("userWeight", userWeight);
                            editor.putString("userType",userType);
                            editor.putString("userOccurDate",userOccurDate);
                            editor.putString("userMax",userMax);
                            editor.putString("userMin",userMin);
                            editor.putString("userDanger", userDanger);
                            editor.putString("userDrug", userDrug);
                            editor.commit();

                            //new PromptDialog(SignUpDoneActivity.this)
                            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                    .setAnimationEnable(true)
                                    .setTitleText("성공")
                                    .setContentText("회원 등록에 성공했습니다.")
                                    .setPositiveListener("시작하기", new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                            finish(); // 회원가입 성공후 창 닫음.
                                        }
                                    }).show();

                            Log.e(TAG, "onResponse: 두번째 회원등록 성공 "  );
                        } else {

                            new PromptDialog(SignUpDoneActivity.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                                    .setAnimationEnable(true)
                                    .setTitleText("실패")
                                    .setContentText("회원 등록에 실패했습니다.")
                                    .setPositiveListener("응", new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            Log.e(TAG, "onResponse: 회원등록 실패  "  );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RegisterRequest registerRequest = new RegisterRequest(
                    userID, userPassword, userEmail, userName,
                    userGender, userPhone, userBirth, userAge, userHeight, userWeight,
                    userType, userOccurDate, userMax, userMin, userDanger, userDrug,
                    "1", responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignUpDoneActivity.this);
            queue.add(registerRequest);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (promptDialog != null) {
            promptDialog.dismiss();
            promptDialog = null;
        }
    }
}
