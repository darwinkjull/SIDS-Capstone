package com.example.sids_checklist.checklistutils;

/*
Using SQLite to organize database - Android offers/Uses SQLite by
default and had built in tools in Android Studio to query with SQLite.

Database Handler will deal with all queries to the database

TODO: Add static items that user cannot delete (populate database)

 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.ChecksSdkIntAtLeast;

import com.example.sids_checklist.Checklist_Activity;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class Checklist_DatabaseHandler extends SQLiteOpenHelper {
    // Define Database parameters and query for creating database table
    private static final int VERSION = 1; //Should be kept at 1 until release
    private static final String NAME = "ChecklistDatabase";
    private static final String CHECKLIST_TABLE_PREFIX = "profile_";
    private static final String CHECKLIST_TABLE_SUFFIX = "_checklist";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";
    private static final String CREATE_CHECKLIST_TABLE_PREFIX = "CREATE TABLE " + CHECKLIST_TABLE_PREFIX;
    private static final String CREATE_CHECKLIST_TABLE_SUFFIX = CHECKLIST_TABLE_SUFFIX + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ITEM + " TEXT, "
            + STATUS + " INTEGER)";

    private Context context;
    private SQLiteDatabase db;
    private Profile_DatabaseHandler profile_db;
    public Checklist_DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    // create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("tag", "Now calling onCreate for Checklist");
        List<ProfileModel> userList;
        profile_db = new Profile_DatabaseHandler(context.getApplicationContext());
        profile_db.openDatabase();
        userList = profile_db.getAllProfiles();

        for (ProfileModel i : userList) {
            String newQuery = CREATE_CHECKLIST_TABLE_PREFIX + i.getId() + CREATE_CHECKLIST_TABLE_SUFFIX;
            Log.d("DatabaseHandler", "SQL Query: " + newQuery);
            db.execSQL(newQuery); // execute query
        }
    }

    // upgrade the table to the new version and drop the old table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<ProfileModel> userList;
        profile_db = new Profile_DatabaseHandler(context.getApplicationContext());
        profile_db.openDatabase();
        userList = profile_db.getAllProfiles();

        for (ProfileModel i : userList) {
            String tableName;
            tableName = CHECKLIST_TABLE_PREFIX + i.getId() + CHECKLIST_TABLE_SUFFIX;
            db.execSQL("DROP TABLE IF EXISTS " + tableName); // drop the old version
            onCreate(db); // create upgraded table
        }
    }

    // open the database to write to
    public void openDatabase() {
        db = this.getWritableDatabase(); // open as a writable database since we want to update

        /* IF THE DATABASE EVER BUGS, UNCOMMENT THIS FOR ONE CYCLE*/
        //db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        //onCreate(db); // create upgraded table
    }

    // ability to add new items to the database (SQL)
    public void insertItem(ChecklistModel item, int profile_ID) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item.getItem()); // get item name
        cv.put(STATUS, 0); // set item as "unchecked"

        String tableName = CHECKLIST_TABLE_PREFIX + profile_ID + CHECKLIST_TABLE_SUFFIX;
        db.insert(tableName, null, cv); // insert new item to database
    }

    // Ability to get current items from the database (SQL)
    @SuppressLint("Range")
    public List<ChecklistModel> getAllItems(int profileID) {
        List<ChecklistModel> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction(); // ensure safe storage of database even if interrupted
        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;

        try {
            cur = db.query(tableName, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        {
                            ChecklistModel item = new ChecklistModel();
                            item.setId(cur.getInt(cur.getColumnIndex(ID)));
                            item.setItem(cur.getString(cur.getColumnIndex(ITEM)));
                            item.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                            itemList.add(item);
                        }
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return itemList;
    }

    // Update the status of the checklist item (checked/unchecked) (SQL)
    public void updateStatus(int id, int status, int profileID) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);

        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.update(tableName, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Update the name of the checklist item (SQL)
    public void updateItem(int id, String item, int profileID) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item);

        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.update(tableName, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a checklist item (SQL)
    public void deleteItem(int id, int profileID) {
        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.delete(tableName, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void createTable(int profileID){
        db.execSQL(CREATE_CHECKLIST_TABLE_PREFIX + profileID + CREATE_CHECKLIST_TABLE_SUFFIX);
    }

    public void deleteTable(int profileID){
        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}


