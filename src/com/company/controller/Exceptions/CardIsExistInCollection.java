package com.company.controller.Exceptions;

public class CardIsExistInCollection extends Exception {
    public CardIsExistInCollection(){
        super("Card Is Exist In Your Collection");
    }
}
