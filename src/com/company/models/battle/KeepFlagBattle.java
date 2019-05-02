package com.company.models.battle;

import com.company.models.Player;

public class KeepFlagBattle extends Battle
{
    //Flag flag = new Flag();

    public KeepFlagBattle(Player firstPlayer, Player secondPlayer)
    {
        super(firstPlayer, secondPlayer);
        //battleMap.warriorsOnMap[2][4] = flag;
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
}
