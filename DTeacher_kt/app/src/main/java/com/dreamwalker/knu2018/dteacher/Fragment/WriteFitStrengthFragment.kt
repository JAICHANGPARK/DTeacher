package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent
import com.dreamwalker.knu2018.dteacher.Utils.ValueChangedEvent
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.webianks.library.scroll_choice.ScrollChoice

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WriteFitStrengthFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WriteFitStrengthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteFitStrengthFragment : Fragment(), Step {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var PAGE_TYPE: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    internal var bus = EventBus.getDefault()

    internal var scrollChoice: ScrollChoice
    internal var arrayData: MutableList<String> = ArrayList()

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

        val view = inflater.inflate(R.layout.fragment_write_fit_strength, container, false)
        scrollChoice = view.findViewById<View>(R.id.scroll_choice) as ScrollChoice
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addDefaultList()
        scrollChoice.addItems(arrayData, 3)
        PAGE_TYPE = arrayData[3]
        scrollChoice.setOnItemSelectedListener { scrollChoice, position, name ->
            Log.e(TAG, name)
            val bus = EventBus.getDefault()
            bus.post(ValueChangedEvent(PAGE_TYPE, name))
        }
    }
    //    // TODO: Rename method, update argument and hook method into UI event
    //    public void onButtonPressed(Uri uri) {
    //        if (mListener != null) {
    //            mListener.onFragmentInteraction(uri);
    //        }
    //    }

    private fun addDefaultList() {

        arrayData.add("3.5km/h")
        arrayData.add("3.6km/h")
        arrayData.add("3.7km/h")
        arrayData.add("3.8km/h")
        arrayData.add("3.9km/h")
        arrayData.add("4.0km/h")
        arrayData.add("4.1km/h")
        arrayData.add("4.2km/h")
        arrayData.add("4.3km/h")
        arrayData.add("4.4km/h")
        arrayData.add("4.5km/h")
        arrayData.add("4.6km/h")
        arrayData.add("4.7km/h")
        arrayData.add("4.8km/h")
        arrayData.add("4.9km/h")
        arrayData.add("5.0km/h")
        arrayData.add("5.1km/h")
        arrayData.add("5.2km/h")
        arrayData.add("5.3km/h")
        arrayData.add("5.4km/h")
        arrayData.add("5.5km/h")
        arrayData.add("5.6km/h")
        arrayData.add("5.7km/h")
        arrayData.add("5.8km/h")
        arrayData.add("5.9km/h")
        arrayData.add("6.0km/h")
        arrayData.add("6.1km/h")
        arrayData.add("6.2km/h")
        arrayData.add("6.3km/h")
        arrayData.add("6.4km/h")
        arrayData.add("6.5km/h")
        arrayData.add("6.6km/h")
        arrayData.add("6.7km/h")
        arrayData.add("6.8km/h")
        arrayData.add("6.9km/h")
        arrayData.add("7.0km/h")

    }

    private fun addWorkingList() {
        arrayData.clear()
        arrayData.add("산책용")
        arrayData.add("약간빠르게")
        arrayData.add("빠르게")
        arrayData.add("경보")
        scrollChoice.addItems(arrayData, 0)
    }

    private fun addJoggingList() {
        arrayData.clear()
        arrayData.add("가볍게")
        arrayData.add("보통")
        arrayData.add("약간빠르게")
        arrayData.add("빠르게")
        arrayData.add("전력질주")
        scrollChoice.addItems(arrayData, 1)
    }

    private fun addStairList() {
        arrayData.clear()
        arrayData.add("가볍게")
        arrayData.add("보통")
        arrayData.add("빠르게")
        scrollChoice.addItems(arrayData, 0)
    }
    //    @Override
    //    public void onAttach(Context context) {
    //        super.onAttach(context);
    //        if (context instanceof OnFragmentInteractionListener) {
    //            mListener = (OnFragmentInteractionListener) context;
    //        } else {
    //            throw new RuntimeException(context.toString()
    //                    + " must implement OnFragmentInteractionListener");
    //        }
    //    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        bus.getDefault().unregister(this)
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    // TODO: 2018-02-08 이벤트 처리하는 부분.
    //catch Event from fragment A
    @Subscribe
    fun onEvent(event: TextChangedEvent) {
        PAGE_TYPE = event.newText
        Log.e(TAG, "Strength Fragment onEvent: " + PAGE_TYPE!!)
        if (PAGE_TYPE == "걷기") {
            addWorkingList()
        }
        if (PAGE_TYPE == "달리기조깅") {
            addJoggingList()
        }
        if (PAGE_TYPE == "계단걷기") {
            addStairList()
        }
        if (PAGE_TYPE == "계단달리기") {
            addStairList()
        }
        if (PAGE_TYPE == "트레드밀걷기") {
            if (arrayData.isEmpty()) {
                addDefaultList()
            } else {
                arrayData.clear()
                addDefaultList()
            }
            scrollChoice.addItems(arrayData, 3)
        }
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

        private val TAG = "WriteFitStrengthFragmen"

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): WriteFitStrengthFragment {
            val fragment = WriteFitStrengthFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
