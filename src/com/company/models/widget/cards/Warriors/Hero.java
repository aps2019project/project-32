package com.company.models.widget.cards.warriors;

import com.company.models.widget.items.Flag;

import java.util.ArrayList;

public abstract class Hero extends Warrior
{
    private ArrayList<Flag> collectedFlags = new ArrayList<>();

    public String toShowHero()
    {
        return String.format("(Hero) Name : %s - AP : %d - HP : %d - Class : %s SellCost : %d\n",
                this.name, this.power, this.health,
                this.attackType.toString(), this.price / 2);
    }

    public ArrayList<Flag> getCollectedFlags()
    {
        return collectedFlags;
    }

    public void setCollectedFlags(ArrayList<Flag> collectedFlags)
    {
        this.collectedFlags = collectedFlags;
    }
}
