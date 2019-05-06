package com.company.controller.Exceptions;

public class InvalidBattleName extends Exception

{
    public InvalidBattleName(){
        super("Invalid Battle Name");
    }
}
