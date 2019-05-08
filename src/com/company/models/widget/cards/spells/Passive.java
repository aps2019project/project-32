package com.company.models.widget.cards.spells;

import com.company.models.widget.cards.Card;

public class Passive extends Card
{
    private Spell spell;
    private FOE foe;
    private TargetType targetType;

    public Passive(String name, int price, Spell spell, FOE foe, TargetType targetType)
    {
        super(name, price);
        this.spell = spell;
        this.foe = foe;
        this.targetType = targetType;
    }

    @Override
    public String toShow()
    {
        return String.format("(Passive) Name : %s - SellCost : %d - BuyCost : %d- TargetType : %s\n\n"
                , name, price / 2, price, targetType);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Passive passive = null;
        passive = new Passive(name, price, ((Spell) spell.clone()), foe, targetType);

        passive.ownerPlayer = this.ownerPlayer;

        return passive;
    }

    public Spell getSpell()
    {
        return spell;
    }

    public FOE getFoe()
    {
        return foe;
    }

    public TargetType getTargetType()
    {
        return targetType;
    }
}
