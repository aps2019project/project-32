package com.company.controller;

import com.company.controller.Exceptions.*;
import com.company.controller.Menus.*;
import com.company.models.Player;
import com.company.models.battle.Battle;
import com.company.view.Request;

import java.util.ArrayList;

public class Controller
{

    private Controller()
    {
    }
    private static Controller controllerInstance;
    public static Controller getInstance()
    {
        return controllerInstance;
    }

    private AbstractMenu currentMenu;
    private Player currentPlayer;
    private Battle currentBattle;
    private ArrayList<AbstractMenu> menus = new ArrayList<>();

    private void activeMenus()
    {
        menus.add(EntryMenu.getInstance());
        menus.add(MainMenu.getInstance());
        menus.add(BattleMenu.getInstance());
        menus.add(CollectionMenu.getInstance());
        menus.add(GraveYardMenu.getInstance());
        menus.add(ShopMenu.getInstance());
    }

    public void run() throws CardExistInDeckAlready, UserNameDidntExist, UserNameAlreadyExist, DeckIsFull, WrongPassword, WeekPassword, DeckNameAlreadyExist, CardNotFoundInCollection, InvalidDeck, DeckNotFound, DeckHasHeroAlready, DeckHasPassiveAlready {
        currentMenu = EntryMenu.getInstance();
        activeMenus();
        while (true)
        {
            String command = Request.getInstance().getNewCommand();
            currentMenu.selectOptionByCommand(command);
        }
    }

    public AbstractMenu getCurrentMenu()
    {
        return currentMenu;
    }

    public void changeCurrentMenuTo(AbstractMenu currentMenu)
    {
        currentMenu.help(); //send this to view
        this.currentMenu = currentMenu;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<AbstractMenu> getMenus()
    {
        return menus;
    }

    public void setMenus(ArrayList<AbstractMenu> menus)
    {
        this.menus = menus;
    }
}
