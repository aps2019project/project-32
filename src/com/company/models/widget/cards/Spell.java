package com.company.models.widget.cards;

import com.company.models.Position;

public abstract class Spell extends Card
{
    protected int manaCost;
    protected int coolDown;
    protected int coolDownRemaining;

    public Spell(int manaCost, int coolDown)
    {
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.coolDownRemaining = 0;
    }

    @Override
    public String toShow()
    {
        return String.format
                ("(Spell) - Name : %s – MP : %d - CoolDown : %d \n", this.name, this.manaCost, this.coolDown);
    }

    public abstract void doEffect(Position... positions);

    public int getManaCost()
    {
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
