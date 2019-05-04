package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class DeBufPoison extends Effectable
{

    public DeBufPoison(int turnNumber, BuffType buffType) {
        super(turnNumber,buffType);
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType) {
        this.turnRemaining--;
        warrior.getEffectsOnWarrior().removeIf(effective -> (effective instanceof ChangeHealthEffect) && ((ChangeHealthEffect) effective).getChangeHealthPoint()==-1);
    }

    @Override
    public Effectable clone() {
        return new DeBufPoison(this.turnNumber,this.buffType);
    }
}
