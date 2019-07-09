package client.controller.Exceptions;

import java.io.Serializable;

public class WeekPassword extends Exception implements Serializable
{
    public WeekPassword()
    {
        super("Password is Weak! Please Choose Stronger Password!");
    }
}
