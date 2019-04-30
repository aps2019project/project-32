package com.company.models.widget.cards;


import com.company.models.Player;
import com.company.models.widget.Widget;

public abstract class Card extends Widget
{
    protected int price;
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
    public int getPrice()
    {
        return price;
    }
    public void setPrice(int price)
    {
        this.price = price;
    }
    public int getID()
    {
        return ID;
    }
    public void setID(int ID)
    {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Card))
            return false;
        return ((Card) obj).getID()==this.getID();
    }

    @Override
    public int hashCode()
    {
        return price;
    }
}
