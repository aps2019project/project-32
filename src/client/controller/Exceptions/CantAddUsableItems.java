package client.controller.Exceptions;

import java.io.Serializable;

public class CantAddUsableItems extends Exception implements Serializable
{
    public CantAddUsableItems()
    {
        super("Cant Add More Than 3 PassiveItem!");
    }
}
