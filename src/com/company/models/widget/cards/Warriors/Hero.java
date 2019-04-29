package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.Card;
import com.company.models.widget.items.Flag;

import java.util.ArrayList;

public abstract class Hero extends Warrior
{
    private ArrayList<Flag> collectedFlags = new ArrayList<>();

    public Hero(String name, int health, int power, int cash, int manaCost,AttackType attackType) {
        super(name, health, power, cash, manaCost,attackType);
    }

    public abstract String toShowHero();

    public abstract void attack(Card defender);

    public abstract void defend(Card attacker);

    public ArrayList<Flag> getCollectedFlags()
    {
        return collectedFlags;
    }

    public void setCollectedFlags(ArrayList<Flag> collectedFlags)
    {
        this.collectedFlags = collectedFlags;
    }
}
