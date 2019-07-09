package client.models.widget.cards.spells.effects;

import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.ActiveTime;

import java.io.Serializable;

public class HealthPoint extends Effectable implements Serializable
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

    @Override
    public String toShow() {
        return String.format("Change Health Point : %d\n" +
                "Turn Number Active : %d\n"
                ,changeHealthPoint,turnNumber,activeTime);
    }

    public int getChangeHealthPoint()
    {
        return changeHealthPoint;
    }
}
