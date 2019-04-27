package com.company.controller.Menus;

public class ItemMenu implements AbstractMenu
{


    public ItemMenu()
    {
        super();
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if(command.matches("show info")){

        }else if(command.matches("use location \\d \\d")){

        }else if(command.matches("show next card")){

        }else if(command.matches("enter graveyard")){

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
