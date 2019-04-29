package com.company.controller.Exceptions;

public class CardNotFoundInShop extends Exception {
    public CardNotFoundInShop() {
        super("Card not found in Shop");
    }
}
