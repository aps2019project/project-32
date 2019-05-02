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
    private StartBattleMenu(){}

    @Override
    public void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist, InvalidDeck, DeckNameAlreadyExist, CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready, UnSelectable, NotEnoughCash, CantAddUsableItems, InvalidTargetException, InvalidAttackException, OpponentMinionIsUnvalidForAttack, InvalidWarriorForAttack, WarriorUnderStun, WarriorIsTired, CoolDownRemaining, NotEnoughMana, BattleHasNotInitialise
    {
        if (command.matches("New Game (AI) \\w+"))
        {
            newGameWithAI(command);
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
        }
    }

    @Override
    public String toShowMenu()
    {
        return "1.New Game AI <BattleMode>2.New Game Player <Player>";
    }

    public void newGameWithAI(String command)
    {
        String battleModeInformation = command.split(" ")[4];
        BattleMode battleMode = BattleMode.valueOf(battleModeInformation);
        switch (battleMode)
        {
            case hasFlag:
                BattleMenu.getInstance().setCurrentBattle
                        (new KeepFlagBattle(Controller.getInstance().getCurrentPlayer(), Battle.createAIPlayer()));
            case moreFlag:
                BattleMenu.getInstance().setCurrentBattle
                        (new CollectFlagBattle(Controller.getInstance().getCurrentPlayer(), Battle.createAIPlayer()));
            case deadMatch:
                BattleMenu.getInstance().setCurrentBattle
                        (new DeadBattle(Controller.getInstance().getCurrentPlayer(), Battle.createAIPlayer()));
        }
    }
}
