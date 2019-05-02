package com.company.controller.Exceptions;

public class GameIsNotOver extends Exception
{
    public GameIsNotOver()
    {
        super("Game Is Not Over");
    }
}
