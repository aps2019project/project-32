package com.company.controller.Exceptions;

public class CardNotFoundInCollection extends Exception
{
    public CardNotFoundInCollection()
    {
        super("This Card Or Item not Found In Collection!");
    }
}
