package com.example.sids_checklist.infopages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.R;

import java.util.Objects;

/**
 * This class represents the activity for displaying general information.
 * It extends AppCompatActivity to provide compatibility with older Android versions.
 * The activity sets up a WebView to display a YouTube video embedded in an iframe.
 * The video is loaded from a specific URL.
 * It also includes a back button to navigate back to the previous activity.
 */
public class General_Info_Activity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @SuppressLint("SetJavaScriptEnabled")
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
