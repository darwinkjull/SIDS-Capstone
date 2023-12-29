package com.example.sids_checklist.checklistutils;

/*

 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Checklist_UtilDatabaseHandler extends SQLiteOpenHelper {
    // Define Database parameters and query for creating database table
    private static final int VERSION = 1;
    private static final String NAME = "UtilChecklistDatabase";
    private static final String CHECKLIST_TABLE = "DisplayChecklist";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";

    private static final String SESSION = "session";
    private static final String PROFILE_ID = "profile_id";
    private static final String CREATE_CHECKLIST_TABLE = "CREATE TABLE " + CHECKLIST_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SESSION + " TEXT, "
            + ITEM + " TEXT, "
            + STATUS + " INTEGER, "
            + "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES Profiles(id))";

    private SQLiteDatabase disp_db;

    public Checklist_UtilDatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    // create the table
    @Override
    public void onCreate(SQLiteDatabase disp_db) {
        disp_db.execSQL(CREATE_CHECKLIST_TABLE); // execute query
    }

    // upgrade the table to the new version and drop the old table
    @Override
    public void onUpgrade(SQLiteDatabase disp_db, int oldVersion, int newVersion) {
        disp_db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        onCreate(disp_db); // create upgraded table
    }

    // open the database to write to
    public void openDatabase() {
        disp_db = this.getWritableDatabase(); // open as a writable database since we want to update

        /* IF THE DATABASE EVER BUGS OR NEEDS TO BE CLEARED, UNCOMMENT THIS FOR ONE CYCLE*/
        //disp_db.execSQL("DROP TABLE IF EXISTS " + CHECKLIST_TABLE); // drop the old version
        //onCreate(disp_db); // create upgraded table
    }

    // ability to add new items to the database (SQL)
    public void insertItem(String name, int status, String session) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, name);
        cv.put(STATUS, status);
        cv.put(SESSION, session);
        disp_db.insert(CHECKLIST_TABLE, null, cv); // insert new item to database
    }

    public ArrayList getAllSessions() {
        ArrayList<String> sessionList = new ArrayList<>();
        String[] currSession;
        Cursor cursor = disp_db.rawQuery("SELECT DISTINCT " + SESSION + " FROM " + CHECKLIST_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                currSession = (cursor.getString(0).split(":"));
                sessionList.add(currSession[0] + ":" + currSession[1]);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return (sessionList);
    }

    public List calculateSessionData() {
        ArrayList<String> sessionList = new ArrayList<>();
        List<Float> dataCalc = new ArrayList<>();
        AtomicInteger dataVal = new AtomicInteger();
        AtomicInteger dataCount = new AtomicInteger();

        Cursor cursor = disp_db.rawQuery("SELECT DISTINCT " + SESSION + " FROM " + CHECKLIST_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                sessionList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        sessionList.forEach((session) -> {
            Cursor dataCursor = disp_db.rawQuery("SELECT * FROM " + CHECKLIST_TABLE +
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

    public List[] selectSessionData(String session) {
        ArrayList<String> sessionName = new ArrayList<>();
        ArrayList<String> sessionItems = new ArrayList<>();
        ArrayList<String> itemStatus = new ArrayList<>();

        String[] sessionSplit = session.split(" ");

        String sessionDate = sessionSplit[0] + " " + sessionSplit[1] + " " + sessionSplit[2];
        String sessionYear = sessionSplit[5];

        Cursor cursor = disp_db.rawQuery("SELECT * FROM " + CHECKLIST_TABLE + " WHERE " + SESSION + " LIKE '%" + sessionDate + "%'", null);
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
