package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class DeBuff extends Effectable
{
    protected DeBuff(int turnNumber, BuffType buffType)
    {
        super(turnNumber, buffType);
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType)
    {
        if (spellType == SpellType.onEnemy)
            warrior.getEffectsOnWarrior().removeIf(effectable -> effectable.buffType == BuffType.Positive);

        else if (spellType == SpellType.onFriend)
            warrior.getEffectsOnWarrior().removeIf(effectable -> effectable.buffType == BuffType.Negative);
    }

    @Override
    public Effectable clone()
    {
        return null;
    }


}
