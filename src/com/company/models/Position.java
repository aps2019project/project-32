package com.company.models;

import com.company.controller.Menus.battlemenus.BattleMenu;

import java.security.SecureRandom;

public class Position
{
    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    private static SecureRandom randomMaker = new SecureRandom();

    public static Position getRandomPosition()
    {

        int row;
        int col;
        while (true)
        {
            row = randomMaker.nextInt(5);
            col = randomMaker.nextInt(9);
            if (BattleMenu.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap()[row][col] == null)
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
