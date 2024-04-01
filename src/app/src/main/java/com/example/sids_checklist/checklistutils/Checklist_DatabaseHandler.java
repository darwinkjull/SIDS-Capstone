package com.example.sids_checklist.checklistutils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sids_checklist.Tips;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Checklist_DatabaseHandler is a SQLiteOpenHelper class responsible for managing the checklist
 * database, including creating, upgrading, opening, and interacting with database tables.
 */
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

    private final Context context;
    private SQLiteDatabase db;
    private Profile_DatabaseHandler profile_db;

    /**
     * Constructor for Checklist_DatabaseHandler.
     *
     * @param context the database
     */
    public Checklist_DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    /**
     * Handle the view creation
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        List<ProfileModel> userList;
        profile_db = new Profile_DatabaseHandler(context.getApplicationContext());
        profile_db.openDatabase();
        userList = profile_db.getAllProfiles();

        for (ProfileModel i : userList) {
            String newQuery = CREATE_CHECKLIST_TABLE_PREFIX + i.getId() + CREATE_CHECKLIST_TABLE_SUFFIX;
            db.execSQL(newQuery); // execute query
        }
    }

    /**
     * Upgrade the database table if a new version exists.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
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

    /**
     * open the database to write to.
     */
    public void openDatabase() {
        db = this.getWritableDatabase(); // open as a writable database since we want to update

        /* IF THE DATABASE EVER BUGS, UNCOMMENT THIS FOR ONE CYCLE*/
        //db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        //onCreate(db); // create upgraded table
    }

    /**
     * Insert new items to the select database
     *
     * @param item the item to be inserted
     * @param profile_ID the current profile id
     */
    public void insertItem(ChecklistModel item, int profile_ID) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item.getItem()); // get item name
        cv.put(STATUS, 0); // set item as "unchecked"

        String tableName = CHECKLIST_TABLE_PREFIX + profile_ID + CHECKLIST_TABLE_SUFFIX;
        db.insert(tableName, null, cv); // insert new item to database
    }

    /**
     * obtain a list of the items within the checklist database
     *
     * @param profileID the current profile id
     * @return itemList - a list of checklist items of the ChecklistModel class
     */
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

    /**
     * Update the status of the checklist item (checked/unchecked) (SQL)
     *
     * @param id the id of the item in the list
     * @param status the status of the item
     * @param profileID the current profile id
     */
    public void updateStatus(int id, int status, int profileID) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);

        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.update(tableName, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * Update the name of the checklist item (SQL)
     *
     * @param id the id of the current item
     * @param item the text for the item
     * @param profileID the current profile id
     */
    public void updateItem(int id, String item, int profileID) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item);

        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.update(tableName, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * Delete a checklist item (SQL)
     *
     * @param id the id of the item
     * @param profileID the profile id
     */
    public void deleteItem(int id, int profileID) {
        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.delete(tableName, ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * create a new table in the database
     *
     * @param profileID the profile id
     */
    public void createTable(int profileID){
        db.execSQL(CREATE_CHECKLIST_TABLE_PREFIX + profileID + CREATE_CHECKLIST_TABLE_SUFFIX);
    }

    /**
     * delete a table from the database
     *
     * @param profileID the profile id
     */
    public void deleteTable(int profileID){
        String tableName = CHECKLIST_TABLE_PREFIX + profileID + CHECKLIST_TABLE_SUFFIX;
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    /**
     * insert a tip from the tips menu into the database
     *
     * @param item the item
     * @param profile_ID the profile id
     */
    public void insertTip(Tips item, int profile_ID) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item.getItem()); // get item name
        cv.put(STATUS, 0); // set item as "unchecked"

        String tableName = CHECKLIST_TABLE_PREFIX + profile_ID + CHECKLIST_TABLE_SUFFIX;
        db.insert(tableName, null, cv); // insert new item to database
    }
}



