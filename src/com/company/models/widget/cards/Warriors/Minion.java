package com.company.models.widget.cards.Warriors;

public abstract class Minion extends Warrior
{
    @Override
    public String toShow()
    {
        return String.format("(Minion) Name : %s - Class : %s - AP : %d - HP : %d - MP : %d - SellCost : %d \n",
                this.name, this.attackType.toString(), this.power, this.health, this.manaCost, this.cash / 2);
    }
}
