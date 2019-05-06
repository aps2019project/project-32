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
        return String.format("(Passive) Name : %s - Price : %d - TargetType : %s", name, price, targetType);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new Passive(name, price, ((Spell) spell.clone()), foe, targetType);
    }
}
