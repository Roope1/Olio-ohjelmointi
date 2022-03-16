package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    BottleDispenser bd = BottleDispenser.getInstance();

    Button lisaaRahaa;
    SeekBar lisattavaRahaMaara;
    TextView rahaaKoneessa;
    TextView tapahtuma;
    Spinner lista;

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

}