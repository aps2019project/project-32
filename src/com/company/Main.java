package com.company;

import com.company.controller.Controller;

import java.util.function.Function;

public class Main
{
    public static void main(String[] args)
    {
        Controller.getInstance().run();

        Function<Integer,Integer> add = a->a*4;

        System.out.println(add.apply(3));


    }


}


