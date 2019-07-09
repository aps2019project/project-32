package client.models.widget.cards.spells.effects;

import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.ActiveTime;

import java.io.Serializable;

public class DeBuff extends Effectable implements Serializable
{
    public DeBuff(int turnNumber, PON pon, ActiveTime activeTime, BuffType buffWillBeDeBuff)
    {
        super(turnNumber, pon, activeTime);
        this.buffWillBeDeBuff = buffWillBeDeBuff;
    }

    private BuffType buffWillBeDeBuff;

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new DeBuff(turnNumber, pon, activeTime, buffWillBeDeBuff);
    }

    @Override
    public void doEffect(Warrior warrior)
    {
        this.turnRemaining--;

        switch (buffWillBeDeBuff)
        {
            case deBuffHoly:
                warrior.getEffectsOnWarrior().removeIf(effective ->
                        !effective.isContinuous
                                && (effective instanceof HealthPoint)
                                && ((HealthPoint) effective).getChangeHealthPoint() == +1
                                && effective.activeTime == ActiveTime.onDefend);

                break;
            case deBuffPoison:
                warrior.getEffectsOnWarrior().removeIf(effective ->
                        !effective.isContinuous
                                && (effective instanceof HealthPoint)
                                && ((HealthPoint) effective).getChangeHealthPoint() == -1
                                && effective.activeTime == ActiveTime.perTurn);

                break;
            case deBuffDisarm:
                warrior.getEffectsOnWarrior().removeIf(effective ->
                        !effective.isContinuous
                                && effective instanceof Disarm);
                warrior.setCanAttack(true);

                break;
            case deBuffNegatives:
                warrior.getEffectsOnWarrior().removeIf(effective ->
                        !effective.isContinuous
                                && effective.pon == PON.Positive);

                break;
            case deBuffPositives:
                warrior.getEffectsOnWarrior().removeIf(effective ->
                        !effective.isContinuous
                                && effective.pon == PON.Negative);

                break;
        }
    }

    @Override
    public String toShow() {
        return String.format("Debuff Effect\n" +
                        "Turn Number Active : %d\n"
                ,turnNumber,activeTime);
    }
}
