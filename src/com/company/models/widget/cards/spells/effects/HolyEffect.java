package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;

public class HolyEffect extends Effectable {

    final private int healthPointIncreaseAfterAttack;

    public HolyEffect(int healthPointIncreaseAfterAttack,int turnNumber) {
        super(turnNumber);
        this.healthPointIncreaseAfterAttack = healthPointIncreaseAfterAttack;
    }

    @Override
    public void doEffect(Warrior warrior) {

    }

    @Override
    public Effectable clone() {
        return new ChangeHealthEffect(this.healthPointIncreaseAfterAttack,this.turnNumber);
    }
}
