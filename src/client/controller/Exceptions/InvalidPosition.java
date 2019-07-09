package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidPosition extends Exception implements Serializable
{
    public InvalidPosition()
    {
        super("Invalid Position!");
    }
}
