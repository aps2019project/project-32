package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class DisarmEffect extends Effectable {


    public DisarmEffect(int turnNumber,BuffType buffType) {
        super(turnNumber,buffType);
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType) {
        this.turnRemaining--;
        warrior.setCanAttack(false);
    }

    @Override
    public Effectable clone() {
        return new DisarmEffect(this.turnNumber,this.buffType);
    }
}
