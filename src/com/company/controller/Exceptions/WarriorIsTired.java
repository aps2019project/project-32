package com.company.controller.Exceptions;

public class WarriorIsTired extends Exception
{
    public WarriorIsTired()
    {
        super("Warrior is Tired! So Cant Attack Or Move!");
    }
}
