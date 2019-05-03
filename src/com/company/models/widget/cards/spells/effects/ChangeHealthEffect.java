package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;

public class ChangeHealthEffect extends Effectable {

    final private int changeHealthPoint;

    public ChangeHealthEffect(int changeHealthPoint, int turnNumber) {
        super(turnNumber);
        this.changeHealthPoint = changeHealthPoint;
    }

    @Override
    public void doEffect(Warrior warrior) {
        super.turnRemaining--;
        warrior.setHealth(warrior.getHealth() + changeHealthPoint);
    }

    @Override
    public Effectable clone() {
        return new ChangeHealthEffect(this.changeHealthPoint,this.turnNumber);
    }
}
