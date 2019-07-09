package client.controller.battlemenus;


import client.controller.Controller;
import client.controller.menus.AbstractMenu;
import client.controller.menus.MainMenu;
import client.models.Player;

public class EndGameMenu extends AbstractMenu
{
    private static EndGameMenu ourInstance = new EndGameMenu();

    public static EndGameMenu getInstance()
    {
        return ourInstance;
    }

    private Player winnerPlayer;
    private Player loserPlayer;

    private EndGameMenu()
    {
    }

    public void selectOptionByCommand(String command)
    {
        if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
    }

    public String toShowMenu()
    {
        return winnerPlayer.getName() + " has Won!\n" + loserPlayer.getName() + " has Lost!\n" + "1.Exit\n";
    }

    public Player getWinnerPlayer()
    {
        return winnerPlayer;
    }

    public void setWinnerAndLosePlayer(Player winnerPlayer, Player loserPlayer)
    {
        this.winnerPlayer = winnerPlayer;
        this.loserPlayer = loserPlayer;
    }

    @Override
    public void initializeMenuGUI() {

    }
}
