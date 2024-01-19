package com.example.sids_checklist.checklistmodel;

public class ProfileModel {
    private int id;
    private String username, age, profile_color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfile_color() {
        return profile_color;
    }

    public void setProfile_color(String profile_color) {
        this.profile_color = profile_color;
    }
}
