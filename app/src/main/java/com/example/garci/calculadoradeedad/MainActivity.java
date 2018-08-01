package com.example.garci.calculadoradeedad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        /*mEditText = (EditText) findViewById(R.id.myEditText);
        findViewById(R.id.myButton).setOnClickListener(this);



        findViewById(R.id.myButton).setOnClickListener(this);*/
    }
    private void init(){
        findViewById(R.id.myCompareTwoDatesBtn).setOnClickListener(this);
        findViewById(R.id.myCompareWithCurrentTimeBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myCompareWithCurrentTimeBtn:
                startActivity(new Intent(this, TwoDatesCompActivity.class));
        }
    }

    /*public void getDate(){


        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.of(yearFin, monthFin, dayFin);

        String time = calculateAge(birthDate, currentDate);
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
    }

    public static String calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return String.valueOf(Period.between(birthDate, currentDate).getYears()) +" Años " + String.valueOf(Period.between(birthDate, currentDate).getMonths()
            +" Meses " + String.valueOf(Period.between(birthDate, currentDate).getDays()) + " Días ");
        } else {
            return "Valor no válido.";
        }
    }*/
}
