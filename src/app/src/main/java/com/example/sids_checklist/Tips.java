package com.example.sids_checklist;

import java.util.ArrayList;

public class Tips {
    private String item;
    private int status;

    public Tips(String item, int status){
        this.item = item;
        this.status = status;
    }

    public Tips() {

    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        item = item;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public ArrayList<Tips> initSmokingTips(){
        ArrayList<Tips> smokingList = new ArrayList<>();
        smokingList.add(new Tips("Put baby to sleep in separate smoke free room",0));
        return smokingList;
    }
    public ArrayList<Tips> initCoSleepingTips(){
        ArrayList<Tips> coSleepingList = new ArrayList<>();
        coSleepingList.add(new Tips("Create a barrier between parent and baby sleeping area", 0));
        coSleepingList.add(new Tips("Clear all items from baby's sleeping area", 0));

        return coSleepingList;
    }
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
