package com.company.controller.Exceptions;

public class BattleHasNotInitialise extends Exception
{
    public BattleHasNotInitialise()
    {
        super("Please Initialise Battle!");
    }
}
