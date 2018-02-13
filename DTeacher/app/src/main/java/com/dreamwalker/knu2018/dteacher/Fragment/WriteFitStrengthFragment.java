package com.dreamwalker.knu2018.dteacher.Fragment;

import android.content.Context;
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
import com.dreamwalker.knu2018.dteacher.Utils.ValueChangedEvent;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.webianks.library.scroll_choice.ScrollChoice;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WriteFitStrengthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WriteFitStrengthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteFitStrengthFragment extends Fragment implements Step {

    private static final String TAG = "WriteFitStrengthFragmen";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String PAGE_TYPE;

    private OnFragmentInteractionListener mListener;
    EventBus bus = EventBus.getDefault();

    ScrollChoice scrollChoice;
    List<String> arrayData = new ArrayList<>();

    public WriteFitStrengthFragment() {
        // Required empty public constructor
    }

    public static WriteFitStrengthFragment newInstance(String param1, String param2) {
        WriteFitStrengthFragment fragment = new WriteFitStrengthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        bus.register(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_write_fit_strength, container, false);
        scrollChoice = (ScrollChoice) view.findViewById(R.id.scroll_choice);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addDefaultList();
        scrollChoice.addItems(arrayData, 3);
        PAGE_TYPE = arrayData.get(3);
        scrollChoice.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                Log.e(TAG, name);
                EventBus bus = EventBus.getDefault();
                bus.post(new ValueChangedEvent(PAGE_TYPE, name));
            }
        });
    }
    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    private void addDefaultList() {

        arrayData.add("3.5km/h");
        arrayData.add("3.6km/h");
        arrayData.add("3.7km/h");
        arrayData.add("3.8km/h");
        arrayData.add("3.9km/h");
        arrayData.add("4.0km/h");
        arrayData.add("4.1km/h");
        arrayData.add("4.2km/h");
        arrayData.add("4.3km/h");
        arrayData.add("4.4km/h");
        arrayData.add("4.5km/h");
        arrayData.add("4.6km/h");
        arrayData.add("4.7km/h");
        arrayData.add("4.8km/h");
        arrayData.add("4.9km/h");
        arrayData.add("5.0km/h");
        arrayData.add("5.1km/h");
        arrayData.add("5.2km/h");
        arrayData.add("5.3km/h");
        arrayData.add("5.4km/h");
        arrayData.add("5.5km/h");
        arrayData.add("5.6km/h");
        arrayData.add("5.7km/h");
        arrayData.add("5.8km/h");
        arrayData.add("5.9km/h");
        arrayData.add("6.0km/h");
        arrayData.add("6.1km/h");
        arrayData.add("6.2km/h");
        arrayData.add("6.3km/h");
        arrayData.add("6.4km/h");
        arrayData.add("6.5km/h");
        arrayData.add("6.6km/h");
        arrayData.add("6.7km/h");
        arrayData.add("6.8km/h");
        arrayData.add("6.9km/h");
        arrayData.add("7.0km/h");

    }

    private void addWorkingList() {
        arrayData.clear();
        arrayData.add("산책용");
        arrayData.add("약간빠르게");
        arrayData.add("빠르게");
        arrayData.add("경보");
        scrollChoice.addItems(arrayData, 0);
    }

    private void addJoggingList() {
        arrayData.clear();
        arrayData.add("가볍게");
        arrayData.add("보통");
        arrayData.add("약간빠르게");
        arrayData.add("빠르게");
        arrayData.add("전력질주");
        scrollChoice.addItems(arrayData, 1);
    }

    private void addStairList() {
        arrayData.clear();
        arrayData.add("가볍게");
        arrayData.add("보통");
        arrayData.add("빠르게");
        scrollChoice.addItems(arrayData, 0);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        bus.getDefault().unregister(this);
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

    // TODO: 2018-02-08 이벤트 처리하는 부분.
    //catch Event from fragment A
    @Subscribe
    public void onEvent(TextChangedEvent event) {
        PAGE_TYPE = event.newText;
        Log.e(TAG, "Strength Fragment onEvent: " + PAGE_TYPE);
        if (PAGE_TYPE.equals("걷기")) {
            addWorkingList();
        }
        if (PAGE_TYPE.equals("달리기조깅")) {
            addJoggingList();
        }
        if (PAGE_TYPE.equals("계단걷기")) {
            addStairList();
        }
        if (PAGE_TYPE.equals("계단달리기")) {
            addStairList();
        }
        if (PAGE_TYPE.equals("트레드밀걷기")) {
            if (arrayData.isEmpty()){
                addDefaultList();
            }else {
                arrayData.clear();
                addDefaultList();
            }
            scrollChoice.addItems(arrayData, 3);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
