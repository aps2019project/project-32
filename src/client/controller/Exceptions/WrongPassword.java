package client.controller.Exceptions;

import java.io.Serializable;

public class WrongPassword extends Exception implements Serializable
{
    public WrongPassword()
    {
        super("PassWord is Wrong");
    }
}
