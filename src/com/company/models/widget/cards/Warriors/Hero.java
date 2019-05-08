package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.spells.Spell;

public class Hero extends Warrior
{
    public Hero(String name, int power, int health, int price, AttackType attackType, int attackRadius, Spell spell)
    {
        super(name, price, health, power, attackType, attackRadius, spell);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Hero hero = null;

        if (this.getSpecialSpell() != null)
            hero = new Hero(name, power, health, price, attackType, attackRadius, ((Spell) specialSpell.clone()));
        else
            hero = new Hero(name, power, health, price, attackType, attackRadius, null);

        hero.ownerPlayer = this.ownerPlayer;

        return hero;
    }

    public String toShow()
    {
        String show = String.format("(Hero) Name : %s - AP : %d - HP : %d - Class : %s SellCost : %d - BuyCost : %d - CardID : %d - ",
                this.name, this.power, this.health, this.attackType.toString(), this.price / 2, this.price, this.ID);

        if (specialSpell == null)
            show = show.concat(String.format("No SpecialSpell\n"));
        else
            show = show.concat(String.format("SpecialSpellCD : %d\n", specialSpell.getCoolDown()));

        return show;
    }
}
