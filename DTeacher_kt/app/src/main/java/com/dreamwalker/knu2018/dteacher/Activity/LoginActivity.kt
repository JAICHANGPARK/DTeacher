package com.dreamwalker.knu2018.dteacher.Activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity0
import com.dreamwalker.knu2018.dteacher.Utils.LoginRequest

import org.json.JSONException
import org.json.JSONObject

import butterknife.BindView
import butterknife.ButterKnife
import cn.refactor.lib.colordialog.PromptDialog
import info.hoang8f.widget.FButton

class LoginActivity : AppCompatActivity() {

    @BindView(R.id.nextButton)
    internal var skipText: TextView? = null
    @BindView(R.id.signUpText)
    internal var signUpText: TextView? = null
    @BindView(R.id.loginButton)
    internal var signInButton: FButton? = null

    @BindView(R.id.userName)
    internal var userIDEdt: EditText? = null

    @BindView(R.id.password)
    internal var userPasswordEdt: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_login)

        ButterKnife.bind(this)

        val lottieAnimationView = findViewById<View>(R.id.animation_view2) as LottieAnimationView
        lottieAnimationView.imageAssetsFolder = "images/"
        lottieAnimationView.setAnimation("bus.json")
        lottieAnimationView.loop(true)
        lottieAnimationView.playAnimation()

        skipText!!.setOnClickListener {
            // TODO: 2018-02-02 스킵을 누르면 바로 홈으로 갈지 인트로 화면으로 갈지 결정해야.
            val intent = Intent(applicationContext, IntroActivity::class.java)
            startActivity(intent)
            finish()
            //                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            //                startActivity(intent);
            //                finish();
        }

        signInButton!!.setOnClickListener {
            // TODO: 2018-01-30 로그인 처리 하기

            val userID = userIDEdt!!.text.toString() // EditText에서 값을 받아옴.
            val userPassword = userPasswordEdt!!.text.toString()

            if (userID == "" || userPassword == "") {
                PromptDialog(this@LoginActivity)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                        .setAnimationEnable(true)
                        .setTitleText("경고")
                        .setContentText("등록하신 아이디와 비밀번호를 입력해주세요.")
                        .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
            }

            Log.e(TAG, "userID: " + userID)
            Log.e(TAG, "userPassword: " + userPassword)

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")

                    Log.e(TAG, "jsonResult: " + jsonResponse)
                    Log.e(TAG, "success: " + success)

                    if (success) {
                        //                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        //                                dialog = builder.setMessage("로그인에 성공했습니다.")
                        //                                        .setPositiveButton("확인", null)
                        //                                        .create();
                        //                                dialog.show();

                        PromptDialog(this@LoginActivity)
                                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                .setAnimationEnable(true)
                                .setTitleText("성공")
                                .setContentText("로그인에 성공했습니다")
                                .setPositiveListener("시작하기") { dialog -> dialog.dismiss() }.show()

                        val intent = Intent(this@LoginActivity, IntroActivity::class.java)
                        this@LoginActivity.startActivity(intent)
                        finish()
                    } else {
                        //                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        //                                dialog = builder.setMessage("로그인에 실패했습니다. 계정을 확인하세요")
                        //                                        .setNegativeButton("확인", null)
                        //                                        .create();
                        //                                dialog.show();
                        PromptDialog(this@LoginActivity)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("실패")
                                .setContentText("로그인에 실패했습니다. 계정을 확인하세요")
                                .setPositiveListener("확인") { dialog -> dialog.dismiss() }.show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            val loginRequest = LoginRequest(userID, userPassword, responseListener)
            val queue = Volley.newRequestQueue(this@LoginActivity)
            queue.add(loginRequest)

            //                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            //                startActivity(intent);
            //                finish();
        }

        signUpText!!.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity0::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private val TAG = "LoginActivity"
    }
}