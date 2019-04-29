package com.company.models.battle;

import com.company.models.Player;

public class CollectFlagBattle extends Battle
{
    private int flagNumbers = 7;

    CollectFlagBattle(Player firstPlayer, Player secondPlayer, int flagNumbers)
    {
        super(firstPlayer, secondPlayer);
        this.flagNumbers = flagNumbers;
    }

    @Override
    public void checkBattleResult()
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
