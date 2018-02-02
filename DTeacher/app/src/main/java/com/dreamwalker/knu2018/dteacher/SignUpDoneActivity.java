package com.dreamwalker.knu2018.dteacher;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.Utils.RegisterRequest;
import com.eminayar.panter.PanterDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;

import static com.dreamwalker.knu2018.dteacher.Const.IntentConst.SIGNUP_EXTRA_DATA_0;

public class SignUpDoneActivity extends AppCompatActivity {

    private static final String TAG = "SignUpDoneActivity";
    PromptDialog promptDialog;

    String userID, userPassword, userEmail;
    String userName, userGender, userPhone, userBirth, userAge, userHeight, userWeight;
    String userType, userOccurDate, userMax, userMin, userDanger, userDrug;
    ArrayList<String> userRegiDataList;
    int registerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_done);

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

//                            new PanterDialog(SignUpDoneActivity.this)
//                                    .setHeaderBackground(R.drawable.pattern_bg_yellow)
//                                    .setTitle("성공")
//                                    .setPositive("계속할래요", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            finish();
//                                        }
//                                    }) // You can pass also View.OnClickListener as second param
//                                    .setMessage("회원가입에 성공했습니다. 환영합니다")
//                                    .isCancelable(false)
//                                    .show();
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

        } else if (registerType == 1) {
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
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (promptDialog != null){
            promptDialog.dismiss();
            promptDialog = null;
        }
    }
}
