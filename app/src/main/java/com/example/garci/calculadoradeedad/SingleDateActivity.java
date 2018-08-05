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
   private TextView mDateDisplay, mTimeDisplay, mDateTimeDisplay;

    private DatePickerDialog myDatePicker;
    private TimePickerDialog myTimePicker;
    private SimpleDateFormat mDateFormat, mTimeFormat;
    private Calendar mCalendar, today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date);

        mCalendar = Calendar.getInstance();
        today = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        mTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        //Creación del objeto DatePickerDialog para la fecha futura.
        myDatePicker = new DatePickerDialog(this, mDatePickerListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DATE));
                try{
                    if (mCalendar.after(today)){
                        Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
                    }
                }catch (IllegalArgumentException e){
                    Toast.makeText(this, "Favor no introducir fechas del futuro.", Toast.LENGTH_SHORT).show();
                }

        //Creación del objeto TimePickerDialog para la fecha.
        myTimePicker = new TimePickerDialog(SingleDateActivity.this, mTimePickerListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                true);
        //Habilitando el OnClickListener de estos elemntos de la interfaz gráfica.
        findViewById(R.id.date_pick).setOnClickListener(this);
        findViewById(R.id.time_pick).setOnClickListener(this);
        //Conexión los elementos de la interfaz gráfica con los objetos correspondientes en Java.
        mDateDisplay = findViewById(R.id.date_display);
        mTimeDisplay = findViewById(R.id.time_display);
        mDateTimeDisplay = findViewById(R.id.date_time_display);
        refreshDisplays();
    }
    //Determinando los eventos a tomar parte al hacer click en los diferentes elementos.
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
    /*Los 2 métodos siguientes captan los datos de los widgets, en variables Calendar. */
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
            mCalendar.set(Calendar.YEAR, i);
            mCalendar.set(Calendar.MONTH, i1);
            mCalendar.set(Calendar.DATE, i2);
            refreshDisplays();
        }
    };
    /*Método: calculateTime
     * Entrada: -ninguna-
     * Salida: String "time"
     * Función: Hacer utilizables los datos tomados por los widgets, para sus respectivos cálculos.
     *  Seguido, delegar los cálculos, y procesarlos en un String para su muestra. */
    private String calculateTime() {
        try{
            if (mCalendar.after(today)){
                Toast.makeText(this, "Por favor verifique la consistencia de la(s) fecha(s) seleccionada(s)", Toast.LENGTH_SHORT).show();
                // throw new IllegalArgumentException("Favor no introducir fechas del futuro.");
            }else{
                int thisYear = today.get(Calendar.YEAR);
                int thisMonth = today.get(Calendar.MONTH);
                int thisDayOfMonth = today.get(Calendar.DAY_OF_MONTH);

                int thisHour = today.get(Calendar.HOUR_OF_DAY);
                int thisMinute = today.get(Calendar.MINUTE);

                int startingYear = mCalendar.get(Calendar.YEAR);
                int startingMonth = mCalendar.get(Calendar.MONTH);
                int startingDayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                int startingHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                int startingMinute = mCalendar.get(Calendar.MINUTE);


                LocalDate currentDate = LocalDate.of(thisYear, thisMonth, thisDayOfMonth);
                LocalDate startingDate = LocalDate.of(startingYear, startingMonth, startingDayOfMonth);

                LocalTime startingTime = LocalTime.of(startingHour, startingMinute);
                LocalTime currentTime = LocalTime.of(thisHour,thisMinute);

                String time = calculateElapsedTime(startingDate, currentDate, startingTime, currentTime);
                Toast.makeText(this, "Cálculo exitoso", Toast.LENGTH_SHORT).show();
                return time;
            }
        }catch(IllegalArgumentException e){
            Toast.makeText(this, "Favor no introducir fechas del futuro.", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private String calculateElapsedTime(LocalDate startingDate, LocalDate currentDate, LocalTime startingTime, LocalTime currentTime) {
        String displayString;
        if ((startingDate != null) && (currentDate != null)) {
            int hour, minutes;
            String year = String.valueOf(Period.between(startingDate, currentDate).getYears());
            String month = String.valueOf(Period.between(startingDate, currentDate).getMonths());
            String day = String.valueOf(Period.between(startingDate, currentDate).getDays());

            int startingHour = startingTime.getHour();
            int currentHour = currentTime.getHour();
            int startingMinutes = startingTime.getMinute();
            int currentMinutes = currentTime.getMinute();


            hour = hourRestraints(startingHour, currentHour);
            minutes = minuteRestraints(startingMinutes, currentMinutes);

            if (Period.between(startingDate, currentDate).getYears() == 0 && Period.between(startingDate, currentDate).getMonths() ==0
                    && Period.between(startingDate, currentDate).getDays() == 0 && hour == 0 && minutes == 0){
                displayString = "";
            }else {
                displayString = (year + " año(s), " + month + " mes(es), " + day + " día(s)\n     "
                        + String.valueOf(hour) + " hora(s) y " + String.valueOf(minutes) + " minuto(s)");
            }
        } else {
            displayString =  "";
        }
        return displayString;
    }
    /*Método: hourRestraints.
     * Entrada: 2 variables int.
     * Salida: int hour.
     * Función: Calcular las horas transcurridas, sin perder precisión, dadas las
     *  condiciones propias del formato de tiempo.*/
    private int hourRestraints(int startingHour, int currentHour){
        int hour = 0;
        if (startingHour > currentHour){
            hour = (24-startingHour) + currentHour;
        }if (startingHour < currentHour){
            hour = currentHour - startingHour;
        }if (startingHour == currentHour){
            hour = 0;
        }
        return hour;
    }
    /*Método: minuteRetraints.
     * Entrada: 2 variables int.
     * Salida: int minutes.
     * Función: Calcular los minutos transcurridos, sin perder precisión, dadas las
     *  condiciones propias del formato de tiempo.*/
    private int minuteRestraints(int startingMinutes, int currentMinutes) {
        int minutes = 0;
        if (startingMinutes > currentMinutes){
            minutes = (60 - startingMinutes) + currentMinutes;
        }if (startingMinutes < currentMinutes){
            minutes = currentMinutes - startingMinutes;
        }if (startingMinutes == currentMinutes){
            minutes = 0;
        }
        return minutes;
    }
    /*Método: refreshDisplays.
     * Entrada: -ninguna-
     * Salida: -ninguna-
     * Función: Actualizar los elementos de la interfaz gráfica
     *     con la información recolectada en los widgets y los resultados obtenidos.*/
    /*Método: refreshDisplays.
     * Entrada: -ninguna-
     * Salida: -ninguna-
     * Función: Actualizar los elementos de la interfaz gráfica
     *     con la información recolectada en los widgets y los resultados obtenidos.*/
    private void refreshDisplays() {
        mDateDisplay.setText(mDateFormat.format(mCalendar.getTime()));
        mTimeDisplay.setText(mTimeFormat.format(mCalendar.getTime()));
        mDateTimeDisplay.setText(calculateTime());
    }
}
