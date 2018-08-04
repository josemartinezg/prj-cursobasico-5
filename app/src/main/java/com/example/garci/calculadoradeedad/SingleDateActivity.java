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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Locale;

public class SingleDateActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView prevDateDisplay, prevTimeDisplay;
    private TextView futDateDisplay, futTimeDisplay, futDateTimeDisplay;

    private DatePickerDialog prevDatePicker, futDatePicker;
    private TimePickerDialog prevTimePicker, futTimePicker;
    private SimpleDateFormat mDateFormat, mTimeFormat;
    private Calendar prevCalendar, futCalendar;
    private Calendar today = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date);

        prevCalendar = Calendar.getInstance();
        futCalendar = Calendar.getInstance();

        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        mTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());


        prevDatePicker = new DatePickerDialog(this, prevDatePickerListener,
                prevCalendar.get(Calendar.YEAR),
                prevCalendar.get(Calendar.MONTH),
                prevCalendar.get(Calendar.DATE));
        try{
            if (prevCalendar.after(today)){
                Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
                //throw new IllegalArgumentException("Favor no introducir fechas del futuro.");
            }
        }catch (IllegalArgumentException e){
            Toast.makeText(this, "Favor no introducir fechas del futuro.", Toast.LENGTH_SHORT).show();
        }
        prevTimePicker = new TimePickerDialog(SingleDateActivity.this, prevTimePickerListener,
                prevCalendar.get(Calendar.HOUR_OF_DAY),
                prevCalendar.get(Calendar.MINUTE),
                true/*24H Format FLAG*/);

        futDatePicker = new DatePickerDialog(this, futDatePickerListener,
                futCalendar.get(Calendar.YEAR),
                futCalendar.get(Calendar.MONTH),
                futCalendar.get(Calendar.DATE));
        try{
            if ((prevCalendar.after(futCalendar) || futCalendar.after(today))){
                Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
                //throw new IllegalArgumentException("Favor no introducir fechas del futuro.");
            }
        }catch (IllegalArgumentException e){
            Toast.makeText(this, "Favor no introducir fechas del futuro.", Toast.LENGTH_SHORT).show();
        }

        futTimePicker = new TimePickerDialog(SingleDateActivity.this, futTimePickerListener,
                futCalendar.get(Calendar.HOUR_OF_DAY),
                futCalendar.get(Calendar.MINUTE),
                true);

        findViewById(R.id.prev_date_pick).setOnClickListener(this);
        findViewById(R.id.prev_time_pick).setOnClickListener(this);
        findViewById(R.id.future_date_pick).setOnClickListener(this);
        findViewById(R.id.future_time_pick).setOnClickListener(this);

        prevDateDisplay = findViewById(R.id.prev_date_display);
        prevTimeDisplay = findViewById(R.id.prev_time_display);


        futDateDisplay = findViewById(R.id.future_date_display);
        futTimeDisplay = findViewById(R.id.future_time_display);
        futDateTimeDisplay = findViewById(R.id.time_elapsed_display_fortwo);
        refreshDisplays();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.prev_date_pick:
                prevDatePicker.show();
                break;
            case R.id.future_date_pick:
                futDatePicker.show();
                break;
            case R.id.prev_time_pick:
                prevTimePicker.show();
                break;
            case R.id.future_time_pick:
                futTimePicker.show();
                break;
        }
    }
    private TimePickerDialog.OnTimeSetListener prevTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            prevCalendar.set(Calendar.HOUR_OF_DAY, i);
            prevCalendar.set(Calendar.MINUTE, i1);
            refreshDisplays();
        }
    };

    private DatePickerDialog.OnDateSetListener prevDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            prevCalendar.set(Calendar.YEAR, i);
            prevCalendar.set(Calendar.MONTH, i1);
            prevCalendar.set(Calendar.DATE, i2);
            refreshDisplays();

        }
    };

    private TimePickerDialog.OnTimeSetListener futTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            futCalendar.set(Calendar.HOUR_OF_DAY, i);
            futCalendar.set(Calendar.MINUTE, i1);
            refreshDisplays();
        }
    };

    private DatePickerDialog.OnDateSetListener futDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            futCalendar.set(Calendar.YEAR, i);
            futCalendar.set(Calendar.MONTH, i1);
            futCalendar.set(Calendar.DATE, i2);
            refreshDisplays();
        }
    };

    private String calculateTime() {
        Calendar today = Calendar.getInstance();
        try{
            if (prevCalendar.after(futCalendar) || futCalendar.after(today)){
              //  Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)");
            }else{
                int prevDayOfMonth = prevCalendar.get(Calendar.DAY_OF_MONTH);
                int prevMonth = prevCalendar.get(Calendar.MONTH);
                int prevYear = prevCalendar.get(Calendar.YEAR);
                int prevMinute = prevCalendar.get(Calendar.MINUTE);
                int prevHour = prevCalendar.get(Calendar.HOUR_OF_DAY);
                int prevSecond = prevCalendar.get(Calendar.SECOND);

                int futYear = futCalendar.get(Calendar.YEAR);
                int futDayOfMonth = futCalendar.get(Calendar.DAY_OF_MONTH);
                int futMonth = futCalendar.get(Calendar.MONTH);
                int futMinute = futCalendar.get(Calendar.MINUTE);
                int futHour = futCalendar.get(Calendar.HOUR_OF_DAY);
                int futSecond = futCalendar.get(Calendar.SECOND);

                LocalDate futureDate = LocalDate.of(futYear, futMonth, futDayOfMonth);
                LocalDate previousDate = LocalDate.of(prevYear, prevMonth, prevDayOfMonth);

                LocalTime futureTime = LocalTime.of(futHour, futMinute, futSecond);
                LocalTime previousTime = LocalTime.of(prevHour, prevMinute, prevSecond);

                String time = calculateAge(previousDate, futureDate, previousTime, futureTime);
                Toast.makeText(this, "Cálculo exitoso", Toast.LENGTH_SHORT).show();
                return time;
            }
        }catch(IllegalArgumentException e){
            Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
        }
        return "";
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
            if (Period.between(startingDate, currentDate).getYears() == 0 && Period.between(startingDate, currentDate).getMonths() ==0
                    && Period.between(startingDate, currentDate).getDays() == 0 && hour == 0 && minutes == 0){
                return "";
            }
            return (anno + " año(s), " + mes + " mes(es), " + dia + " día(s)\n     " + String.valueOf(hour) + " hora(s) y " + String.valueOf(minutes) + " minuto(s)");
        } else {
            return "0";
        }
    }

    private void refreshDisplays() {
        prevDateDisplay.setText(mDateFormat.format(prevCalendar.getTime()));
        prevTimeDisplay.setText(mTimeFormat.format(prevCalendar.getTime()));

        futDateDisplay.setText(mDateFormat.format(futCalendar.getTime()));
        futTimeDisplay.setText(mTimeFormat.format(futCalendar.getTime()));
        futDateTimeDisplay.setText(calculateTime());

    }
}
