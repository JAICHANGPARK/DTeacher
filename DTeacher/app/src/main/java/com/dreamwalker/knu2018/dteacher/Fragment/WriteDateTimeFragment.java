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
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.paperdb.Paper;

public class WriteDateTimeFragment extends Fragment implements Step, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public interface OnBSDateTimeSetListener {
        void onBsDateTimeSet(String date, String time);
    }

    private static final String TAG = "WriteDateTimeFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private String dateValue;
    private String timeValue;
    // TODO: Rename and change types of parameters

    TextView dateTextView, timeTextView;
    TextView dateTextLabel;
    private OnFragmentInteractionListener mListener;
    private OnBSDateTimeSetListener onBSDateTimeSetListener;

    SwitchDateTimeDialogFragment dateTimeDialogFragment;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public WriteDateTimeFragment() {
        // Required empty public constructor
    }

    public static WriteDateTimeFragment newInstance(String param1, String param2) {
        WriteDateTimeFragment fragment = new WriteDateTimeFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_date_time, container, false);
        dateTextView = (TextView) view.findViewById(R.id.dateText);
        timeTextView = (TextView) view.findViewById(R.id.timeText);
        dateTextLabel = (TextView) view.findViewById(R.id.dateTextLabel);

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
        Log.e(TAG, "onActivityCreated: " + year + (month + 1) + day + "," + hour + ": " + minutes);
        dateTextView.setText(year + "년" + month + "월" + day +"일");
        timeTextView.setText(hour + "시" + minutes + "분");

        dateTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getActivity().getFragmentManager(), "dialog_date");
                //dpd.show(getFragmentManager(),"Datepickerdialog");
                //dateTimeDialogFragment.show(getFragmentManager(), "dialog_time");
            }
        });
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getActivity().getFragmentManager(), "dialog_time");
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        if (context instanceof OnBSDateTimeSetListener) {
            onBSDateTimeSetListener = (OnBSDateTimeSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        onBSDateTimeSetListener = null;
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
//        String date = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        dateValue = date;
        dateTextView.setText(date);
        // TODO: 2018-02-07 액티비티로 보내는 인터페이스
        onBSDateTimeSetListener.onBsDateTimeSet(dateValue, timeValue);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute;
        timeTextView.setText(time);
        timeValue = time;
        onBSDateTimeSetListener.onBsDateTimeSet(dateValue, timeValue);
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
