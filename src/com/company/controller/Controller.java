package com.company.controller;

import com.company.controller.Menus.AbstractMenu;
import com.company.controller.Menus.EntryMenu;
import com.company.controller.Menus.ShopMenu;
import com.company.models.Player;
import com.company.view.Request;
import com.company.view.View;

public class Controller
{
    private Controller()
    {
    }
    private static Controller controllerInstance = new Controller();
    public static Controller getInstance()
    {
        return controllerInstance;
    }

    private AbstractMenu currentMenu;
    private Player currentPlayer;

    public void run()
    {
        currentMenu = EntryMenu.getInstance();
        ((ShopMenu) ShopMenu.getInstance()).addCardToShop();
        while (true)
        {
            String command = Request.getInstance().getNewCommand();
            try
            {
                currentMenu.selectOptionByCommand(command);
            } catch (Exception e)
            {
                View.getInstance().show(e.getMessage());
            }
        }
    }

    public AbstractMenu getCurrentMenu()
    {
        return currentMenu;
    }

    public void changeCurrentMenuTo(AbstractMenu currentMenu)
    {
        View.getInstance().show(currentMenu.toShowMenu()); //send this to view
        this.currentMenu = currentMenu;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
}
