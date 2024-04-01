package com.example.sids_checklist;

import java.util.ArrayList;

/**
 * The Tips class represents tips for safe sleeping practices for babies.
 * It provides methods to initialize lists of tips for various scenarios.
 */
public class Tips {
    private String item;
    private int status;

    /**
     * Constructs a Tip object with the given item and status.
     *
     * @param item The tip description.
     * @param status The status of the tip.
     */
    public Tips(String item, int status){
        this.item = item;
        this.status = status;
    }

    /**
     * Default constructor for Tip objects.
     */
    public Tips() {}

    /**
     * Retrieves the tip item.
     *
     * @return The tip item.
     */
    public String getItem() {
        return item;
    }

    /**
     * Retrieves the status of the tip.
     *
     * @return The status of the tip.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the tip.
     *
     * @param status The status of the tip to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Initializes a list of smoking-related tips.
     *
     * @return The list of smoking-related tips.
     */
    public ArrayList<Tips> initSmokingTips(){
        ArrayList<Tips> smokingList = new ArrayList<>();
        smokingList.add(new Tips("Put baby to sleep in separate smoke free room",0));
        return smokingList;
    }

    /**
     * Initializes a list of co-sleeping-related tips.
     *
     * @return The list of co-sleeping-related tips.
     */
    public ArrayList<Tips> initCoSleepingTips(){
        ArrayList<Tips> coSleepingList = new ArrayList<>();
        coSleepingList.add(new Tips("Create a barrier between parent and baby sleeping area", 0));
        coSleepingList.add(new Tips("Clear all items from baby's sleeping area", 0));

        return coSleepingList;
    }

    /**
     * Initializes a list of standard safe sleeping tips.
     *
     * @return The list of standard safe sleeping tips.
     */
    public ArrayList<Tips> initStandardTips() {
        ArrayList<Tips> standardList = new ArrayList<>();
        standardList.add(new Tips("Put crib in the same room as parent", 0));
        standardList.add(new Tips("Set room temperature for comfort in a single-layer", 0));
        standardList.add(new Tips("Dress baby in one layer", 0));
        standardList.add(new Tips("Remove all items from crib", 0));
        standardList.add(new Tips("Ensure all bedding on mattress is secure", 0));
        standardList.add(new Tips("Put baby to sleep on their back", 0));
        standardList.add(new Tips("Ensure baby's head is completely uncovered", 0));

        return standardList;
    }
}

