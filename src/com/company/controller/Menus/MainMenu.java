package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.models.Player;

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
        if (command.matches("enter collection"))
            Controller.getInstance().changeCurrentMenuTo(CollectionMenu.getInstance());

        else if (command.matches("enter show leaderboard"))
            Player.toShowLeaderBoard(); // send to view

        else if (command.matches("enter shop"))
            Controller.getInstance().changeCurrentMenuTo(ShopMenu.getInstance());

        else if (command.matches("enter battle"))
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());

        else if (command.matches("enter exit"))
            Controller.getInstance().changeCurrentMenuTo(EntryMenu.getInstance());

        else if (command.matches("enter help"))
            mainMenuInstance.help();

        else if (command.matches("enter save"))
            mainMenuInstance.save();
    }

    @Override
    public String help()
    {
        return "1.Collection\n2.Shop\n3.Battle\n4.Exit\n5.Help\n"; // sendToView
    }

    @Override
    public String toShowMenu()
    {
        return null;
    }

    private void save()
    {
    }
}
