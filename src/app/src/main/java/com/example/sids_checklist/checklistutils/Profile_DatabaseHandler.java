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
    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + PROFILE_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT UNIQUE)";
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

    // Updates the username of a given profile in the PROFILE_TABLE table
    public void updateUsername(int id, String username) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        db.update(PROFILE_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Removes the target profile from the PROFILE_TABLE table
    public void deleteProfile(int id) {
        db.delete(PROFILE_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }


}
