package client.controller.battlemenus;


import client.controller.Controller;
import client.controller.Exceptions.CardNotFound;
import client.controller.Exceptions.UnSelectable;
import client.controller.menus.AbstractMenu;
import client.controller.menus.MainMenu;
import client.models.Player;
import client.models.Position;
import client.models.battle.Battle;
import client.models.widget.Widget;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.Warrior;
import client.view.View;

public class BattleController extends AbstractMenu
{
    private Battle currentBattle;

    protected BattleController()
    {
    }

    private static BattleController battleControllerInstance = new BattleController();

    public static BattleController getInstance()
    {
        return battleControllerInstance;
    }

    public void selectOptionByCommand(String command) throws Exception
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

        else if (command.matches("Select \\d+")){}
//            selectActions(Integer.parseInt(command.split(" ")[1]));

        else if (command.matches("Enter GraveYardMenu"))
            Controller.getInstance().changeCurrentMenuTo(GraveYardMenu.getInstance());

        else if (command.matches("End Game"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance() );

        else if (command.matches("End Turn"))
            currentBattle.getTurnHandler().changeTurn();

        else if (command.matches("Show Collectables"))
            View.getInstance().show(currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowCollectedItems());

        else if (command.matches("Show Hand"))
            View.getInstance().show(currentBattle.getTurnHandler().getPlayerHasTurn().getPlayerHand().toShowHand());

        else if (command.matches("Insert \\w+ in \\d \\d"))
            insertCard(command);

        else if (command.matches("Determent"))
            determent();
    }


    public String toShowMenu()
    {
        return "0.Help\n1.Game Information\n2.My Minions\n3.Opponent Minions\n4.Show Card Information\n5.Select\n6.Enter GraveYard\n" +
                "7.End Game\n8.End Turn\n9.Show Collectible\n10.Show Hand\n11.Inset\n12.Determent\n";
    }

    public void selectActions(Card card) throws UnSelectable
    {
        Warrior warrior = ((Warrior) card);
        if (warrior != null && BattleController.getInstance().getCurrentBattle()
                .getTurnHandler().getPlayerHasTurn() == warrior.getOwnerPlayer())
        {
            WarriorSelectMenu.getInstance().setCurrentWarrior(warrior);
            WarriorSelectMenu.getInstance().setCurrentBattle(this.currentBattle);
            Controller.getInstance().changeCurrentMenuTo(WarriorSelectMenu.getInstance());
            Controller.getInstance().setCurrentMenu(WarriorSelectMenu.getInstance());
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
        BattleController.getInstance().currentBattle = currentBattle;
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
        BattleController.getInstance().getCurrentBattle().checkBattleResult();
    }

    public void determent()
    {
        Player determentPlayer = Controller.getInstance().getCurrentPlayer();
        Player winnerPlayer = currentBattle.getOtherPlayer(determentPlayer);
        currentBattle.winActions(winnerPlayer);
    }

    @Override
    public void initializeMenuGUI() {

    }
}





