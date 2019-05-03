package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class ChangeAttackPoint extends Effectable
{

    final private int changeAttackPoint;

    public ChangeAttackPoint(int changeAttackPoint, int turnNumber, BuffType buffType)
    {
        super(turnNumber, buffType);
        this.changeAttackPoint = changeAttackPoint;
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType)
    {
        super.turnRemaining--;
        warrior.setPower(warrior.getPower() + changeAttackPoint);
    }

    @Override
    public Effectable clone()
    {
        return new ChangeHealthEffect(this.changeAttackPoint, this.turnNumber, this.buffType);
    }
}

