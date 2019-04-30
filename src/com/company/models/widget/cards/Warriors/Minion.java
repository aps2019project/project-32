package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellType;

public abstract class Minion extends Warrior
{
    int manaCost;

    public Minion(String name,int manaCost, int power, int health, int price, AttackType attackType, int attackRange){
        super(name,price,health,power,attackType,attackRange);
        this.manaCost = manaCost;
    }



    public String toShowMinion()
    {
        return String.format("(Minion) Name : %s - Class : %s - AP : %d - HP : %d - MP : %d - SellCost : %d \n",
                this.name, this.attackType.toString(), this.power, this.health, this.manaCost, this.price / 2);
    }
}
