package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.eminayar.panter.PanterDialog
import com.shawnlin.numberpicker.NumberPicker
import com.shuhart.stepview.StepView

import java.util.ArrayList
import java.util.Locale

import butterknife.BindView
import butterknife.ButterKnife
import info.hoang8f.widget.FButton

/**
 * 당뇨 발병 년도 선택 뷰
 */
class SignUpActivity4 : AppCompatActivity() {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null
    @BindView(R.id.nextButton)
    internal var nextButton: FButton? = null
    @BindView(R.id.number_picker)
    internal var numberPicker: NumberPicker? = null

    internal var userSignUpInfo: ArrayList<String>
    internal var userOccurDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up4)
        ButterKnife.bind(this)

        userSignUpInfo = ArrayList()
        userSignUpInfo = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_3)

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


        // Set divider color
        numberPicker!!.dividerColor = ContextCompat.getColor(this, R.color.colorPrimary)
        numberPicker!!.setDividerColorResource(R.color.colorPrimary)
        // Set formatter
        numberPicker!!.setFormatter(getString(R.string.number_picker_formatter))
        numberPicker!!.setFormatter(R.string.number_picker_formatter)
        // Set selected text color
        numberPicker!!.selectedTextColor = ContextCompat.getColor(this, R.color.colorPrimary)
        numberPicker!!.setSelectedTextColorResource(R.color.colorPrimary)
        // Set selected text size
        numberPicker!!.selectedTextSize = resources.getDimension(R.dimen.selected_text_size)
        numberPicker!!.setSelectedTextSize(R.dimen.selected_text_size)
        // Set text color
        numberPicker!!.textColor = ContextCompat.getColor(this, R.color.dark_grey)
        numberPicker!!.setTextColorResource(R.color.dark_grey)
        // Set text size
        numberPicker!!.textSize = resources.getDimension(R.dimen.text_size)
        numberPicker!!.setTextSize(R.dimen.text_size)
        // Set typeface
        numberPicker!!.typeface = Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL)
        numberPicker!!.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL)
        numberPicker!!.setTypeface(getString(R.string.roboto_light))
        numberPicker!!.setTypeface(R.string.roboto_light, Typeface.NORMAL)
        numberPicker!!.setTypeface(R.string.roboto_light)
        //Set value
        numberPicker!!.maxValue = 2040
        numberPicker!!.minValue = 1980
        numberPicker!!.value = 2018
        // Set fading edge enabled
        numberPicker!!.isFadingEdgeEnabled = true
        // Set scroller enabled
        numberPicker!!.isScrollerEnabled = true
        // Set wrap selector wheel
        numberPicker!!.wrapSelectorWheel = true

        // OnClickListener
        numberPicker!!.setOnClickListener { Log.d(TAG, "Click on current value") }

        // OnValueChangeListener
        numberPicker!!.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal))
            userOccurDate = newVal.toString()
        }

        nextButton!!.setOnClickListener {
            userSignUpInfo.add(userOccurDate)
            val intent = Intent(applicationContext, SignUpActivity5::class.java)
            intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_4, userSignUpInfo)
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

    companion object {

        private val TAG = "SignUpActivity4"
    }
}
