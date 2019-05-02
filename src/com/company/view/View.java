package com.company.view;

public class View
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
