package com.example.sids_checklist.checklistprofiles;

/**
 * The Profile_PopUpInterface interface defines methods for interacting with profile pop-ups.
 */
public interface Profile_PopUpInterface {
    /**
     * Refreshes the activity that calls a pop-up.
     */
    void refreshProfiles(); // A method to refresh the activity that calls a pop up
    /**
     * Refreshes the activity after a new profile has been added.
     *
     * @param newProfileID The ID of the newly added profile.
     */
    void refreshProfilesAdded(int newProfileID);
}
