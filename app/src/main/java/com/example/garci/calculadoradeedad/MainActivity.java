package com.example.garci.calculadoradeedad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //Habilitando el OnClickListener de estos elemntos de la interfaz gráfica.
    private void init(){
        findViewById(R.id.myCompareTwoDatesBtn).setOnClickListener(this);
        findViewById(R.id.myCompareWithCurrentTimeBtn).setOnClickListener(this);
    }
    //Determinando los eventos a tomar parte al hacer click en los diferentes elementos.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myCompareTwoDatesBtn:
                startActivity(new Intent(this, TwoDatesActivity.class));
                break;
            case R.id.myCompareWithCurrentTimeBtn:
                startActivity(new Intent(this, SingleDateActivity.class));
                break;
        }
    }
}
