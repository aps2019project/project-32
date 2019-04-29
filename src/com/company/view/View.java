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
}
