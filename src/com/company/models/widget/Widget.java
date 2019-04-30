package com.company.models.widget;

import com.company.models.Player;

public abstract class Widget
{
    protected Player ownerPlayer;
    protected String name;
    protected int ID;
    protected static int idCounter;

    public Widget(String name) {
        this.name = name;
        this.ID = idCounter;
        idCounter++;
    }

    public abstract String toShow();
    public Player getOwnerPlayer()
    {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer)
    {
        this.ownerPlayer = ownerPlayer;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }
}
