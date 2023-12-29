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
            + USERNAME + " TEXT)";
    private SQLiteDatabase db;

    public Profile_DatabaseHandler(Context context){super(context, NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(CREATE_PROFILE_TABLE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertProfile(ProfileModel profile){
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, profile.getUsername());
        db.insert(PROFILE_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<ProfileModel> getProfiles(){
        List<ProfileModel> profileList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(PROFILE_TABLE, null, null, null, null, null, null, null);
            if (cur != null){
                if (cur.moveToFirst()){
                    do{
                        ProfileModel profile = new ProfileModel();
                        profile.setId(cur.getInt(cur.getColumnIndex(ID)));
                        profile.setUsername(cur.getString(cur.getColumnIndex(USERNAME)));
                        profileList.add(profile);
                    }while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return profileList;
    }

    public void updateUsername(int id, String username){
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        db.update(PROFILE_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteProfile(int id){
        db.delete(PROFILE_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }



}
