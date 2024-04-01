package com.example.sids_checklist.checklistprofiles;

import android.annotation.SuppressLint;

import com.example.sids_checklist.checklistmodel.ProfileModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The Profile_DateHandler class provides functionality to handle date-related operations for a profile.
 */
public class Profile_DateHandler {

    private final ProfileModel profile;

    /**
     * Constructs a Profile_DateHandler object with the given profile.
     *
     * @param profile The profile for which date operations will be handled.
     */
    public Profile_DateHandler(ProfileModel profile){
        this.profile = profile;
    }

    /**
     * Calculates the number of weeks that have passed since the profile's birthday.
     *
     * @return A string representing the number of weeks.
     * @throws RuntimeException if there is an error parsing the date of birth.
     */
    public String getWeeks() throws RuntimeException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirth;
        Date currentDate = new Date();

        try {
            dateOfBirth = dateFormat.parse(profile.getAge());
        }
        catch (ParseException e){
            throw new RuntimeException(e);
        }

        assert dateOfBirth != null;
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
