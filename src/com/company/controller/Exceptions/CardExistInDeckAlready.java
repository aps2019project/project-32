package com.company.controller.Exceptions;

public class CardExistInDeckAlready extends Exception
{
    public CardExistInDeckAlready()
    {
        super("This Card Already Exist In This Deck!");
    }
}
