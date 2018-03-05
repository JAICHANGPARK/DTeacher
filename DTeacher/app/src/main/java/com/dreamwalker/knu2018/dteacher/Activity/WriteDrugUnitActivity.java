package com.dreamwalker.knu2018.dteacher.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Adapter.WriteDrugUnitAdapter;
import com.dreamwalker.knu2018.dteacher.DBHelper.DrugDBHelper;
import com.dreamwalker.knu2018.dteacher.DBHelper.FitnessDBHelper;
import com.dreamwalker.knu2018.dteacher.Model.Drug;
import com.dreamwalker.knu2018.dteacher.R;

import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity1;
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent;
import com.dreamwalker.knu2018.dteacher.Utils.DrugWriteEvent;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.refactor.lib.colordialog.PromptDialog;

public class WriteDrugUnitActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "WriteDrugUnitActivity";

    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.doneTextView)
    TextView doneTextView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String dateValue, timeValue;

    RecyclerView.LayoutManager layoutManager;
    WriteDrugUnitAdapter adapter;
    ArrayList<String> myList;
    ArrayList<Drug> drugArrayList;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EventBus bus = EventBus.getDefault();
    private DrugDBHelper drugDBHelper;

    String drugName;
    int values;
    int positions;

    String dbName = "drug.db";
    int dbVersion = 1; // 데이터베이스 버전


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_drug_unit);

        ButterKnife.bind(this);
        bus.register(this);
        drugDBHelper = new DrugDBHelper(this, dbName, null, dbVersion); //db 객체 생성

        myList = new ArrayList<>();
        drugArrayList = new ArrayList<>();
        String result = getIntent().getStringExtra("WRITE_DRUG_TYPE");
        Log.e(TAG, "onCreate: " + result);

        String[] drugNames = result.split(",");

        for (int i = 0; i < drugNames.length; i++) {
            myList.add(drugNames[i]);
            drugArrayList.add(new Drug("1", drugNames[i]));
        }

        layoutManager = new LinearLayoutManager(this);
        adapter = new WriteDrugUnitAdapter(myList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Calendar now = Calendar.getInstance();

        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);

        String year = String.valueOf(now.get(Calendar.YEAR));
        int tempMonth = now.get(Calendar.MONTH);
        tempMonth = tempMonth + 1;
        String month;
        if (tempMonth < 10) {
            month = "0" + String.valueOf(tempMonth);
        } else {
            month = String.valueOf(tempMonth);
        }
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        // TODO: 2018-02-07 default로 현재의시간값을 넣음 구분자 필요없음
        dateValue = year + "-" + month + "-" + day;
        timeValue = hour + ":" + minutes;
        dateTextView.setText(dateValue);
        timeTextView.setText(timeValue);

        dateTextView.setOnClickListener(v -> {
            Log.e(TAG, "onClick: dateValueText ");
            datePickerDialog.show(getFragmentManager(), "fit_date");
        });
        timeTextView.setOnClickListener(v -> {
            Log.e(TAG, "onClick: dateValueText ");
            timePickerDialog.show(getFragmentManager(), "fit_time");
        });

        doneTextView.setOnClickListener(v -> new PromptDialog(WriteDrugUnitActivity.this)
                .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                .setAnimationEnable(true)
                .setTitleText("알림")
                .setContentText("기록하시겠어요?")
                .setPositiveListener("응", dialog -> {
                    for (int i = 0; i < drugArrayList.size(); i++) {
                        if (!drugArrayList.isEmpty()) {
                            drugDBHelper.insertDrugData(drugArrayList.get(i).getDrugName(), drugArrayList.get(i).getValueUnit(), dateValue, timeValue);
                        }
                    }
                    dialog.dismiss();
                    finish();
                }).show());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int tempMonth = monthOfYear + 1;
        String month;
        if (tempMonth < 10) {
            month = "0" + String.valueOf(tempMonth);
        } else {
            month = String.valueOf(tempMonth);
        }
        //String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String date = year + "-" + month + "-" + dayOfMonth;
        dateValue = date;
        dateTextView.setText(date);
    }

    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute;
        timeTextView.setText(time);
        timeValue = time;
    }

    /**
     * EventBus 이벤트를 받아오는 부분.
     * Unit Value는 Adapter에서 받아 옴.
     * @param event
     * @Author JAICHANGPARK
     */
    @Subscribe
    public void onEvent(DrugWriteEvent event) {
        String valueString;
        values = event.valueUnit;
        valueString = String.valueOf(values);
        drugName = event.drugName;
        drugArrayList.set(event.position, new Drug(valueString, drugName));

        for (int k = 0; k < drugArrayList.size(); k++) {
            Log.e(TAG, "Unit onEvent: " + drugArrayList.get(k).getDrugName() + ", " + drugArrayList.get(k).getValueUnit());
        }
    }

    @Override
    protected void onStop() {
        //bus.unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
