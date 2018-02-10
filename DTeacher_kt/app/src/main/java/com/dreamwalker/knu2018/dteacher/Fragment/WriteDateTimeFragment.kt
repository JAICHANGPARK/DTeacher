package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.R
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

import io.paperdb.Paper

class WriteDateTimeFragment : Fragment(), Step, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var dateValue: String? = null
    private var timeValue: String? = null
    // TODO: Rename and change types of parameters

    internal var dateTextView: TextView
    internal var timeTextView: TextView
    internal var dateTextLabel: TextView
    private var mListener: OnFragmentInteractionListener? = null
    private var onBSDateTimeSetListener: OnBSDateTimeSetListener? = null

    internal var dateTimeDialogFragment: SwitchDateTimeDialogFragment? = null
    internal var datePickerDialog: DatePickerDialog
    internal var timePickerDialog: TimePickerDialog

    interface OnBSDateTimeSetListener {
        fun onBsDateTimeSet(date: String?, time: String?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(activity!!)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write_date_time, container, false)
        dateTextView = view.findViewById<View>(R.id.dateText) as TextView
        timeTextView = view.findViewById<View>(R.id.timeText) as TextView
        dateTextLabel = view.findViewById<View>(R.id.dateTextLabel) as TextView

        return view
    }

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
        Log.e(TAG, "onActivityCreated: " + year + (month + 1) + day + "," + hour + ": " + minutes)
        dateTextView.text = year + "년" + month + "월" + day + "일"
        timeTextView.text = hour + "시" + minutes + "분"

        dateTextLabel.setOnClickListener {
            datePickerDialog.show(activity!!.fragmentManager, "dialog_date")
            //dpd.show(getFragmentManager(),"Datepickerdialog");
            //dateTimeDialogFragment.show(getFragmentManager(), "dialog_time");
        }
        timeTextView.setOnClickListener { timePickerDialog.show(activity!!.fragmentManager, "dialog_time") }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //        if (context instanceof OnFragmentInteractionListener) {
        //            mListener = (OnFragmentInteractionListener) context;
        //        } else {
        //            throw new RuntimeException(context.toString()
        //                    + " must implement OnFragmentInteractionListener");
        //        }
        if (context is OnBSDateTimeSetListener) {
            onBSDateTimeSetListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        onBSDateTimeSetListener = null
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        //        String date = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        val date = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
        dateValue = date
        dateTextView.text = date
        // TODO: 2018-02-07 액티비티로 보내는 인터페이스
        onBSDateTimeSetListener!!.onBsDateTimeSet(dateValue, timeValue)
    }

    override fun onTimeSet(view: TimePickerDialog, hourOfDay: Int, minute: Int, second: Int) {
        val time = hourOfDay.toString() + ":" + minute
        timeTextView.text = time
        timeValue = time
        onBSDateTimeSetListener!!.onBsDateTimeSet(dateValue, timeValue)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val TAG = "WriteDateTimeFragment"

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): WriteDateTimeFragment {
            val fragment = WriteDateTimeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
