package com.company.controller.Exceptions;

public class DeckNotFound extends Exception
{
    public DeckNotFound()
    {
        super("Deck not Found In This Player Decks!");
    }
}
