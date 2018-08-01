package com.example.garci.calculadoradeedad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class TwoDatesCompActivity extends AppCompatActivity implements View.OnClickListener{
   private TextView mDateDisplay, mTimeDisplay, mDateTimeDisplay;

    private DatePickerDialog myDatePicker;
    private TimePickerDialog myTimePicker;
    private SimpleDateFormat mDateFormat, mTimeFormat, mDateTimeFormat;
    private Calendar mCalendar;
    private LocalDate initialDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_dates_comp);

        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        mTimeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        mDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());

        myDatePicker = new DatePickerDialog(this, mDatePickerListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DATE));
        myTimePicker = new TimePickerDialog(TwoDatesCompActivity.this, mTimePickerListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                true);
        findViewById(R.id.date_pick).setOnClickListener(this);
        findViewById(R.id.time_pick).setOnClickListener(this);

        mDateDisplay = findViewById(R.id.date_display);
        mTimeDisplay = findViewById(R.id.time_display);
        mDateTimeDisplay = findViewById(R.id.date_time_display);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_pick:
                myDatePicker.show();
                break;
            case R.id.time_pick:
                myTimePicker.show();
                break;
        }
    }
    private TimePickerDialog.OnTimeSetListener mTimePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            mCalendar.set(Calendar.HOUR_OF_DAY, i);
            mCalendar.set(Calendar.MINUTE, i1);
            refreshDisplays();
        }
    };
    private  DatePickerDialog.OnDateSetListener mDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mCalendar.set(Calendar.YEAR, i);
            mCalendar.set(Calendar.MONTH, i1);
            mCalendar.set(Calendar.DATE, i2);
            refreshDisplays();
        }
    };

    private void refreshDisplays() {
        mDateDisplay.setText(mDateFormat.format(mCalendar.getTime()));
        mTimeDisplay.setText(mTimeFormat.format(mCalendar.getTime()));
        mDateTimeDisplay.setText(mDateTimeFormat.format(mCalendar.getTime()));
    }
}
