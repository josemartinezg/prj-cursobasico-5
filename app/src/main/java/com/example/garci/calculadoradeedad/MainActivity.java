package com.example.garci.calculadoradeedad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.myEditText);
        findViewById(R.id.myButton).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String date = mEditText.getText().toString();
        if (date != null){
            String dateArray[] = date.split("/");
            int day = Integer.valueOf(dateArray[0]);
            int month = Integer.valueOf(dateArray[1]);
            int year = Integer.valueOf(dateArray[2]);

            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            if (date.indexOf("/") != 2 || date.lastIndexOf("/") !=  5 || date.length() != 10){
                Toast.makeText(this, "Error. Formato Incorrecto.", Toast.LENGTH_SHORT).show();
            }else {
                if ((day > 0 && day < 31) || (month > 0 && month < 12) || (year > 1900 && year < currentDate.getYear())){
                    int age = calculateAge(birthDate, currentDate);
                    Toast.makeText(this, String.valueOf(age), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error. Formato Incorrecto.", Toast.LENGTH_SHORT).show();
                }

            }
        }else{
            Toast.makeText(this, "Favor introducri datos.", Toast.LENGTH_SHORT).show();
        }
    }
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
