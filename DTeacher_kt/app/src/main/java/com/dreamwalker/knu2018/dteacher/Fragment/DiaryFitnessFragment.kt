package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper
import com.dreamwalker.knu2018.dteacher.DBHelper.FitnessDBHelper
import com.dreamwalker.knu2018.dteacher.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DiaryFitnessFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DiaryFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiaryFitnessFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    internal var dbText: TextView
    private var fitnessDBHelper: FitnessDBHelper? = null
    private val db: SQLiteDatabase? = null

    private var mListener: OnFragmentInteractionListener? = null

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
        val view = inflater.inflate(R.layout.fragment_diary_fitness, container, false)
        dbText = view.findViewById<View>(R.id.label) as TextView
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
        fitnessDBHelper = FitnessDBHelper(activity!!, "fitness.db", null!!, 1)
        val dbResult = fitnessDBHelper!!.selectAllData()
        dbText.text = dbResult
    }

    /*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    override fun onDetach() {
        super.onDetach()
        mListener = null
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

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): DiaryFitnessFragment {
            val fragment = DiaryFitnessFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
