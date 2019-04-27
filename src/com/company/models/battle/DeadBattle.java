package com.company.models.battle;

import com.company.TimeHandler;
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
}
