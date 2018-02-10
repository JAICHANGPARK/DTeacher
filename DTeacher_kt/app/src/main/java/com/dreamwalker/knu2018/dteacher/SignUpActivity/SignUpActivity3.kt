package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.eminayar.panter.PanterDialog
import com.shuhart.stepview.StepView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import cn.refactor.lib.colordialog.PromptDialog
import co.ceryle.radiorealbutton.RadioRealButton
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import info.hoang8f.widget.FButton

/**
 * 당뇨 유형 선택 뷰
 */
class SignUpActivity3 : AppCompatActivity() {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null
    @BindView(R.id.nextButton)
    internal var nextButton: FButton? = null

    @BindView(R.id.radioRealButtonGroup)
    internal var diabetesTypeGroup: RadioRealButtonGroup? = null

    //List<String> stepLabel;

    internal var panterDialog: PanterDialog? = null

    internal var userDiabetesType: String
    internal var userSignUpInfo: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)
        title = "Sign Up"

        ButterKnife.bind(this)

        userSignUpInfo = ArrayList()
        userSignUpInfo = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_2)

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

        setpview!!.go(2, false)

        diabetesTypeGroup!!.setOnPositionChangedListener { button, currentPosition, lastPosition ->
            userDiabetesType = currentPosition.toString()

            when (currentPosition) {
                0 -> Snackbar.make(window.decorView.rootView, "제 1형 당뇨병" + currentPosition, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                1 -> Snackbar.make(window.decorView.rootView, "제 2형 당뇨병" + currentPosition, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                2 -> Snackbar.make(window.decorView.rootView, "임신성 당뇨병" + currentPosition, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                3 -> Snackbar.make(window.decorView.rootView, "기타" + currentPosition, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
            }//Toast.makeText(SignUpActivity3.this, "제 1형 당뇨병", Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUpActivity3.this, "임신성 당뇨병", Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUpActivity3.this, "기타 당뇨병", Toast.LENGTH_SHORT).show();

            //Toast.makeText(SignUpActivity3.this, "Clicked! Position: " + currentPosition, Toast.LENGTH_SHORT).show();
        }

        diabetesTypeGroup!!.setOnLongClickedButtonListener { button, position ->
            when (position) {
                0 -> {
                }
                1 -> {
                }
                2 -> {
                }
                3 -> {
                }
            }//Toast.makeText(SignUpActivity3.this, "제 1형 당뇨병", Toast.LENGTH_SHORT).show();
            //                        new PanterDialog(getBaseContext())
            //                                .setHeaderBackground(R.drawable.pattern_bg_orange)
            //                                .setTitle("Sample Title")
            //                                .setPositive("I GOT IT")// You can pass also View.OnClickListener as second param
            //                                .setNegative("DISMISS")
            //                                .setMessage("제 2형 당뇨병에대한 설명 ")
            //                                .isCancelable(false)
            //                                .show();
            //Toast.makeText(SignUpActivity3.this, "제 2형 당뇨병", Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUpActivity3.this, "임신성 당뇨병", Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUpActivity3.this, "기타 당뇨병", Toast.LENGTH_SHORT).show();
            false
        }

        nextButton!!.setOnClickListener {
            if (userSignUpInfo == "") {
                PromptDialog(this@SignUpActivity3)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("경고")
                        .setContentText("당신의 당뇨 유형을 선택해주세요 ")
                        .setPositiveListener("알겠어") { dialog -> dialog.dismiss() }.show()
            }
            userSignUpInfo.add(userDiabetesType)
            val intent = Intent(applicationContext, SignUpActivity4::class.java)
            intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_3, userSignUpInfo)
            startActivity(intent)
            finish()
        }

    }

    override fun onDestroy() {

        if (panterDialog != null) {
            panterDialog!!.dismiss()
        }

        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onBackPressed() {
        PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_orange)
                .setTitle("알림")
                .setPositive("계속할래요") // You can pass also View.OnClickListener as second param
                .setNegative("그만둘래요") { finish() }
                .setMessage("보나 나은 서비스 이용을 위해서 회원가입이 필요한데 그래도 그만두시겠어요?")
                .isCancelable(false)
                .show()
    }
}
