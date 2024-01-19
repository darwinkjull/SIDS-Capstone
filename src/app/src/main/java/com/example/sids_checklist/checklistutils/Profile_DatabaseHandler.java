package com.example.sids_checklist.checklistutils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class Profile_DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "ProfileDatabase";
    private static final String PROFILE_TABLE = "Profiles";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String AGE = "age";
    private static final String PROFILE_COLOR = "profile_color";
    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + PROFILE_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT UNIQUE, "
            + AGE + " TEXT, "
            + PROFILE_COLOR + " TEXT)";
    private SQLiteDatabase db;

    public Profile_DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    // Allows us to add new profiles to the PROFILE_TABLE table
    public void insertProfile(ProfileModel profile) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, profile.getUsername());
        cv.put(AGE, profile.getAge());
        cv.put(PROFILE_COLOR, profile.getProfile_color());
        db.insert(PROFILE_TABLE, null, cv);
    }

    // Provides all of the user profiles currently in the PROFILE_TABLE table as an ArrayList
    @SuppressLint("Range")
    public List<ProfileModel> getAllProfiles() {
        List<ProfileModel> profileList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(PROFILE_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ProfileModel profile = new ProfileModel();
                        profile.setId(cur.getInt(cur.getColumnIndex(ID)));
                        profile.setUsername(cur.getString(cur.getColumnIndex(USERNAME)));
                        profile.setAge(cur.getString(cur.getColumnIndex(AGE)));
                        profile.setProfile_color(cur.getString(cur.getColumnIndex(PROFILE_COLOR)));
                        profileList.add(profile);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return profileList;
    }


    // Provides all of the usernames currently in the PROFILE_TABLE table as an ArrayList
    // Probably not the best way for this to be implemented, but is how I am going to do it for now.
    @SuppressLint("Range")
    public List<String> getAllUsernames() {
        List<String> usernameList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(PROFILE_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        String username = cur.getString(cur.getColumnIndex(USERNAME));
                        usernameList.add(username);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return usernameList;
    }

    // Provides the corresponding ID number for a username in the PROFILE_TABLE table
    // Since username is currently unique, there cannot be overlapping entries
    @SuppressLint("Range")
    public int getIDByUsername(String username) {
        String[] column = new String[]{ID};
        String[] row = new String[]{username};
        int id = 0;

        Cursor cur = db.query(PROFILE_TABLE, column, USERNAME + "=?", row, null, null, null);
        if (cur.moveToFirst()) {
            id = cur.getInt(cur.getColumnIndex(ID));
        }
        cur.close();

        return id;
    }

    @SuppressLint("Range")
    public String getUsernameByID(int id){
        String profileID = Integer.toString(id);

        String[] column = new String[]{USERNAME};
        String[] row = new String[]{profileID};
        String username = new String();

        Cursor cur = db.query(PROFILE_TABLE, column, ID + "=?", row, null, null, null);
        if (cur.moveToFirst()) {
            username = cur.getString(cur.getColumnIndex(USERNAME));
        }
        cur.close();

        return username;
    }

    // Updates all profile information for a given profile which already exists in the PROFILE_TABLE table
    public void updateProfile(int id, ProfileModel profile) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, profile.getUsername());
        cv.put(AGE, profile.getAge());
        cv.put(PROFILE_COLOR, profile.getProfile_color());
        db.update(PROFILE_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Updates only the username for a given profile which already exists in the PROFILE_TABLE table
    public void updateUsername(int id, String username) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        db.update(PROFILE_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }


    // Removes the target profile from the PROFILE_TABLE table
    public void deleteProfile(int id) {
        db.delete(PROFILE_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public ProfileModel getProfileInfoFromID(int id){
        ProfileModel profile = new ProfileModel();

        String[] row = new String[]{String.valueOf(id)};
        Cursor cur = db.query(PROFILE_TABLE, null, ID + "=?", row, null, null, null);
        if (cur.moveToFirst()) {
            profile.setId(id);
            profile.setUsername(cur.getString(cur.getColumnIndex(USERNAME)));
            profile.setAge(cur.getString(cur.getColumnIndex(AGE)));
            profile.setProfile_color(cur.getString(cur.getColumnIndex(PROFILE_COLOR)));
        }

        cur.close();
        return profile;
    }

    @SuppressLint("Range")
    public ProfileModel getProfileInfoFromUsername(String username){
        ProfileModel profile = new ProfileModel();

        String[] row = new String[]{username};
        Cursor cur = db.query(PROFILE_TABLE, null, USERNAME + "=?", row, null, null, null);
        if (cur.moveToFirst()) {
            profile.setId(cur.getInt(cur.getColumnIndex(ID)));
            profile.setUsername(username);
            profile.setAge(cur.getString(cur.getColumnIndex(AGE)));
            profile.setProfile_color(cur.getString(cur.getColumnIndex(PROFILE_COLOR)));
        }

        cur.close();
        return profile;
    }


}
