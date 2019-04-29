package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.Card;

public abstract class Minion extends Warrior
{
    public Minion(String name, int health, int power, int cash, int manaCost,AttackType attackType) {
        super(name, health, power, cash, manaCost,attackType);
    }

    public abstract void attack(Card defender);

    public abstract void defend(Card attacker);

    public abstract String toShowMinion();
}
