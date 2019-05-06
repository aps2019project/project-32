package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;

public class DeBuff extends Effectable
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
}
