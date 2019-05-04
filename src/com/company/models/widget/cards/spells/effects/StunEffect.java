package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class StunEffect extends Effectable {

    public StunEffect(int turnNumber,BuffType buffType) {
        super(turnNumber,buffType);
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType) {
        this.turnRemaining--;
        warrior.setCanMove(false);
        warrior.setCanAttack(false);
    }

    @Override
    public Effectable clone() {
        return new StunEffect(this.turnNumber,this.buffType) {
        };
    }
}
