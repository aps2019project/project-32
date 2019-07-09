package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidDeck extends Exception  implements Serializable
{
    public InvalidDeck()
    {
        super("This Deck is Invalid!");
    }
}
