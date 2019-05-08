package com.company.controller.Menus.battlemenus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.controller.Menus.AbstractMenu;
import com.company.models.AIPlayer;
import com.company.models.battle.CollectFlagBattle;
import com.company.models.battle.DeadBattle;
import com.company.models.battle.KeepFlagBattle;

public class CustomGameMenu implements AbstractMenu
{
    private static CustomGameMenu ourInstance = new CustomGameMenu();

    public static CustomGameMenu getInstance()
    {
        return ourInstance;
    }

    private CustomGameMenu()
    {
    }

    @Override
    public void selectOptionByCommand(String command) throws InvalidBattleName, InvalidDeck, CloneNotSupportedException
    {

        if (command.matches("Start Game \\w \\d+")){
            setCollectFlagBattle(command);
        }
        if (command.matches("Start Game \\w")){
            setBattle(command);
        }


    }

    public void setCollectFlagBattle(String command) throws InvalidBattleName, InvalidDeck, CloneNotSupportedException
    {
        String battleName = command.split(" ")[2];
        int numberOfFlag = Integer.parseInt(command.split(" ")[3]);
        if (battleName.equals("CollectFlag")){
            BattleMenu.getInstance().setCurrentBattle(new CollectFlagBattle
                    (Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1000, numberOfFlag));
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
        }
        else
            throw new InvalidBattleName();
    }
    public void setBattle(String command) throws InvalidBattleName, InvalidDeck, CloneNotSupportedException
    {
        String battleName = command.split(" ")[2];
        if (battleName.equals("DeadBattle")){
            BattleMenu.getInstance().setCurrentBattle(new DeadBattle
                    (Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 500));
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
        }
        else if (battleName.equals("KeepFlag")){
            BattleMenu.getInstance().setCurrentBattle(new KeepFlagBattle
                    (Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1500));
            Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
        }
        else
            throw new InvalidBattleName();
    }

    @Override
    public String toShowMenu()
    {
        return "StartGame\n";
    }
}
