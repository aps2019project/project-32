package com.company.controller.Menus.battlemenus;

import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.models.widget.cards.spells.Spell;

public class CollectibleSelectMenu implements AbstractMenu
{
    private static CollectibleSelectMenu collectibleSelectMenuInstance = new CollectibleSelectMenu();
    private Spell currentCollectible;

    public static CollectibleSelectMenu getInstance()
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
    }


    @Override
    public String toShowMenu()
    {
        return null;
    }

    public Spell getCurrentCollectible()
    {
        return currentCollectible;
    }

    public void setCurrentCollectible(Spell currentCollectible)
    {
        this.currentCollectible = currentCollectible;
    }
}