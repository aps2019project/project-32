package com.company.controller.Exceptions;

public class NotEnoughCash extends Exception
{
    public NotEnoughCash()
    {
        super("You Dont Have Enough Cash!");
    }
}
