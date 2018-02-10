package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.UIViews.ScaleRulerView
import com.eminayar.panter.PanterDialog
import com.shuhart.stepview.StepView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import info.hoang8f.widget.FButton

/**
 *
 * 전 엑티비티에서 받아오는 데이터
 * 1. 사용자 아이디  : index 0
 * 2. 사용자 비밀번호 : index 1
 * 3. 사용자 이메일 : index 2
 * 4. 사용자 이름 : index 3
 * 5. 성별 ( default : male) : index 4
 * 6. 전화번호 : index 5
 * 7. 생년월일 : index 6
 * 8. 나이 : index 7
 *
 * 처리되는 데이터
 * 1. 사용자 신장(키) (default : 170)
 * 2. 사용자 몸무게   (default : 60)
 *
 */

class SignUpActivity2 : AppCompatActivity() {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null
    @BindView(R.id.nextButton)
    internal var nextButton: FButton? = null

    @BindView(R.id.scaleWheelView_height)
    internal var mHeightWheelView: ScaleRulerView? = null
    @BindView(R.id.tv_user_height_value)
    internal var mHeightValue: TextView? = null

    @BindView(R.id.scaleWheelView_weight)
    internal var mWeightWheelView: ScaleRulerView? = null
    @BindView(R.id.tv_user_weight_value)
    internal var mWeightValue: TextView? = null

    private var mHeight = 170f
    private val mMaxHeight = 220f
    private val mMinHeight = 100f

    private var mWeight = 60.0f
    private val mMaxWeight = 200f
    private val mMinWeight = 25f

    internal var userSignUpInfo: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)
        title = "Sign Up"

        ButterKnife.bind(this)

        userSignUpInfo = ArrayList()
        userSignUpInfo = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_1)

        val stringBuilder = StringBuilder()

        for (i in userSignUpInfo.indices) {
            stringBuilder.append(userSignUpInfo[i])
            stringBuilder.append(",")
            Log.e(TAG, "onCreate: " + userSignUpInfo[i])
        }

        //Toast.makeText(this, "전달받은 데이터" + stringBuilder, Toast.LENGTH_SHORT).show();

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

        setpview!!.go(1, false)

        ruler_init()

        nextButton!!.setOnClickListener {
            val stringHeight = mHeight.toString()
            val stringWeight = mWeight.toString()
            userSignUpInfo.add(stringHeight)
            userSignUpInfo.add(stringWeight)

            //Toast.makeText(SignUpActivity2.this, "选择身高： " + mHeight + " 体重： " + mWeight, Toast.LENGTH_LONG).show();

            val intent = Intent(applicationContext, SignUpActivity3::class.java)
            intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_2, userSignUpInfo)
            startActivity(intent)
            finish()
        }
    }

    private fun ruler_init() {

        mHeightValue!!.text = mHeight.toInt().toString() + ""
        mWeightValue!!.text = mWeight.toString() + ""

        mHeightWheelView!!.initViewParam(mHeight, mMaxHeight, mMinHeight)
        mHeightWheelView!!.setValueChangeListener { value ->
            mHeightValue!!.text = value.toInt().toString() + ""
            mHeight = value
        }

        mWeightWheelView!!.initViewParam(mWeight, mMaxWeight, mMinWeight)
        mWeightWheelView!!.setValueChangeListener { value ->
            mWeightValue!!.text = value.toString() + ""
            mWeight = value
        }
    }

    override fun onPause() {

        for (i in 3 until userSignUpInfo.size) {
            userSignUpInfo.removeAt(i)
        }

        super.onPause()

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

    companion object {
        private val TAG = "SignUpActivity2"
    }
}
