package com.company.controller.Menus.battlemenus;

import com.company.controller.Menus.AbstractMenu;

public class GraveYardMenu implements AbstractMenu
{
    private GraveYardMenu()
    {
    }

    private static GraveYardMenu graveYardMenuInstance = new GraveYardMenu();

    public static AbstractMenu getInstance()
    {
        return graveYardMenuInstance;
    }


    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("show info \\d+"))
        {

        }
        else if (command.matches("show cards"))
        {

        }
        else if (command.matches("help"))
        {

        }
        else if (command.matches("exit"))
        {

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

    public void toShowCards()
    {

    }

    public void toShowCard(int cardID)
    {

    }
}
