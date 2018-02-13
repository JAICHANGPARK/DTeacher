package com.dreamwalker.knu2018.dteacher.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent;
import com.dreamwalker.knu2018.dteacher.Utils.ValueChangedEvent;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

/**
 * 운동시간을 입력하는 프레그먼트
 */
public class WriteFitTimeFragment extends Fragment implements Step, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public interface OnFitnessDateTimeSetListener {
        void onFitnessDateTimeSet(String fitType, String fitStrength, float mets, String fitTime, String date, String time);
    }

    private static final String TAG = "WriteFitTimeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText valueEdt;
    private TextView dateValueText, timeValueText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String FIT_TYPE;
    String FIT_STRENGTH;
    String FIT_TIME;
    String dateValue;
    String timeValue;
    float METs;

    private OnFitnessDateTimeSetListener onFitnessDateTimeSetListener;

    EventBus bus = EventBus.getDefault();

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public WriteFitTimeFragment() {
        // Required empty public constructor
    }

    public static WriteFitTimeFragment newInstance(String param1, String param2) {
        WriteFitTimeFragment fragment = new WriteFitTimeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_fit_time, container, false);
        valueEdt = (EditText) view.findViewById(R.id.valueEdt);
        dateValueText = (TextView) view.findViewById(R.id.dateTextView);
        timeValueText = (TextView) view.findViewById(R.id.timeTextView);
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

        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        timePickerDialog = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        String year = String.valueOf(now.get(Calendar.YEAR));
        int tempMonth = now.get(Calendar.MONTH);
        tempMonth = tempMonth + 1;
        String month = String.valueOf(tempMonth);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        // TODO: 2018-02-07 default로 현재의시간값을 넣음 구분자 필요없음
        dateValue = year + "-" + month + "-" + day;
        timeValue = hour + ":" + minutes;
        // TODO: 2018-02-08  default 운동시간 30분으로 설정
        FIT_TIME = "30";
        Log.e(TAG, "onActivityCreated: " + year + (month + 1) + day + "," + hour + ": " + minutes);
        dateValueText.setText(year + "년" + month + "월" + day + "일");
        timeValueText.setText(hour + "시" + minutes + "분");


        dateValueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: dateValueText ");
                datePickerDialog.show(getActivity().getFragmentManager(), "fit_date");
            }
        });

        timeValueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: dateValueText ");
                timePickerDialog.show(getActivity().getFragmentManager(), "fit_time");
            }
        });
        valueEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                FIT_TIME = valueEdt.getText().toString();
                if (FIT_TIME.equals("")){
                    FIT_TIME = "30";
                }
                Log.e(TAG, "afterTextChanged: FIT_TIME  : " + FIT_TIME);
                onFitnessDateTimeSetListener.onFitnessDateTimeSet(FIT_TYPE, FIT_STRENGTH, METs, FIT_TIME, dateValue, timeValue);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFitnessDateTimeSetListener) {
            onFitnessDateTimeSetListener = (OnFitnessDateTimeSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFitnessDateTimeSetListener = null;
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        dateValue = date;
        dateValueText.setText(date);
        // TODO: 2018-02-07 액티비티로 보내는 인터페이스
        onFitnessDateTimeSetListener.onFitnessDateTimeSet(FIT_TYPE, FIT_STRENGTH, METs, FIT_TIME, dateValue, timeValue);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute;
        timeValueText.setText(time);
        timeValue = time;
        onFitnessDateTimeSetListener.onFitnessDateTimeSet(FIT_TYPE, FIT_STRENGTH, METs, FIT_TIME, dateValue, timeValue);
    }

    @Subscribe
    public void onEvent(ValueChangedEvent event) {
        FIT_TYPE = event.newText;
        FIT_STRENGTH = event.newText2;

        if (FIT_TYPE.equals("트레드밀걷기")) {
            if (FIT_STRENGTH.equals("3.8km/h")) {
                METs = 2.81f;
            }
        }
        Log.e(TAG, "onEvent: TimeFragment" + FIT_TYPE + ", " + FIT_STRENGTH + ", " + METs);
    }
}
