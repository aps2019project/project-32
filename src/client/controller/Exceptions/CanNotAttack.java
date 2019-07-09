package client.controller.Exceptions;

import java.io.Serializable;

public class CanNotAttack extends Exception implements Serializable
{
    public CanNotAttack()
    {
        super("Warrior Cant Attack!");
    }
}
