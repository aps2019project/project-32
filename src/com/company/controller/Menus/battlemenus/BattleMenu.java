package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.controller.Menus.MainMenu;
import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.warriors.Warrior;
import com.company.models.widget.items.Collectible;

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
    public void selectOptionByCommand(String command) throws UnSelectable
    {
        if (command.matches("Game Info"))
            battle.toShowGameInfo(); // send to view

        else if (command.matches("Show My Minions"))
            if (battle.getBattleTurnHandler().getPlayerHasTurn().equals(battle.getFirstPlayer()))
                battle.getBattleMap().toShowMinionInMap(battle.getFirstPlayer()); // send to view
            else
                battle.getBattleMap().toShowMinionInMap(battle.getSecondPlayer());
        else if (command.matches("Show Opponent Minions"))
            if (battle.getBattleTurnHandler().getPlayerHasTurn().equals(battle.getFirstPlayer()))
                battle.getBattleMap().toShowMinionInMap(battle.getSecondPlayer());
            else
                battle.getBattleMap().toShowMinionInMap(battle.getFirstPlayer());
        else if (command.matches("Show Card Info \\d+"))
            toShowCardInfo(Integer.parseInt(command.split(" ")[3]));

        else if (command.matches("Select \\d+"))
            selectActions(Integer.parseInt(command.split(" ")[1]));

        else if (command.matches("Enter GraveYardMenu"))
            Controller.getInstance().changeCurrentMenuTo(GraveYardMenu.getInstance());

        else if (command.matches("End Game"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

        else if (command.matches("End Turn"))
            battle.getBattleTurnHandler().changeTurn();

        else if (command.matches("Show Collectable"))
            battle.getBattleTurnHandler().getPlayerHasTurn().getPlayerHand().toShowCollectedItems(); // send to view
    }

    @Override
    public String toShowMenu()
    {
        return "1.Game Information 2.My Minions 3.OpponentMinions  "
    }

    public void selectActions(int cardID) throws UnSelectable
    {
        Widget widget = battle.getBattleMap().selectCard(cardID);
        if (widget instanceof Warrior)
        {
            WarriorSelectMenu.getInstance().setCurrentWarrior(((Warrior) widget));
            Controller.getInstance().changeCurrentMenuTo(WarriorSelectMenu.getInstance());
        }
        else if (widget instanceof Collectible)
        {
            CollectibleSelectMenu.getInstance().setCurrentCollectible(((Collectible) widget));
            Controller.getInstance().changeCurrentMenuTo(CollectibleSelectMenu.getInstance());
        }
        else
            throw new UnSelectable();
    }

    public String toShowCardInfo(int cardID)
    {
        Widget widget = battle.getBattleMap().selectCard(cardID);
        Position position = battle.getBattleMap().getPosition(widget);
        return widget.toShow() + position.toString() + "\n"; // show card info
    }

    public String toShowHand()
    {
        return battle.getBattleTurnHandler().getPlayerHasTurn().getPlayerHand().toShowHand(); //send to view
    }

    public String toShowEndGameInfo()
    {
        return battle.toShowEndGameDetails();
    }
}





