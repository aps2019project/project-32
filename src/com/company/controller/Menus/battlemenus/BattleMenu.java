package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.controller.Menus.MainMenu;
import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.view.View;

public class BattleMenu implements AbstractMenu
{
    protected Battle currentBattle;

    protected BattleMenu()
    {
    }
    private static BattleMenu battleMenuInstance = new BattleMenu();
    public static BattleMenu getInstance()
    {
        return battleMenuInstance;
    }

    @Override
    public void selectOptionByCommand(String command) throws UnSelectable, CardNotFound, InvalidPosition, InvalidTargetException, InvalidAttackException, OpponentMinionIsUnvalidForAttack, InvalidWarriorForAttack, CoolDownRemaining, NotEnoughMana
    {
        if (command.matches("Game Information"))
            View.getInstance().show(currentBattle.toShowGameInfo());

        else if (command.matches("Show My Minions"))
            View.getInstance().show(currentBattle.getBattleMap().toShowMinionInMap
                    (currentBattle.getTurnHandler().getPlayerHasTurn()));

        else if (command.matches("Show Opponent Minions"))
            View.getInstance().show(currentBattle.getBattleMap().toShowMinionInMap
                    (currentBattle.getTurnHandler().getPlayerInRest()));

        else if (command.matches("Show Card Info \\d+"))
            View.getInstance().show(toShowCardInfo(Integer.parseInt(command.split(" ")[3])));

        else if (command.matches("Select \\d+"))
            selectActions(Integer.parseInt(command.split(" ")[1]));

        else if (command.matches("Enter GraveYardMenu"))
            Controller.getInstance().changeCurrentMenuTo(GraveYardMenu.getInstance());

        else if (command.matches("End Game"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

        else if (command.matches("End Turn"))
            currentBattle.getTurnHandler().changeTurn();

        else if (command.matches("Show Collectables"))
            View.getInstance().show(currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowCollectedItems());

        else if (command.matches("Show Hand"))
            View.getInstance().show(currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowHand());

        else if (command.matches("Insert \\w+ in \\d \\d"))
            insertCard(command);
    }


    @Override
    public String toShowMenu()
    {
        return "1.Game Information 2.My Minions 3.Opponent Minions 4.Show Card Information 5.Select 6.Enter GraveYard" +
                " 7.End Game 8.End Turn 9.Show Collectible 10.Show Hand";
    }

    private void selectActions(int cardID) throws UnSelectable
    {
        Widget widget = currentBattle.getBattleMap().selectCard(cardID);
        if (widget instanceof Warrior)
        {
            WarriorSelectMenu.getInstance().setCurrentWarrior(((Warrior) widget));
            Controller.getInstance().changeCurrentMenuTo(WarriorSelectMenu.getInstance());
        }
        else
            throw new UnSelectable();
    }

    private String toShowCardInfo(int cardID)
    {
        Widget widget = currentBattle.getBattleMap().selectCard(cardID);
        Position position = currentBattle.getBattleMap().getPosition(widget);
        return widget.toShow() + position.toString() + "\n"; // show card info
    }

    public String toShowHand()
    {
        return currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowHand(); //send to view
    }

    public String toShowEndGameInfo()
    {
        return currentBattle.toShowEndGameDetails();
    }

    public Battle getCurrentBattle()
    {
        return currentBattle;
    }

    public void setCurrentBattle(Battle currentBattle)
    {
        BattleMenu.getInstance().currentBattle = currentBattle;
    }

    public void insertCard(String command) throws CardNotFound, InvalidPosition
    {
        String cardName = command.split(" ")[1];
        int row = Integer.parseInt(command.split(" ")[4]);
        int col = Integer.parseInt(command.split(" ")[3]);
        Position position = new Position(row, col);
        Card intendedCard = currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().getCardByName(cardName);

        if (intendedCard == null)
            throw new CardNotFound();

        currentBattle.getBattleMap().insertCard(intendedCard, position);
        intendedCard.getOwnerPlayer().getPlayerHand().putCardFromHandActions(intendedCard);
    }
}





