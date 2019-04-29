package com.company.models.battle;

import com.company.models.Player;
import com.company.models.widget.items.Flag;

public class KeepFlagBattle extends Battle
{
    Flag flag = new Flag();

    KeepFlagBattle(Player firstPlayer, Player secondPlayer)
    {
        super(firstPlayer, secondPlayer);
        battleMap.map[2][4] = flag;
    }

    public void dropFlagAfterDead()
    {

    }

    @Override
    public void checkBattleResult()
    {

        //use addBattleToBattleHistories(gameResault);
    }

    @Override
    public String toShowGameInfo()
    {
        return null;
    }

    public void dropFlagAfterDeath()
    {


    }
}
