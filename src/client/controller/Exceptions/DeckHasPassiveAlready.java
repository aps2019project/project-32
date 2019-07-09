package client.controller.Exceptions;

import java.io.Serializable;

public class DeckHasPassiveAlready extends Exception implements Serializable
{
    public DeckHasPassiveAlready()
    {
        super("Deck has Passive Spell Already!");
    }
}
