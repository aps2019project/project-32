package client.models.widget.cards.spells.effects;

import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.ActiveTime;

import java.io.Serializable;

public class AttackPoint extends Effectable implements Serializable
{
    final private int changeAttackPoint;

    public AttackPoint(int changeAttackPoint, int turnNumber, PON PON, ActiveTime activeTime)
    {
        super(turnNumber, PON, activeTime);
        this.changeAttackPoint = changeAttackPoint;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new AttackPoint(changeAttackPoint,turnNumber,pon,activeTime);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        super.turnRemaining--;
        warrior.setPower(warrior.getPower() + changeAttackPoint);
    }

    @Override
    public String toShow() {
        return String.format("Change Attack Point : %d\n" +
                "Turn Number Active : %d\n",
                changeAttackPoint,turnNumber,activeTime);
    }
}

