package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.UIViews.RangeSliderWithNumber
import com.dreamwalker.knu2018.dteacher.UIViews.SeekBarWithNumber
import com.eminayar.panter.PanterDialog
import com.shuhart.stepview.StepView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import info.hoang8f.widget.FButton

/**
 * 당뇨 목표 구간 선택 뷰
 */

class SignUpActivity5 : AppCompatActivity() {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null
    @BindView(R.id.nextButton)
    internal var nextButton: FButton? = null

    @BindView(R.id.rsn_bubble)
    internal var aimRangeNumber: RangeSliderWithNumber? = null
    //당 위험 수치 뷰
    @BindView(R.id.sbn)
    internal var dangerSeekBar: SeekBarWithNumber? = null

    internal var userSignUpInfo: ArrayList<String>
    internal var userMax: String
    internal var userMin: String
    internal var userDanger: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up5)

        title = "Sign Up"

        ButterKnife.bind(this)

        userSignUpInfo = ArrayList()
        userSignUpInfo = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_4)

        userMax = "200" //default
        userMin = "50" // default
        userDanger = "50" // default

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

        // TODO: 2018-01-31 목표 당뇨수치 값 변화 리스너
        aimRangeNumber!!.rangeSliderListener = object : RangeSliderWithNumber.RangeSliderListener {
            override fun onMaxChanged(newValue: Int) {
                userMax = newValue.toString()
            }

            override fun onMinChanged(newValue: Int) {
                userMin = newValue.toString()
            }
        }
        // TODO: 2018-01-31 위험 당수치 변화 리스너
        dangerSeekBar!!.setDefaultSelected(50)
        dangerSeekBar!!.rangeSliderListener = SeekBarWithNumber.NumberChangeListener { newValue -> userDanger = newValue.toString() }

        nextButton!!.setOnClickListener {
            userSignUpInfo.add(userMax)
            userSignUpInfo.add(userMin)
            userSignUpInfo.add(userDanger)
            val intent = Intent(applicationContext, SignUpActivity6::class.java)
            intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_5, userSignUpInfo)
            startActivity(intent)
            finish()
        }
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
