package com.example.sids_checklist.checklistmodel;

/**
 * The ProfileModel class represents a profile with its ID, username, age, and profile color.
 */
public class ProfileModel {
    private int id;
    private String username, age, profile_color;

    /**
     * Retrieves the ID of the profile.
     *
     * @return The ID of the profile.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the profile.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the username of the profile.
     *
     * @return The username of the profile.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the profile.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the age of the profile.
     *
     * @return The age of the profile.
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age of the profile.
     *
     * @param age The age to set.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Retrieves the profile color of the profile.
     *
     * @return The profile color of the profile.
     */
    public String getProfile_color() {
        return profile_color;
    }

    /**
     * Sets the profile color of the profile.
     *
     * @param profile_color The profile color to set.
     */
    public void setProfile_color(String profile_color) {
        this.profile_color = profile_color;
    }
}
