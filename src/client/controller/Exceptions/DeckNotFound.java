package client.controller.Exceptions;

import java.io.Serializable;

public class DeckNotFound extends Exception implements Serializable
{
    public DeckNotFound()
    {
        super("Deck not Found In This Player Decks!");
    }
}
