package com.example.sids_checklist.checklistutils;

/*

 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Checklist_UtilDatabaseHandler extends SQLiteOpenHelper {
    // Define Database parameters and query for creating database table
    private static final int VERSION = 1;
    private static final String NAME = "UtilChecklistDatabase";
    private static final String CHECKLIST_TABLE = "_displayChecklist";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";

    private static final String SESSION = "session";
    private static final String CREATE_CHECKLIST_TABLE_PREFIX = "CREATE TABLE ";
    private static final String CREATE_CHECKLIST_TABLE_SUFFIX = CHECKLIST_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SESSION + " TEXT, "
            + ITEM + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase disp_db;

    private Profile_DatabaseHandler profile_db;
    public Checklist_UtilDatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    // create the table
    @Override
    public void onCreate(SQLiteDatabase disp_db) {
        Log.d("tag", "Now calling onCreate for Display Checklist");
        List<String> userList;
        userList = profile_db.getAllUsernames();

        for (String i : userList) {
            String newQuery = new String();
            newQuery = CREATE_CHECKLIST_TABLE_PREFIX + i + CREATE_CHECKLIST_TABLE_SUFFIX +
                    Log.d("DatabaseHandler", "SQL Query: " + newQuery);
            disp_db.execSQL(newQuery); // execute query
        }
    }

    // upgrade the table to the new version and drop the old table
    @Override
    public void onUpgrade(SQLiteDatabase disp_db, int oldVersion, int newVersion) {
        Log.d("tag", "Now calling onUpgrade for Display Checklist");

        List<String> userList;
        userList = profile_db.getAllUsernames();

        for (String i : userList) {
            String tableName;
            tableName = i + CHECKLIST_TABLE;
            disp_db.execSQL("DROP TABLE IF EXISTS " + tableName); // drop the old version
            onCreate(disp_db); // create upgraded table
        }
    }

    // open the database to write to
    public void openDatabase() {
        disp_db = this.getWritableDatabase(); // open as a writable database since we want to update

        /* IF THE DATABASE EVER BUGS OR NEEDS TO BE CLEARED, UNCOMMENT THIS FOR ONE CYCLE*/
        //disp_db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        //onCreate(disp_db); // create upgraded table
    }

    // ability to add new items to the database (SQL)
    public void insertItem(String name, int status, String session, String username) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, name);
        cv.put(STATUS, status);
        cv.put(SESSION, session);

        String tableName = username + CHECKLIST_TABLE;
        disp_db.insert(tableName, null, cv); // insert new item to database
    }

    public ArrayList getAllSessions(String username) {
        ArrayList<String> sessionList = new ArrayList<>();
        String[] currSession;
        String tableName = username + CHECKLIST_TABLE;

        Cursor cursor = disp_db.rawQuery("SELECT DISTINCT " + SESSION + " FROM " + tableName, null);
        if (cursor.moveToFirst()) {
            do {
                currSession = (cursor.getString(0).split(":"));
                sessionList.add(currSession[0] + ":" + currSession[1]);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return (sessionList);
    }

    public List calculateSessionData(String username) {
        ArrayList<String> sessionList = new ArrayList<>();
        List<Float> dataCalc = new ArrayList<>();
        AtomicInteger dataVal = new AtomicInteger();
        AtomicInteger dataCount = new AtomicInteger();
        String tableName = username + CHECKLIST_TABLE;

        Cursor cursor = disp_db.rawQuery("SELECT DISTINCT " + SESSION + " FROM " + tableName, null);
        if (cursor.moveToFirst()) {
            do {
                sessionList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        sessionList.forEach((session) -> {
            Cursor dataCursor = disp_db.rawQuery("SELECT * FROM " + tableName +
                    " WHERE " + SESSION + " = '" + session + "'", null);
            dataVal.set(0);
            dataCount.set(0);

            if (dataCursor.moveToFirst()) {
                do {
                    dataVal.set(dataVal.intValue() + dataCursor.getInt(3));
                    dataCount.set(dataCount.intValue() + 1);
                } while (dataCursor.moveToNext());
            }

            dataCalc.add((dataVal.floatValue() / dataCount.floatValue()) * 100);
            dataCursor.close();
        });
        return (dataCalc);
    }

    public List[] selectSessionData(String session, String username) {
        ArrayList<String> sessionName = new ArrayList<>();
        ArrayList<String> sessionItems = new ArrayList<>();
        ArrayList<String> itemStatus = new ArrayList<>();

        String[] sessionSplit = session.split(" ");

        String sessionDate = sessionSplit[0] + " " + sessionSplit[1] + " " + sessionSplit[2];
        String sessionYear = sessionSplit[5];

        String tableName = username + CHECKLIST_TABLE;

        Cursor cursor = disp_db.rawQuery("SELECT * FROM " + tableName + " WHERE " + SESSION + " LIKE '%" + sessionDate + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(1).contains(sessionYear)) {
                    String[] dateSplit = cursor.getString(1).split(" ");
                    sessionName.add(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2] + " " + dateSplit[3]);
                    sessionItems.add(cursor.getString(2));
                    itemStatus.add(cursor.getString(3));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        List[] returnVals = new List[3];
        returnVals[0] = sessionName;
        returnVals[1] = sessionItems;
        returnVals[2] = itemStatus;
        return (returnVals);
    }
}
