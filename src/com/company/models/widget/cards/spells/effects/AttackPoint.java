package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public class AttackPoint extends Effectable
{
    final private int changeAttackPoint;

    public AttackPoint(int changeAttackPoint, int turnNumber, PON PON, ActiveTime activeTime)
    {
        super(turnNumber, PON, activeTime);
        this.changeAttackPoint = changeAttackPoint;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new AttackPoint(changeAttackPoint,turnNumber,pon,activeTime);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        super.turnRemaining--;
        warrior.setPower(warrior.getPower() + changeAttackPoint);
    }
}

