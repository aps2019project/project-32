package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public class HealthPoint extends Effectable
{

    final private int changeHealthPoint;

    public HealthPoint(int changeHealthPoint, int turnNumber, PON PON, ActiveTime activeTime)
    {
        super(turnNumber, PON, activeTime);
        this.changeHealthPoint = changeHealthPoint;
    }

    public HealthPoint(int changeHealthPoint,int turnNumber, PON pon, ActiveTime activeTime, boolean isContinuous)
    {
        super(turnNumber, pon, activeTime, isContinuous);
        this.changeHealthPoint = changeHealthPoint;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new HealthPoint(changeHealthPoint,turnNumber,pon,activeTime);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        super.turnRemaining--;
        warrior.setHealth(warrior.getHealth() + changeHealthPoint);
    }

    public int getChangeHealthPoint()
    {
        return changeHealthPoint;
    }
}
