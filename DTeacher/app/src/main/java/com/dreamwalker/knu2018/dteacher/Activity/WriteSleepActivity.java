package com.dreamwalker.knu2018.dteacher.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dreamwalker.knu2018.dteacher.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WriteSleepActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_sleep);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        button = (Button) findViewById(R.id.button1);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);


        calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String strdate = "22-2-2018";
            String strdate2 = "26-2-2018";
            Date newdate = dateFormat.parse(strdate);
            Date newdate2 = dateFormat.parse(strdate2);
            arrayList.add(newdate);
            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date())
// deactivates given dates, non selectable
                .withDeactivateDates(list)
// highlight dates in red color, mean they are aleady used.
                .withHighlightedDates(arrayList);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("list",  calendar.getSelectedDates().toString());
            }
        });


    }
}
