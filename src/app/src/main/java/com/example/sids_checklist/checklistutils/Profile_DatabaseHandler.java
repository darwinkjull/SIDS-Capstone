package com.example.sids_checklist.checklistutils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A handler for managing profile data in the database.
 * Provides methods for inserting, retrieving, updating, and deleting profiles.
 */
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

    /**
     * Constructor for the Profile_DatabaseHandler class.
     *
     * @param context The context in which the database will be used.
     */
    public Profile_DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    /**
     * create the table
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    /**
     * replace the table with a new one if there is a version update
     *
     * @param db The database.
     * @param OldVersion The old database version.
     * @param NewVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(db);
    }

    /**
     * open the desired database
     */
    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    // Allows us to add new profiles to the PROFILE_TABLE table
    /**
     * Inserts a new profile into the database.
     *
     * @param profile The ProfileModel object containing the profile information to be inserted.
     */
    public void insertProfile(ProfileModel profile) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, profile.getUsername());
        cv.put(AGE, profile.getAge());
        cv.put(PROFILE_COLOR, profile.getProfile_color());
        db.insert(PROFILE_TABLE, null, cv);
    }

    // Provides all of the user profiles currently in the PROFILE_TABLE table as an ArrayList
    /**
     * Retrieves all profiles from the database.
     *
     * @return A List containing all ProfileModel objects representing the profiles in the database.
     */
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

    /**
     * Retrieves all usernames from the database.
     *
     * @return A List containing all usernames in the PROFILE_TABLE.
     */
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

    /**
     * Retrieves all user IDs from the database.
     *
     * @return A List containing all user IDs in the PROFILE_TABLE.
     */
   @SuppressLint("Range")
    public List<Integer> getAllID() {
        List<Integer> IDList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(PROFILE_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        Integer userID = cur.getInt(cur.getColumnIndex(ID));
                        IDList.add(userID);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return IDList;
    }

    /**
     * Retrieves the ID corresponding to the given username from the database.
     *
     * @param username The username for which the ID is to be retrieved.
     * @return The ID corresponding to the given username. Returns 0 if the username is not found.
     */
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

    /**
     * Retrieves the username corresponding to the given ID from the database.
     *
     * @param id The ID for which the username is to be retrieved.
     * @return The username corresponding to the given ID. Returns an empty string if the ID is not found.
     */
    @SuppressLint("Range")
    public String getUsernameByID(int id){
        String profileID = Integer.toString(id);

        String[] column = new String[]{USERNAME};
        String[] row = new String[]{profileID};
        String username = "";

        Cursor cur = db.query(PROFILE_TABLE, column, ID + "=?", row, null, null, null);
        if (cur.moveToFirst()) {
            username = cur.getString(cur.getColumnIndex(USERNAME));
        }
        cur.close();

        return username;
    }

    /**
     * Updates all profile information for the given profile ID with the provided profile data.
     *
     * @param id      The ID of the profile to be updated.
     * @param profile The updated profile information.
     */
    public void updateProfile(int id, ProfileModel profile) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, profile.getUsername());
        cv.put(AGE, profile.getAge());
        cv.put(PROFILE_COLOR, profile.getProfile_color());
        db.update(PROFILE_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Updates only the username for a given profile which already exists in the PROFILE_TABLE table
    /**
     * Updates only the username for the given profile ID.
     *
     * @param id       The ID of the profile whose username is to be updated.
     * @param username The new username.
     */
    public void updateUsername(int id, String username) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        db.update(PROFILE_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * Deletes the profile with the specified ID from the PROFILE_TABLE table.
     *
     * @param id The ID of the profile to be deleted.
     */
    public void deleteProfile(int id) {
        db.delete(PROFILE_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * Retrieves profile information for the given profile ID from the PROFILE_TABLE table.
     *
     * @param id The ID of the profile.
     * @return The profile information corresponding to the given ID.
     */
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

    /**
     * Retrieves profile information for the given username from the PROFILE_TABLE table.
     *
     * @param username The username of the profile.
     * @return The profile information corresponding to the given username.
     */
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
