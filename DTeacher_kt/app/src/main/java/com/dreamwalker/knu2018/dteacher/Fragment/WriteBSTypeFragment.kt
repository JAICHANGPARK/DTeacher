package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.dreamwalker.knu2018.dteacher.R
import com.github.kimkevin.cachepot.CachePot
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError

import io.paperdb.Paper

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WriteBSTypeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WriteBSTypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteBSTypeFragment : Fragment(), Step {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var onTimePickerSetListener: OnTimePickerSetListener? = null

    internal var inData: EditText? = null
    internal var typeButton0: Button
    internal var typeButton1: Button
    internal var typeButton2: Button
    internal var typeButton3: Button
    internal var typeButton4: Button
    internal var typeButton5: Button
    internal var typeButton6: Button
    internal var typeButton7: Button
    internal var typeButton8: Button
    internal var typeButton9: Button
    internal var typeButton10: Button

    internal var buttonCheck0 = true
    internal var buttonCheck1 = true
    internal var buttonCheck2 = true
    internal var buttonCheck3 = true
    internal var buttonCheck4 = true
    internal var buttonCheck5 = true
    internal var buttonCheck6 = true
    internal var buttonCheck7 = true
    internal var buttonCheck8 = true
    internal var buttonCheck9 = true
    internal var buttonCheck10 = true

    interface OnTimePickerSetListener {
        fun onTimePickerSet(value: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(activity!!)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write_bstype, container, false)

        typeButton0 = view.findViewById<View>(R.id.typeButton0) as Button
        typeButton1 = view.findViewById<View>(R.id.typeButton1) as Button
        typeButton2 = view.findViewById<View>(R.id.typeButton2) as Button
        typeButton3 = view.findViewById<View>(R.id.typeButton3) as Button
        typeButton4 = view.findViewById<View>(R.id.typeButton4) as Button
        typeButton5 = view.findViewById<View>(R.id.typeButton5) as Button
        typeButton6 = view.findViewById<View>(R.id.typeButton6) as Button
        typeButton7 = view.findViewById<View>(R.id.typeButton7) as Button
        typeButton8 = view.findViewById<View>(R.id.typeButton8) as Button
        typeButton9 = view.findViewById<View>(R.id.typeButton9) as Button
        typeButton10 = view.findViewById<View>(R.id.typeButton10) as Button
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        typeButton0.setOnClickListener {
            if (buttonCheck0) {
                //Log.e(TAG, "onClick: " + " typeButton0 unchecked");
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck0 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton0 checked");
                // TODO: 2018-02-07 자기자신 체킹
                onTimePickerSetListener!!.onTimePickerSet("새벽")
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck0 = true
                // TODO: 2018-02-07 자신것 플레그 제외 모두 false 처리 이는 더블 터치하여 선택되는것을 방지한다.
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }

        typeButton1.setOnClickListener {
            if (buttonCheck1) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck1 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("공복")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck1 = true
                buttonCheck0 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }

        typeButton2.setOnClickListener {
            if (buttonCheck2) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck2 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("아침식전")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck2 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }
        typeButton3.setOnClickListener {
            if (buttonCheck3) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck3 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("아침식후")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck3 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }

        typeButton4.setOnClickListener {
            if (buttonCheck4) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck4 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("점심식전")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck4 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }

        typeButton5.setOnClickListener {
            if (buttonCheck5) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck5 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("점심식후")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck5 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }
        typeButton6.setOnClickListener {
            if (buttonCheck6) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck6 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("저녁식전")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck6 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }
        typeButton7.setOnClickListener {
            if (buttonCheck7) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck7 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("저녁식후")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck7 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck8 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }

        typeButton8.setOnClickListener {
            if (buttonCheck8) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck8 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("운동전")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck8 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck9 = false
                buttonCheck10 = false
            }
        }

        typeButton9.setOnClickListener {
            if (buttonCheck9) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck9 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("운동후")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck9 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck10 = false
            }
        }

        typeButton10.setOnClickListener {
            if (buttonCheck10) {
                //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                buttonCheck10 = false
            } else {
                //Log.e(TAG, "onClick: " + " typeButton1 checked");
                onTimePickerSetListener!!.onTimePickerSet("아픈날")
                // TODO: 2018-02-07 자기자신 체킹
                typeButton10.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_check)
                // TODO: 2018-02-07 나머지 언 채킹
                typeButton0.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton1.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton2.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton3.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton4.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton5.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton6.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton7.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton8.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                typeButton9.background = ContextCompat.getDrawable(activity!!, R.drawable.round_shape_button_uncheck)
                // TODO: 2018-02-07 Flag Toggle
                buttonCheck10 = true
                buttonCheck0 = false
                buttonCheck1 = false
                buttonCheck2 = false
                buttonCheck3 = false
                buttonCheck4 = false
                buttonCheck5 = false
                buttonCheck6 = false
                buttonCheck7 = false
                buttonCheck8 = false
                buttonCheck9 = false
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String input = inData.getText().toString();
//                //Paper.book("write_dang_temp").destroy();
//                Paper.book("write_dang_temp").write("page1", input);
                onTimePickerSetListener.onTimePickerSet(10,20,"asd");
            }
        });*/

        /*
        btnPassData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch: " + event );
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btnPassData.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.accent));
                    break;
                    case MotionEvent.ACTION_UP:
                        btnPassData.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primary_dark));
                    break;
                }
                return false;
            }
        });*/


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnTimePickerSetListener) {
            onTimePickerSetListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
        //        if (context instanceof OnFragmentInteractionListener) {
        //            mListener = (OnFragmentInteractionListener) context;
        //        } else {
        //            throw new RuntimeException(context.toString()
        //                    + " must implement OnFragmentInteractionListener");
        //        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        onTimePickerSetListener = null
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

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
        private val TAG = "WriteBSTypeFragment"
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WriteBSTypeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): WriteBSTypeFragment {
            val fragment = WriteBSTypeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
