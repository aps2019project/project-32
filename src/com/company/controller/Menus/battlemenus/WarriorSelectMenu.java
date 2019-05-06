package com.company.controller.Menus.battlemenus;

import com.company.controller.Exceptions.*;
import com.company.models.Position;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Warrior;


class WarriorSelectMenu extends BattleMenu
{
    private static WarriorSelectMenu warriorSelectMenuInstance = new WarriorSelectMenu();
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
    public void selectOptionByCommand(String command) throws InvalidTargetException, InvalidAttackException, OpponentMinionIsUnvalidForAttack, InvalidWarriorForAttack, CoolDownRemaining, NotEnoughMana
    {
        if (command.matches("Move to \\d \\d"))
            moveCard(command);

        else if (command.matches("Attack \\d \\d"))
            attack(command);

        else if (command.matches("Attack Combo \\d+ (\\d+)+"))
            comboAttack(command);

        else if (command.matches("Use Special Power \\d \\d"))
            UseSpecialSpell(command);
    }

    private void UseSpecialSpell(String command) throws InvalidTargetException, CoolDownRemaining, NotEnoughMana
    {
        int col = Integer.parseInt(command.split(" ")[2]);
        int row = Integer.parseInt(command.split(" ")[3]);
        if (col >= 9 || row >= 5)
            throw new InvalidTargetException();

        if (currentWarrior instanceof Hero)
            BattleMenu.getInstance().getCurrentBattle().doWarriorSpell(currentWarrior, new Position(row, col));
    }

    public void comboAttack(String command)
    {

    }

    public void moveCard(String command) throws InvalidTargetException
    {
        Position cardPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(getCurrentWarrior());
        Widget[][] map = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap();
        int col = Integer.parseInt(command.split(" ")[2]);
        int row = Integer.parseInt(command.split(" ")[3]);
        if (col >= 9 || row >= 5 || Math.abs(cardPosition.col - col) + Math.abs(cardPosition.row - row) > 2 || map[col][row] != null)
            throw new InvalidTargetException();

        if (col == cardPosition.col)
        {
            if (row > cardPosition.row && map[row - 1][col] != null)
                throw new InvalidTargetException();

            if (row < cardPosition.col && map[row + 1][col] != null)
                throw new InvalidTargetException();
        }
        if (row == cardPosition.row)
        {
            if (col > cardPosition.col && map[row][col - 1] != null)
                throw new InvalidTargetException();

            if (col < cardPosition.col && map[row][col + 1] != null)
                throw new InvalidTargetException();
        }
        if (row != cardPosition.row && col != cardPosition.col && map[cardPosition.row][col] != null && map[row][cardPosition.col] != null)
            throw new InvalidTargetException();

        //BattleMenu.getAIPlayer().getCurrentBattle().moveWarriorOptions(currentWarrior, cardPosition, new Position(row, col));
    }

    public void attack(String command) throws InvalidAttackException, InvalidWarriorForAttack, OpponentMinionIsUnvalidForAttack
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

       // BattleMenu.getAIPlayer().getCurrentBattle().attackActions(currentWarrior, (Warrior) map[row][col]);
    }



    @Override
    public String toShowMenu()
    {
        return null;
    }

    public Warrior getCurrentWarrior()
    {
        return currentWarrior;
    }

    public void setCurrentWarrior(Warrior currentWarrior)
    {
        this.currentWarrior = currentWarrior;
    }
}