package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.spells.Spell;

public class Hero extends Warrior
{
    public Hero(String name, int power, int health, int price, AttackType attackType, int attackRadius, Spell spell)
    {
        super(name, price, health, power, attackType, attackRadius, spell);
    }

    public Hero(Hero hero)
    {
        this(hero.name, hero.power, hero.health, hero.price, hero.attackType, hero.attackRadius, new Spell(hero.specialSpell));
    }


    public String toShow()
    {
        return String.format("(Hero) Name : %s - AP : %d - HP : %d - Class : %s SellCost : %d - BuyCost : %d - SpecialSpellCD : %d\n",
                this.name, this.power, this.health,
                this.attackType.toString(), this.price / 2, this.price, this.getSpecialSpell().getCoolDown());
    }
}
