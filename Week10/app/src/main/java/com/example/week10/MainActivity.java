package com.example.week10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button search;
    EditText URLbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        search = (Button) findViewById(R.id.button);
        URLbar = (EditText) findViewById(R.id.URLenter);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                URLbar.setText(url);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
    }


    public void search(View v){
        String url = URLbar.getText().toString();

        // TODO Add url to history
        loadPage(url);
    }

    public void reloadPage(View v){
        loadPage(webView.getUrl());
    }

    void loadPage(String url){
        if(!url.matches("(?i)^https?://.*")){
            url = "http://" + url;
        }

        webView.loadUrl(url);
        URLbar.setText(webView.getUrl());
    }
}