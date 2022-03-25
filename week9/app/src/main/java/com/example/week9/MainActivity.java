package com.example.week9;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    TheaterHandler th = TheaterHandler.getInstance();

    Context context;
    Spinner theaterNameList;
    ListView listOfMovies;
    Theater selectedTheater;
    EditText datePick;
    EditText startTime;
    EditText endTime;
    Button applyFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        th.fetchTheaters();
        context = MainActivity.this;
        theaterNameList = (Spinner) findViewById(R.id.spinner);
        listOfMovies = (ListView) findViewById(R.id.listOfMovies);
        datePick = (EditText) findViewById(R.id.DatePick);
        startTime = (EditText) findViewById(R.id.timeStart);
        endTime = (EditText) findViewById(R.id.timeEnd);
        applyFilters = (Button) findViewById(R.id.button);

        // set the date to current date
        datePick.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()).toString());
        startTime.setText("00:00");
        endTime.setText("23:59");
        updateTheaterList();

        //listen to selection change in theater selection
        theaterNameList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int itemID = theaterNameList.getSelectedItemPosition();
                selectedTheater = th.theaters.get(itemID);
                updateMovieList();
            }

            @Override  // Auto generated
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    void updateTheaterList(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, th.getTheaterNames());
        theaterNameList.setAdapter(arrayAdapter);
    }

    void updateMovieList(){
        ArrayList<String> movies = th.getMoviesByTheaterAndDate(selectedTheater.getId(), datePick.getText().toString());
        movies = th.filterByTime(movies, startTime.getText().toString(), endTime.getText().toString());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies);
        listOfMovies.setAdapter(arrayAdapter);

    }

    public void applyFilters(View v){
        updateMovieList();
    }
}