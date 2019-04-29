package com.company.models.widget.cards;

import com.company.Position;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Spell extends Card implements Serializable
{
    protected int manaCost;
    protected int coolDown;
    protected int coolDownRemaining;

    public Spell(String name,int manaCost, int coolDown,int cash)
    {
        super(name,cash,manaCost);
        this.coolDown = coolDown;
        this.coolDownRemaining = 0;
        this.manaCost = manaCost;
    }

    public abstract void doEffect(Position... positions);

    public int getManaCost(){
        return manaCost;
    }

    public int getCoolDown()
    {
        return coolDown;
    }

    public int getCoolDownRemaining()
    {
        return coolDownRemaining;
    }

    public void setCoolDownRemaining(int coolDownRemaining)
    {
        this.coolDownRemaining = coolDownRemaining;
    }

    public void decreaseCoolDownRemaining()
    {
        if (coolDownRemaining >= 1)
            coolDownRemaining -= 1;
    }
}
