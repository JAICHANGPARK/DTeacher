package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.eminayar.panter.PanterDialog
import com.shuhart.stepview.StepView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.xw.repo.BubbleSeekBar

import java.util.ArrayList
import java.util.Calendar
import java.util.regex.Matcher
import java.util.regex.Pattern

import butterknife.BindView
import butterknife.ButterKnife
import cn.refactor.lib.colordialog.PromptDialog
import info.hoang8f.widget.FButton
import io.ghyeok.stickyswitch.widget.StickySwitch


// TODO: 2018-01-31 기본 정보 등록 화면

/**
 *
 * 전 엑티비티에서 받아오는 데이터
 * 1. 사용자 아이디
 * 2. 사용자 비밀번호
 * 3. 사용자 이메일
 *
 * 처리되는 데이터
 * 1. 사용자 이름
 * 2. 성별 ( default : male)
 * 3. 전화번호
 * 4. 생년월일
 * 5. 나이
 *
 */
class SignUpActivity1 : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null

    @BindView(R.id.nextButton)
    internal var nextButton: FButton? = null
    @BindView(R.id.dateButton)
    internal var dateButton: FButton? = null

    @BindView(R.id.userName)
    internal var userNameEdt: EditText? = null
    @BindView(R.id.userBirth)
    internal var userBirthEdt: EditText? = null
    @BindView(R.id.userPhone)
    internal var userPhoneEdt: EditText? = null

    @BindView(R.id.sticky_switch)
    internal var stickySwitch: StickySwitch? = null
    @BindView(R.id.bubbleSeekBar)
    internal var bubbleSeekBar: BubbleSeekBar? = null

    private val dialog: AlertDialog? = null
    internal var datePickerDialog: DatePickerDialog
    internal var userNameString: String
    internal var userPhoneString: String
    internal var userBirthString: String
    internal var userAgeString: String
    internal var userGenderString: String

    internal var userSignUpInfo: ArrayList<String>  //유저 필수 정보를 받는 리스트 자료구조


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Sign Up"
        // TODO: 2018-01-30 화면 회전 방지
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_sign_up1)
        ButterKnife.bind(this)

        // TODO: 2018-02-03 SignUpActivity0 에서 보내온 데이터를 받아야한다.
        userSignUpInfo = ArrayList()
        userSignUpInfo = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_0)

        userAgeString = "male" // null 값 되는것 처리

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

        stickySwitch!!.onSelectedChangeListener = object : StickySwitch.OnSelectedChangeListener {
            override fun onSelectedChange(direction: StickySwitch.Direction, s: String) {
                userGenderString = s
                //Log.d(TAG, "Now Selected : " + direction.name() + ", Current Text : " + s);
            }
        }

        bubbleSeekBar!!.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar, progress: Int, progressFloat: Float) {
                //Log.e(TAG, "onProgressChanged : " + progress);
                //Toast.makeText(SignUpActivity1.this, "onProgressChanged : " + progress, Toast.LENGTH_SHORT).show();
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar, progress: Int, progressFloat: Float) {
                //Log.e(TAG, "getProgressOnActionUp : " + progress);
                userAgeString = progress.toString()
                //Toast.makeText(SignUpActivity1.this, "getProgressOnActionUp : " + progress, Toast.LENGTH_SHORT).show();
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar, progress: Int, progressFloat: Float) {
                //Log.e(TAG, "getProgressOnFinally : " + progress);
                //Toast.makeText(SignUpActivity1.this, "getProgressOnFinally : " + progress, Toast.LENGTH_SHORT).show();
            }
        }

        nextButton!!.setOnClickListener(View.OnClickListener {
            userNameString = userNameEdt!!.text.toString()
            userPhoneString = userPhoneEdt!!.text.toString()
            userBirthString = userBirthEdt!!.text.toString()

            if (userNameString == "") {
                userNameString = "null"
            }
            if (userPhoneString == "") {
                userPhoneString = "null"
            } else {
                if (!checkPhoneNumber(userPhoneString)) {
                    PromptDialog(this@SignUpActivity1)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("경고")
                            .setContentText("정확한 전화번호를 입력해주세요")
                            .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
                    //                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity1.this);
                    //                        dialog = builder.setMessage("정확한 전화번호를 입력해주세요").setNegativeButton("확인", null)
                    //                                .create();
                    //                        dialog.show();
                    return@OnClickListener
                }
            }

            if (userBirthString == "") {
                userBirthString = "null"
            }

            if (userAgeString == "") {
                userAgeString = "male"
            }

            userSignUpInfo.add(userNameString)
            userSignUpInfo.add(userGenderString)
            userSignUpInfo.add(userPhoneString)
            userSignUpInfo.add(userBirthString)
            userSignUpInfo.add(userAgeString)
            // TODO: 2018-02-01 입력 받은데이터를 다음 액티비티로 전달한다.
            val intent = Intent(applicationContext, SignUpActivity2::class.java)
            intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_1, userSignUpInfo)
            startActivity(intent)
            finish()
        })

        dateButton!!.setOnClickListener {
            val now = Calendar.getInstance()
            datePickerDialog = DatePickerDialog.newInstance(
                    this@SignUpActivity1,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.setTitle("Select date :)")
            datePickerDialog.show(fragmentManager, "DatePicker")
            datePickerDialog.version = DatePickerDialog.Version.VERSION_2
        }
        //        List<StepBean> stepsBeanList = new ArrayList<>();
        //        StepBean stepBean0 = new StepBean("接单", 1);
        //        StepBean stepBean1 = new StepBean("打包", 1);
        //        StepBean stepBean2 = new StepBean("出发", 1);
        //        StepBean stepBean3 = new StepBean("送单", 0);
        //        StepBean stepBean4 = new StepBean("完成", -1);
        //        stepsBeanList.add(stepBean0);
        //        stepsBeanList.add(stepBean1);
        //        stepsBeanList.add(stepBean2);
        //        stepsBeanList.add(stepBean3);
        //        stepsBeanList.add(stepBean4);
        //
        //
        //        setpview5
        //                .setStepViewTexts(stepsBeanList)//总步骤
        //                .setTextSize(12)//set textSize
        //                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getApplicationContext(), R.color.Accent3))//设置StepsViewIndicator完成线的颜色
        //                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getApplicationContext(), R.color.fbutton_color_sun_flower))//设置StepsViewIndicator未完成线的颜色
        //                .setStepViewComplectedTextColor(ContextCompat.getColor(getApplicationContext(), R.color.Accent3))//设置StepsView text完成线的颜色
        //                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getApplicationContext(), R.color.secondary_text))//设置StepsView text未完成线的颜色
        //                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
        //                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
        //                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val selectMonth = monthOfYear + 1

        val yearResult = year.toString()
        val monthResult = selectMonth.toString()
        val dayOfMonthResult = dayOfMonth.toString()

        userBirthEdt!!.setText("$yearResult-$monthResult-$dayOfMonthResult")
        Toast.makeText(this, "$yearResult-$monthResult-$dayOfMonthResult", Toast.LENGTH_SHORT).show()
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

        //super.onBackPressed(); // 이주석이 처리되면 다이얼로그 애러가 발생할것임
    }

    companion object {

        private val TAG = "SignUpActivity1"

        /**
         * 이메일 포맷 체크
         *
         * @param phoneNum
         * @return
         */
        fun checkPhoneNumber(phoneNum: String): Boolean {

            val regex = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$"
            val p = Pattern.compile(regex)
            val m = p.matcher(phoneNum)
            return m.matches()
        }
    }
}
