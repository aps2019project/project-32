package com.company;

import com.company.controller.Controller;
import com.company.models.Player;
import com.company.models.battle.Battle;
import com.google.gson.Gson;

public class Main
{
    public static void main(String[] args)
    {
        Controller.getInstance().run();
    }
}


