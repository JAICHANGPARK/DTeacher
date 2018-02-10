package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent
import com.dreamwalker.knu2018.dteacher.Utils.ValueChangedEvent
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import java.util.Calendar

/**
 * 운동시간을 입력하는 프레그먼트
 */
class WriteFitTimeFragment : Fragment(), Step, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var valueEdt: EditText? = null
    private var dateValueText: TextView? = null
    private var timeValueText: TextView? = null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    internal var FIT_TYPE: String
    internal var FIT_STRENGTH: String
    internal var FIT_TIME: String
    internal var dateValue: String
    internal var timeValue: String
    internal var METs: Float = 0.toFloat()

    private var onFitnessDateTimeSetListener: OnFitnessDateTimeSetListener? = null

    internal var bus = EventBus.getDefault()

    internal var datePickerDialog: DatePickerDialog
    internal var timePickerDialog: TimePickerDialog

    interface OnFitnessDateTimeSetListener {
        fun onFitnessDateTimeSet(fitType: String, fitStrength: String, mets: Float, fitTime: String, date: String, time: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        bus.register(this)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write_fit_time, container, false)
        valueEdt = view.findViewById<View>(R.id.valueEdt) as EditText
        dateValueText = view.findViewById<View>(R.id.dateTextView) as TextView
        timeValueText = view.findViewById<View>(R.id.timeTextView) as TextView
        return view
    }

    //    // TODO: Rename method, update argument and hook method into UI event
    //    public void onButtonPressed(Uri uri) {
    //        if (mListener != null) {
    //            mListener.onFragmentInteraction(uri);
    //        }
    //    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val now = Calendar.getInstance()
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        timePickerDialog = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)

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
        // TODO: 2018-02-08  default 운동시간 30분으로 설정
        FIT_TIME = "30"
        Log.e(TAG, "onActivityCreated: " + year + (month + 1) + day + "," + hour + ": " + minutes)
        dateValueText!!.text = year + "년" + month + "월" + day + "일"
        timeValueText!!.text = hour + "시" + minutes + "분"


        dateValueText!!.setOnClickListener {
            Log.e(TAG, "onClick: dateValueText ")
            datePickerDialog.show(activity!!.fragmentManager, "fit_date")
        }

        timeValueText!!.setOnClickListener {
            Log.e(TAG, "onClick: dateValueText ")
            timePickerDialog.show(activity!!.fragmentManager, "fit_time")
        }
        valueEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                FIT_TIME = valueEdt!!.text.toString()
                if (FIT_TIME == "") {
                    FIT_TIME = "30"
                }
                Log.e(TAG, "afterTextChanged: FIT_TIME  : " + FIT_TIME)
                onFitnessDateTimeSetListener!!.onFitnessDateTimeSet(FIT_TYPE, FIT_STRENGTH, METs, FIT_TIME, dateValue, timeValue)
            }
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnFitnessDateTimeSetListener) {
            onFitnessDateTimeSetListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        onFitnessDateTimeSetListener = null
        bus.getDefault().unregister(this)
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
        dateValue = date
        dateValueText!!.text = date
        // TODO: 2018-02-07 액티비티로 보내는 인터페이스
        onFitnessDateTimeSetListener!!.onFitnessDateTimeSet(FIT_TYPE, FIT_STRENGTH, METs, FIT_TIME, dateValue, timeValue)
    }

    override fun onTimeSet(view: TimePickerDialog, hourOfDay: Int, minute: Int, second: Int) {
        val time = hourOfDay.toString() + ":" + minute
        timeValueText!!.text = time
        timeValue = time
        onFitnessDateTimeSetListener!!.onFitnessDateTimeSet(FIT_TYPE, FIT_STRENGTH, METs, FIT_TIME, dateValue, timeValue)
    }

    @Subscribe
    fun onEvent(event: ValueChangedEvent) {
        FIT_TYPE = event.newText
        FIT_STRENGTH = event.newText2

        if (FIT_TYPE == "트레드밀걷기") {
            if (FIT_STRENGTH == "3.8km/h") {
                METs = 2.81f
            }
        }
        Log.e(TAG, "onEvent: TimeFragment$FIT_TYPE, $FIT_STRENGTH, $METs")
    }

    companion object {

        private val TAG = "WriteFitTimeFragment"
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): WriteFitTimeFragment {
            val fragment = WriteFitTimeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
