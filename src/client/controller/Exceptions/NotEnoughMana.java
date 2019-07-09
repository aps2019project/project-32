package client.controller.Exceptions;

import java.io.Serializable;

public class NotEnoughMana extends Exception implements Serializable
{
    public NotEnoughMana()
    {
        super("Not Enough Mana!");
    }
}
