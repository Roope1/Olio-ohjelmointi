package com.example.week7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    TextView text;
    EditText msg;
    EditText editable;
    EditText filename;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
        msg = (EditText) findViewById(R.id.TextField);
        editable = (EditText) findViewById(R.id.editField);
        filename = (EditText) findViewById(R.id.Tiedostonimi);
        context = MainActivity.this;

        msg.addTextChangedListener(new TextWatcher() {

            /* Automatically created stuff that breaks everything if deleted */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            /*---------------------------------------------------------------*/

            @Override
            public void afterTextChanged(Editable editable) {
                text.setText(msg.getText());
            }
        });

        text.setText("Button make app go brrr!");
        msg.setText("Syöttökenttä");

    }

    public void buttonPress(View v){
        text.setText(msg.getText());
        System.out.println("Hello World!");
    }

    public void loadText(View v){
        try {
            InputStream ins = context.openFileInput(filename.getText().toString());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s;
            while((s = br.readLine()) != null){
                editable.setText(s);
            }
            ins.close();
        } catch (IOException e){
            Log.e("IOExeption", "Virhe tiedoston lukemisessa.");
        }
    }
    public void saveText(View v){
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(String.valueOf(filename.getText()), Context.MODE_PRIVATE));
            String s = String.valueOf(editable.getText());
            ows.write(s);
            ows.close();
        } catch (IOException e){
            Log.e("IOExeption", "Virhe tiedoston kirjoittamisessa");
        }
    }
}