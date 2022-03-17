package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottleDispenser bd = BottleDispenser.getInstance();

    Button lisaaRahaa;
    SeekBar lisattavaRahaMaara;
    TextView rahaaKoneessa;
    TextView tapahtuma;
    Spinner lista;

    ArrayList<Bottle> bottlesRaw;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lisaaRahaa = (Button) findViewById(R.id.button);
        lisattavaRahaMaara = (SeekBar) findViewById(R.id.seekBar);
        rahaaKoneessa = (TextView) findViewById(R.id.textView);
        tapahtuma = (TextView) findViewById(R.id.textView2);
        lista = (Spinner) findViewById(R.id.spinner);


        lisaaRahaa.setText("Lisää 0.00€");
        updateMoney();
        updateBottleList();

        lisattavaRahaMaara.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lisaaRahaa.setText("Lisää " + df.format(lisattavaRahaMaara.getProgress() / 10) +"€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void addMoneyToDispenser(View v){
        bd.addMoney(lisattavaRahaMaara.getProgress() / 10);
        updateCurrentEvent("Lisättiin " + lisattavaRahaMaara.getProgress() / 10 + "€ koneeseen.");
        lisattavaRahaMaara.setProgress(0);
        updateMoney();
    }

    void updateMoney(){
        System.out.println("Money updated");
        rahaaKoneessa.setText("Rahaa " + df.format(bd.getMoney()) + "€");
    }

    void updateCurrentEvent(String msg){
        tapahtuma.setText(msg);
    }

    void updateBottleList() {
        bottlesRaw = bd.getBottleList();
        ArrayList<String> bottleNames = new ArrayList<String>();
        for(Bottle bottle : bottlesRaw) {
            bottleNames.add(bottle.getName() + " " + bottle.getSize() + "l (" + df.format(bottle.getPrice()) + "€)");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bottleNames);
        lista.setAdapter(arrayAdapter);
    }
}