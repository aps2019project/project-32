package com.company.models;

public class Position
{
    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString()
    {
        return String.format("Location (%d,%d)", col, row);
    }

    public int row;
    public int col;
}
