package com.company.controller.Test;

import com.company.controller.Exceptions.*;
import com.company.controller.Menus.battlemenus.CustomGameMenu;
import org.junit.Assert;
import org.junit.Test;

public class CustomBattleTest
{
    @Test()
    public void Throw_Exception_For_Choosing_FlagBattle_Or_DeadBattle()
    {
        boolean thrown = false;
        try
        {
            CustomGameMenu.getInstance().selectOptionByCommand("Start Game qwert");
        }catch (InvalidBattleName e){
            thrown = true;
        }
        Assert.assertTrue(thrown);
    }
    @Test(expected = InvalidBattleName.class)
    public void  Throw_Exception_For_Choosing_CollectFlagBattle() throws InvalidBattleName
    {
        CustomGameMenu.getInstance().selectOptionByCommand("Start Game qwert 15");
    }

}
