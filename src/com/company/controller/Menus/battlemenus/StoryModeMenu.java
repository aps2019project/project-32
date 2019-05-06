package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.InvalidDeck;
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
    public void selectOptionByCommand(String command) throws CloneNotSupportedException
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

    public void startStoryModeGame(int stepNumber) throws CloneNotSupportedException, InvalidDeck
    {
        switch (stepNumber)
        {
            case 1:
                AIPlayer.getInstance().setAI();
                DeadBattle.setBattle(Controller.getInstance().getCurrentPlayer(),
                        AIPlayer.getInstance().getAIPlayer(),
                        500);

                BattleMenu.getInstance().setCurrentBattle(DeadBattle.getInstance());
                Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
                break;
            case 2:
                AIPlayer.getInstance().setAI();
                CollectFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(),
                        AIPlayer.getInstance().getAIPlayer(),
                        1000, 7);

                BattleMenu.getInstance().setCurrentBattle(CollectFlagBattle.getInstance());
                Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
                break;
            case 3:
                AIPlayer.getInstance().setAI();
                KeepFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(),
                        AIPlayer.getInstance().getAIPlayer(),
                        1500 );

                BattleMenu.getInstance().setCurrentBattle(KeepFlagBattle.getInstance());
                Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
                break;
        }
    }
}

