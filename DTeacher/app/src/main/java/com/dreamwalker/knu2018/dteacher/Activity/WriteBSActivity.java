package com.dreamwalker.knu2018.dteacher.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.dreamwalker.knu2018.dteacher.Adapter.DiarySectionsPagerAdapter;
import com.dreamwalker.knu2018.dteacher.Adapter.StepSampleAdapter;
import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDangFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFitnessFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.DiaryFoodFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSTypeFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSValueFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteDateTimeFragment;
import com.dreamwalker.knu2018.dteacher.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Calendar;

import io.paperdb.Paper;

public class WriteBSActivity extends AppCompatActivity implements
        StepperLayout.StepperListener,
        WriteBSTypeFragment.OnTimePickerSetListener,
        WriteBSValueFragment.OnBSValueSetListener,
        WriteDateTimeFragment.OnBSDateTimeSetListener {

    private static final String TAG = "WriteBSActivity";
    private StepperLayout mStepperLayout;

    // TODO: 2018-02-07 Page에서 넘겨온 값을 처리하는 변수이다.
    String pageTypeValue;
    String pageBSValue;
    String pageDateValue;
    String pageTimeValue;

    private BSDBHelper bsdbHelper;
    private SQLiteDatabase db;
    String dbName = "bs.db";
    int dbVersion = 1; // 데이터베이스 버전

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_bs);
        Paper.init(this);

        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepSampleAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);

        // TODO: 2018-02-07 시스템의 현재 시간을 가져오는 코드  달의 경우 +1 해줘야한다.
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        int tempMonth = now.get(Calendar.MONTH);
        tempMonth = tempMonth + 1;
        // TODO: 2018-02-28 월 표기 처리하는 곳.
        String month;
        String dayOfMonth;
        if (tempMonth < 10) {
            month = "0" + String.valueOf(tempMonth);
        } else {
            month = String.valueOf(tempMonth);
        }
        int iDayofMonth = now.get(Calendar.DAY_OF_MONTH);
        if (iDayofMonth < 10) {
            dayOfMonth = "0" + String.valueOf(iDayofMonth);
        } else {
            dayOfMonth = String.valueOf(iDayofMonth);
        }
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));

        // TODO: 2018-02-07 각 기록 값 default 입력
        pageTypeValue = "UNKNOWN";
        pageBSValue = "80";
        //pageDateValue = year + "-" + month + "-" + day;
        pageDateValue = year + "-" + month + "-" + dayOfMonth;
        pageTimeValue = hour + ":" + minutes;

        bsdbHelper = new BSDBHelper(this, dbName, null, dbVersion);
        try {
//         // 데이터베이스 객체를 얻어오는 다른 간단한 방법
//         db = openOrCreateDatabase(dbName,  // 데이터베이스파일 이름
//                          Context.MODE_PRIVATE, // 파일 모드
//                          null);    // 커서 팩토리
//
//         String sql = "create table mytable(id integer primary key autoincrement, name text);";
//        db.execSQL(sql);
            db = bsdbHelper.getWritableDatabase(); // 읽고 쓸수 있는 DB
            //db = helper.getReadableDatabase(); // 읽기 전용 DB select문
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(TAG, "데이터베이스를 얻어올 수 없음");
            finish(); // 액티비티 종료
        }
    }

    /**
     * 저장 버튼을 눌렀을때 발생하는 콜백 리스너
     *
     * @param completeButton
     * @Author JAICHANGPARK
     */

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
        bsdbHelper.insertBSData(pageTypeValue, pageBSValue, pageDateValue, pageTimeValue);
        Log.e(TAG, "onCompleted: " + "DB 입력 완료 ");
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        //Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    /**
     * 이름은 이런데 당뇨 유형을 가져오는 메소드
     * 시험ㅇ르 하다보디 이렇게 되었다 시간이 되면 수정하세요 이름을
     *
     * @param value
     */
    @Override
    public void onTimePickerSet(String value) {
        pageTypeValue = value;
        Toast.makeText(this, " result" + pageTypeValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBsValueSet(String value) {
        pageBSValue = value;
        Toast.makeText(this, " result" + pageTypeValue + ", " + pageBSValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBsDateTimeSet(String date, String time) {
        pageDateValue = date;
        pageTimeValue = time;
        Toast.makeText(this, "" + date + time, Toast.LENGTH_SHORT).show();
    }

}
