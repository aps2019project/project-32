package com.company.models;

import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.spells.Spell;

import java.util.ArrayList;

public class AIPlayer
{
    private static Player AIPlayer;

    public static void setAIPlayerWith(Hero hero, ArrayList<Spell> spells, ArrayList<Minion> minions, Spell passiveSpell)
    {
        Player player = new Player();
        Player.Deck deck = player.new Deck(hero,spells,minions,passiveSpell);
        player.setMainDeck(deck);
        AIPlayer = player;
    }

    public static Player getAIPlayer()
    {
        return AIPlayer;
    }
}
