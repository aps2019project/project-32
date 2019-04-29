package com.company;

import com.company.controller.Controller;
import com.company.models.Player;
import com.company.models.battle.Battle;
import com.company.models.widget.cards.Spell;
import com.google.gson.Gson;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Controller.getInstance().run();
    }
}


