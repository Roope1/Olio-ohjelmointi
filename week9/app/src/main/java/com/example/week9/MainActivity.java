package com.example.week9;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



public class MainActivity extends AppCompatActivity {
    TheaterHandler th = TheaterHandler.getInstance();

    Context context;
    Spinner theaterNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        th.fetchTheaters();
        context = MainActivity.this;
        theaterNameList = (Spinner) findViewById(R.id.spinner);
        updateTheaterList();
    }

    void updateTheaterList(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, th.getTheaterNames());
        theaterNameList.setAdapter(arrayAdapter);
    }
}