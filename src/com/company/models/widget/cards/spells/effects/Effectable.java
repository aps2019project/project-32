package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public abstract class Effectable
{
    protected final SpellType spellType;
    protected final int turnNumber;
    protected final BuffType buffType;
    protected int turnRemaining;

    public Effectable(int turnNumber, BuffType buffType, SpellType spellType)
    {
        this.turnNumber = turnNumber;
        turnRemaining = turnNumber;
        this.buffType = buffType;
        this.spellType = spellType;
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
