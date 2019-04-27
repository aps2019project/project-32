package com.company.controller.Menus;

import com.company.models.widget.cards.Card;
import com.company.models.widget.items.Item;

public class ShopMenu implements AbstractMenu
{
    private ShopMenu()
    {
    }

    private static ShopMenu shopMenuInstance = new ShopMenu();

    public static AbstractMenu getInstance()
    {
        return shopMenuInstance;
    }

    private int IDCounter = 1;

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("exit"))
        {

        }
        else if (command.matches("search \\w+ \\w+"))
        {

        }
        else if (command.matches("search collection \\w+ \\w+"))
        {

        }
        else if (command.matches("buy \\w+ \\w+"))
        {

        }
        else if (command.matches("sell \\w+ \\w+"))
        {

        }
        else if (command.matches("show"))
        {

        }
        else if (command.matches("help"))
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

    public void buyCard()
    {

    }

    public void sellCard()
    {

    }

    public Card searchCard(String cardName)
    {
        return null;
    }

    public Item searchItem(String itemName)
    {
        return null;
    }

    public int getID(String name)
    {
        return 0;
    }

    public void toShowShop()
    {

    }

}
