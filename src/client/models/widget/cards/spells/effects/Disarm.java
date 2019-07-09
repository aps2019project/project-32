package client.models.widget.cards.spells.effects;

import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.ActiveTime;

import java.io.Serializable;

public class Disarm extends Effectable implements Serializable
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

    @Override
    public String toShow() {
        return String.format("Disarm Effect\n" +
                        "Turn Number Active : %d\n"
                ,turnNumber,activeTime);
    }
}
