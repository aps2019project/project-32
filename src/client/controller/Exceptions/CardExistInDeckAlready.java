package client.controller.Exceptions;

import java.io.Serializable;

public class CardExistInDeckAlready extends Exception implements Serializable
{
    public CardExistInDeckAlready()
    {
        super("This Card Already Exist In This Deck!");
    }
}
