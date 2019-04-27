package com.company.controller.Exceptions;

public class WrongPassword extends Exception
{
    public WrongPassword()
    {
        super("PassWord is InCorrect!");
    }
}
