package client.models;


import client.controller.battlemenus.BattleController;
import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.Spell;

import java.io.Serializable;
import java.security.SecureRandom;

public class Position implements Serializable
{
    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    private static SecureRandom randomMaker = new SecureRandom();

    public static Position getRandomFreePosition(Class type)
    {
        int row;
        int col;
        while (true)
        {
            row = Math.abs(randomMaker.nextInt()) % 5;
            col = Math.abs(randomMaker.nextInt()) % 9;
            if (type == Warrior.class)
            {
                //if (BattleMenuGUI.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap()[row][col] == null)
                    return new Position(row, col);
            }
            else if (type == Spell.class)
            {
                //if (BattleMenuGUI.getInstance().getCurrentBattle().getBattleMap().getSpellsAndCollectibleOnMap()[row][col] == null)
                    return new Position(row, col);
            }
        }
    }

    public static Position getRandomCaptured()
    {
        int row;
        int col;
        while (true)
        {
            row =  Math.abs(randomMaker.nextInt()) % 5;
            col =  Math.abs(randomMaker.nextInt()) % 9;

            if (BattleController.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap()[row][col] != null)
                return new Position(row, col);
        }
    }

    @Override
    public String toString()
    {
        return String.format("Location (%d,%d)", col, row);
    }

    public int row;
    public int col;
}
