package com.company.controller.Test;

import com.company.controller.Controller;
import com.company.controller.Exceptions.CantAddUsableItems;
import com.company.controller.Exceptions.CardNotFound;
import com.company.controller.Exceptions.NotEnoughCash;
import com.company.controller.Menus.ShopMenu;
import com.company.models.Player;
import org.junit.Assert;
import org.junit.Test;


public class ShopMenuTest
{
    @Test
    public void Check_Decrease_Cash_While_Buying() throws NotEnoughCash, CardNotFound, CantAddUsableItems
    {

        Controller.getInstance().setCurrentPlayer(new Player("ali","214"));
        ((ShopMenu)ShopMenu.getInstance()).addCardToShop();
        ((ShopMenu)ShopMenu.getInstance()).buy("Buy TotalDisarm");
        Assert.assertEquals(14000,Controller.getInstance().getCurrentPlayer().getCash());

    }

}
