package com.company.controller.Menus;

import com.company.Position;
import com.company.models.battle.Battle;

public class BattleMenu implements AbstractMenu
{
    public enum Commands
    {

    }

    private Battle battle;

    private BattleMenu()
    {
    }

    private static BattleMenu battleMenuInstance = new BattleMenu();

    public static BattleMenu getInstance()
    {
        return battleMenuInstance;
    }

    public void setBattle(Battle battle)
    {
        this.battle = battle;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if(command.matches("game info")){

        }else if(command.matches("show my minions")){

        }else if(command.matches("show opponent minions")){

        }else if(command.matches("show card info \\d+")){

        }else if(command.matches("select \\d+")){

            // barrasi shavad item ast ya warrior
        }



    }



    @Override
    public String help()
    {
        return null;
    }

    @Override
    public String toShowMenu()
    {
        return null;
    }


    public void toShowGameInfo()
    {

    }

    public void toShowMinion()
    {

    }

    public void toShowHand()
    {

    }

    public void toShowNextCard()
    {

    }

    public void toShowEndGameInfo()
    {

    }

    public void selectUnit(int cardID)
    {

    }

    public void moveTo(Position destinationPosition)
    {

    }


}
