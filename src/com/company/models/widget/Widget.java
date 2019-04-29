package com.company.models.widget;

import com.company.models.Player;

public abstract class Widget {
    protected Player ownerPlayer;
    protected String name;
    protected int ID;
    static private int IDCounter = 1;

    public Widget(String name) {
        this.name = name;
        this.ID = IDCounter;
        IDCounter++;
    }


    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
