package com.company.controller.Exceptions;

public class WeekPassword extends Exception
{
    public WeekPassword()
    {
        super("Password is Weak! Please Choose Stronger Password!");
    }
}
