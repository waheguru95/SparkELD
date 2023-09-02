package com.example.eld;

public class Modelmy {
    private boolean isSelected;
    private String player;


    public Modelmy(boolean isSelected,
                   String player) {
        this.isSelected = isSelected;
        this.player = player;
    }

    public Modelmy() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
