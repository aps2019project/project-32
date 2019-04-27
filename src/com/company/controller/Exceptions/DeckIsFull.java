package com.company.controller.Exceptions;

public class DeckIsFull extends Exception
{
    public DeckIsFull()
    {
        super("Deck is Full!");
    }
}
