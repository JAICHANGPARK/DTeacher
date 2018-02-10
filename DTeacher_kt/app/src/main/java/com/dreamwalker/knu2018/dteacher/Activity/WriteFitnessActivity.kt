package com.dreamwalker.knu2018.dteacher.Activity

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.dreamwalker.knu2018.dteacher.Adapter.StepSampleAdapter
import com.dreamwalker.knu2018.dteacher.Adapter.WriteFitnessAdapter
import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper
import com.dreamwalker.knu2018.dteacher.DBHelper.FitnessDBHelper
import com.dreamwalker.knu2018.dteacher.Fragment.WriteFitTimeFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteFitnessTypeFragment
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity0
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

import java.util.Calendar

import cn.refactor.lib.colordialog.PromptDialog

class WriteFitnessActivity : AppCompatActivity(), StepperLayout.StepperListener, WriteFitnessTypeFragment.OnFitnessTypeListener, WriteFitTimeFragment.OnFitnessDateTimeSetListener {
    private var mStepperLayout: StepperLayout? = null

    private var fitnessDBHelper: FitnessDBHelper? = null
    private val db: SQLiteDatabase? = null
    internal var dbName = "fitness.db"
    internal var dbVersion = 1 // 데이터베이스 버전

    internal var FIT_TYPE: String
    internal var FIT_STRENGTH: String
    internal var FIT_TIME: String
    internal var DATE_VALUE: String
    internal var TIME_VALUE: String
    internal var met: Float = 0.toFloat()
    internal var weight: Int = 0
    internal var kcal: Int = 0
    internal var fitnessTime: Int = 0
    internal var temp_kcal: Float = 0.toFloat()
    internal var parseKcal: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_fitness)

        val now = Calendar.getInstance()
        val year = now.get(Calendar.YEAR).toString()
        var tempMonth = now.get(Calendar.MONTH)
        tempMonth = tempMonth + 1
        val month = tempMonth.toString()
        val day = now.get(Calendar.DAY_OF_MONTH).toString()
        val hour = now.get(Calendar.HOUR).toString()
        val minutes = now.get(Calendar.MINUTE).toString()

        DATE_VALUE = "$year-$month-$day"
        TIME_VALUE = hour + ":" + minutes

        mStepperLayout = findViewById<View>(R.id.stepperLayout) as StepperLayout
        mStepperLayout!!.adapter = WriteFitnessAdapter(supportFragmentManager, this)
        mStepperLayout!!.setListener(this)

        fitnessDBHelper = FitnessDBHelper(this, dbName, null, dbVersion)
    }


    override fun onCompleted(completeButton: View) {
        PromptDialog(this@WriteFitnessActivity)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("알림")
                .setContentText("소모칼로리는" + parseKcal + "입니다.")
                .setPositiveListener("기록하기") { dialog ->
                    if (FIT_TYPE != "" || FIT_STRENGTH != "" || FIT_TIME != "") {
                        fitnessDBHelper!!.insertFitnessData(FIT_TYPE, FIT_STRENGTH, FIT_TIME, parseKcal, DATE_VALUE, TIME_VALUE)
                    } else {
                        FIT_TYPE = "UNKNOWN"
                        FIT_STRENGTH = "UNKNOWN"
                        FIT_TIME = "UNKNOWN"
                        parseKcal = "UNKNOWN"
                        fitnessDBHelper!!.insertFitnessData(FIT_TYPE, FIT_STRENGTH, FIT_TIME, parseKcal, DATE_VALUE, TIME_VALUE)
                    }
                    dialog.dismiss()
                    finish()
                }.show()
    }

    override fun onError(verificationError: VerificationError) {

    }

    override fun onStepSelected(newStepPosition: Int) {

    }

    override fun onReturn() {

    }

    override fun onFitnessTypeListener(data: String) {

    }

    override fun onFitnessDateTimeSet(fitType: String, fitStrength: String, mets: Float, fitTime: String, date: String, time: String) {
        Log.e(TAG, "onFitnessDateTimeSet: 받은 데이터 " + fitType + "," +
                fitStrength + ", " + mets + "," + fitTime + ", " + date + time)

        FIT_TYPE = fitType
        FIT_STRENGTH = fitStrength
        FIT_TIME = fitTime
        DATE_VALUE = date
        TIME_VALUE = time

        met = mets
        weight = 60
        fitnessTime = Integer.parseInt(fitTime)
        Log.e(TAG, "onFitnessDateTimeSet:Integer.parseInt(fitTime); " + fitnessTime)
        // TODO: 2018-02-08 소모 칼로리 계산
        temp_kcal = met * weight.toFloat() * fitnessTime.toFloat()
        Log.e(TAG, "onFitnessDateTimeSet:temp_kcal1 " + temp_kcal)
        temp_kcal = (3.5 * temp_kcal).toFloat()
        Log.e(TAG, "onFitnessDateTimeSet:temp_kcal2 " + temp_kcal)
        temp_kcal = (temp_kcal * 0.005).toFloat()
        Log.e(TAG, "onFitnessDateTimeSet:temp_kcal3 " + temp_kcal)
        kcal = Math.round(temp_kcal)
        //long kcal2 = Math.round(temp_kcal);
        parseKcal = kcal.toString()
        Log.e(TAG, "onFitnessDateTimeSet: " + kcal)

    }

    companion object {

        private val TAG = "WriteFitnessActivity"
    }
}
