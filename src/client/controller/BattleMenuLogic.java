package client.controller;


import client.controller.Exceptions.*;
import client.models.Player;
import client.models.Position;
import client.models.battle.Battle;
import client.models.widget.Widget;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.Warrior;

public class BattleMenuLogic
{
    private Battle currentBattle;

    protected BattleMenuLogic()
    {
    }

    private static BattleMenuLogic battleMenuLogicInstance = new BattleMenuLogic();

    public static BattleMenuLogic getInstance()
    {
        return battleMenuLogicInstance;
    }


    public void selectOptionByCommand(String command) throws Exception
    {
        if(command.matches("Help"));
           // View.getInstance().show(toShowMenu());

        else if (command.matches("Game Information"));
           // View.getInstance().show(currentBattle.toShowGameInfo());

        else if (command.matches("Show My Minions"));
            //View.getInstance().show(currentBattle.getBattleMap().toShowMinionInMap
                    //(currentBattle.getTurnHandler().getPlayerHasTurn()));

        else if (command.matches("Show Opponent Minions"));
           // View.getInstance().show(currentBattle.getBattleMap().toShowMinionInMap
                    //(currentBattle.getTurnHandler().getPlayerInRest()));

        else if (command.matches("Show Card Info \\d+"));
           // View.getInstance().show(toShowCardInfo(Integer.parseInt(command.split(" ")[3])));

        else if (command.matches("Select \\d+"))
            selectActions(Integer.parseInt(command.split(" ")[1]));

        else if (command.matches("Enter GraveYardMenu"));
          //  Controller.getInstance().changeCurrentMenuTo(GraveYardMenu.getInstance());

        else if (command.matches("End Game"));
           // Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

        else if (command.matches("End Turn"))
            currentBattle.getTurnHandler().changeTurn();

        else if (command.matches("Show Collectables"));
           // View.getInstance().show(currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowCollectedItems());

        else if (command.matches("Show Hand"));
          //  View.getInstance().show(currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowHand());

        else if (command.matches("Insert \\w+ in \\d \\d"));
            //insertCard(command);

        else if (command.matches("Determent"))
            determent();
    }


//    @Override
//    public String toShowMenu()
//    {
//        return "0.Help\n1.Game Information\n2.My Minions\n3.Opponent Minions\n4.Show Card Information\n5.Select\n6.Enter GraveYard\n" +
//                "7.End Game\n8.End Turn\n9.Show Collectible\n10.Show Hand\n11.Inset\n12.Determent\n";
//    }

    public void selectActions(int cardID) throws UnSelectable
    {
        Warrior warrior = currentBattle.getBattleMap().selectCard(cardID);
        if (warrior != null && BattleMenuLogic.getInstance().getCurrentBattle()
                .getTurnHandler().getPlayerHasTurn() == warrior.getOwnerPlayer())
        {
//            WarriorSelectMenu.getInstance().setCurrentWarrior(warrior);
//            WarriorSelectMenu.getInstance().setCurrentBattle(this.currentBattle);
            //Controller.getInstance().changeCurrentMenuTo(WarriorSelectMenu.getInstance());
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

    public Battle getCurrentBattle()
    {
        return currentBattle;
    }

    public void setCurrentBattle(Battle currentBattle)
    {
        BattleMenuLogic.getInstance().currentBattle = currentBattle;
    }

    public void insertCard(String command) throws Exception
    {
        String cardName = command.split(" ")[1];
        int row = Integer.parseInt(command.split(" ")[4]);
        int col = Integer.parseInt(command.split(" ")[3]);
        Position position = new Position(row, col);
        Card intendedCard = currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().getCardByName(cardName);

        if (intendedCard == null)
            throw new CardNotFound();

        currentBattle.insertCard(intendedCard,position);
        intendedCard.getOwnerPlayer().getPlayerHand().putCardFromHandActions(intendedCard);
        BattleMenuLogic.getInstance().getCurrentBattle().checkBattleResult();
    }

    public void determent()
    {
        Player determentPlayer = Controller.getInstance().getCurrentPlayer();
        Player winnerPlayer = currentBattle.getOtherPlayer(determentPlayer);
        currentBattle.winActions(winnerPlayer);
    }
    public void insertCard(Card selectedCard,Position position) throws Exception {
        currentBattle.insertCard(selectedCard,position);
        selectedCard.getOwnerPlayer().getPlayerHand().putCardFromHandActions(selectedCard);
    }
    public void moveCard(Card selectedCard,int row,int col) throws InvalidTargetException, CantMove
    {
        Position cardPosition = currentBattle.getBattleMap().getPosition(selectedCard);
        Widget[][] map = currentBattle.getBattleMap().getWarriorsOnMap();
        if (col >= 9 || row >= 5 || col < 0 || row < 0 || Math.abs(cardPosition.col - col) + Math.abs(cardPosition.row - row) > 2 || map[row][col] != null)
            throw new InvalidTargetException();

        if (row != cardPosition.row && col != cardPosition.col && map[cardPosition.row][col] != null && map[row][cardPosition.col] != null)
            throw new InvalidTargetException();

        currentBattle.moveWarriorOptions(((Warrior) selectedCard), cardPosition, new Position(row, col));
    }
    public void attack(Warrior warrior , Position position) throws Exception
    {
        Position cardPosition = BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getPosition(warrior);
        int col = position.col;
        int row = position.row;
        Widget[][] map = BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap();
        if (col >= 9 || row >= 5 || map[row][col] == null)
            throw new InvalidAttackException();

        if (map[row][col].getOwnerPlayer() == warrior.getOwnerPlayer())
            throw new InvalidWarriorForAttack();

        if (Math.abs(cardPosition.col - col) + Math.abs(cardPosition.row - row) > warrior.getAttackRadius())
            throw new OpponentMinionIsUnvalidForAttack();

        BattleMenuLogic.getInstance().getCurrentBattle().attackActions(warrior, (Warrior) map[row][col]);
        //Controller.getInstance().changeCurrentMenuTo(BattleMenuLogic.getInstance());
    }
}





