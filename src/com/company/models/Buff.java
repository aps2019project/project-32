package com.company.models;

import com.company.Position;
import com.company.models.widget.cards.Spell;

public abstract class Buff extends Spell
{
    public Buff()
    {
        super(0, 0);
    }

    @Override
    public void doEffect(Position... positions)
    {

    }
}
