package com.company.controller.Exceptions;

public class InvalidDeck extends Exception
{
    public InvalidDeck()
    {
        super("This Deck is Invalid!");
    }
}
