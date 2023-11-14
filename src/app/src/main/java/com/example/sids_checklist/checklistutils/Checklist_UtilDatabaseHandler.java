package com.example.sids_checklist.checklistutils;

/*

 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Checklist_UtilDatabaseHandler extends SQLiteOpenHelper {
    // Define Database parameters and query for creating database table
    private static final int VERSION = 1;
    private static final String NAME = "UtilChecklistDatabase";
    private static final String CHECKLIST_TABLE = "DisplayChecklist";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";

    private static final String SESSION = "session";
    private static final String CREATE_CHECKLIST_TABLE = "CREATE TABLE " + CHECKLIST_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SESSION  + " TEXT, "
            + ITEM  + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase disp_db;
    public Checklist_UtilDatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    // create the table
    @Override
    public void onCreate(SQLiteDatabase disp_db){
        disp_db.execSQL(CREATE_CHECKLIST_TABLE); // execute query
    }

    // upgrade the table to the new version and drop the old table
    @Override
    public void onUpgrade(SQLiteDatabase disp_db, int oldVersion, int newVersion){
        disp_db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        onCreate(disp_db); // create upgraded table
    }

    // open the database to write to
    public void openDatabase(){
        disp_db = this.getWritableDatabase(); // open as a writable database since we want to update

        /* IF THE DATABASE EVER BUGS OR NEEDS TO BE CLEARED, UNCOMMENT THIS FOR ONE CYCLE*/
        // disp_db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        // onCreate(disp_db); // create upgraded table
    }

    // ability to add new items to the database (SQL)
    public void insertItem(String name, int status, String session){
        ContentValues cv = new ContentValues();
        cv.put(ITEM, name);
        cv.put(STATUS, status);
        cv.put(SESSION, session);
        disp_db.insert(CHECKLIST_TABLE, null, cv); // insert new item to database
    }
}
