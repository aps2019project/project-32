package com.company.controller.Menus;

import com.company.controller.Exceptions.*;

public interface AbstractMenu
{
    void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist, InvalidDeck, DeckNameAlreadyExist, CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready, UnSelectable, NotEnoughCash, CantAddUsableItems, InvalidTargetException, InvalidAttackException, OpponentMinionIsUnvalidForAttack, InvalidWarriorForAttack, WarriorUnderStun, WarriorIsTired, CoolDownRemaining, NotEnoughMana, BattleHasNotInitialise, InvalidPosition, InvalidBattleName, GameIsNotOver;

    String toShowMenu();
}

