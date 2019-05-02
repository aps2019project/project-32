package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.models.Player;
import com.company.view.View;

import java.util.ArrayList;
import java.util.Comparator;

public class MainMenu implements AbstractMenu
{
    private MainMenu()
    {
    }
    private static MainMenu mainMenuInstance = new MainMenu();
    public static AbstractMenu getInstance()
    {
        return mainMenuInstance;
    }

    @Override
    public void selectOptionByCommand(String command)
    {

        if (command.matches("Collection"))
            Controller.getInstance().changeCurrentMenuTo(CollectionMenu.getInstance());

        else if (command.matches("Show LeaderBoard"))
            View.getInstance().show(toShowLeaderBoard());

        else if (command.matches("Shop"))
            Controller.getInstance().changeCurrentMenuTo(ShopMenu.getInstance());

        else if (command.matches("Battle"))
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(EntryMenu.getInstance());

        else if (command.matches("Help"))
            View.getInstance().show(mainMenuInstance.toShowMenu());

        else if (command.matches("Save"))
            mainMenuInstance.save();
    }

    @Override
    public String toShowMenu()
    {
        return "1.Collection\n2.Shop\n3.Battle\n4.Exit\n5.Help\n6.Show LeaderBoard";
    }

    private void save()
    {
    }

    private String toShowLeaderBoard()
    {
        ArrayList<Player> copiedPlayers = new ArrayList<>(Player.getPlayers());
        copiedPlayers.sort(new CompareByWinNumber());

        String leaderBoard = "";
        int counter = 1;
        for (Player copiedPlayer : copiedPlayers)
        {
            leaderBoard = leaderBoard.concat(String.format
                    ("%d- UserName: %s - Wins: %d\n", counter, copiedPlayer.getName(), copiedPlayer.getWinNumber()));
            counter++;
        }

        return leaderBoard;
    }

    class CompareByWinNumber implements Comparator<Player>
    {
        @Override
        public int compare(Player o1, Player o2)
        {
            return Integer.compare(o2.getWinNumber(), o1.getWinNumber());
        }
    }
}
