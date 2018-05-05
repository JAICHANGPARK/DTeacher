package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xw.repo.BubbleSeekBar;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class WriteMealActivity extends AppCompatActivity {

    private static final String TAG = "WriteMealActivity";

    @BindView(R.id.doneTextView)
    TextView doneTextView;
    @BindView(R.id.dateButton)
    FButton dateButton;
    @BindView(R.id.startTimeButton)
    FButton startTimeButton;
    @BindView(R.id.endTimeButton)
    FButton endTimeButton;

    @BindView(R.id.dateText)
    TextView dateText;
    @BindView(R.id.startTimeText)
    TextView startTimeText;
    @BindView(R.id.endTimeText)
    TextView endTimeText;

    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;

    @BindView(R.id.seek_bar_1)
    BubbleSeekBar seekBar01;
    @BindView(R.id.seek_bar_2)
    BubbleSeekBar seekBar02;
    @BindView(R.id.seek_bar_3)
    BubbleSeekBar seekBar03;
    @BindView(R.id.seek_bar_4)
    BubbleSeekBar seekBar04;
    @BindView(R.id.seek_bar_5)
    BubbleSeekBar seekBar05;
    @BindView(R.id.seek_bar_6)
    BubbleSeekBar seekBar06;

    @BindView(R.id.seek_bar_7)
    BubbleSeekBar bubbleSeekBar;


    String inputType;
    String dateValue;
    String timeValue;
    String startTimeValue;
    String endTimeValue;
    String typeValue;
    String satisfaction;
    String exchangeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_meal);

        ButterKnife.bind(this);
        inputType = "아침";
        initBubbleSeekBar();
        List<String> dataset = new LinkedList<>(Arrays.asList("아침", "점심", "저녁", "간식"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        inputType = "아침";

                        break;
                    case 1:
                        inputType = "점심";

                        break;
                    case 2:
                        inputType = "저녁";

                        break;
                    case 3:
                        inputType = "간식";

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
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
//                                dateValue = date;
                                dateText.setText(date);
                                // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show(getFragmentManager(), "datePickerView");

            }
        });

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                                String minuteString = minute < 10 ? "0" + minute : "" + minute;
                                startTimeValue = hourString + ":" + minuteString;
                                startTimeText.setText(hourString + ":" + minuteString);

                            }
                        },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                                String minuteString = minute < 10 ? "0" + minute : "" + minute;

                                endTimeValue = hourString + ":" + minuteString;
                                endTimeText.setText(hourString + ":" + minuteString);

                            }
                        },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });


        doneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + inputType +
                        dateValue +
                        timeValue +
                        startTimeValue +
                        endTimeValue +
                        typeValue +
                        satisfaction +
                        exchangeValue + "\n");
            }
        });

    }


    private void initBubbleSeekBar() {
        bubbleSeekBar.setCustomSectionTextArray((sectionCount, array) -> {
            array.clear();
            array.put(2, "bad");
            array.put(5, "ok");
            array.put(7, "good");
            array.put(9, "great");

            return array;
        });

        bubbleSeekBar.setProgress(50);

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //super.onProgressChanged(bubbleSeekBar, progress, progressFloat);
                int color;
                if (progress <= 10) {
                    color = ContextCompat.getColor(getApplicationContext(), R.color.color_red);
                } else if (progress <= 40) {
                    color = ContextCompat.getColor(getApplicationContext(), R.color.color_red_light);
                } else if (progress <= 70) {
                    color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
                } else if (progress <= 90) {
                    color = ContextCompat.getColor(getApplicationContext(), R.color.color_blue);
                } else {
                    color = ContextCompat.getColor(getApplicationContext(), R.color.color_green);
                }

                bubbleSeekBar.setSecondTrackColor(color);
                bubbleSeekBar.setThumbColor(color);
                bubbleSeekBar.setBubbleColor(color);

                satisfaction = String.valueOf(progress); // TODO: 2018-05-04 정수형 변수를 문자열로 변환

                //bus.post(new TWDataEvent(dateValue, timeValue, inputType, exchangeValue, startTime, endTime, satisfaction, PAGE_NUM));
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
