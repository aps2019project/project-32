package com.company.models.widget.cards.Warriors;


import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.spells.Spell;

public class Minion extends Warrior
{
    private int manaCost;
    private boolean canCombo;

    public Minion(String name, int manaCost, int power, int health, int price, AttackType attackType, int attackRange, Spell spell,boolean canCombo)
    {
        super(name, price, health, power, attackType, attackRange,spell);
        this.manaCost = manaCost;
        this.canCombo = canCombo;
    }

    public Minion(Minion minion)
    {
        this(minion.name, minion.manaCost, minion.power, minion.health, minion.price, minion.attackType,minion.attackRadius, minion.specialSpell,minion.canCombo);
    }


    public String toShow()
    {
        return String.format("(Minion) Name : %s - Class : %s - AP : %d - HP : %d - MP : %d - SellCost : %d - Buy Cost : %d \n",
                this.name, this.attackType.toString(), this.power, this.health, this.manaCost, this.price / 2, this.price);
    }

    public void attack(Card defender)
    {

    }



    public int getManaCost()
    {
        return manaCost;
    }

    public void setManaCost(int manaCost)
    {
        this.manaCost = manaCost;
    }
}
