package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    BottleDispenser bd = BottleDispenser.getInstance();

    Button lisaaRahaa;
    SeekBar lisattavaRahaMaara;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lisaaRahaa = (Button) findViewById(R.id.button);
        lisattavaRahaMaara = (SeekBar) findViewById(R.id.seekBar);


        lisaaRahaa.setText("Lisää 0.00€");

        lisattavaRahaMaara.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lisaaRahaa.setText("Lisää " + df.format(lisattavaRahaMaara.getProgress() / 5) +"€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void addMoneyToDispenser(View v){
        bd.addMoney(lisattavaRahaMaara.getProgress() / 5);
    }

}