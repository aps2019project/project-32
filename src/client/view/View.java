package client.view;

import java.io.Serializable;

public class View implements Serializable
{
    private static View viewInstance = new View();

    private View()
    {
    }

    public static View getInstance()
    {
        return viewInstance;
    }

    public void show(String string)
    {
        System.out.println(string);
    }
}
