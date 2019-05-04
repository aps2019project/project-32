package com.company.models.widget.cards.spells.effects;

import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.SpellType;

public class ChangeMana extends Effectable
{

    int changeManaNumber;

    public ChangeMana(int changeManaNumber,int turnNumber, BuffType buffType)
    {
        super(turnNumber, buffType);
        this.changeManaNumber = changeManaNumber;
    }

    @Override
    public void doEffect(Warrior warrior, SpellType spellType)
    {
        warrior.getOwnerPlayer().changeMana(changeManaNumber);
    }

    @Override
    public Effectable clone()
    {
        return null;
    }
}
