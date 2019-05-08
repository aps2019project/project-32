package com.company.models.battle;

import com.company.controller.Exceptions.InvalidDeck;
import com.company.models.Player;
import com.company.models.widget.Flag;

public class KeepFlagBattle extends Battle
{
    public KeepFlagBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck, CloneNotSupportedException
    {
        super(firstPlayer, secondPlayer, winnerPrize);
        battleMap.spellsAndCollectibleOnMap[2][4] = new Flag();
    }

    private static KeepFlagBattle keepFlagBattleInstance;

    public static void setBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws
            InvalidDeck, CloneNotSupportedException
    {
        keepFlagBattleInstance = new KeepFlagBattle(firstPlayer,secondPlayer,winnerPrize);
    }

    public static KeepFlagBattle getInstance()
    {
        return keepFlagBattleInstance;
    }

    @Override
    public void checkBattleResult()
    {
        // TODO
        if (this.getTurnHandler().firstPlayerHasFlagTurnNumber == 6)
            winActions(firstPlayer);

        if (this.getTurnHandler().secondPlayerHasFlagTurnNumber == 6)
            winActions(secondPlayer);
    }

    @Override
    public String toShowGameInfo()
    {
        String toShow = "Battle : KeepFlagBattle\n";
        toShow = toShow + super.toShowGameInfo();
        return toShow;
    }

    public void dropFlagAfterDead()
    {

    }
}
