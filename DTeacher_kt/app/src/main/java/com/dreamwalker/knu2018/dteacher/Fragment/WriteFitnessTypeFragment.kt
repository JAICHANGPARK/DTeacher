package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.webianks.library.scroll_choice.ScrollChoice

import org.greenrobot.eventbus.EventBus

import java.util.ArrayList

class WriteFitnessTypeFragment : Fragment(), Step {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    internal var FIT_TYPE: String

    private var onFitnessTypeListener: OnFitnessTypeListener? = null

    internal var scrollChoice: ScrollChoice
    internal var bus = EventBus.getDefault()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write_fitness_type, container, false)
        scrollChoice = view.findViewById<View>(R.id.scroll_choice) as ScrollChoice
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

        val arrayData = ArrayList<String>()
        arrayData.add("걷기")
        arrayData.add("달리기조깅")
        arrayData.add("계단걷기")
        arrayData.add("계단달리기")
        arrayData.add("트레드밀걷기")
        arrayData.add("트레드밀뛰기")
        arrayData.add("고정자전거")
        arrayData.add("자전거")
        arrayData.add("등산")
        arrayData.add("수영")
        arrayData.add("요가")
        arrayData.add("줄넘기")
        arrayData.add("스쿼트")
        arrayData.add("윗몸일으키기")

        scrollChoice.addItems(arrayData, 4)
        FIT_TYPE = arrayData[4]

        bus.post(TextChangedEvent(FIT_TYPE))

        scrollChoice.setOnItemSelectedListener { scrollChoice, position, name ->
            Log.e(TAG, name)
            bus.post(TextChangedEvent(name))
            onFitnessTypeListener!!.onFitnessTypeListener(name)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFitnessTypeListener) {
            onFitnessTypeListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        onFitnessTypeListener = null
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

    interface OnFitnessTypeListener {
        fun onFitnessTypeListener(data: String)
    }

    companion object {

        private val TAG = "WriteFitnessTypeFragmen"
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): WriteFitnessTypeFragment {
            val fragment = WriteFitnessTypeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
