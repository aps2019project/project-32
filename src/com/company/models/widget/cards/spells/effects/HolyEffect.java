package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class HolyEffect extends Effectable {

    final private int healthPointIncreaseAfterAttack;

    public HolyEffect(int healthPointIncreaseAfterAttack,int turnNumber,BuffType buffType) {
        super(turnNumber,buffType);
        this.healthPointIncreaseAfterAttack = healthPointIncreaseAfterAttack;
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType) {

    }

    @Override
    public Effectable clone() {
        return new ChangeHealthEffect(this.healthPointIncreaseAfterAttack,this.turnNumber,this.buffType);
    }
}
