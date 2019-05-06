package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public abstract class Effectable
{
    protected final ActiveTime activeTime;
    protected final int turnNumber;
    protected final PON pon;
    // fon
    protected int turnRemaining;
    protected boolean isContinuous = false;

    public Effectable(int turnNumber, PON pon, ActiveTime activeTime)
    {
        this.turnNumber = turnNumber;
        turnRemaining = turnNumber;
        this.pon = pon;
        this.activeTime = activeTime;
    }

    public Effectable(int turnNumber,PON pon, ActiveTime activeTime, boolean isContinuous)
    {
        this.turnNumber = turnNumber;
        turnRemaining = turnNumber;
        this.pon = pon;
        this.activeTime = activeTime;
        this.isContinuous = isContinuous;
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;

    public abstract void doEffect(Warrior warrior);

    public int getTurnRemaining()
    {
        return turnRemaining;
    }

    public void setTurnRemaining(int turnRemaining)
    {
        this.turnRemaining = turnRemaining;
    }

    public ActiveTime getActiveTime()
    {
        return activeTime;
    }

    public int getTurnNumber()
    {
        return turnNumber;
    }

    public PON getPon()
    {
        return pon;
    }

    public boolean isContinuous()
    {
        return isContinuous;
    }

    public void setContinuous(boolean continuous)
    {
        isContinuous = continuous;
    }
}
