package com.company.models.battle;

import com.company.models.Player;

public class DeadBattle extends Battle
{
    public DeadBattle(Player firstPlayer, Player secondPlayer)
    {
        super(firstPlayer, secondPlayer);
    }

    @Override
    public void checkBattleResult()
    {
        if (firstPlayer.getMainDeck().getHero().isDead())
        {
            gameResault = GameResault.SecondPlayerWin;
            secondPlayer.setCash(secondPlayer.getCash() + 1000);
            addBattleToBattleHistories(gameResault);
        }
        if (secondPlayer.getMainDeck().getHero().isDead())
        {
            gameResault = GameResault.FristPlayerWin;
            firstPlayer.setCash(firstPlayer.getCash() + 1000);
            addBattleToBattleHistories(gameResault);
        }
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
