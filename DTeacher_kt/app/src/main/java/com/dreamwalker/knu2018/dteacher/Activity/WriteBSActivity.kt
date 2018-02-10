package com.dreamwalker.knu2018.dteacher.Activity

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.badoualy.stepperindicator.StepperIndicator
import com.dreamwalker.knu2018.dteacher.Adapter.DiarySectionsPagerAdapter
import com.dreamwalker.knu2018.dteacher.Adapter.StepSampleAdapter
import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDangFragment
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFitnessFragment
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFoodFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSTypeFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSValueFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteDateTimeFragment
import com.dreamwalker.knu2018.dteacher.R
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

import java.util.Calendar

import io.paperdb.Paper

class WriteBSActivity : AppCompatActivity(), StepperLayout.StepperListener, WriteBSTypeFragment.OnTimePickerSetListener, WriteBSValueFragment.OnBSValueSetListener, WriteDateTimeFragment.OnBSDateTimeSetListener {
    private var mStepperLayout: StepperLayout? = null

    // TODO: 2018-02-07 Page에서 넘겨온 값을 처리하는 변수이다.
    internal var pageTypeValue: String
    internal var pageBSValue: String
    internal var pageDateValue: String
    internal var pageTimeValue: String

    private var bsdbHelper: BSDBHelper? = null
    private var db: SQLiteDatabase? = null
    internal var dbName = "bs.db"
    internal var dbVersion = 1 // 데이터베이스 버전

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_bs)
        Paper.init(this)

        mStepperLayout = findViewById<View>(R.id.stepperLayout) as StepperLayout
        mStepperLayout!!.adapter = StepSampleAdapter(supportFragmentManager, this)
        mStepperLayout!!.setListener(this)

        // TODO: 2018-02-07 시스템의 현재 시간을 가져오는 코드  달의 경우 +1 해줘야한다.
        val now = Calendar.getInstance()
        val year = now.get(Calendar.YEAR).toString()
        var tempMonth = now.get(Calendar.MONTH)
        tempMonth = tempMonth + 1
        val month = tempMonth.toString()
        val day = now.get(Calendar.DAY_OF_MONTH).toString()
        val hour = now.get(Calendar.HOUR).toString()
        val minutes = now.get(Calendar.MINUTE).toString()

        // TODO: 2018-02-07 각 기록 값 default 입력
        pageTypeValue = "UNKNOWN"
        pageBSValue = "80"
        pageDateValue = "$year-$month-$day"
        pageTimeValue = hour + ":" + minutes

        bsdbHelper = BSDBHelper(this, dbName, null, dbVersion)
        try {
            //         // 데이터베이스 객체를 얻어오는 다른 간단한 방법
            //         db = openOrCreateDatabase(dbName,  // 데이터베이스파일 이름
            //                          Context.MODE_PRIVATE, // 파일 모드
            //                          null);    // 커서 팩토리
            //
            //         String sql = "create table mytable(id integer primary key autoincrement, name text);";
            //        db.execSQL(sql);
            db = bsdbHelper!!.writableDatabase // 읽고 쓸수 있는 DB
            //db = helper.getReadableDatabase(); // 읽기 전용 DB select문
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Log.e(TAG, "데이터베이스를 얻어올 수 없음")
            finish() // 액티비티 종료
        }

    }

    override fun onCompleted(completeButton: View) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show()
        bsdbHelper!!.insertBSData(pageTypeValue, pageBSValue, pageDateValue, pageTimeValue)
        Log.e(TAG, "onCompleted: " + "DB 입력 완료 ")
        finish()
    }

    override fun onError(verificationError: VerificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStepSelected(newStepPosition: Int) {
        //Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    override fun onReturn() {
        finish()
    }

    /**
     * 이름은 이런데 당뇨 유형을 가져오는 메소드
     * 시험ㅇ르 하다보디 이렇게 되었다 시간이 되면 수정하세요 이름을
     *
     * @param value
     */
    override fun onTimePickerSet(value: String) {
        pageTypeValue = value
        Toast.makeText(this, " result" + pageTypeValue, Toast.LENGTH_SHORT).show()
    }

    override fun onBsValueSet(value: String) {
        pageBSValue = value
        Toast.makeText(this, " result$pageTypeValue, $pageBSValue", Toast.LENGTH_SHORT).show()
    }

    override fun onBsDateTimeSet(date: String, time: String) {
        pageDateValue = date
        pageTimeValue = time
        Toast.makeText(this, "" + date + time, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val TAG = "WriteBSActivity"
    }

}
