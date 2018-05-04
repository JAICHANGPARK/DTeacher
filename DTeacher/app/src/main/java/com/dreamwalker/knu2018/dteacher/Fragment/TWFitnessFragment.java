package com.dreamwalker.knu2018.dteacher.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.TWDataEvent;
import com.shawnlin.numberpicker.NumberPicker;
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


public class TWFitnessFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String TAG = "TWFitnessFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PAGE_NUM = 1;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.dateText)
    TextView dateText;
    @BindView(R.id.timeText)
    TextView timeText;
    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    @BindView(R.id.demo_4_seek_bar_4)
    BubbleSeekBar bubbleSeekBar;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    EventBus bus ;

    String strDate;
    String dateValue;
    String timeValue;
    String inputType;
    String fitnessTime;
    String satisfaction;

//    private OnFragmentInteractionListener mListener;

    public TWFitnessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TWFitnessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TWFitnessFragment newInstance(String param1, String param2) {
        TWFitnessFragment fragment = new TWFitnessFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twfitness, container, false);
        ButterKnife.bind(this, view);

        List<String> dataset = new LinkedList<>(Arrays.asList("실내걷기", "실외걷기", "실외달리기", "실내자전거"));
        niceSpinner.attachDataSource(dataset);

        bus = EventBus.getDefault();

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
        timePickerDialog = TimePickerDialog.newInstance(this,
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
        //dateValue = year + "-" + month + "-" + day;
        dateValue = strDate;
        timeValue = hour + ":" + minutes;
        // TODO: 2018-02-08  default 운동시간 30분으로 설정

        dateText.setText(year + "년" + month + "월" + day + "일");
        timeText.setText(hour + "시" + minutes + "분");

        // TODO: 2018-05-04 time, date click event set
        dateText.setOnClickListener(v -> datePickerDialog.show(getActivity().getFragmentManager(), "date"));
        timeText.setOnClickListener(v -> timePickerDialog.show(getActivity().getFragmentManager(), "time"));

        inputType = "실내걷기";
        fitnessTime = "30";
        satisfaction = "50";

        numberPicker.setDividerColorResource(R.color.colorPrimary); // Set divider color
        numberPicker.setSelectedTextColorResource(R.color.colorPrimary);// Set selected text color
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size); // Set selected text size
        numberPicker.setTextColorResource(R.color.dark_grey);// Set text color
        numberPicker.setTypeface(R.string.roboto_light);
        numberPicker.setFadingEdgeEnabled(true);  // Set fading edge enabled
        numberPicker.setScrollerEnabled(true); // Set scroller enabled
        numberPicker.setWrapSelectorWheel(true);   // Set wrap selector wheel

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            fitnessTime = String.valueOf(newVal);
            bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
            Log.e(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));

        });


        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        inputType = "실내걷기";
                        bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
                        break;
                    case 1:
                        inputType = "실외걷기";
                        bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
                        break;
                    case 2:
                        inputType = "실외달리기";
                        bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
                        break;
                    case 3:
                        inputType = "실내자전거";
                        bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        bubbleSeekBar.setCustomSectionTextArray((sectionCount, array) -> {
//            array.clear();
//            array.put(2, "bad");
//            array.put(5, "ok");
//            array.put(7, "good");
//            array.put(9, "great");
//
//            return array;
//        });

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //super.onProgressChanged(bubbleSeekBar, progress, progressFloat);
//                int color;
//                if (progress <= 10) {
//                    color = ContextCompat.getColor(getActivity(), R.color.color_red);
//                } else if (progress <= 40) {
//                    color = ContextCompat.getColor(getActivity(), R.color.color_red_light);
//                } else if (progress <= 70) {
//                    color = ContextCompat.getColor(getActivity(), R.color.colorAccent);
//                } else if (progress <= 90) {
//                    color = ContextCompat.getColor(getActivity(), R.color.color_blue);
//                } else {
//                    color = ContextCompat.getColor(getActivity(), R.color.color_green);
//                }
//
//                bubbleSeekBar.setSecondTrackColor(color);
//                bubbleSeekBar.setThumbColor(color);
//                bubbleSeekBar.setBubbleColor(color);

                satisfaction = String.valueOf(progress); // TODO: 2018-05-04 정수형 변수를 문자열로 변환
                bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
            }
        });

        //bubbleSeekBar.setProgress(60);

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
        if (tempMonth < 10) {
            tempStrMonth = "0" + String.valueOf(tempMonth);
        } else {
            tempStrMonth = String.valueOf(tempMonth);
        }
        if (iDayOfMonth < 10) {
            sDayOfMonth = "0" + String.valueOf(iDayOfMonth);
        } else {
            sDayOfMonth = String.valueOf(iDayOfMonth);
        }
        //String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        //String date = year + "-" + tempStrMonth + "-" + dayOfMonth;
        String date = year + "-" + tempStrMonth + "-" + sDayOfMonth;
        dateValue = date;
        dateText.setText(date);
        // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
        bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        String time = hourOfDay + ":" + minute;
        timeValue = time;
        timeText.setText(time);

        // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
        bus.post(new TWDataEvent(dateValue, timeValue, inputType, fitnessTime, satisfaction, "null", PAGE_NUM));
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
