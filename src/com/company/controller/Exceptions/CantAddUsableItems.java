package com.company.controller.Exceptions;

public class CantAddUsableItems extends Exception
{
    public CantAddUsableItems()
    {
        super("Cant Add More Than 3 Usable Item!");
    }
}
