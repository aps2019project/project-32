package com.company;

import com.company.controller.Controller;
import com.company.models.Player;
import com.company.models.battle.Battle;
import com.company.models.widget.cards.Spell;
import com.google.gson.Gson;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //Controller.getInstance().run();

        Spell spell = new Spell(3,3) {
            @Override
            public void doEffect(Position... positions) {
                System.out.println("salam");
            }
        };
        Gson gson = new Gson();
        String playerStr = gson.toJson(spell);
        gson.
        OutputStream outputStream = new FileOutputStream("C:\\Users\\Tarahan\\Desktop\\project32\\project-32\\Classes.txt");
        outputStream.write(playerStr.getBytes());
    }
}


