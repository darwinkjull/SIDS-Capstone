package com.example.sids_checklist.checklistprofiles;

public interface Profile_PopUpInterface {
    public void refreshProfiles(); // A method to refresh the activity that calls a pop up
    public void refreshProfilesAdded(int newProfileID);
}
