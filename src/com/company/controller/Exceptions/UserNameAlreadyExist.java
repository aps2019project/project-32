package com.company.controller.Exceptions;

public class UserNameAlreadyExist extends Exception
{
    public UserNameAlreadyExist()
    {
        super("UserName Has Been Used Already!");
    }
}
