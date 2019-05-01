package com.company.models.widget.cards.Warriors;

import com.company.models.widget.items.Flag;

import java.util.ArrayList;

public  class Hero extends Warrior
{
    private ArrayList<Flag> collectedFlags = new ArrayList<>();

    public Hero(String name, int power, int health, int price, AttackType attackType, int attackRange){
        super(name,price,health,power,attackType,attackRange);
    }
    public Hero(Hero hero){
        this(hero.name,hero.power,hero.health,hero.price,hero.attackType,hero.attackRadius);
    }


    public String toShow()
    {
        return String.format("(Hero) Name : %s - AP : %d - HP : %d - Class : %s SellCost : %d - Buy Cost : %d \n",
                this.name, this.power, this.health,
                this.attackType.toString(), this.price / 2,this.price);
    }

    public ArrayList<Flag> getCollectedFlags()
    {
        return collectedFlags;
    }

    public void setCollectedFlags(ArrayList<Flag> collectedFlags)
    {
        this.collectedFlags = collectedFlags;
    }
}
