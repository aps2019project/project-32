package com.company.models.widget.cards;

import com.company.models.widget.cards.spells.Spell;

public class Usable extends Spell
{
    @Override
    public String toShow()
    {
        return String.format("(Passive) Name : %s - SellCost : %d\n", this.getName(), this.getPrice() / 2);
    }

    public void affectUsable(){

    }
}
