package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottleDispenser bd = BottleDispenser.getInstance();

    Button lisaaRahaa;
    SeekBar lisattavaRahaMaara;
    TextView rahaaKoneessa;
    TextView tapahtuma;
    Spinner lista;
    Button kuitti;
    Button osta;
    Context context;


    ArrayList<Bottle> bottlesRaw;
    Bottle previousBuy;

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
        kuitti = (Button) findViewById(R.id.button2);
        osta = (Button) findViewById(R.id.button3);
        context = MainActivity.this;

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

    public void buyItem(View v) {
        Bottle selected = bottlesRaw.get(lista.getSelectedItemPosition());
        if (bd.getMoney() >= selected.getPrice()) {
            previousBuy = selected;
            bd.buyBottle(selected);
            updateCurrentEvent(selected.getName() + " tuli ulos");
            bottlesRaw.remove(selected);
            updateBottleList();
            updateMoney();
        } else {
            updateCurrentEvent("Ei tarpeeksi rahaa, lisää rahaa!");
        }

    }

    public void printReceipt(View v) {
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("kuitti.txt", Context.MODE_PRIVATE));
            String s = "Item: " + previousBuy.getName() + " Size: " + previousBuy.getSize() + "L Price: " + previousBuy.getPrice() + "€.";
            ows.write(s);
            ows.close();
            updateCurrentEvent("Kuitti kirjoitettu.");
        } catch (IOException e){
            Log.e("IOExeption", "Virhe tiedoston kirjoittamisessa");
        }
    }
}