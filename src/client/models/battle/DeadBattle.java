package client.models.battle;

import client.controller.Exceptions.InvalidDeck;
import client.models.Player;

public class DeadBattle extends Battle
{
    public DeadBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck, CloneNotSupportedException
    {
        super(firstPlayer, secondPlayer, winnerPrize);
    }

    private static DeadBattle deadBattleInstance;


    public static void setBattle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws InvalidDeck, CloneNotSupportedException
    {
        deadBattleInstance = new DeadBattle(firstPlayer, secondPlayer, winnerPrize);
    }

    public static DeadBattle getInstance()
    {
        return deadBattleInstance;
    }

    @Override
    public void checkBattleResult()
    {
        System.out.println("result");
        System.out.println(firstPlayer.getCopiedMainDeck().getHero().getHealth());
        System.out.println(secondPlayer.getCopiedMainDeck().getHero().getHealth());

        if (firstPlayer.getCopiedMainDeck().getHero().isDead())
            winActions(firstPlayer);

        if (secondPlayer.getCopiedMainDeck().getHero().isDead())
            winActions(secondPlayer);
    }

    @Override
    public String toShowGameInfo()
    {
        String toShow = "Battle : DeadBattle\n";
        toShow = toShow + super.toShowGameInfo();
        return toShow;
    }
}
