package com.company.models.battle;

import com.company.controller.Exceptions.InvalidDeck;
import com.company.models.Player;

public class DeadBattle extends Battle
{
    private DeadBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck
    {
        super(firstPlayer, secondPlayer, winnerPrize);
        initialiseBattle();
    }

    private static DeadBattle deadBattleInstance;


    public static void setBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck
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

        gameResault = GameResault.UnCertain;
    }

    @Override
    public String toShowGameInfo()
    {
        String gameInfoString = "";

        gameInfoString = gameInfoString.concat
                (firstPlayer.getName() + " Hero Health is " + firstPlayer.getMainDeck().getHero().getHealth() + "\n");
        gameInfoString = gameInfoString.concat
                (secondPlayer.getName() + " Hero Health is " + secondPlayer.getMainDeck().getHero().getHealth() + "\n");

        return gameInfoString;
    }
}
