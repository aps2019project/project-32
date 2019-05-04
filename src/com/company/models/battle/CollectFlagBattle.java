package com.company.models.battle;

import com.company.models.Player;
import com.company.models.Position;
import com.company.models.widget.Flag;

public class CollectFlagBattle extends Battle
{
    private int flagNumbers;

    public CollectFlagBattle(Player firstPlayer, Player secondPlayer, int winnerPrize, int flagNumbers)
    {
        super(firstPlayer, secondPlayer, winnerPrize);
        this.flagNumbers = flagNumbers;
        addFlagsToMap();
    }

    @Override
    public void makeBattle(Player firstPlayer, Player secondPlayer, int winnerPrize)
    {
        currentBattle = new CollectFlagBattle(firstPlayer, secondPlayer, winnerPrize, flagNumbers);
    }

    @Override
    public void checkBattleResult()
    {
        if (firstPlayer.getPlayerHand().getFlagNumbersInCollectedItems() > flagNumbers / 2)
            winActions(firstPlayer);

        if (secondPlayer.getPlayerHand().getFlagNumbersInCollectedItems() > flagNumbers / 2)
            winActions(secondPlayer);
    }

    @Override
    public String toShowGameInfo()
    {
        return null;
    }

    private void addFlagsToMap()
    {
        for (int i = 0; i < flagNumbers; i++)
        {
            Position randomPosition = Position.getRandomFreePosition();
            battleMap.getSpellsAndCollectibleOnMap()[randomPosition.row][randomPosition.col] = new Flag();
        }
    }
}
