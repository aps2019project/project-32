package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class DeBufDisarm extends Effectable
{



    public DeBufDisarm(int turnNumber, BuffType buffType) {
        super(turnNumber,buffType);
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType) {
        this.turnRemaining--;
        warrior.setCanAttack(true);
    }

    @Override
    public Effectable clone() {
        return new DeBufDisarm(this.turnNumber,this.buffType);
    }
}
