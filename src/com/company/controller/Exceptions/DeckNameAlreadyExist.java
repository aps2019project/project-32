package com.company.controller.Exceptions;

public class DeckNameAlreadyExist extends Exception
{
    public DeckNameAlreadyExist()
    {
        super("DeckName Already Existed!");
    }
}
