package com.example.garci.calculadoradeedad;

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
    private Spinner spnDiaInicio;
    private Spinner spnMesInicio;
    private Spinner spnAnnoInicio;

    private Spinner spnDiaFinal;
    private Spinner spnMesFinal;
    private Spinner spnAnnoFinal;

    private String[] dias = {"1","2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] mes = {"1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] anno = {"1900","1901","1902","1903","1904","1905","1906","1907","1908","1909",
            "1910","1911","1912","1913","1914","1915","1916","1917","1918","1919","1920","1921","1922",
            "1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935",
            "1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948",
            "1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961",
            "1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974",
            "1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987",
            "1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000",
            "2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013",
            "2014","2015","2016","2017","2018","2019","2020"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.myEditText);
        findViewById(R.id.myButton).setOnClickListener(this);

        spnDiaInicio = (Spinner) findViewById(R.id.spnDiaInicio);
        spnMesInicio = (Spinner) findViewById(R.id.spnMesInicio);
        spnAnnoInicio = (Spinner) findViewById(R.id.spnAnnoInicio);

        spnDiaFinal = (Spinner) findViewById(R.id.spnDiaFinal);
        spnMesFinal = (Spinner) findViewById(R.id.spnMesFinal);
        spnAnnoFinal = (Spinner) findViewById(R.id.spnAnnoFinal);

        ArrayAdapter<String> adapterDiasInicio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dias);
        adapterDiasInicio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDiaInicio.setAdapter(adapterDiasInicio);

        ArrayAdapter<String> adapterMesInicio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mes);
        adapterMesInicio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMesInicio.setAdapter(adapterMesInicio);

        ArrayAdapter<String> adapterAnnoInicio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anno);
        adapterAnnoInicio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAnnoInicio.setAdapter(adapterAnnoInicio);


        ArrayAdapter<String> adapterDiasFinal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dias);
        adapterDiasFinal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDiaFinal.setAdapter(adapterDiasFinal);

        ArrayAdapter<String> adapterMesFinal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mes);
        adapterMesFinal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMesFinal.setAdapter(adapterMesFinal);

        ArrayAdapter<String> adapterAnnoFinal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anno);
        adapterAnnoFinal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAnnoFinal.setAdapter(adapterAnnoFinal);

        findViewById(R.id.myButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        getDate();
        //Toast.makeText(this, spnDia.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    public void getDate(){
        int day = Integer.valueOf(spnDiaInicio.getSelectedItem().toString());
        int month = Integer.valueOf(spnMesInicio.getSelectedItem().toString());
        int year = Integer.valueOf(spnAnnoInicio.getSelectedItem().toString());

        int dayFin = Integer.valueOf(spnDiaFinal.getSelectedItem().toString());
        int monthFin = Integer.valueOf(spnMesFinal.getSelectedItem().toString());
        int yearFin = Integer.valueOf(spnAnnoFinal.getSelectedItem().toString());

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.of(yearFin, monthFin, dayFin);

        String time = calculateAge(birthDate, currentDate);
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
    }
    /*@Override
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
    }*/
    public static String calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return String.valueOf(Period.between(birthDate, currentDate).getYears()) +" Años " + String.valueOf(Period.between(birthDate, currentDate).getMonths()
            +" Meses " + String.valueOf(Period.between(birthDate, currentDate).getDays()) + " Días ");
        } else {
            return "Valor no válido.";
        }
    }
}
