package com.company.models.widget;

import com.company.models.widget.cards.spells.*;

public class Flag extends Spell
{
    public Flag()
    {
        super(Area.noArea, FOE.enemyOrFriend, TargetType.onMinionOrHero, ActiveTime.perTurn,
                Type.Flag, "Flag", 0, 0, 0, 0, null);
    }
}
