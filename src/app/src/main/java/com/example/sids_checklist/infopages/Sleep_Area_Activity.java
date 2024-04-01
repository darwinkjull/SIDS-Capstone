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
 * This class represents the activity for displaying information about sleeping areas.
 * It extends AppCompatActivity to provide compatibility with older Android versions.
 * The activity allows users to view information about sleeping areas and includes a button to navigate back to the Info_Page_Activity.
 */
public class Sleep_Area_Activity extends AppCompatActivity{

    /**
     * Called when the activity is starting.
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeping_area_layout);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button backButton = findViewById(R.id.InfoAreaBack);
        backButton.setOnClickListener(v-> startActivity(new Intent(Sleep_Area_Activity.this, Info_Page_Activity.class)));


        WebView webview = findViewById(R.id.webViewArea);
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/7cXwlpSJL08?si=tEJgSUQevfJp2Mya\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webview.loadData(video, "text/html","utf-8");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
    }
}
