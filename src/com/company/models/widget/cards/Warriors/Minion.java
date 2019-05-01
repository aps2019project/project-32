package com.company.models.widget.cards.Warriors;



public class Minion extends Warrior
{
    int manaCost;
    MinionSpellType minionSpellType;

    public Minion(String name,int manaCost, int power, int health, int price, AttackType attackType, int attackRange,MinionSpellType minionSpellType){
        super(name,price,health,power,attackType,attackRange);
        this.manaCost = manaCost;
        this.minionSpellType = minionSpellType;
    }
    public Minion(Minion minion){
        this(minion.name,minion.manaCost,minion.power,minion.health,minion.price,minion.attackType,minion.attackRadius,minion.minionSpellType);
    }


    public String toShowMinion()
    {
        return String.format("(Minion) Name : %s - Class : %s - AP : %d - HP : %d - MP : %d - SellCost : %d \n",
                this.name, this.attackType.toString(), this.power, this.health, this.manaCost, this.price / 2);
    }
}
