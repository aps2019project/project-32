package com.company.models.battle;

import com.company.models.Player;
import com.company.models.widget.Flag;

public class KeepFlagBattle extends Battle
{
    public KeepFlagBattle(Player firstPlayer, Player secondPlayer)
    {
        super(firstPlayer, secondPlayer);
        battleMap.spellsAndCollectibleOnMap[2][4] = new Flag();
    }

    public void dropFlagAfterDead()
    {

    }

    @Override
    public void checkBattleResult()
    {
        if (this.getBattleTurnHandler().firstPlayerHasFlagTurnNumber == 6)
            winActions(firstPlayer, GameResault.FristPlayerWin);

        if (this.getBattleTurnHandler().secondPlayerHasFlagTurnNumber == 6)
            winActions(secondPlayer, GameResault.SecondPlayerWin);
    }

    @Override
    public String toShowGameInfo()
    {
        return null;
    }
}
