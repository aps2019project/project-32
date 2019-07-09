package client.controller.Exceptions;

import java.io.Serializable;

public class DeckNameAlreadyExist extends Exception implements Serializable
{
    public DeckNameAlreadyExist()
    {
        super("DeckName Already Existed!");
    }
}
