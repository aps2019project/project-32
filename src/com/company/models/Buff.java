package com.company.models;

import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellKind;
import com.company.models.widget.cards.spells.SpellType;

public abstract class Buff extends Spell
{

    public Buff(SpellKind spellKind, String name, int price, int manaCost, int coolDown, int affectChangeAbilityTurnNumber, int affectDisarmTurnNumber, int affectStunTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpellType... spellTypes)
    {
        super(spellKind, name, price, manaCost, coolDown, affectChangeAbilityTurnNumber, affectDisarmTurnNumber, affectStunTurnNumber, spellRange, changeAttackPoint, changeHealthPoint, spellTypes);
    }

    public void doEffect(Position... positions)
    {

    }
}
