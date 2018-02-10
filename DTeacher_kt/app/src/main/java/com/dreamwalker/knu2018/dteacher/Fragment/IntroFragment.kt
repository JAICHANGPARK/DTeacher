package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.dreamwalker.knu2018.dteacher.R


class IntroFragment : Fragment() {
    private var layoutResId: Int = 0

    private var mListener: OnFragmentInteractionListener? = null

    private val settingButton: Button? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //        settingButton = (Button) getView().findViewById(R.id.settingButton);
        //        // setting Button 이 눌렸을 떄 동작하도록
        //        settingButton.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                //startActivity(new Intent(getActivity().getApplicationContext(), SettingInitActivity.class));
        //            }
        //        });
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments!!.containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = arguments!!.getInt(ARG_LAYOUT_RES_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.intro_slide_03, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        private val ARG_LAYOUT_RES_ID = "layoutResId"

        // TODO: Rename and change types and number of parameters
        fun newInstance(layoutResId: Int): IntroFragment {
            val fragment = IntroFragment()
            //        Bundle args = new Bundle();
            //        args.putString(ARG_PARAM1, param1);
            //        args.putString(ARG_PARAM2, param2);
            //        fragment.setArguments(args);
            //        return fragment;

            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
