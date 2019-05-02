package com.company.models.battle;

import com.company.models.Player;
import com.company.models.Position;
import com.company.models.widget.Flag;

public class CollectFlagBattle extends Battle
{
    private int flagNumbers;

    public CollectFlagBattle(Player firstPlayer, Player secondPlayer)
    {
        super(firstPlayer, secondPlayer);
        this.flagNumbers = 7;
        addFlagsToMap();
    }

    @Override
    public void checkBattleResult()
    {
        if (firstPlayer.getPlayerHand().getFlagNumbersInCollectedItems() > flagNumbers / 2)
           winActions(firstPlayer,GameResault.FristPlayerWin);

        if (secondPlayer.getPlayerHand().getFlagNumbersInCollectedItems() > flagNumbers / 2)
            winActions(secondPlayer,GameResault.SecondPlayerWin);

        gameResault = GameResault.UnCertain;
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
            Position randomPosition = Position.getRandomPosition();
            battleMap.getSpellsAndCollectibleOnMap()[randomPosition.row][randomPosition.col] = new Flag();
        }
    }
}
