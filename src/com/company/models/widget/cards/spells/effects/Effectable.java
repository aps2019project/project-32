package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;

public abstract class Effectable {

    protected final int turnNumber;
    protected int turnRemaining;

    protected Effectable(int turnNumber) {
        this.turnNumber = turnNumber;
        turnRemaining = turnNumber;
    }

    public abstract void doEffect(Warrior warrior);

    public int getTurnRemaining() {
        return turnRemaining;
    }

    public void setTurnRemaining(int turnRemaining) {
        this.turnRemaining = turnRemaining;
    }

    public abstract Effectable clone();
}
