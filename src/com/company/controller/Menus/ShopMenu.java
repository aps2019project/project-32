package com.company.controller.Menus;

import com.company.controller.Controller;
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
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
        }
        else if (command.matches("search \\w+ ")) //search in shop
        {

        }
        else if (command.matches("search collection \\w+")) // search in collection
        {

        }
        else if (command.matches("buy \\w+"))
        {


        }
        else if (command.matches("sell \\w+ \\w+"))
        {

        }
        else if (command.matches("show collection"))
        {
            Controller.getInstance().getCurrentPlayer().getCollection().toShowCollection(); //send to view
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

    public void buyCard(String command)
    {

    }

    public void sellCard()
    {

    }

    public String searchCard(String cardName)
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
