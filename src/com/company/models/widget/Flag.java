package com.company.models.widget;

import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellKind;

public class Flag extends Spell
{
    public Flag()
    {
        super(SpellKind.Flag,"flag",0,0,0,0,0,0,0,0,0);
    }
    @Override
    public String toShow()
    {
        return null;
    }
}
