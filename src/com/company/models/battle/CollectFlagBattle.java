package com.company.models.battle;

import com.company.controller.Exceptions.InvalidDeck;
import com.company.models.Player;
import com.company.models.Position;
import com.company.models.widget.Flag;

public class CollectFlagBattle extends Battle
{
    private CollectFlagBattle(Player firstPlayer, Player secondPlayer, int winnerPrize, int flagNumbers) throws InvalidDeck
    {
        super(firstPlayer, secondPlayer, winnerPrize);
        initialiseBattle();
        this.flagNumbers = flagNumbers;
        addFlagsToMap();
    }

    private static CollectFlagBattle collectFlagBattleInstance;


    public static void setBattle(Player firstPlayer, Player secondPlayer, int winnerPrize, int flagNumbers) throws InvalidDeck
    {
        collectFlagBattleInstance = new CollectFlagBattle(firstPlayer, secondPlayer, winnerPrize, flagNumbers);
    }

    public static CollectFlagBattle getInstance()
    {
        return collectFlagBattleInstance;
    }

    private int flagNumbers;

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
