package client.controller.Exceptions;

import java.io.Serializable;

public class DeckHasHeroAlready extends Exception implements Serializable
{
    public DeckHasHeroAlready()
    {
        super("This Deck has Hero Already!");
    }
}
