package client.controller.Exceptions;

import java.io.Serializable;

public class UserNameDidntExist extends Exception implements Serializable
{
    public UserNameDidntExist()
    {
        super("UserName didnt Exist!");
    }
}
