package client.models.widget.cards.spells.effects;

import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.ActiveTime;

import java.io.Serializable;

public class Stun extends Effectable implements Serializable
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

    @Override
    public String toShow() {
        return String.format("Stun Effect\n" +
                        "Turn Number Active : %d\n"
                ,turnNumber,activeTime);
    }
}
