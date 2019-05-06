package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public class Mana extends Effectable
{

    int changeManaNumber;

    public Mana(int changeManaNumber, int turnNumber, PON PON, ActiveTime activeTime)
    {
        super(turnNumber, PON, activeTime);
        this.changeManaNumber = changeManaNumber;

    }

    public Mana(int changeManaNumber, int turnNumber, PON pon, ActiveTime activeTime, boolean isContinuous)
    {
        super(turnNumber, pon, activeTime, isContinuous);
        this.changeManaNumber = changeManaNumber;
        this.isContinuous = isContinuous;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new Mana(changeManaNumber, turnNumber, pon, activeTime);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        warrior.getOwnerPlayer().changeMana(changeManaNumber);
    }
}
