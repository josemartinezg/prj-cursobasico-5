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

    }
    private void init(){
        findViewById(R.id.myCompareTwoDatesBtn).setOnClickListener(this);
        findViewById(R.id.myCompareWithCurrentTimeBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myCompareTwoDatesBtn:
                startActivity(new Intent(this, SingleDateActivity.class));
                break;
            case R.id.myCompareWithCurrentTimeBtn:
                startActivity(new Intent(this, TwoDatesCompActivity.class));
                break;
        }
    }

}
