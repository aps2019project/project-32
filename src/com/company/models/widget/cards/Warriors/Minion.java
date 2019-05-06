package com.company.models.widget.cards.Warriors;


import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.spells.Spell;

public class Minion extends Warrior
{
    private int manaCost;

    public Minion(String name, int manaCost, int power, int health, int price, AttackType attackType, int attackRange, Spell spell)
    {
        super(name, price, health, power, attackType, attackRange,spell);
        this.manaCost = manaCost;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new Minion(name,manaCost,power,health,price,attackType,attackRadius, ((Spell) specialSpell.clone()));
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
