package com.company.controller.Exceptions;

public class CoolDownRemaining extends Exception
{
    public CoolDownRemaining()
    {
        super("Spell is In CoolDown!");
    }
}
