package com.dreamwalker.knu2018.dteacher.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dreamwalker.knu2018.dteacher.R;
import com.github.kimkevin.cachepot.CachePot;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WriteBSTypeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WriteBSTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteBSTypeFragment extends Fragment implements Step {
    private static final String TAG = "WriteBSTypeFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public interface OnTimePickerSetListener {
        void onTimePickerSet(String value);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private OnTimePickerSetListener onTimePickerSetListener;

    EditText inData;
    Button typeButton0, typeButton1, typeButton2, typeButton3, typeButton4, typeButton5, typeButton6, typeButton7;
    Button typeButton8, typeButton9, typeButton10;

    boolean buttonCheck0 = true;
    boolean buttonCheck1 = true;
    boolean buttonCheck2 = true;
    boolean buttonCheck3 = true;
    boolean buttonCheck4 = true;
    boolean buttonCheck5 = true;
    boolean buttonCheck6 = true;
    boolean buttonCheck7 = true;
    boolean buttonCheck8 = true;
    boolean buttonCheck9 = true;
    boolean buttonCheck10 = true;

    public WriteBSTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WriteBSTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteBSTypeFragment newInstance(String param1, String param2) {
        WriteBSTypeFragment fragment = new WriteBSTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_bstype, container, false);

        typeButton0 = (Button) view.findViewById(R.id.typeButton0);
        typeButton1 = (Button) view.findViewById(R.id.typeButton1);
        typeButton2 = (Button) view.findViewById(R.id.typeButton2);
        typeButton3 = (Button) view.findViewById(R.id.typeButton3);
        typeButton4 = (Button) view.findViewById(R.id.typeButton4);
        typeButton5 = (Button) view.findViewById(R.id.typeButton5);
        typeButton6 = (Button) view.findViewById(R.id.typeButton6);
        typeButton7 = (Button) view.findViewById(R.id.typeButton7);
        typeButton8 = (Button) view.findViewById(R.id.typeButton8);
        typeButton9 = (Button) view.findViewById(R.id.typeButton9);
        typeButton10 = (Button) view.findViewById(R.id.typeButton10);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        typeButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck0) {
                    //Log.e(TAG, "onClick: " + " typeButton0 unchecked");
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck0 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton0 checked");
                    // TODO: 2018-02-07 자기자신 체킹
                    onTimePickerSetListener.onTimePickerSet("새벽");
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck0 = true;
                    // TODO: 2018-02-07 자신것 플레그 제외 모두 false 처리 이는 더블 터치하여 선택되는것을 방지한다.
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck1) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck1 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("공복");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck1 = true;
                    buttonCheck0 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck2) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck2 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("아침식전");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck2 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });
        typeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck3) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck3 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("아침식후");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck3 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck4) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck4 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("점심식전");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck4 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck5) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck5 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("점심식후");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck5 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });
        typeButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck6) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck6 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("저녁식전");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck6 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });
        typeButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck7) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck7 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("저녁식후");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck7 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck8) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck8 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("운동전");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck8 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck9 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck9) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck9 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("운동후");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck9 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck10 = false;
                }
            }
        });

        typeButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheck10) {
                    //Log.e(TAG, "onClick: " + " typeButton1 unchecked");
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    buttonCheck10 = false;
                } else {
                    //Log.e(TAG, "onClick: " + " typeButton1 checked");
                    onTimePickerSetListener.onTimePickerSet("아픈날");
                    // TODO: 2018-02-07 자기자신 체킹
                    typeButton10.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_check));
                    // TODO: 2018-02-07 나머지 언 채킹
                    typeButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton5.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton6.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton7.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton8.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    typeButton9.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape_button_uncheck));
                    // TODO: 2018-02-07 Flag Toggle
                    buttonCheck10 = true;
                    buttonCheck0 = false;
                    buttonCheck1 = false;
                    buttonCheck2 = false;
                    buttonCheck3 = false;
                    buttonCheck4 = false;
                    buttonCheck5 = false;
                    buttonCheck6 = false;
                    buttonCheck7 = false;
                    buttonCheck8 = false;
                    buttonCheck9 = false;
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnTimePickerSetListener) {
            onTimePickerSetListener = (OnTimePickerSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        onTimePickerSetListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
