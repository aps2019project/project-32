package com.company.models.battle;

import com.company.models.Player;
import com.company.models.widget.Flag;

public class KeepFlagBattle extends Battle
{
    public KeepFlagBattle(Player firstPlayer, Player secondPlayer, int winnerPrize)
    {
        super(firstPlayer, secondPlayer, winnerPrize);
        battleMap.spellsAndCollectibleOnMap[2][4] = new Flag();
    }

    @Override
    public void makeBattle(Player firstPlayer, Player secondPlayer, int winnerPrize)
    {
        currentBattle = new KeepFlagBattle(firstPlayer,secondPlayer,winnerPrize);
    }

    @Override
    public void checkBattleResult()
    {
        if (this.getTurnHandler().firstPlayerHasFlagTurnNumber == 6)
            winActions(firstPlayer);

        if (this.getTurnHandler().secondPlayerHasFlagTurnNumber == 6)
            winActions(secondPlayer);
    }

    @Override
    public String toShowGameInfo()
    {
        return null;
    }

    public void dropFlagAfterDead()
    {

    }
}
