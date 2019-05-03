package com.company.controller.Exceptions;

public class CantAttack extends Exception
{
    public CantAttack()
    {
        super("Warrior Cant Attack!");
    }
}
