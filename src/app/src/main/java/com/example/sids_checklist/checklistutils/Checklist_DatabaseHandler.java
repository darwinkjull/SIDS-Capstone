package com.example.sids_checklist.checklistutils;

/*
Using SQLite to organize database - Android offers/Uses SQLite by
default and had built in tools in Android Studio to query with SQLite.

Database Handler will deal with all queries to the database

TODO: Add static items that user cannot delete (populate database)

 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.ChecksSdkIntAtLeast;

import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class Checklist_DatabaseHandler extends SQLiteOpenHelper {
    // Define Database parameters and query for creating database table
    private static final int VERSION = 5; //Now on version 2, due to new foreign key column
    private static final String NAME = "ChecklistDatabase";
    private static final String CHECKLIST_TABLE = "checklist";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";
    private static final String PROFILE_ID = "profile_id";


    private static final String CREATE_CHECKLIST_TABLE_PREFIX = "CREATE TABLE ";
    private static final String CREATE_CHECKLIST_TABLE_SUFFIX = CHECKLIST_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ITEM + " TEXT, "
            + STATUS + " INTEGER)";


    private SQLiteDatabase db;
    private Profile_DatabaseHandler profile_db;
    public Checklist_DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    // create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("tag", "Now calling onCreate for Checklist");
        List<String> userList;
        userList = profile_db.getAllUsernames();

        for (String i : userList) {
            String newQuery = new String();
            newQuery = CREATE_CHECKLIST_TABLE_PREFIX + i + "_" + CREATE_CHECKLIST_TABLE_SUFFIX +
            Log.d("DatabaseHandler", "SQL Query: " + newQuery);
            db.execSQL(newQuery); // execute query
        }
    }

    // upgrade the table to the new version and drop the old table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("tag", "Now calling onUpgrade for Checklist");

        List<String> userList;
        userList = profile_db.getAllUsernames();

        for (String i : userList) {
            String newQuery;
            newQuery = i + "_" + CHECKLIST_TABLE;
            db.execSQL("DROP TABLE IF EXISTS " + newQuery); // drop the old version
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
    public void insertItem(ChecklistModel item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item.getItem()); // get item name
        cv.put(STATUS, 0); // set item as "unchecked"
        cv.put(PROFILE_ID, item.getProfile_id());
        db.insert(CHECKLIST_TABLE, null, cv); // insert new item to database
    }

    // Ability to get current items from the database (SQL)
    @SuppressLint("Range")
    public List<ChecklistModel> getAllItems(int profile_id) {
        List<ChecklistModel> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction(); // ensure safe storage of database even if interrupted
        try {
            cur = db.query(CHECKLIST_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        // Check to see if entry is for the selected profile
                        if (cur.getInt(cur.getColumnIndex(PROFILE_ID)) == profile_id) {
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
    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(CHECKLIST_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Update the name of the checklist item (SQL)
    public void updateItem(int id, String item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item);
        db.update(CHECKLIST_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a checklist item (SQL)
    public void deleteItem(int id) {
        db.delete(CHECKLIST_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }
}


