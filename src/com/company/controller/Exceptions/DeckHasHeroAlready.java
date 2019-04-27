package com.company.controller.Exceptions;

public class DeckHasHeroAlready extends Exception
{
    public DeckHasHeroAlready()
    {
        super("This Deck has Hero Already!");
    }
}
