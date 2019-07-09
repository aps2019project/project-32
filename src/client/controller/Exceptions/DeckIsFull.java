package client.controller.Exceptions;

import java.io.Serializable;

public class DeckIsFull extends Exception implements Serializable
{
    public DeckIsFull()
    {
        super("Deck is Full!");
    }
}
