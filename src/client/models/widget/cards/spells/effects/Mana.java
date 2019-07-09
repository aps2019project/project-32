package client.models.widget.cards.spells.effects;

import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.ActiveTime;

import java.io.Serializable;

public class Mana extends Effectable implements Serializable
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

    @Override
    public String toShow() {
        return String.format("Change Mana Number : %d\n" +
                "Turn Number Active : %d\n"
                ,changeManaNumber,turnNumber,activeTime);
    }
}
