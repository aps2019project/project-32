package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;

public class StunEffect extends Effectable {

    public StunEffect(int turnNumber) {
        super(turnNumber);
    }

    @Override
    public void doEffect(Warrior warrior) {
        this.turnRemaining--;
        warrior.setCanMove(false);
        warrior.setCanAttack(false);
    }

    @Override
    public Effectable clone() {
        return new StunEffect(this.turnNumber) {
        };
    }
}
