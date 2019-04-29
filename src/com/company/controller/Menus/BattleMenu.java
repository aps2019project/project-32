package com.company.controller.Menus;

import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;

public class BattleMenu implements AbstractMenu
{
    private static Battle battle;

    private BattleMenu()
    {
    }

    private static BattleMenu battleMenuInstance = new BattleMenu();

    public static BattleMenu getInstance()
    {
        return battleMenuInstance;
    }

    public static void setBattle(Battle battle)
    {
        BattleMenu.battle = battle;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("game info"))
        {

        }
        else if (command.matches("show my minions"))
        {

        }
        else if (command.matches("show opponent minions"))
        {

        }
        else if (command.matches("show card info \\d+"))
        {
            int cardID = Integer.parseInt(command.split(" ")[3]);

        }
        else if (command.matches("select \\d+"))
        {
            // barrasi shavad item ast ya warrior
        }
    }


    @Override
    public String help()
    {
        return null;
    }

    @Override
    public String toShowMenu()
    {
        return null;
    }


    public String toShowGameInfo()
    {
        return battle.toShowGameInfo(); // send to view
    }

    public String toShowMinion()
    {
        return battle.getBattleMap().toShowWidgetsInMap();
    }

    public String toShowCardInfo(int cardID)
    {
        Widget widget = battle.getBattleMap().selectCard(cardID);
        return null; // show card info
    }

    public String toShowHand()
    {
        return battle.getBattleTurnHandler().getPlayerHasTurn().getPlayerHand().toShowHand(); //send to view
    }

    public String toShowNextCard()
    {
        return battle.getBattleTurnHandler().getPlayerHasTurn().getPlayerHand().toShowNextCard();
    }

    public String toShowEndGameInfo()
    {
        return battle.toShowEndGameDetails();
    }

    public void moveTo(Position destinationPosition)
    {

    }


}
