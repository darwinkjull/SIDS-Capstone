package com.example.sids_checklist.infopages;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.R;

import java.util.Objects;

public class General_Info_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_care);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button backButton = findViewById(R.id.InfoGenBack);

        backButton.setOnClickListener(v-> startActivity(new Intent(General_Info_Activity.this, Info_Page_Activity.class)));

        WebView webview = findViewById(R.id.webViewGen);
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/TwEU44FEGJU?si=NntSQotQssaPwZqQ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webview.loadData(video, "text/html","utf-8");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
    }
}
