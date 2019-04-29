package com.company;

import com.company.controller.Controller;
import com.company.models.Player;
import com.company.models.battle.Battle;
import com.google.gson.Gson;

import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Controller.getInstance().run();
    }
}


