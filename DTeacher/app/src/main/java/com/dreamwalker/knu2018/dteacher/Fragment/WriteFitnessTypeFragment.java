package com.dreamwalker.knu2018.dteacher.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.webianks.library.scroll_choice.ScrollChoice;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class WriteFitnessTypeFragment extends Fragment implements Step {

    private static final String TAG = "WriteFitnessTypeFragmen";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String FIT_TYPE;

    private OnFitnessTypeListener onFitnessTypeListener;

    ScrollChoice scrollChoice;
    EventBus bus = EventBus.getDefault();

    public WriteFitnessTypeFragment() {
        // Required empty public constructor
    }

    public static WriteFitnessTypeFragment newInstance(String param1, String param2) {
        WriteFitnessTypeFragment fragment = new WriteFitnessTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_fitness_type, container, false);
        scrollChoice = (ScrollChoice) view.findViewById(R.id.scroll_choice);
        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> arrayData = new ArrayList<>();
        arrayData.add("걷기");
        arrayData.add("달리기조깅");
        arrayData.add("계단걷기");
        arrayData.add("계단달리기");
        arrayData.add("트레드밀걷기");
        arrayData.add("트레드밀뛰기");
        arrayData.add("고정자전거");
        arrayData.add("자전거");
        arrayData.add("등산");
        arrayData.add("수영");
        arrayData.add("요가");
        arrayData.add("줄넘기");
        arrayData.add("스쿼트");
        arrayData.add("윗몸일으키기");

        scrollChoice.addItems(arrayData,4);
        FIT_TYPE = arrayData.get(4);

        bus.post(new TextChangedEvent(FIT_TYPE));

        scrollChoice.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                Log.e(TAG, name);
                bus.post(new TextChangedEvent(name));
                onFitnessTypeListener.onFitnessTypeListener(name);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFitnessTypeListener) {
            onFitnessTypeListener = (OnFitnessTypeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFitnessTypeListener = null;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFitnessTypeListener {
        void onFitnessTypeListener(String data);
    }
}
