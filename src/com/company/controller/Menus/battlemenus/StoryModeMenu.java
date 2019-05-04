package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Menus.AbstractMenu;
import com.company.models.AIPlayer;
import com.company.models.battle.CollectFlagBattle;
import com.company.models.battle.DeadBattle;
import com.company.models.battle.KeepFlagBattle;
import com.company.view.View;

public class StoryModeMenu implements AbstractMenu
{
    private static StoryModeMenu ourInstance = new StoryModeMenu();

    public static StoryModeMenu getInstance()
    {
        return ourInstance;
    }

    private StoryModeMenu()
    {
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("Step 1") || command.matches("Step 2") || command.matches("Step 3"))
            startStoryModeGame(Integer.parseInt(command.split(" ")[1]));

        else if (command.matches("Help"))
            View.getInstance().show(toShowMenu());

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());
    }

    @Override
    public String toShowMenu()
    {
        return "1.Step <1>(Dead Battle)\n2.Step <2>(Collect Flag Battle)\n3.Step <3>(Keep Flag Battle)\n4.Help\n5.Exit";
    }

    public void startStoryModeGame(int stepNumber)
    {
        switch (stepNumber)
        {
            case 1:
                BattleMenu.getInstance().setCurrentBattle(new DeadBattle
                        (Controller.getInstance().getCurrentPlayer(), AIPlayer.getAIPlayer(), 500));
            case 2:
                BattleMenu.getInstance().setCurrentBattle(new CollectFlagBattle
                        (Controller.getInstance().getCurrentPlayer(), AIPlayer.getAIPlayer(), 1000, 7));
            case 3:
                BattleMenu.getInstance().setCurrentBattle(new KeepFlagBattle
                        (Controller.getInstance().getCurrentPlayer(), AIPlayer.getAIPlayer(), 1500));
        }
    }
}
