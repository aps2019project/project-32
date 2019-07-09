package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidAttack extends Exception implements Serializable
{
    public InvalidAttack()
    {
        super("Invalid Attack!");
    }
}
