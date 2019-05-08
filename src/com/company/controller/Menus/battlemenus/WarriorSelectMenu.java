package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Warrior;


public class WarriorSelectMenu implements AbstractMenu
{
    private static WarriorSelectMenu warriorSelectMenuInstance = new WarriorSelectMenu();
    private Battle currentBattle;
    private Warrior currentWarrior;

    public static WarriorSelectMenu getInstance()
    {
        return warriorSelectMenuInstance;
    }

    private WarriorSelectMenu()
    {
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void selectOptionByCommand(String command) throws Exception
    {
        if (command.matches("Move to \\d \\d"))
            moveCard(command);

        else if (command.matches("Attack \\d \\d"))
            attack(command);

        else if (command.matches("Attack Combo \\d+ (\\d+)+"))
            comboAttack(command);

        else if (command.matches("Use Special Power \\d \\d"))
            UseSpecialSpell(command);

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
    }

    private void UseSpecialSpell(String command) throws Exception
    {
        int col = Integer.parseInt(command.split(" ")[2]);
        int row = Integer.parseInt(command.split(" ")[3]);
        if (col >= 9 || row >= 5)
            throw new InvalidTargetException();

        if (currentWarrior instanceof Hero)
            BattleMenu.getInstance().getCurrentBattle().doWarriorSpell(currentWarrior, new Position(row, col));
    }

    public void moveCard(String command) throws InvalidTargetException, CantMove
    {
        Position cardPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(getCurrentWarrior());
        Widget[][] map = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap();
        int col = Integer.parseInt(command.split(" ")[2]);
        int row = Integer.parseInt(command.split(" ")[3]);
        if (col >= 9 || row >= 5 || col < 0 || row < 0 || Math.abs(cardPosition.col - col) + Math.abs(cardPosition.row - row) > 2 || map[col][row] != null)
            throw new InvalidTargetException();

        if (row != cardPosition.row && col != cardPosition.col && map[cardPosition.row][col] != null && map[row][cardPosition.col] != null)
            throw new InvalidTargetException();

        BattleMenu.getInstance().getCurrentBattle().moveWarriorOptions(currentWarrior, cardPosition, new Position(row, col));
    }

    public void attack(String command) throws Exception
    {
        Position cardPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(getCurrentWarrior());
        int col = Integer.parseInt(command.split(" ")[2]);
        int row = Integer.parseInt(command.split(" ")[3]);
        Widget[][] map = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap();
        if (col >= 9 || row >= 5 || map[row][col] != null)
            throw new InvalidAttackException();

        if (map[row][col].getOwnerPlayer() == currentWarrior.getOwnerPlayer())
            throw new InvalidWarriorForAttack();

        if (Math.abs(cardPosition.col - col) + Math.abs(cardPosition.row - row) > currentWarrior.getAttackRadius())
            throw new OpponentMinionIsUnvalidForAttack();

        BattleMenu.getInstance().getCurrentBattle().attackActions(currentWarrior, (Warrior) map[row][col]);
        Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
    }

    public void comboAttack(String command)
    {
        //TODO
    }

    @Override
    public String toShowMenu()
    {
        return "1.Move\n2.Attack\n3.Attack Combo\n4.Use Special Spell\n5.Exit\n";
    }

    public Warrior getCurrentWarrior()
    {
        return currentWarrior;
    }

    public void setCurrentWarrior(Warrior currentWarrior)
    {
        this.currentWarrior = currentWarrior;
    }

    public void setCurrentBattle(Battle currentBattle)
    {
        this.currentBattle = currentBattle;
    }

    public Battle getCurrentBattle()
    {
        return currentBattle;
    }
}