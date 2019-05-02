package com.company.controller.Menus.battlemenus;

import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.models.widget.items.Collectible;

public class CollectibleSelectMenu implements AbstractMenu
{
    private static com.company.controller.Menus.battlemenus.CollectibleSelectMenu collectibleSelectMenuInstance = new com.company.controller.Menus.battlemenus.CollectibleSelectMenu();
    private Collectible currentCollectible;

    public static com.company.controller.Menus.battlemenus.CollectibleSelectMenu getInstance()
    {
        return collectibleSelectMenuInstance;
    }

    private CollectibleSelectMenu()
    {
    }

    @Override
    public void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist, InvalidDeck, DeckNameAlreadyExist, CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready
    {
        if (command.matches("Show Info"))
        {

        }
        else if (command.matches("Use Location \\d \\d"))
        {

        }
        else if (command.matches("Show NextCard"))
        {

        }
    }


    @Override
    public String toShowMenu()
    {
        return null;
    }

    public Collectible getCurrentCollectible()
    {
        return currentCollectible;
    }

    public void setCurrentCollectible(Collectible currentCollectible)
    {
        this.currentCollectible = currentCollectible;
    }
}