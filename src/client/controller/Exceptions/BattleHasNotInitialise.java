package client.controller.Exceptions;

import java.io.Serializable;

public class BattleHasNotInitialise extends Exception implements Serializable
{
    public BattleHasNotInitialise()
    {
        super("Please Initialise Battle!");
    }
}
