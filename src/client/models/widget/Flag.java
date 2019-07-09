package client.models.widget;

import client.controller.AnimationGIFSPack;
import client.models.widget.cards.spells.*;

import java.io.Serializable;

public class Flag extends Spell implements Serializable
{
    public Flag()
    {
        super(Area.noArea, FOE.enemyOrFriend, TargetType.onMinionOrHero, ActiveTime.perTurn,
                Type.Flag, "Flag", 0, 0, 0, 0,
                new AnimationGIFSPack("file:Css/pictures/New folder (3)/ui/collection_card_rarity_mythron.png"));
    }
}
