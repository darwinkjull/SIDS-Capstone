package com.example.sids_checklist.checklistutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class Profile_DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "ProfileDatabase";
    private static final String PROFILE_TABLE = "Profiles";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + PROFILE_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT)";

    public Profile_DatabaseHandler(Context context){super(context, NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(CREATE_PROFILE_TABLE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(db);
    }


}
