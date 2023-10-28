package com.example.eld;

public class PendingLog {
    private boolean isSelected;
    private String date;


    public PendingLog(boolean isSelected,
                      String date) {
        this.isSelected = isSelected;
        this.date = date;
    }

    public PendingLog() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
