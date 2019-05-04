package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.models.battle.*;

public class StartBattleMenu implements AbstractMenu
{
    private static StartBattleMenu startBattleMenuInstance = new StartBattleMenu();
    public static StartBattleMenu getInstance()
    {
        return startBattleMenuInstance;
    }
    private StartBattleMenu()
    {
    }

    @Override
    public void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist, InvalidDeck, DeckNameAlreadyExist, CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready, UnSelectable, NotEnoughCash, CantAddUsableItems, InvalidTargetException, InvalidAttackException, OpponentMinionIsUnvalidForAttack, InvalidWarriorForAttack, WarriorUnderStun, WarriorIsTired, CoolDownRemaining, NotEnoughMana, BattleHasNotInitialise
    {
        if (command.matches("Story Mode"))
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());

        else if (command.matches("Custom Game"))
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());

        else if(command.matches("Multy Player"))
        {

        }
    }

    @Override
    public String toShowMenu()
    {
        return "1.Story Mode\n2.Custom Game\n3.NetWork Game";
    }
}
