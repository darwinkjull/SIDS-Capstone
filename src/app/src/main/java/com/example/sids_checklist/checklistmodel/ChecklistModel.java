package com.example.sids_checklist.checklistmodel;

/**
 * The ChecklistModel class represents a checklist item with its id, status, and text.
 */
public class ChecklistModel {
    private int id, status; // defining variables to use for checklist functionality
    private String item; // text for the checklist

    // Getter and Setter functions for each attribute
    /**
     * Retrieves the ID of the checklist item.
     *
     * @return The ID of the checklist item.
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the ID of the checklist item.
     *
     * @param id The ID to set.
     */public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the status of the checklist item.
     *
     * @return The status of the checklist item.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the checklist item.
     *
     * @param status The status to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Retrieves the text of the checklist item.
     *
     * @return The text of the checklist item.
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the text of the checklist item.
     *
     * @param item The text to set.
     */
    public void setItem(String item) {
        this.item = item;
    }

}
