package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;

public class DisarmEffect extends Effectable {


    public DisarmEffect(int turnNumber) {
        super(turnNumber);
    }

    @Override
    public void doEffect(Warrior warrior) {
        this.turnRemaining--;
        warrior.setCanAttack(false);
    }

    @Override
    public Effectable clone() {
        return new DisarmEffect(this.turnNumber);
    }
}
