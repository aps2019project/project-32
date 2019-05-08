package com.company.models.battle;

import com.company.controller.Exceptions.InvalidDeck;
import com.company.models.Player;

public class DeadBattle extends Battle
{
    public DeadBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck, CloneNotSupportedException
    {
        super(firstPlayer, secondPlayer, winnerPrize);
    }

    private static DeadBattle deadBattleInstance;


    public static void setBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck, CloneNotSupportedException
    {
        deadBattleInstance = new DeadBattle(firstPlayer, secondPlayer, winnerPrize);
    }

    public static DeadBattle getInstance()
    {
        return deadBattleInstance;
    }

    @Override
    public void checkBattleResult()
    {
        if (firstPlayer.getMainDeck().getHero().isDead())
            winActions(firstPlayer);

        if (secondPlayer.getMainDeck().getHero().isDead())
            winActions(secondPlayer);
    }

    @Override
    public String toShowGameInfo()
    {
        String toShow = "Battle : DeadBattle\n";
        toShow = toShow + super.toShowGameInfo();
        return toShow;
    }
}
