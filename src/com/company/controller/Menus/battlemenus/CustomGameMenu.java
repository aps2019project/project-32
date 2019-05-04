package com.company.controller.Menus.battlemenus;

import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;

public class CustomGameMenu implements AbstractMenu
{
    private static CustomGameMenu ourInstance = new CustomGameMenu();

    public static CustomGameMenu getInstance()
    {
        return ourInstance;
    }

    private CustomGameMenu()
    {
    }

    @Override
    public void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist, InvalidDeck, DeckNameAlreadyExist, CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready, UnSelectable, NotEnoughCash, CantAddUsableItems, InvalidTargetException, InvalidAttackException, OpponentMinionIsUnvalidForAttack, InvalidWarriorForAttack, CoolDownRemaining, NotEnoughMana, BattleHasNotInitialise, InvalidPosition
    {




    }

    @Override
    public String toShowMenu()
    {
        return null;
    }
}
