package com.example.sids_checklist.checklistutils;

import android.annotation.SuppressLint;

import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Profile_DateHandler {

    private ProfileModel profile;

    public Profile_DateHandler(ProfileModel profile){
        this.profile = profile;
    }

    /**
    * A function to return the number of weeks that have been passed since a given birthday.
     * The number of weeks returned is truncated.
    */
    public String getWeeks() throws RuntimeException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirth = new Date();
        Date currentDate = new Date();

        try {
            dateOfBirth = dateFormat.parse(profile.getAge());
        }
        catch (ParseException e){
            throw new RuntimeException(e);
        }

        long dateDifference = currentDate.getTime() - dateOfBirth.getTime();
        long numDays = TimeUnit.DAYS.convert(dateDifference, TimeUnit.MILLISECONDS);
        int numWeeks = ((int) numDays) / 7;
        if(numWeeks == 1){
            return numWeeks + " Week";
        }
        else {
            return numWeeks + " Weeks";
        }
    }
}
