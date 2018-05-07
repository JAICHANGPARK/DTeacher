package com.dreamwalker.knu2018.dteacher.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.DBHelper.MealDBHelper;
import com.dreamwalker.knu2018.dteacher.R;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xw.repo.BubbleSeekBar;

import org.angmarch.views.NiceSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class WriteMealActivity extends AppCompatActivity {

    private static final String TAG = "WriteMealActivity";
    private static final int GOKRYU_UNIT_KCAL = 100;
    private static final int BEEF_UNIT_KCAL = 75;
    private static final int VEGETABLE_UNIT_KCAL = 20;
    private static final int FAT_UNIT_KCAL = 45;
    private static final int MILK_UNIT_KCAL = 125;
    private static final int FRUIT_UNIT_KCAL = 50;

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

    private MealDBHelper mealDBHelper;
    private SQLiteDatabase db;
    String dbName = "meal.db";
    int dbVersion = 1; // 데이터베이스 버전

    int intGokryu = 0;
    int intBeef = 0;
    int intVegetable = 0;
    int intFat = 0;
    int intMilk = 0;
    int intFruit = 0;
    int intTotalExchange = 0;
    int intTotalKcal = 0;
    // TODO: 2018-05-05 DB 순서대로 정렬
    String inputType;
    String typeValue;
    String gokryuValue;
    String beefValue;
    String vegetableValue;
    String fatValue;
    String milkValue;
    String fruitValue;

    String exchangeValue;
    String satisfaction;
    String kcalValue;

    String startTimeValue;
    String endTimeValue;
    String mealTimeValue;
    String dateValue;
    String timeValue;
    String strDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_meal);
        ButterKnife.bind(this);

        mealDBHelper = new MealDBHelper(this, dbName, null, dbVersion);

        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String hour = String.valueOf(now.get(Calendar.HOUR));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));

        strDate = simpleDateFormat.format(now.getTime());
        dateValue = strDate;
        timeValue = hour + ":" + minutes;

        inputType = "아침";

        initBubbleSeekBar();
        initExchangeSeekBar();
        initSpinner();
        initDateTimePicker();


        doneTextView.setOnClickListener(v -> {
            String tempString = null;
            Date startDate = null;
            SimpleDateFormat dataFormat = new SimpleDateFormat("kk:mm", Locale.KOREA);

            try {
                if (startTimeValue != null || endTimeValue != null) {
                    startDate = dataFormat.parse(startTimeValue);
                    Date endDate = dataFormat.parse(endTimeValue);
                    long duration = (endDate.getTime() - startDate.getTime()) / 60000;
                    mealTimeValue = String.valueOf(duration);
                    Log.e(TAG, "duration: " + duration);
                    tempString = (mealTimeValue != null) ? mealTimeValue : "정보 없음";

                } else {
                    Toast.makeText(this, "시간을 설정해주세요 ", Toast.LENGTH_SHORT).show();
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


            intTotalExchange = (intGokryu ) + (intBeef ) + (intVegetable ) + (intFat) + (intMilk ) + (intFruit );
            intTotalKcal = (intGokryu * GOKRYU_UNIT_KCAL) + (intBeef * BEEF_UNIT_KCAL) +
                    (intVegetable * VEGETABLE_UNIT_KCAL) + (intFat * FAT_UNIT_KCAL) +
                    (intMilk * MILK_UNIT_KCAL) + (intFruit * FRUIT_UNIT_KCAL);
            kcalValue = String.valueOf(intTotalKcal);
            exchangeValue = String.valueOf(intTotalExchange); //교환단위

            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(WriteMealActivity.this)
                    .setTextTitle("SAVE")
                    .setTextSubTitle(inputType + " 식사시간 : " + tempString + "분 " + kcalValue + " KCAL")
                    .setBody("입력하신 데이터는 다음과 같아요.\n"
                            + "곡류군 :" + String.valueOf(intGokryu) + "\n"
                            + "어육류 :" + String.valueOf(intBeef) + "\n"
                            + "채소군 :" + String.valueOf(intVegetable) + "\n"
                            + "지방군 :" + String.valueOf(intFat) + "\n"
                            + "우유군 :" + String.valueOf(intMilk) + "\n"
                            + "채소군 :" + String.valueOf(intFruit) + "\n"
                            + "만족도 :" + String.valueOf(satisfaction) + "\n"
                    )
                    .setNegativeColor(R.color.primary)
                    .setNegativeButtonText("Later")
                    .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                        @Override
                        public void OnClick(View view, Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButtonText("Continue")
                    .setPositiveColor(R.color.fbutton_color_emerald)
                    .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                        @Override
                        public void OnClick(View view, Dialog dialog) {
                            gokryuValue = String.valueOf(intGokryu);
                            beefValue = String.valueOf(intBeef);
                            vegetableValue = String.valueOf(intVegetable);
                            fatValue = String.valueOf(intFat);
                            milkValue = String.valueOf(intMilk);
                            fruitValue = String.valueOf(intFruit);

                            mealDBHelper.insertMealData(inputType, gokryuValue, beefValue, vegetableValue, fatValue, milkValue, fruitValue,
                                    exchangeValue, satisfaction, kcalValue, startTimeValue,endTimeValue, mealTimeValue, dateValue,timeValue);

                            Toast.makeText(WriteMealActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setBodyGravity(FancyAlertDialog.TextGravity.LEFT)
                    .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                    .setSubtitleGravity(FancyAlertDialog.TextGravity.RIGHT)
                    .setCancelable(false)
                    .build();
            alert.show();


            Log.e(TAG, "onClick: " + inputType +
                    dateValue +
                    timeValue +
                    startTimeValue +
                    endTimeValue +
                    satisfaction +
                    exchangeValue + "\n");
            Log.e(TAG, "onClick: total kcal = " + intTotalExchange);
        });


    }

    private void initDateTimePicker() {
        dateButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    (view, year, monthOfYear, dayOfMonth) -> {
                        int tempMonth = monthOfYear + 1;
                        int iDayOfMonth = dayOfMonth;
                        String tempStrMonth;
                        String sDayOfMonth;
                        tempStrMonth = (tempMonth < 10) ? "0" + String.valueOf(tempMonth) : String.valueOf(tempMonth);
                        sDayOfMonth = (iDayOfMonth < 10) ? "0" + String.valueOf(iDayOfMonth) : String.valueOf(iDayOfMonth);
//                        if (tempMonth < 10) {
//                            tempStrMonth = "0" + String.valueOf(tempMonth);
//                        } else {
//                            tempStrMonth = String.valueOf(tempMonth);
//                        }
//                        if (iDayOfMonth < 10) {
//                            sDayOfMonth = "0" + String.valueOf(iDayOfMonth);
//                        } else {
//                            sDayOfMonth = String.valueOf(iDayOfMonth);
//                        }
                        //String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        //String date = year + "-" + tempStrMonth + "-" + dayOfMonth;
                        String date = year + "-" + tempStrMonth + "-" + sDayOfMonth;
//                                dateValue = date;
                        dateText.setText(date);
                        // TODO: 2018-05-04 상위 activity로 보내지는 이벤트 버스
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show(getFragmentManager(), "datePickerView");

        });

        startTimeButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(
                    (view, hourOfDay, minute, second) -> {
                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String minuteString = minute < 10 ? "0" + minute : "" + minute;
                        startTimeValue = hourString + ":" + minuteString;
                        startTimeText.setText(hourString + ":" + minuteString);

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
        });

        endTimeButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(
                    (view, hourOfDay, minute, second) -> {
                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String minuteString = minute < 10 ? "0" + minute : "" + minute;

                        endTimeValue = hourString + ":" + minuteString;
                        endTimeText.setText(hourString + ":" + minuteString);

                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
            );
            tpd.setOnCancelListener(dialogInterface -> Log.d("TimePicker", "Dialog was cancelled"));
            tpd.show(getFragmentManager(), "Timepickerdialog");
        });
    }

    private void initSpinner() {
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

    }

    private void initExchangeSeekBar() {
        seekBar01.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                gokryuValue = String.valueOf(progress);
                intGokryu = progress;
                Log.e(TAG, "onProgressChanged: " + progress + ", " + progressFloat);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });
        seekBar02.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                beefValue = String.valueOf(progress);
                intBeef = progress;
                Log.e(TAG, "onProgressChanged 2 : " + progress + ", " + progressFloat);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });
        seekBar03.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                vegetableValue = String.valueOf(progress);
                intVegetable = progress;
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });
        seekBar04.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                fatValue = String.valueOf(progress);
                intFat = progress;
            }
        });
        seekBar05.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                milkValue = String.valueOf(progress);
                intMilk = progress;
            }
        });
        seekBar06.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                fruitValue = String.valueOf(progress);
                intFruit = progress;
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
