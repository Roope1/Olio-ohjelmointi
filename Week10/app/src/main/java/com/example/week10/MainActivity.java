package com.example.week10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button search;
    EditText URLbar;
    String previousPageUrl;
    String nextPageUrl;

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
                previousPageUrl = URLbar.getText().toString();
                URLbar.setText(url);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
    }


    public void search(View v){
        String url = URLbar.getText().toString();

        loadPage(url);
    }

    public void reloadPage(View v){
        loadPage(webView.getUrl());
    }

    void loadPage(String url){
        System.out.println(url);

        if(!url.matches("(?i)^https?://.*") && !url.equals("index.html")){
            url = "http://" + url;
        }

        if(url.equals("index.html")) {
            url = "file:///android_asset/index.html";
        }
        webView.loadUrl(url);
        URLbar.setText(webView.getUrl());
    }

    public void previousPage(View v){
        if(previousPageUrl != null){
            nextPageUrl = URLbar.getText().toString();
            loadPage(previousPageUrl);
        }
    }
    public void nextPage(View v){
        if(nextPageUrl != null){
            previousPageUrl = URLbar.getText().toString();
            loadPage(nextPageUrl);
        }
    }

    public void JSshoutOut(View v){
        webView.evaluateJavascript("javascript:shoutOut()", null);
    }
    public void JSinitialize(View v){
        webView.evaluateJavascript("javascript:initialize()", null);
    }
}