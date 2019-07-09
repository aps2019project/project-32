package client.controller.Exceptions;

import java.io.Serializable;

public class UserNameAlreadyExist extends Exception  implements Serializable
{
    public UserNameAlreadyExist()
    {
        super("UserName Has Been Used Already!");
    }
}
