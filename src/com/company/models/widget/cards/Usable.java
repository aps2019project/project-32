package com.company.models.widget.cards;

import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellKind;
import com.company.models.widget.cards.spells.SpellType;

public class Usable extends Spell
{
    public Usable(SpellKind spellKind, String name, int price, int manaCost, int coolDown, int affectChangeAbilityTurnNumber, int affectDisarmTurnNumber, int affectStunTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpellType... spellTypes)
    {
        super(spellKind, name, price, manaCost, coolDown, affectChangeAbilityTurnNumber, affectDisarmTurnNumber, affectStunTurnNumber, spellRange, changeAttackPoint, changeHealthPoint, spellTypes);
    }

    @Override
    public String toShow()
    {
        return String.format("(Passive) Name : %s - SellCost : %d\n", this.getName(), this.getPrice() / 2);
    }

    public void affectUsable(){

    }
}
