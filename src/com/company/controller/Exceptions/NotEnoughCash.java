package com.company.controller.Exceptions;

public class NotEnoughCash extends Exception {
    public NotEnoughCash() {
        super("Not enough cash");
    }
}
