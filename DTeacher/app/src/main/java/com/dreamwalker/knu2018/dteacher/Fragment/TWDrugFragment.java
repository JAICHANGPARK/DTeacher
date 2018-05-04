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


public class TWDrugFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PAGE_NUM = 3;
    private static final String TAG = "TWDrugFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.dateText)
    TextView dateText;
    @BindView(R.id.timeText)
    TextView timeText;

    @BindView(R.id.spinner_top)
    NiceSpinner spinnerTop;
    @BindView(R.id.spinner_bottom)
    NiceSpinner spinnerBottom;

    @BindView(R.id.number_picker)
    NumberPicker numberPicker;


    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;


    EventBus bus = EventBus.getDefault();

    String strDate;
    String dateValue;
    String timeValue;
    String inputType;
    String inputType2;
    String drugUnit;


//    private OnFragmentInteractionListener mListener;

    public TWDrugFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TWDrugFragment newInstance(String param1, String param2) {
        TWDrugFragment fragment = new TWDrugFragment();
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
        View view = inflater.inflate(R.layout.fragment_twdrug, container, false);
        ButterKnife.bind(this, view);
        List<String> dataset = new LinkedList<>(Arrays.asList("초속효성", "속효성", "중간형", "지속형", "혼합형"));
        spinnerTop.attachDataSource(dataset);
        List<String> dataset2 = new LinkedList<>(Arrays.asList("노보래피드", "애피드라", "휴마로그"));
        spinnerBottom.attachDataSource(dataset2);
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
        timePickerDialog = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

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

        inputType = "초속효성";
        inputType2 = "노보래피드";
        drugUnit = "1";

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
            drugUnit = String.valueOf(newVal);
            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
            Log.e(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
        });

        spinnerTop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        inputType = "초속효성";
                        List<String> set = new LinkedList<>(Arrays.asList("노보래피드", "애피드라", "휴마로그"));
                        spinnerBottom.attachDataSource(set);
                        break;
                    case 1:
                        inputType = "속효성";
                        List<String> set1 = new LinkedList<>(Arrays.asList("휴물린", "노보린R", "노보렛"));
                        spinnerBottom.attachDataSource(set1);
                        break;
                    case 2:
                        inputType = "중간형";
                        List<String> set2 = new LinkedList<>(Arrays.asList("인슈라타드", "휴물린N", "노보린N", "노보렛N"));
                        spinnerBottom.attachDataSource(set2);
                        break;
                    case 3:
                        inputType = "지속형";
                        List<String> set3 = new LinkedList<>(Arrays.asList("란투스", "레버미어", "투제오", "트레시바"));
                        spinnerBottom.attachDataSource(set3);
                        break;
                    case 4:
                        inputType = "혼합형";
                        List<String> set4 = new LinkedList<>(Arrays.asList("노보믹스30", "노보믹스50", "노보믹스70", "믹스타드30",
                                "휴마로그믹스25", "휴마로그믹스50", "휴물린70/30"));
                        spinnerBottom.attachDataSource(set4);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (inputType.equals("초속효성")) {
                    switch (position) {
                        case 0:
                            inputType2 = "노보래피드";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 1:
                            inputType2 = "애피드라";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 2:
                            inputType2 = "휴마로그";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                    }
                } else if (inputType.equals("속효성")) {
                    switch (position) {
                        case 0:
                            inputType2 = "휴물린";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 1:
                            inputType2 = "노보린R";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 2:
                            inputType2 = "노보렛";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                    }
                } else if (inputType.equals("중간형")) {
                    switch (position) {
                        case 0:
                            inputType2 = "인슈라타드";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 1:
                            inputType2 = "휴물린N";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 2:
                            inputType2 = "노보린N";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 3:
                            inputType2 = "노보렛N";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                    }
                } else if (inputType.equals("지속형")) {
                    switch (position) {
                        case 0:
                            inputType2 = "란투스";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 1:
                            inputType2 = "레버미어";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 2:
                            inputType2 = "투제오";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 3:
                            inputType2 = "트레시바";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                    }
                } else if (inputType.equals("혼합형")) {
                    switch (position) {
                        case 0:
                            inputType2 = "노보믹스30";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 1:
                            inputType2 = "노보믹스50";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 2:
                            inputType2 = "노보믹스70";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 3:
                            inputType2 = "믹스타드30";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 4:
                            inputType2 = "휴마로그믹스25";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 5:
                            inputType2 = "휴마로그믹스50";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                        case 6:
                            inputType2 = "휴물린70/30";
                            bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//            if (mListener != null) {
//                mListener.onFragmentInteraction(uri);
//            }
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
//        if (tempMonth < 10) {
//            tempStrMonth = "0" + String.valueOf(tempMonth);
//        } else {
//            tempStrMonth = String.valueOf(tempMonth);
//        }

        sDayOfMonth = (iDayOfMonth < 10) ? "0" + String.valueOf(iDayOfMonth) : String.valueOf(iDayOfMonth);
//        if (iDayOfMonth < 10) {
//            sDayOfMonth = "0" + String.valueOf(iDayOfMonth);
//        } else {
//            sDayOfMonth = String.valueOf(iDayOfMonth);
//        }
        //String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        //String date = year + "-" + tempStrMonth + "-" + dayOfMonth;
        String date = year + "-" + tempStrMonth + "-" + sDayOfMonth;
        dateValue = date;
        dateText.setText(date);
        // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
        bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute;
        timeValue = time;
        timeText.setText(time);

        // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
        bus.post(new TWDataEvent(dateValue, timeValue, inputType, inputType2, drugUnit, "null", PAGE_NUM));
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
