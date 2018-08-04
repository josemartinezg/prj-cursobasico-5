package com.example.garci.calculadoradeedad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
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
        mTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.getDefault());

        myDatePicker = new DatePickerDialog(this, mDatePickerListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DATE));
        myTimePicker = new TimePickerDialog(TwoDatesCompActivity.this, mTimePickerListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                true/*24H Format FLAG*/);
        findViewById(R.id.date_pick).setOnClickListener(this);
        findViewById(R.id.time_pick).setOnClickListener(this);

        mDateDisplay = findViewById(R.id.date_display);
        mTimeDisplay = findViewById(R.id.time_display);
        mDateTimeDisplay = findViewById(R.id.date_time_display);
        refreshDisplays();
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
    private DatePickerDialog.OnDateSetListener mDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Calendar today = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR, i);
            mCalendar.set(Calendar.MONTH, i1);
            mCalendar.set(Calendar.DATE, i2);
            if (mCalendar.after(today)){
                throw new IllegalArgumentException("Favor no introducir fechas del futuro.");
            }
            refreshDisplays();
        }
    };

    private String calculateTime() {
       Calendar today = Calendar.getInstance();
        if (mCalendar.after(today)){
            Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("Favor no introducir fechas del futuro.");
        }else{
            int thisYear = today.get(Calendar.YEAR);
            int thisDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
            int thisMonth = today.get(Calendar.MONTH);
            int thisMinute = today.get(Calendar.MINUTE);
            int thisHour = today.get(Calendar.HOUR_OF_DAY);

            int startingDayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
            int startingMonth = mCalendar.get(Calendar.MONTH);
            int startingYear = mCalendar.get(Calendar.YEAR);
            int startingMinute = mCalendar.get(Calendar.MINUTE);
            int startingHour = mCalendar.get(Calendar.HOUR_OF_DAY);

            LocalDate currentDate = LocalDate.of(thisYear, thisMonth, thisDayOfMonth);
            LocalDate startingDate = LocalDate.of(startingYear, startingMonth, startingDayOfMonth);

            LocalTime startingTime = LocalTime.of(startingHour, startingMinute);
            LocalTime currentTime = LocalTime.of(thisHour,thisMinute);

            String time = calculateAge(startingDate, currentDate, startingTime, currentTime);
            Toast.makeText(this, "Cálculo exitoso", Toast.LENGTH_SHORT).show();
            return time;
        }

    }

    private String calculateAge(LocalDate startingDate, LocalDate currentDate, LocalTime startingTime, LocalTime currentTime) {
        if ((startingDate != null) && (currentDate != null)) {
            String anno = String.valueOf(Period.between(startingDate, currentDate).getYears());
            String mes = String.valueOf(Period.between(startingDate, currentDate).getMonths());
            String dia = String.valueOf(Period.between(startingDate, currentDate).getDays());

            int startingHour = startingTime.getHour();
            int currentHour = currentTime.getHour();
            int startingMinutes = startingTime.getMinute();
            int currentMinutes = currentTime.getMinute();
            int hour = 0, minutes = 0;
            if (startingHour > currentHour){
                hour = (24-startingHour) + currentHour;

            }if (startingHour < currentHour){
                hour = currentHour - startingHour;
            }if (startingHour == currentHour){
                hour = 0;
            }
            if (startingMinutes > currentMinutes){
                minutes = (60 - startingMinutes) + currentMinutes;
            }if (startingMinutes < currentMinutes){
                minutes = currentMinutes - startingMinutes;
            }if (startingMinutes == currentMinutes){
                minutes = 0;
            }

            return (anno + " año(s), " + mes + " mes(es), " + dia + " día(s)\n     " + String.valueOf(hour) + " hora(s) y " + String.valueOf(minutes) + " minuto(s)");
        } else {
            return "0";
        }
    }

    private void refreshDisplays() {
        mDateDisplay.setText(mDateFormat.format(mCalendar.getTime()));
        mTimeDisplay.setText(mTimeFormat.format(mCalendar.getTime()));
        mDateTimeDisplay.setText(calculateTime());
    }
}
