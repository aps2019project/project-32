package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Menus.AbstractMenu;
import com.company.controller.Menus.MainMenu;

public class StartBattleMenu implements AbstractMenu
{
    private static StartBattleMenu startBattleMenuInstance = new StartBattleMenu();

    public static StartBattleMenu getInstance()
    {
        return startBattleMenuInstance;
    }

    private StartBattleMenu()
    {
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("Story Mode"))
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());

        else if (command.matches("Custom Game"))
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());

        else if (command.matches("Multy Player"))
            multiPlayer();

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
    }

    @Override
    public String toShowMenu()
    {
        return "1.Story Mode\n2.Custom Game\n3.NetWork Game\n4.Exit\n";
    }

    public void multiPlayer()
    {
        //TODO
    }
}
