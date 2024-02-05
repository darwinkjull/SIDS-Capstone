package com.example.sids_checklist;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Topics {
    private String title;
    private int image;

    public Topics(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public Topics() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<Topics> initTopics(){
        ArrayList<Topics> list = new ArrayList<>();

        list.add(new Topics("Sleep Location", R.drawable.baseline_location_on_24));
        list.add(new Topics("Sleep Area and Position", R.drawable.crib_icon));
        list.add(new Topics("General Care", R.drawable.baseline_baby_changing_station_24));
        list.add(new Topics("Additional Resources", R.drawable.info_icon));
        return list;
    }
}
