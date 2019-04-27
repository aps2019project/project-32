package com.company.controller.Exceptions;

public class DeckHasPassiveAlready extends Exception
{
    public DeckHasPassiveAlready()
    {
        super("Deck has Passive Item Already!");
    }
}
