package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.ValidateRequest
import com.eminayar.panter.PanterDialog
import com.eminayar.panter.enums.Animation
import com.shuhart.stepview.StepView

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import cn.refactor.lib.colordialog.PromptDialog
import info.hoang8f.widget.FButton

class SignUpActivity0 : AppCompatActivity() {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null

    @BindView(R.id.vaildateButton)
    internal var vaildateButton: FButton? = null
    @BindView(R.id.registerButton)
    internal var registerButton: FButton? = null

    @BindView(R.id.userName)
    internal var userName: EditText? = null
    @BindView(R.id.password)
    internal var password: EditText? = null
    @BindView(R.id.email)
    internal var email: EditText? = null


    //ColorDialog colorDialog;
    private var validate = false //아이디 중복검사 변수
    internal var userID: String
    internal var userPassword: String
    internal var userEmail: String

    internal var userInfo: ArrayList<String>

    internal var panterDialog: PanterDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Sign Up"
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_sign_up0)

        ButterKnife.bind(this)

        panterDialog = PanterDialog(this@SignUpActivity0)

        // colorDialog = new ColorDialog(this);

        userInfo = ArrayList()

        setpview!!.state
                .selectedTextColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.fbutton_color_sun_flower))
                .selectedCircleRadius(resources.getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(object : ArrayList<String>() {
                    init {
                        add("필수 정보")
                        add("기본 정보")
                        add("당뇨 정보")
                        add("투약 정보")
                    }
                })
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(resources.getDimensionPixelSize(R.dimen.dp1))
                .textSize(resources.getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(resources.getDimensionPixelSize(R.dimen.sp16))
                // other state methods are equal to the corresponding xml attributes
                .commit()

        setpview!!.go(0, false)

        val lottieAnimationView = findViewById<View>(R.id.animation_view) as LottieAnimationView
        lottieAnimationView.setAnimation("loading_animation.json")
        lottieAnimationView.loop(true)
        lottieAnimationView.playAnimation()

        registerButton!!.setOnClickListener(View.OnClickListener {
            userID = userName!!.text.toString()
            userPassword = password!!.text.toString()
            userEmail = email!!.text.toString()

            if (!validate) {
                PromptDialog(this@SignUpActivity0)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("알림")
                        .setContentText("먼저 중복체크를 해주세요.")
                        .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
                //                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity0.this);
                //                    dialog = builder.setMessage("먼저 중복체크를 해주세요.").setNegativeButton("확인", null)
                //                            .create();
                //                    dialog.show();
                return@OnClickListener
            }

            if (userID == "" || userPassword == "" || userEmail == "") {

                PromptDialog(this@SignUpActivity0)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("경고")
                        .setContentText("필수 정보는 빈 칸 없이 입력해주세요.")
                        .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
                //                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity0.this);
                //                    dialog = builder.setMessage("필수 정보는 빈 칸 없이 입력해주세요.").setNegativeButton("확인", null)
                //                            .create();
                //                    dialog.show();
                return@OnClickListener
            }

            if (userPassword.length < 8) {
                PromptDialog(this@SignUpActivity0)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("경고")
                        .setContentText("보안을 높이기 위해 8자리 이상의 비밀번호로 구성해주세요")
                        .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
                //                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity0.this);
                //                    dialog = builder.setMessage("보안을 높이기 위해 8자리 이상의 비밀번호로 구성해주세요").setNegativeButton("확인", null)
                //                            .create();
                //                    dialog.show();
                return@OnClickListener
            }
            if (!checkEmail(userEmail)) {

                PromptDialog(this@SignUpActivity0)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("경고")
                        .setContentText("정확한 이메일을 입력해주세요")
                        .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
                return@OnClickListener
            }

            //new PanterDialog(SignUpActivity0.this)
            panterDialog!!.setHeaderBackground(R.drawable.pattern_bg_blue)
                    .setTitle("알림")
                    .setPositive("좋아요") {
                        userInfo.add(userID)
                        userInfo.add(userPassword)
                        userInfo.add(userEmail)
                        val intent = Intent(applicationContext, SignUpActivity1::class.java)
                        intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0, userInfo)
                        startActivity(intent)
                        finish()
                    } // You can pass also View.OnClickListener as second param
                    .setNegative("괜찮아요.이정도면 충분해요") {
                        userInfo.add(userID)
                        userInfo.add(userPassword)
                        userInfo.add(userEmail)
                        val intent = Intent(applicationContext, SignUpDoneActivity::class.java)
                        intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0, userInfo)
                        intent.putExtra(IntentConst.SIGNUP_REGISTER_TYPE, 0)
                        startActivity(intent)
                        finish()
                    }
                    .setMessage("조금더 많은 정보를 입력해주시면 당뇨 관리 및 데이터 제공에 도움이 되요. 좀 더 입력하시겠어요?")
                    .isCancelable(false)
                    .show()


            // TODO: 2018-02-01 입력 받은데이터를 다음 액티비티로 전달한다.
            //                Intent intent = new Intent(getApplicationContext(), SignUpActivity1.class);
            //                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0, userInfo);
            //                startActivity(intent);
            //                finish();
        })
    }

    override fun onBackPressed() {
        PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_orange)
                .setTitle("알림")
                .setPositive("계속할래요") // You can pass also View.OnClickListener as second param
                .setNegative("그만둘래요") {
                    // panterDialog.dismiss();
                    finish()
                }
                .withAnimation(Animation.POP)
                .setMessage("보나 나은 서비스 이용을 위해서 회원가입이 필요한데 그래도 그만두시겠어요?")
                .isCancelable(false)
                .show()
    }

    @OnClick(R.id.vaildateButton)
    fun onValidateButton(view: View) {

        val userID = userName!!.text.toString()

        if (validate) {
            return
        }
        if (userID == "") {
            PromptDialog(this@SignUpActivity0)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("경고")
                    .setContentText("아이디는 빈 칸일 수 없습니다.")
                    .setPositiveListener("알겠어") { dialog -> dialog.dismiss() }.show()
            return
        }

        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")

                Log.e(TAG, "jsonResult: " + jsonObject)
                Log.e(TAG, "sucesse: " + success)

                if (success) {

                    PromptDialog(this@SignUpActivity0)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("알림")
                            .setContentText("사용할 수 있는 아이디입니다.")
                            .setPositiveListener("알겠어") { dialog -> dialog.dismiss() }.show()

                    validate = true
                    userName!!.isEnabled = false
                    vaildateButton!!.isEnabled = false
                    vaildateButton!!.setBackgroundColor(resources.getColor(R.color.fbutton_color_emerald))

                } else {
                    // TODO: 2017-10-12 중복 체크에 실패 했다면.
                    PromptDialog(this@SignUpActivity0)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("사용할 수 없는 아이디입니다.")
                            .setPositiveListener("알겠어") { dialog -> dialog.dismiss() }.show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val validateRequest = ValidateRequest(userID, responseListener)
        val queue = Volley.newRequestQueue(this@SignUpActivity0)
        queue.add(validateRequest)

    }


    override fun onStop() {
        super.onStop()
        if (panterDialog != null) {
            panterDialog!!.dismiss()
            panterDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private val TAG = "SignUpActivity0"

        /**
         * 이메일 포맷 체크
         *
         * @param email
         * @return
         */
        fun checkEmail(email: String): Boolean {

            val regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
            val p = Pattern.compile(regex)
            val m = p.matcher(email)
            return m.matches()
        }
    }
}
