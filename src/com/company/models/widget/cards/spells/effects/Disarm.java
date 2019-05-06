package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public class Disarm extends Effectable
{
    public Disarm(int turnNumber, PON PON, ActiveTime activeTime)
    {
        super(turnNumber, PON, activeTime);
    }

    public Disarm(int turnNumber, PON PON, ActiveTime activeTime, boolean isCon)
    {
        super(turnNumber, PON, activeTime, isCon);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new Disarm(turnNumber,pon,activeTime,isContinuous);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        this.turnRemaining--;
    }
}
