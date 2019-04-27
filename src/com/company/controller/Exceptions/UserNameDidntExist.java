package com.company.controller.Exceptions;

public class UserNameDidntExist extends Exception
{
    public UserNameDidntExist()
    {
        super("UserName didnt Exist!");
    }
}
