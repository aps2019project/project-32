package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;

public class ChangeAttackPoint extends Effectable {

   final private int changeAttackPoint;

    public ChangeAttackPoint(int changeAttackPoint, int turnNumber) {
        super(turnNumber);
        this.changeAttackPoint = changeAttackPoint;
    }

    @Override
    public void doEffect(Warrior warrior) {
        super.turnRemaining--;
        warrior.setPower(warrior.getPower() + changeAttackPoint);
    }

    @Override
    public Effectable clone() {
        return new ChangeHealthEffect(this.changeAttackPoint,this.turnNumber);
    }
}

