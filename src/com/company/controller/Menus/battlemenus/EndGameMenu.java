package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Menus.AbstractMenu;
import com.company.controller.Menus.MainMenu;
import com.company.models.Player;

public class EndGameMenu implements AbstractMenu
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

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
    }

    @Override
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
}
