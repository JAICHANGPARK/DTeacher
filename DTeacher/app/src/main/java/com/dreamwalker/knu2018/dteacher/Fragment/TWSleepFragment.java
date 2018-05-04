package com.dreamwalker.knu2018.dteacher.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.TWDataEvent;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xw.repo.BubbleSeekBar;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TWSleepFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PAGE_NUM = 4;
    private static final String TAG = "TWSleepFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    @BindView(R.id.demo_3_seek_bar_2)
    BubbleSeekBar bubbleSeekBar;

    @BindView(R.id.dateText)
    TextView dateText;
    @BindView(R.id.startTimeText)
    TextView startTimeText;
    @BindView(R.id.endTimeText)
    TextView endTimeText;
    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TimePickerDialog timePickerDialog2;

    EventBus bus = EventBus.getDefault();

    String strDate;
    String dateValue;
    String timeValue;
    String inputType;
    String startTime;
    String endTime;
    String satisfaction;


    public TWSleepFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TWSleepFragment newInstance(String param1, String param2) {
        TWSleepFragment fragment = new TWSleepFragment();
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
        View view = inflater.inflate(R.layout.fragment_twsleep, container, false);
        ButterKnife.bind(this, view);
        List<String> dataset = new LinkedList<>(Arrays.asList("저녁", "낮잠"));
        niceSpinner.attachDataSource(dataset);
        return view;
    }

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

        timePickerDialog = TimePickerDialog.newInstance(
                (view, hourOfDay, minute, second) -> {
                    String timeStart = hourOfDay + ":" + minute;
                    String textTmp = hourOfDay + "시 " + minute + "분";
                    timeValue = timeStart;
                    startTimeText.setText(textTmp);
                    bus.post(new TWDataEvent(dateValue, timeValue, inputType, startTime, endTime, satisfaction, PAGE_NUM));
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);

        timePickerDialog2 = TimePickerDialog.newInstance(
                (view, hourOfDay, minute, second) -> {
                    String timeEnd = hourOfDay + ":" + minute;
                    String textTmp = hourOfDay + "시 " + minute + "분";
                    timeValue = timeEnd;
                    endTimeText.setText(textTmp);
                    bus.post(new TWDataEvent(dateValue, timeValue, inputType, startTime, endTime, satisfaction, PAGE_NUM));
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        strDate = simpleDateFormat.format(now.getTime());

        String year = String.valueOf(now.get(Calendar.YEAR));

        int tempMonth = now.get(Calendar.MONTH);
        tempMonth = tempMonth + 1;
        String month = String.valueOf(tempMonth);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));

        // TODO: 2018-02-07 default로 현재의시간값을 넣음 구분자 필요없음
        dateValue = strDate;
        timeValue = hour + ":" + minutes;
        // TODO: 2018-02-08  default 운동시간 30분으로 설정

        dateText.setText(year + "년" + month + "월" + day + "일");

        startTimeText.setText(timeValue);
        endTimeText.setText(hour + "시 " + minutes + "분");

        // TODO: 2018-05-04 time, date click event set
        dateText.setOnClickListener(v -> datePickerDialog.show(getActivity().getFragmentManager(), "date"));
        startTimeText.setOnClickListener(v -> timePickerDialog.show(getActivity().getFragmentManager(), "Timepickerdialog"));
        endTimeText.setOnClickListener(v -> timePickerDialog2.show(getActivity().getFragmentManager(), "endPicker"));

        inputType = "저녁";
        startTime = timeValue;
        endTime = timeValue;
        satisfaction = "5";

        // OnValueChangeListener
        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //super.onProgressChanged(bubbleSeekBar, progress, progressFloat);

                satisfaction = String.valueOf(progress); // TODO: 2018-05-04 정수형 변수를 문자열로 변환
                bus.post(new TWDataEvent(dateValue, timeValue, inputType, satisfaction, "null", "null", PAGE_NUM));
            }
        });

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        inputType = "저녁";
                        bus.post(new TWDataEvent(dateValue, timeValue, inputType, startTime, endTime, satisfaction, PAGE_NUM));
                        break;
                    case 1:
                        inputType = "낮잠";
                        bus.post(new TWDataEvent(dateValue, timeValue, inputType, startTime, endTime, satisfaction, PAGE_NUM));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
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
//        mListener = null;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int tempMonth = monthOfYear + 1;
        int iDayOfMonth = dayOfMonth;
        String tempStrMonth;
        String sDayOfMonth;

        tempStrMonth = (tempMonth < 10) ? "0" + String.valueOf(tempMonth) : String.valueOf(tempMonth);
        sDayOfMonth = (iDayOfMonth < 10) ? "0" + String.valueOf(iDayOfMonth) : String.valueOf(iDayOfMonth);

        String date = year + "-" + tempStrMonth + "-" + sDayOfMonth;
        dateValue = date;
        dateText.setText(date);
        // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
        bus.post(new TWDataEvent(dateValue, timeValue, inputType, startTime, endTime, satisfaction, PAGE_NUM));
    }

}
