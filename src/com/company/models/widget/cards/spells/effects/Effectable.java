package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public abstract class Effectable
{

    protected final int turnNumber;
    protected int turnRemaining;
    protected final BuffType buffType;

    public Effectable(int turnNumber, BuffType buffType)
    {
        this.turnNumber = turnNumber;
        turnRemaining = turnNumber;
        this.buffType = buffType;
    }

    public abstract void doEffect(Warrior warrior, SpellType spellType);


    public int getTurnRemaining()
    {
        return turnRemaining;
    }

    public void setTurnRemaining(int turnRemaining)
    {
        this.turnRemaining = turnRemaining;
    }

    public abstract Effectable clone();
}
