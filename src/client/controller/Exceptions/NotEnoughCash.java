package client.controller.Exceptions;

import java.io.Serializable;

public class NotEnoughCash extends Exception implements Serializable
{
    public NotEnoughCash()
    {
        super("You Dont Have Enough Cash!");
    }
}
