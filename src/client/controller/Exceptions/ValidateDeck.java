package client.controller.Exceptions;

import java.io.Serializable;

public class ValidateDeck extends Exception implements Serializable
{
    public ValidateDeck()
    {
        super("Deck is Valid!");
    }
}
