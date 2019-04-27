package com.company.models.widget.cards;


import com.company.models.Player;
import com.company.models.widget.Widget;

public abstract class Card extends Widget
{
    protected int cash;
    protected int manaCost;

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

    public int getCash()
    {
        return cash;
    }

    public void setCash(int cash)
    {
        this.cash = cash;
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
