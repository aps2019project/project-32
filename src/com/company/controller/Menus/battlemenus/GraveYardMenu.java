package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Menus.AbstractMenu;
import com.company.view.View;

public class GraveYardMenu implements AbstractMenu
{
    private GraveYardMenu()
    {
    }

    private static GraveYardMenu graveYardMenuInstance;

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
        if (command.matches("Show Information \\d+"))
            Controller.getInstance().getCurrentPlayer().getGraveYard().toShowCard
                    (Integer.valueOf(command.split(" ")[2]));

        else if (command.matches("Show Cards"))
            Controller.getInstance().getCurrentPlayer().getGraveYard().toShowGraveYardCards();

        else if (command.matches("Help"))
            View.getInstance().show(toShowMenu());

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
    }

    @Override
    public String toShowMenu()
    {
        return "1.Show Information <CardID>\n2.Show Cards\n3.Help\n4.Exit";
    }
}
