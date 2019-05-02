package com.company.models.battle;

import com.company.controller.Exceptions.GameIsNotOver;
import com.company.models.Player;

public class CollectFlagBattle extends Battle
{
    private int flagNumbers;

    public CollectFlagBattle(Player firstPlayer, Player secondPlayer)
    {
        super(firstPlayer, secondPlayer);
        this.flagNumbers = 7;
    }

    @Override
    public void checkBattleResult() throws GameIsNotOver
    {
        if (firstPlayer.getPlayerHand().getFlagNumbersInCollectedItems() > flagNumbers / 2)
        {
            gameResault = GameResault.FristPlayerWin;
            firstPlayer.setCash(firstPlayer.getCash() + 1000);
            addBattleToBattleHistories(gameResault);
        }
        if (secondPlayer.getPlayerHand().getFlagNumbersInCollectedItems() > flagNumbers / 2)
        {
            gameResault = GameResault.SecondPlayerWin;
            secondPlayer.setCash(secondPlayer.getCash() + 1000);
            addBattleToBattleHistories(gameResault);
        }
        gameResault = GameResault.UnCertain;
    }

    @Override
    public String toShowGameInfo()
    {
        return null;
    }
}
