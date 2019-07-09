package client.controller.Exceptions;

import java.io.Serializable;

public class CardNotFound extends Exception implements Serializable
{
    public CardNotFound()
    {
        super("Card Not Found!");
    }
}
