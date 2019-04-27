package com.company.models.widget.cards.Warriors;

import com.company.models.widget.items.Flag;

import java.util.ArrayList;

public abstract class Hero extends Warrior
{
    private ArrayList<Flag> collectedFlags = new ArrayList<>();

    public abstract String toShowHero();



    public ArrayList<Flag> getCollectedFlags()
    {
        return collectedFlags;
    }

    public void setCollectedFlags(ArrayList<Flag> collectedFlags)
    {
        this.collectedFlags = collectedFlags;
    }
}
