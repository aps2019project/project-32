package com.company.controller.Menus.battlemenus;

import com.company.controller.Menus.AbstractMenu;
import com.company.models.widget.cards.warriors.Warrior;

class WarriorSelectMenu implements AbstractMenu
{
    private static WarriorSelectMenu warriorSelectMenuInstance = new WarriorSelectMenu();
    private Warrior currentWarrior;

    public static WarriorSelectMenu getInstance()
    {
        return warriorSelectMenuInstance;
    }

    private WarriorSelectMenu()
    {
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("Move to \\d \\d"))
        {

        }
        else if (command.matches("Attack \\d+"))
        {

        }
        else if (command.matches("Attack Combo \\d+ (\\d+)+"))
        {

        }
        else if (command.matches("Use Special Power \\d \\d"))
        {

        }
        else if (command.matches("Show Hand"))
        {

        }
        else if (command.matches("Insert \\w+ in \\d \\d"))
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

    public Warrior getCurrentWarrior()
    {
        return currentWarrior;
    }

    public void setCurrentWarrior(Warrior currentWarrior)
    {
        this.currentWarrior = currentWarrior;
    }
}