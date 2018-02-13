package com.dreamwalker.knu2018.dteacher.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dreamwalker.knu2018.dteacher.Adapter.StepSampleAdapter;
import com.dreamwalker.knu2018.dteacher.Adapter.WriteFitnessAdapter;
import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper;
import com.dreamwalker.knu2018.dteacher.DBHelper.FitnessDBHelper;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteFitTimeFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteFitnessTypeFragment;
import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity0;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Calendar;

import cn.refactor.lib.colordialog.PromptDialog;

public class WriteFitnessActivity extends AppCompatActivity implements
        StepperLayout.StepperListener, WriteFitnessTypeFragment.OnFitnessTypeListener, WriteFitTimeFragment.OnFitnessDateTimeSetListener {

    private static final String TAG = "WriteFitnessActivity";
    private StepperLayout mStepperLayout;

    private FitnessDBHelper fitnessDBHelper;
    private SQLiteDatabase db;
    String dbName = "fitness.db";
    int dbVersion = 1; // 데이터베이스 버전

    String FIT_TYPE, FIT_STRENGTH, FIT_TIME;
    String DATE_VALUE, TIME_VALUE;
    float met;
    int weight;
    int kcal;
    int fitnessTime;
    float temp_kcal;
    String parseKcal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_fitness);

        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        int tempMonth = now.get(Calendar.MONTH);
        tempMonth = tempMonth + 1;
        String month = String.valueOf(tempMonth);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));

        DATE_VALUE = year + "-" + month + "-" + day;
        TIME_VALUE = hour + ":" + minutes;

        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new WriteFitnessAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);

        fitnessDBHelper = new FitnessDBHelper(this, dbName, null, dbVersion);
    }


    @Override
    public void onCompleted(View completeButton) {
        new PromptDialog(WriteFitnessActivity.this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("알림")
                .setContentText("소모칼로리는" + parseKcal + "입니다.")
                .setPositiveListener("기록하기", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        if (!FIT_TYPE.equals("") || !FIT_STRENGTH.equals("") || !FIT_TIME.equals("")) {
                            fitnessDBHelper.insertFitnessData(FIT_TYPE, FIT_STRENGTH, FIT_TIME, parseKcal, DATE_VALUE, TIME_VALUE);
                        }else {
                            FIT_TYPE = "UNKNOWN";
                            FIT_STRENGTH = "UNKNOWN";
                            FIT_TIME = "UNKNOWN";
                            parseKcal = "UNKNOWN";
                            fitnessDBHelper.insertFitnessData(FIT_TYPE, FIT_STRENGTH, FIT_TIME, parseKcal, DATE_VALUE, TIME_VALUE);
                        }
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }

    @Override
    public void onFitnessTypeListener(String data) {

    }

    @Override
    public void onFitnessDateTimeSet(String fitType, String fitStrength, float mets, String fitTime, String date, String time) {
        Log.e(TAG, "onFitnessDateTimeSet: 받은 데이터 " + fitType + "," +
                fitStrength + ", " + mets + "," + fitTime + ", " + date + time);

        FIT_TYPE = fitType;
        FIT_STRENGTH = fitStrength;
        FIT_TIME = fitTime;
        DATE_VALUE = date;
        TIME_VALUE = time;

        met = mets;
        weight = 60;
        fitnessTime = Integer.parseInt(fitTime);
        Log.e(TAG, "onFitnessDateTimeSet:Integer.parseInt(fitTime); " + fitnessTime);
        // TODO: 2018-02-08 소모 칼로리 계산
        temp_kcal = met * weight * fitnessTime;
        Log.e(TAG, "onFitnessDateTimeSet:temp_kcal1 " + temp_kcal);
        temp_kcal = (float) (3.5 * temp_kcal);
        Log.e(TAG, "onFitnessDateTimeSet:temp_kcal2 " + temp_kcal);
        temp_kcal = (float) (temp_kcal * (0.005));
        Log.e(TAG, "onFitnessDateTimeSet:temp_kcal3 " + temp_kcal);
        kcal = Math.round(temp_kcal);
        //long kcal2 = Math.round(temp_kcal);
        parseKcal = String.valueOf(kcal);
        Log.e(TAG, "onFitnessDateTimeSet: " + kcal);

    }
}
