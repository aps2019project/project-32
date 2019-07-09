package client.controller.Exceptions;

import java.io.Serializable;

public class CoolDownRemaining extends Exception  implements Serializable
{
    public CoolDownRemaining()
    {
        super("Spell is In CoolDown!");
    }
}
