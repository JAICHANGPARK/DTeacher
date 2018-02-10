package com.dreamwalker.knu2018.dteacher.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.Adapter.WriteDrugUnitAdapter
import com.dreamwalker.knu2018.dteacher.DBHelper.DrugDBHelper
import com.dreamwalker.knu2018.dteacher.DBHelper.FitnessDBHelper
import com.dreamwalker.knu2018.dteacher.Model.Drug
import com.dreamwalker.knu2018.dteacher.R

import com.dreamwalker.knu2018.dteacher.SignUpActivity.SignUpActivity1
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent
import com.dreamwalker.knu2018.dteacher.Utils.DrugWriteEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import java.util.ArrayList
import java.util.Calendar

import butterknife.BindView
import butterknife.ButterKnife
import cn.refactor.lib.colordialog.PromptDialog

class WriteDrugUnitActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.dateTextView)
    internal var dateTextView: TextView? = null
    @BindView(R.id.timeTextView)
    internal var timeTextView: TextView? = null
    @BindView(R.id.doneTextView)
    internal var doneTextView: TextView? = null

    @BindView(R.id.recyclerView)
    internal var recyclerView: RecyclerView? = null

    internal var dateValue: String
    internal var timeValue: String

    internal var layoutManager: RecyclerView.LayoutManager
    internal var adapter: WriteDrugUnitAdapter
    internal var myList: ArrayList<String>
    internal var drugArrayList: ArrayList<Drug>

    internal var datePickerDialog: DatePickerDialog
    internal var timePickerDialog: TimePickerDialog
    internal var bus = EventBus.getDefault()
    private var drugDBHelper: DrugDBHelper? = null

    internal var drugName: String
    internal var values: Int = 0
    internal var positions: Int = 0

    internal var dbName = "drug.db"
    internal var dbVersion = 1 // 데이터베이스 버전


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_drug_unit)

        ButterKnife.bind(this)
        bus.register(this)
        drugDBHelper = DrugDBHelper(this, dbName, null, dbVersion)

        myList = ArrayList()
        drugArrayList = ArrayList()
        val result = intent.getStringExtra("WRITE_DRUG_TYPE")
        Log.e(TAG, "onCreate: " + result)

        val drugNames = result.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (i in drugNames.indices) {
            myList.add(drugNames[i])
            drugArrayList.add(Drug("0", "unknown"))
        }

        layoutManager = LinearLayoutManager(this)
        adapter = WriteDrugUnitAdapter(myList, this)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = adapter

        val now = Calendar.getInstance()

        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH))
        timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true)

        val year = now.get(Calendar.YEAR).toString()
        var tempMonth = now.get(Calendar.MONTH)
        tempMonth = tempMonth + 1
        val month = tempMonth.toString()
        val day = now.get(Calendar.DAY_OF_MONTH).toString()
        val hour = now.get(Calendar.HOUR).toString()
        val minutes = now.get(Calendar.MINUTE).toString()
        // TODO: 2018-02-07 default로 현재의시간값을 넣음 구분자 필요없음
        dateValue = "$year-$month-$day"
        timeValue = hour + ":" + minutes
        dateTextView!!.text = dateValue
        timeTextView!!.text = timeValue

        dateTextView!!.setOnClickListener {
            Log.e(TAG, "onClick: dateValueText ")
            datePickerDialog.show(fragmentManager, "fit_date")
        }
        timeTextView!!.setOnClickListener {
            Log.e(TAG, "onClick: dateValueText ")
            timePickerDialog.show(fragmentManager, "fit_time")
        }
        doneTextView!!.setOnClickListener {
            PromptDialog(this@WriteDrugUnitActivity)
                    .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                    .setAnimationEnable(true)
                    .setTitleText("알림")
                    .setContentText("기록하시겠어요?")
                    .setPositiveListener("응") { dialog ->
                        for (i in drugArrayList.indices) {
                            if (!drugArrayList.isEmpty()) {
                                drugDBHelper!!.insertDrugData(drugArrayList[i].drugName, drugArrayList[i].valueUnit, dateValue, timeValue)
                            }
                        }
                        dialog.dismiss()
                        finish()
                    }.show()
        }
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
        dateValue = date
        dateTextView!!.text = date
    }

    override fun onTimeSet(view: com.wdullaer.materialdatetimepicker.time.TimePickerDialog, hourOfDay: Int, minute: Int, second: Int) {
        val time = hourOfDay.toString() + ":" + minute
        timeTextView!!.text = time
        timeValue = time
    }

    @Subscribe
    fun onEvent(event: DrugWriteEvent) {
        val valueString: String
        values = event.valueUnit
        valueString = values.toString()
        drugName = event.drugName
        drugArrayList[event.position] = Drug(valueString, drugName)

        for (k in drugArrayList.indices) {
            Log.e(TAG, "Unit onEvent: " + drugArrayList[k].drugName + ", " + drugArrayList[k].valueUnit)
        }
    }

    override fun onStop() {
        //bus.unregister(this);
        super.onStop()
    }

    override fun onDestroy() {
        bus.unregister(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    companion object {
        private val TAG = "WriteDrugUnitActivity"
    }
}
