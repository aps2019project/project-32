package com.company.view;

import com.company.controller.Controller;

import java.util.Scanner;

public class Request
{

    private Request()
    {
    }

    private static Request requestInstance = new Request();

    public static Request getInstance()
    {
        return requestInstance;
    }

    Scanner input = new Scanner(System.in);

    public String getNewCommand()
    {
       return input.nextLine();
    }
}
