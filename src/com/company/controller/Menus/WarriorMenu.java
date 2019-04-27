package com.company.controller.Menus;

public class WarriorMenu implements AbstractMenu
{
    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("move to \\w \\w"))
        {

        }
        else if (command.matches("attack \\d+"))
        {

        }
        else if (command.matches("attack combo \\d+ (\\d+)+"))
        {

        }
        else if (command.matches("use special power \\w \\w"))
        {

        }
        else if (command.matches("show hand"))
        {

        }
        else if (command.matches("insert \\w+ in \\d \\d"))
        {

        }
        else if (command.matches("end turn"))
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
}
