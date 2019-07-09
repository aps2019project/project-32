package client.models.battle;

import client.controller.Exceptions.InvalidDeck;
import client.models.Player;
import client.models.Position;
import client.models.widget.Flag;
import client.models.widget.cards.spells.Spell;

public class CollectFlagBattle extends Battle
{
    public CollectFlagBattle(Player firstPlayer, Player secondPlayer, int winnerPrize, int flagNumbers) throws InvalidDeck, CloneNotSupportedException
    {
        super(firstPlayer, secondPlayer, winnerPrize);
        this.flagNumbers = flagNumbers;
    }

    private static CollectFlagBattle collectFlagBattleInstance;


    public static void setBattle(Player firstPlayer, Player secondPlayer, int winnerPrize, int flagNumbers) throws InvalidDeck, CloneNotSupportedException
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
        String toShow = "Battle : CollectFlagBattle\n";
        toShow = toShow + super.toShowGameInfo();
        return toShow;
    }

    public void addFlagsToMap()
    {
        for (int i = 0; i < flagNumbers; i++)
        {
            Position randomPosition = Position.getRandomFreePosition(Spell.class);
            battleMap.getSpellsAndCollectibleOnMap()[randomPosition.row][randomPosition.col] = new Flag();
        }
    }
}
