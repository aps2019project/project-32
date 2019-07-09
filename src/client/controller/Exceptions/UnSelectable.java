package client.controller.Exceptions;

import java.io.Serializable;

public class UnSelectable extends Exception implements Serializable
{
    public UnSelectable()
    {
        super("Cant Select!");
    }
}
