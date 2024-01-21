package com.example.sids_checklist.infopages;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.R;

public class Sleep_Area_Activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeping_area_layout);
        Button backButton = findViewById(R.id.InfoAreaBack);


        WebView webview = findViewById(R.id.webViewArea);
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/7cXwlpSJL08?si=Dca9pebWtGUtj9F6\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webview.loadData(video, "text/html","utf-8");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
    }
}
