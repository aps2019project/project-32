package client.controller.Exceptions;

import java.io.Serializable;

public class CantMove extends Exception implements Serializable
{
    public CantMove()
    {
        super("Warrior Cant Move!");
    }
}
