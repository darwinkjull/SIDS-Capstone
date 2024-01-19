package com.example.sids_checklist.checklistmodel;

/*
Creating a model to define the structure of the checklist items
- item ID
- Int: Status (checked or unchecked)
- String: Item name
 */


public class ChecklistModel {
    private int id, status; // defining variables to use for checklist functionality
    private String item; // text for the checklist

    // Getter and Setter functions for each attribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
