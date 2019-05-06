package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public class Stun extends Effectable
{

    public Stun(int turnNumber, PON PON, ActiveTime activeTime)
    {
        super(turnNumber, PON, activeTime);
    }

    public Stun(int turnNumber, PON pon, ActiveTime activeTime, boolean isContinuous)
    {
        super(turnNumber, pon, activeTime, isContinuous);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new Stun(turnNumber,pon,activeTime,isContinuous);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        this.turnRemaining--;
        warrior.setCanMove(false);
        warrior.setCanAttack(false);
    }
}
